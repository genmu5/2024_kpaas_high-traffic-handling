package com.example.kpass.repository;

import com.example.kpass.entity.Post;
import com.example.kpass.entity.Region;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

import static com.example.kpass.utils.JdbcUtils.toLocalDateTime;
import static com.example.kpass.utils.JdbcUtils.toUUID;

@Repository
public class PostJdbcRepository implements PostRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CommentJdbcRepository commentJdbcRepository;

    public PostJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, CommentJdbcRepository commentJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.commentJdbcRepository = commentJdbcRepository;
    }

    @Override
    public Post insertPost(Post post) {
        var insert = jdbcTemplate.update(
                "INSERT INTO posts (post_UUID, member_id, title, content, like_count, region, created_at, updated_at) " +
                        "VALUES (UUID_TO_BIN(:postUUID), :memberId, :title, :content, :likeCount, :region, :createdAt, :updatedAt)",
                toParamMap(post)
        );
        if(insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return post;
    }

    @Override
    public Post updatePost(Post post) {
        var update = jdbcTemplate.update(
                "UPDATE posts SET title = :title, content = :content, like_count = :likeCount, region = :region, updated_at = :updatedAt " +
                        "WHERE post_id = :postId",
                toParamMap(post)
        );
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return post;
    }

    @Override
    public void deletePost(Post post) {
        var delete = jdbcTemplate.update(
                "DELETE FROM posts WHERE post_id = :postId",
                Map.of("postId", post.getPostId())
        );
        if (delete != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    @Override
    public List<Post> findAllPosts() {
        return jdbcTemplate.query("SELECT * FROM posts", postRowMapper);
    }

    @Override
    public void deleteAllPosts() {
        jdbcTemplate.update("DELETE FROM posts", Collections.emptyMap());
    }

    @Override
    public List<Post> findByMemberId(Long memberId) {
        return jdbcTemplate.query(
                "SELECT * FROM posts WHERE member_id = :memberId",
                Map.of("memberId", memberId),
                postRowMapper
        );
    }

    @Override
    public Optional<Post> findPostByUUID(UUID postUUID) {
        List<Post> posts = jdbcTemplate.query(
                "SELECT * FROM posts WHERE post_UUID = UUID_TO_BIN(:postUUID)",
                Map.of("postUUID", postUUID.toString().replace("-", "")),
                postRowMapper
        );
        return posts.stream().findFirst();
    }

    private Map<String, Object> toParamMap(Post post) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("postId", post.getPostId());
        paramMap.put("postUUID", post.getPostUUID().toString().replaceAll("-", ""));
        paramMap.put("memberId", post.getMemberId());
        paramMap.put("title", post.getTitle());
        paramMap.put("content", post.getContent());
        paramMap.put("likeCount", post.getLikeCount());
        paramMap.put("region", post.getRegion().toString());
        paramMap.put("createdAt", Timestamp.valueOf(post.getCreatedAt()));
        paramMap.put("updatedAt", Timestamp.valueOf(post.getUpdatedAt()));
        return paramMap;
    }

    private static final RowMapper<Post> postRowMapper = (resultSet, i) -> {
        var postId = resultSet.getLong("post_id");
        var postUUID = toUUID(resultSet.getBytes("post_UUID"));
        var memberId = resultSet.getLong("member_id");
        var title = resultSet.getString("title");
        var content = resultSet.getString("content");
        var likeCount = resultSet.getInt("like_count");
        var region = Region.valueOf(resultSet.getString("region"));
        var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        var updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Post(postId, postUUID, memberId, title, content, new ArrayList<>(), likeCount, region, createdAt, updatedAt);
    };
}
