package com.example.kpass.repository;

import com.example.kpass.entity.Member;
import com.example.kpass.entity.Post;
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
class PostJdbcRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private PostJdbcRepository postJdbcRepository;
    private MemberJdbcRepository memberJdbcRepository;
    private CommentJdbcRepository commentJdbcRepository;
    private Post testPost;
    private Member testMember;

    @BeforeAll
    void setUp() {
        commentJdbcRepository = new CommentJdbcRepository(jdbcTemplate);
        memberJdbcRepository = new MemberJdbcRepository(jdbcTemplate, postJdbcRepository);
        postJdbcRepository = new PostJdbcRepository(jdbcTemplate, commentJdbcRepository);
        UUID memberUUID = UUID.randomUUID();

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
    }

    @Test
    @Order(1)
    @DisplayName("멤버 추가 가능")
    void insertMember() {
        Member insertedMember = memberJdbcRepository.insertMember(testMember);
        assertThat(insertedMember, is(notNullValue()));
    }

    @Test
    @Order(2)
    @DisplayName("포스트 추가 가능")
    void insertPost() {
        Post insertedPost = postJdbcRepository.insertPost(testPost);
        assertThat(insertedPost, is(notNullValue()));
    }

    @Test
    @Order(3)
    @DisplayName("포스트 수정 가능")
    void updatePost() {
        testPost.setContent("Updated Content");
        Post updatedPost = postJdbcRepository.updatePost(testPost);
        assertThat(updatedPost.getContent(), is("Updated Content"));
    }

    @Test
    @Order(4)
    @DisplayName("포스트 삭제 가능")
    void deletePost() {
        postJdbcRepository.deletePost(testPost);
        var result = postJdbcRepository.findAllPosts();
        assertThat(result, is(empty()));
    }

    @Test
    @Order(5)
    @DisplayName("전체 포스트 조회 가능")
    void findAllPosts() {
        postJdbcRepository.insertPost(testPost);
        var posts = postJdbcRepository.findAllPosts();
        assertThat(posts, is(not(empty())));
    }

    @Test
    @Order(6)
    @DisplayName("멤버 ID로 포스트 조회 가능")
    void findByMemberId() {
        var posts = postJdbcRepository.findByMemberId(testPost.getMemberId());
        assertThat(posts, is(not(empty())));
    }

    @Test
    @Order(7)
    @DisplayName("전체 포스트 삭제 가능")
    void deleteAllPosts() {
        postJdbcRepository.deleteAllPosts();
        var result = postJdbcRepository.findAllPosts();
        assertThat(result, is(empty()));
    }
}
