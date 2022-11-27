package com.community.numble.app.board.repository;

import com.community.numble.app.board.domain.Comment;
import com.community.numble.app.board.domain.Post;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepository {

	private final EntityManager em;

	public void save(Post post){
		em.persist(post);
	}

	public Post findOne(Long id) {
		return em.find(Post.class, id);
	}

	public List<Post> findAll(){
		return em.createQuery("select p from Post p",Post.class).getResultList();
	}

	public List<Post> findByMember(Long member) {
		return em.createQuery("select p from Post p where p.member = :member",Post.class)
			.setParameter("member",member).getResultList();
	}

	public List<Post> findByLocation(String location) {
		return em.createQuery("select p from Post p where p.location = :location", Post.class)
			.setParameter("location", location).getResultList();
	}

	public void delete(Long id){
		Post post = findOne(id);
		em.remove(post);
	}

}
