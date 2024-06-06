package com.example.kpass.repository;

import com.example.kpass.entity.Member;
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
class MemberJdbcRepositoryTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    private MemberJdbcRepository memberJdbcRepository;
    private PostJdbcRepository postJdbcRepository;
    private CommentJdbcRepository commentJdbcRepository;
    private Member testMember;

    @BeforeAll
    void setUp() {
        commentJdbcRepository = new CommentJdbcRepository(jdbcTemplate);
        postJdbcRepository = new PostJdbcRepository(jdbcTemplate, commentJdbcRepository);
        memberJdbcRepository = new MemberJdbcRepository(jdbcTemplate, postJdbcRepository);
        testMember = new Member(
                1,
                UUID.randomUUID(),
                "Test Member",
                "testmember@example.com",
                List.of(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    @Order(1)
    @DisplayName("회원 추가 가능")
    void insertMember() {
        Member insertedMember = memberJdbcRepository.insertMember(testMember);
        assertThat(insertedMember, is(notNullValue()));
    }

    @Test
    @Order(2)
    @DisplayName("회원 수정 가능")
    void updateMember() {
        Member updatedMember = new Member(
                testMember.getMemberId(),
                testMember.getMemberUUID(),
                "Updated Member",
                testMember.getMemberEmail(),
                List.of(),
                testMember.getCreatedAt(),
                LocalDateTime.now()
        );
        updatedMember = memberJdbcRepository.updateMember(updatedMember);
        assertThat(updatedMember.getMemberName(), is("Updated Member"));
    }

    @Test
    @Order(3)
    @DisplayName("회원 삭제 가능")
    void deleteMember() {
        memberJdbcRepository.deleteMember(testMember);
        var result = memberJdbcRepository.findAllMembers();
        assertThat(result, is(empty()));
    }

    @Test
    @Order(4)
    @DisplayName("전체 회원 조회 가능")
    void findAllMembers() {
        memberJdbcRepository.insertMember(testMember);
        var members = memberJdbcRepository.findAllMembers();
        assertThat(members, is(not(empty())));
    }

    @Test
    @Order(5)
    @DisplayName("이메일로 회원 조회 가능")
    void findMemberByEmail() {
        var member = memberJdbcRepository.findMemberByUUID(testMember.getMemberUUID());
        assertThat(member.isPresent(), is(true));
        assertThat(member.get().getMemberEmail(), is(testMember.getMemberEmail()));
    }

//    @Test
//    @Order(6)
//    @DisplayName("전체 회원 삭제 가능")
//    void deleteAllMembers() {
//        memberJdbcRepository.deleteAllMembers();
//        var result = memberJdbcRepository.findAllMembers();
//        assertThat(result, is(empty()));
//    }
}
