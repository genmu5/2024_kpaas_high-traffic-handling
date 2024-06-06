package com.example.kpass.repository;

import com.example.kpass.entity.Member;
import com.example.kpass.entity.Post;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

import static com.example.kpass.utils.JdbcUtils.toLocalDateTime;
import static com.example.kpass.utils.JdbcUtils.toUUID;

@Repository
public class MemberJdbcRepository implements MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final PostJdbcRepository postJdbcRepository;

    public MemberJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate, PostJdbcRepository postJdbcRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.postJdbcRepository = postJdbcRepository;
    }

    @Override
    public Member insertMember(Member member) {
        var insert = jdbcTemplate.update(
                "INSERT INTO members (member_UUID, member_name, member_email, created_at, updated_at) " +
                        "VALUES (UUID_TO_BIN(:memberUUID), :memberName, :memberEmail, :createdAt, :updatedAt)",
                toParamMap(member)
        );
        if (insert != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return member;
    }

    @Override
    public Member updateMember(Member member) {
        var update = jdbcTemplate.update(
                "UPDATE members SET member_name = :memberName, member_email = :memberEmail, created_at = :createdAt, updated_at = :updatedAt " +
                        "WHERE member_id = :memberId",
                toParamMap(member)
        );
        if (update != 1) {
            return null;
        }
        return member;
    }

    @Override
    public void deleteMember(Member member) {
        var delete = jdbcTemplate.update(
                "DELETE FROM members WHERE member_id = :memberId",
                Map.of("memberId", member.getMemberId())
        );
        if (delete != 1) {
            throw new RuntimeException("Nothing was deleted");
        }
    }

    @Override
    public List<Member> findAllMembers() {
        var members = jdbcTemplate.query("SELECT * FROM members", memberRowMapper);
        members.forEach(member -> {
            List<Post> posts = postJdbcRepository.findByMemberId(member.getMemberId());
            member.getPosts().addAll(posts);
        });
        return members;
    }

    @Override
    public void deleteAllMembers() {
        jdbcTemplate.update("DELETE FROM members", Collections.emptyMap());
    }

    @Override
    public Optional<Member> findMemberById(Long memberId) { // memberId는 Long 타입으로 변경
        return jdbcTemplate.query(
                "SELECT * FROM members WHERE member_id = :memberId",
                Collections.singletonMap("memberId", memberId),
                memberRowMapper
        ).stream().findFirst();
    }

    @Override
    public Optional<Member> findMemberByUUID(UUID memberUUID) {
        var member = jdbcTemplate.query(
                "SELECT * FROM members WHERE member_UUID = UUID_TO_BIN(:memberUUID)",
                Map.of("memberUUID", memberUUID.toString().replaceAll("-", "")),
                memberRowMapper
        ).stream().findFirst();

        member.ifPresent(m -> {
            List<Post> posts = postJdbcRepository.findByMemberId(m.getMemberId());
            m.getPosts().addAll(posts);
        });

        return member;
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        var member = jdbcTemplate.query(
                "SELECT * FROM members WHERE member_email = :memberEmail",
                Map.of("memberEmail", email),
                memberRowMapper
        ).stream().findFirst();

        member.ifPresent(m -> {
            List<Post> posts = postJdbcRepository.findByMemberId(m.getMemberId());
            m.getPosts().addAll(posts);
        });

        return member;
    }

    private Map<String, Object> toParamMap(Member member) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("memberId", member.getMemberId()); // memberId 추가
        paramMap.put("memberUUID", member.getMemberUUID().toString().replaceAll("-", ""));
        paramMap.put("memberName", member.getMemberName());
        paramMap.put("memberEmail", member.getMemberEmail());
        paramMap.put("createdAt", Timestamp.valueOf(member.getCreatedAt()));
        paramMap.put("updatedAt", Timestamp.valueOf(member.getUpdatedAt()));
        return paramMap;
    }

    private static final RowMapper<Member> memberRowMapper = (resultSet, i) -> {
        var memberId = resultSet.getLong("member_id"); // memberId 추가
        var memberUUID = toUUID(resultSet.getBytes("member_UUID"));
        var memberName = resultSet.getString("member_name");
        var memberEmail = resultSet.getString("member_email");
        var createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        var updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));
        return new Member(memberId, memberUUID, memberName, memberEmail, new ArrayList<>(), createdAt, updatedAt);
    };
}
