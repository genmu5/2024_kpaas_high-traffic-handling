package com.example.kpass.repository;

import com.example.kpass.entity.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {
    Member insertMember(Member member);
    Member updateMember(Member member);
    void deleteMember(Member member);
    List<Member> findAllMembers();
    void deleteAllMembers();
    Optional<Member> findMemberById(Long memberId);
    Optional<Member> findMemberByUUID(UUID memberUUID);
    Optional<Member> findMemberByEmail(String email);
}
