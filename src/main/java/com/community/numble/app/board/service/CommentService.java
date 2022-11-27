package com.community.numble.app.board.service;

import com.community.numble.app.board.domain.Comment;
import com.community.numble.app.board.domain.Post;
import com.community.numble.app.board.dto.CommentDto.CommentRequest;
import com.community.numble.app.board.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	private final MemberService memberService;
	private final PostService postService;

	public Long save(Comment comment){
		commentRepository.save(comment);
		return comment.getId();
	}

	public List<Comment> findComment() {
		return commentRepository.findAll();
	}

	public Comment findOne(Long commentId) {
		return commentRepository.findOne(commentId);
	}


	public List<Comment> findCommentMember(Long memberid) {
		return commentRepository.findByMember(memberid);

	}

	public List<Comment> findCommentPost(Long postid) {
		return commentRepository.findByPost(postid);

	}

	public void deleteComment(Long id) {
		commentRepository.delete(id);
	}

	public Long saveComment(Long postId, CommentRequest request){
		Comment comment = new Comment();
		comment.setContent(request.getContent());
		comment.setMember(memberService.findOne(request.getMember()));
		comment.setPost(postService.findOne(postId));
		comment.setLocation(request.getLocation());

		Long id = save(comment);

		return id;

	}

	public Long updateComment(Long commentId, CommentRequest request){
		Comment comment = commentRepository.findOne(commentId);
		comment.setContent(request.getContent());
		comment.setMember(memberService.findOne(request.getMember()));
		comment.setLocation(request.getLocation());

		Long id = save(comment);

		return id;

	}

}
