package com.community.numble.app.board.service;

import com.community.numble.app.board.domain.Comment;
import com.community.numble.app.board.domain.PostLike;
import com.community.numble.app.board.repository.CommentRepository;
import com.community.numble.app.board.repository.PostLikeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostLikeService {

	private final PostLikeRepository postLikeRepository;

	public Long save(PostLike postLike){
		postLikeRepository.save(postLike);
		return postLike.getId();
	}

	public List<PostLike> findPostLike() {
		return postLikeRepository.findAll();
	}

	public PostLike findOne(Long postLikeId) {
		return postLikeRepository.findOne(postLikeId);
	}


	public List<PostLike> findByMember(Long member) {
		return postLikeRepository.findByMember(member);
	}

	public Integer deletePostLike(Long post, Long member) {
		return postLikeRepository.deletePostLike(post,member);
	}
}
