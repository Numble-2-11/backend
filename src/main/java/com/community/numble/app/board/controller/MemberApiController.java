package com.community.numble.app.board.controller;

import com.community.numble.app.board.domain.Member;
import com.community.numble.app.board.dto.MemberDto.CreateMemberRequest;
import com.community.numble.app.board.dto.MemberDto.CreateMemberResponse;
import com.community.numble.app.board.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Tag(name = "MemberTest", description = "사용자 관련 api")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberApiController {


	private final MemberService memberService;

	@PostMapping("/membertest")
	@Operation(summary = "회원가입 테스트 메서드", description = "회원가입 테스트 메서드입니다.")
	public ResponseEntity saveMember(@RequestBody CreateMemberRequest request) {
		Member member = new Member();
		member.setName(request.getName());

		Long id = memberService.join(member);
		return ResponseEntity.status(HttpStatus.OK).body(new CreateMemberResponse(id));
	}

	@PostMapping("/members2")
	public CreateMemberResponse saveMemberv2(@RequestBody CreateMemberRequest request) {
		Member member = new Member();
		member.setName(request.getName());

		Long id = memberService.join(member);
		return new CreateMemberResponse(id);
	}
}
