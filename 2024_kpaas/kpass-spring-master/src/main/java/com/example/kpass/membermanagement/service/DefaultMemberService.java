package com.example.kpass.service;

import com.example.kpass.entity.Member;
import com.example.kpass.entity.dto.MemberRequestDto;
import com.example.kpass.entity.dto.MemberResponseDto;
import com.example.kpass.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultMemberService implements MemberService {

    private final MemberRepository memberRepository;

    public DefaultMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberResponseDto insertMember(MemberRequestDto memberRequestDto) {
        if (memberRepository.findMemberByEmail(memberRequestDto.getMemberEmail()).isPresent()) {
            return null;
        }
        Member member = new Member(
                0L, // 임시 ID 값 설정
                UUID.randomUUID(),
                memberRequestDto.getMemberName(),
                memberRequestDto.getMemberEmail(),
                new ArrayList<>(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        memberRepository.insertMember(member);
        return MemberResponseDto.fromEntity(member);
    }

    @Override
    public MemberResponseDto updateMember(UUID memberUUID, MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findMemberByUUID(memberUUID).orElse(null);
        if (member == null) {
            throw new RuntimeException("member not found");
        }
        Member updatedMember = new Member(
                member.getMemberId(),
                member.getMemberUUID(),
                memberRequestDto.getMemberName(),
                member.getMemberEmail(),
                member.getPosts(),
                member.getCreatedAt(),
                LocalDateTime.now()
        );
        memberRepository.updateMember(updatedMember);
        return MemberResponseDto.fromEntity(updatedMember);
    }

    @Override
    public void deleteMember(String memberUUID) {
        UUID uuid = UUID.fromString(memberUUID);
        Member member = memberRepository.findMemberByUUID(uuid).orElse(null);
        if (member == null) {
            throw new RuntimeException("member not found");
        }
        memberRepository.deleteMember(member);
    }

    @Override
    public List<MemberResponseDto> findAllMembers() {
        return memberRepository.findAllMembers().stream()
                .map(MemberResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public MemberResponseDto findMemberByUUID(String memberUUID) {
        UUID uuid = UUID.fromString(memberUUID);
        Member member = memberRepository.findMemberByUUID(uuid).orElse(null);
        return member != null ? MemberResponseDto.fromEntity(member) : null;
    }
}
