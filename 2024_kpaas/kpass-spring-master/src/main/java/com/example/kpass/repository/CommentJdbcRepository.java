package com.example.kpass.repository;

import com.example.kpass.entity.Comment;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

import static com.example.kpass.utils.JdbcUtils.toLocalDateTime;
import static com.example.kpass.utils.JdbcUtils.toUUID;

@Repository
public class CommentJdbcRepository implements CommentRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CommentJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Comment insertComment(Comment comment) {
        var insert = jdbcTemplate.update(
                "INSERT INTO comments (comment_UUID, post_id, content, like_count, created_at, updated_at) " +
                        "VALUES (UUID_TO_BIN(:commentUUID), :postId, :content, :likeCount, :createdAt, :updatedAt)",
                toParamMap(comment)
        );
        if (insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return comment;
    }

    @Override
    public Comment updateComment(Comment comment) {
        var update = jdbcTemplate.update(
                "UPDATE comments SET content = :content, like_count = :likeCount, updated_at = :updatedAt " +
                        "WHERE comment_id = :commentId",
                toParamMap(comment)
        );
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return comment;
    }

    @Override
    public void deleteComment(Comment comment) {
        var delete = jdbcTemplate.update(
                "DELETE FROM comments WHERE comment_id = :commentId",
                Map.of("commentId", comment.getCommentId())
        );
        if (delete != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    @Override
    public List<Comment> findAllComments() {
        return jdbcTemplate.query("SELECT * FROM comments", commentRowMapper);
    }

    @Override
    public void deleteAllComments() {
        jdbcTemplate.update("DELETE FROM comments", Collections.emptyMap());
    }

    @Override
    public List<Comment> findByPostId(Long postId) {
        return jdbcTemplate.query(
                "SELECT * FROM comments WHERE post_id = :postId",
                Map.of("postId", postId),
                commentRowMapper
        );
    }

    @Override
    public Optional<Comment> findByCommentUUID(UUID commentUUID) {
        List<Comment> comments = jdbcTemplate.query(
                "SELECT * FROM comments WHERE comment_UUID = UUID_TO_BIN(:commentUUID)",
                Map.of("commentUUID", commentUUID.toString().replaceAll("-","")),
                commentRowMapper
        );
        return comments.stream().findFirst();
    }

    private Map<String, Object> toParamMap(Comment comment) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("commentId", comment.getCommentId());
        paramMap.put("commentUUID", comment.getCommentUUID().toString().replaceAll("-",""));
        paramMap.put("postId", comment.getPostId());
        paramMap.put("content", comment.getContent());
        paramMap.put("likeCount", comment.getLikeCount());
        paramMap.put("createdAt", Timestamp.valueOf(comment.getCreatedAt()));
        paramMap.put("updatedAt", Timestamp.valueOf(comment.getUpdatedAt()));
        return paramMap;
    }

    private static final RowMapper<Comment> commentRowMapper = (resultSet, i) -> {
        var commentId = resultSet.getLong("comment_id");
        var commentUUID = toUUID(resultSet.getBytes("comment_UUID"));
        var postId = resultSet.getLong("post_id");
        var content = resultSet.getString("content");
        var likeCount = resultSet.getInt("like_count");
        var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        var updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Comment(commentId, commentUUID, postId, content, likeCount, createdAt, updatedAt);
    };
}
