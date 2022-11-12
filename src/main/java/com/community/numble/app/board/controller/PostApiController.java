package com.community.numble.app.board.controller;

import com.community.numble.app.board.domain.Post;
import com.community.numble.app.board.dto.PostDto.CreatePostRequest;
import com.community.numble.app.board.dto.PostDto.CreatePostResponse;
import com.community.numble.app.board.dto.PostDto.PostAllDto;
import com.community.numble.app.board.dto.PostDto.PostResult;
import com.community.numble.app.board.repository.PostRepository;
import com.community.numble.app.board.service.MemberService;
import com.community.numble.app.board.service.PostService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class PostApiController {

	private final PostService postService;
	private final MemberService memberService;


	@PostMapping("/numble11/post")
	public ResponseEntity savePost(@RequestBody CreatePostRequest request) {
		Post post = new Post();
		post.setTitle(request.getTitle());
		post.setContent(request.getContent());
		post.setLocation(request.getLocation());
		post.createType(request.getType());
		post.setMember(memberService.findOne(request.getMember()));
		Long id = postService.post(post);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CreatePostResponse(id));
	}

	@GetMapping("/numble11/post")
	public ResponseEntity getAllPost(){
		List<Post> findPosts = postService.findPost();
		List<PostAllDto> collect = findPosts.stream()
			.map(m ->new PostAllDto(m.getId(),m.getTitle(),m.getContent(),m.getLocation(),m.getType(),m.getMember().getId()))
			.collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new PostResult(collect.size(),collect));
	}

}
