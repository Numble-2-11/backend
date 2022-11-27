package com.community.numble.app.board.repository;

import com.community.numble.app.board.domain.Comment;
import com.community.numble.app.board.domain.PostLike;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostLikeRepository {

	private final EntityManager em;

	public void save(PostLike postLike){
		em.persist(postLike);
	}

	public PostLike findOne(Long id) {
		return em.find(PostLike.class, id);
	}

	public List<PostLike> findAll() {
		return em.createQuery("select pl from PostLike pl",PostLike.class).getResultList();
	}

	public List<PostLike> findByMember(Long member) {
		return em.createQuery("select pl from PostLike pl where pl.member = :member",PostLike.class)
			.setParameter("member",member).getResultList();
	}

	public int deletePostLike(Long post, Long member) {
		return em.createQuery("delete from PostLike pl where pl.post = :post and pl.member = :member",PostLike.class)
			.setParameter("post", post)
			.setParameter("member", member)
			.executeUpdate();

	}

}
