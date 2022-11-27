package com.community.numble.app.board.service;

import com.community.numble.app.board.domain.Member;
import com.community.numble.app.board.domain.Post;
import com.community.numble.app.board.domain.PostCategory;
import com.community.numble.app.board.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	public Long post(Post post){
		postRepository.save(post);
		return post.getId();
	}

	public List<Post> findPost() {
		return postRepository.findAll();
	}

	public Post findOne(Long postId) {
		return postRepository.findOne(postId);
	}


	public List<Post> findPostMember(Long memberid) {
		return postRepository.findByMember(memberid);

	}

	public List<Post> findPostLocation(String location){
		return postRepository.findByLocation(location);
	}

	public void deletePost(Long id){
		postRepository.delete(id);
	}

	public String createType(String type){
		if ("NEW".equals(type)) return "새로 생겼어요";
		else if("EVENT".equals(type)) return "행사/축제";
		else if("SALE".equals(type)) return "세일/이벤트";
		else if("MITTING".equals(type)) return "소모임";
		else if("LOST".equals(type)) return "잃어버렸어요";
		else return "자유게시판";

	}

}
