package com.example.kpass.repository;

import com.example.kpass.entity.Comment;
import com.example.kpass.entity.Post;
import com.example.kpass.entity.Member;
import com.example.kpass.entity.Region;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommentJdbcRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private CommentJdbcRepository commentJdbcRepository;
    private PostJdbcRepository postJdbcRepository;
    private MemberJdbcRepository memberJdbcRepository;
    private Comment testComment;
    private Post testPost;
    private Member testMember;

    @BeforeAll
    void setUp() {
        commentJdbcRepository = new CommentJdbcRepository(jdbcTemplate);
        postJdbcRepository = new PostJdbcRepository(jdbcTemplate, commentJdbcRepository);
        memberJdbcRepository = new MemberJdbcRepository(jdbcTemplate, postJdbcRepository);

        testMember = new Member(
                1L,
                UUID.randomUUID(),
                "Test Member",
                "testmember@example.com",
                List.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        testPost = new Post(
                1L,
                UUID.randomUUID(),
                testMember.getMemberId(),
                "Test Title",
                "Test Content",
                List.of(),
                0,
                Region.경기도,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        testComment = new Comment(
                1L,
                UUID.randomUUID(),
                testPost.getPostId(),
                "Test Comment",
                0,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        memberJdbcRepository.insertMember(testMember);
        postJdbcRepository.insertPost(testPost);
    }

    @Test
    @Order(1)
    @DisplayName("댓글 추가 가능")
    void insertComment() {
        Comment insertedComment = commentJdbcRepository.insertComment(testComment);
        assertThat(insertedComment, is(notNullValue()));
    }

    @Test
    @Order(2)
    @DisplayName("댓글 수정 가능")
    void updateComment() {
        testComment.setContent("Updated Comment");
        Comment updatedComment = commentJdbcRepository.updateComment(testComment);
        assertThat(updatedComment.getContent(), is("Updated Comment"));
    }

    @Test
    @Order(3)
    @DisplayName("댓글 삭제 가능")
    void deleteComment() {
        commentJdbcRepository.deleteComment(testComment);
        var result = commentJdbcRepository.findAllComments();
        assertThat(result, is(empty()));
    }

    @Test
    @Order(4)
    @DisplayName("전체 댓글 조회 가능")
    void findAllComments() {
        commentJdbcRepository.insertComment(testComment);
        var comments = commentJdbcRepository.findAllComments();
        assertThat(comments, is(not(empty())));
    }

    @Test
    @Order(5)
    @DisplayName("포스트 ID로 댓글 조회 가능")
    void findByPostId() {
        var comments = commentJdbcRepository.findByPostId(testPost.getPostId());
        assertThat(comments, is(not(empty())));
    }

    @Test
    @Order(6)
    @DisplayName("전체 댓글 삭제 가능")
    void deleteAllComments() {
        commentJdbcRepository.deleteAllComments();
        var result = commentJdbcRepository.findAllComments();
        assertThat(result, is(empty()));
    }
}
