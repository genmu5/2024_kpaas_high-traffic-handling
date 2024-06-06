package com.example.kpass.controller;

import com.example.kpass.entity.dto.MemberRequestDto;
import com.example.kpass.entity.dto.MemberResponseDto;
import com.example.kpass.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/members")
public class MemberRestController {
    private final MemberService memberService;

    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    //member create
    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(memberService.insertMember(memberRequestDto));
    }

    //name을 update
    @PutMapping("/update/{memberUUID}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable String memberUUID, @RequestBody MemberRequestDto memberRequestDto) {
        UUID uuid = UUID.fromString(memberUUID);
        return ResponseEntity.ok(memberService.updateMember(uuid, memberRequestDto));
    }

    //detele member
    @DeleteMapping("/{memberUUID}")
    public ResponseEntity<Void> deleteMember(@PathVariable String memberUUID) {
        memberService.deleteMember(memberUUID);
        return ResponseEntity.noContent().build();
    }

    //return all members
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getAllMembers() {
        return ResponseEntity.ok(memberService.findAllMembers());
    }

    //return member by memberUUID
    @GetMapping("/{memberUUID}")
    public ResponseEntity<MemberResponseDto> getMemberByUUID(@PathVariable String memberUUID) {
        return ResponseEntity.ok(memberService.findMemberByUUID(memberUUID));
    }

}
