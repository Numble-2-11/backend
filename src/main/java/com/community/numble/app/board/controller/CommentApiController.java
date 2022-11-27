package com.community.numble.app.board.controller;


import com.community.numble.app.board.domain.Comment;
import com.community.numble.app.board.dto.CommentDto.CommentListDto;

import com.community.numble.app.board.dto.CommentDto.CommentRequest;
import com.community.numble.app.board.dto.CommentDto.CommentResponse;
import com.community.numble.app.board.dto.CommentDto.CommentResult;
import com.community.numble.app.board.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Tag(name = "Comment", description = "댓글 api")
@RequestMapping("/api/v1")
public class CommentApiController {

	private final CommentService commentService;



	@PostMapping("/comment/{postid}")
	@Operation(summary = "댓글 postId로 업로드 메서드", description = "댓글 postId로 업로드 메서드입니다.")
	public ResponseEntity saveComment(@PathVariable("postid") Long postid,@RequestBody CommentRequest request){
		return ResponseEntity.status(HttpStatus.OK).body(new CommentResponse(commentService.saveComment(postid,request)));
	}

	@GetMapping("/comment/{postid}")
	@Operation(summary = "댓글 postId로 가져오기 메서드", description = "댓글 postId로 가져오기 메서드입니다.")
	public ResponseEntity getCommentPostAll(@PathVariable("postid") Long postid){
		CommentListDto commentListDto;
		List<Comment> findCommnets = commentService.findCommentPost(postid);
		List<CommentListDto> collect = findCommnets.stream()
			.map(m -> CommentListDto.builder()
				.id(m.getId())
				.content(m.getContent())
				.location(m.getLocation())
				.memberId(m.getMember().getId())
				.postId((m.getPost().getId()))
				.createTime(m.getCreateData())
				.modifiedTime(m.getModifiedDate()).build())
			.collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(new CommentResult(collect.size(),collect));
	}

	@PostMapping("/comment/update/{commentid}")
	@Operation(summary = "댓글 commentId로 수정 메서드", description = "댓글 commentId로 수정 메서드입니다.")
	public ResponseEntity updateComment(@PathVariable("commentid") Long commentid,@RequestBody CommentRequest request){
		return ResponseEntity.status(HttpStatus.OK).body(new CommentResponse(commentService.updateComment(commentid,request)));
	}

	@GetMapping("/comment/delete/{commentid}")
	@Operation(summary = "댓글 commentId로 삭제 메서드", description = "댓글 commentId로 삭제 메서드입니다.")
	public ResponseEntity deleteComment(@PathVariable("commentid") Long id){
		commentService.deleteComment(id);
		return ResponseEntity.status(HttpStatus.OK).body("delete");
	}
}
