package com.community.numble.app.board.repository;

import com.community.numble.app.board.domain.Comment;
import com.community.numble.app.board.domain.Post;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

	private final EntityManager em;

	public void save(Comment comment){
		em.persist(comment);
	}

	public Comment findOne(Long id) {
		return em.find(Comment.class, id);
	}

	public List<Comment> findAll() {
		return em.createQuery("select c from Comment c",Comment.class).getResultList();
	}

	public List<Comment> findByMember(Long member) {
		return em.createQuery("select c from Comment c where c.member = :member",Comment.class)
			.setParameter("member",member).getResultList();
	}

	public List<Comment> findByPost(Long post) {
		return em.createQuery("select c from Comment c where c.post.id = :post",Comment.class)
			.setParameter("post",post).getResultList();
	}

	public void delete(Long id){
		Comment comment = findOne(id);
		em.remove(comment);
	}

}
