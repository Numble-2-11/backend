package com.community.numble.app.board.repository;

import com.community.numble.app.board.domain.Post;
import com.community.numble.app.board.domain.UploadFile;
import com.community.numble.app.board.dto.PostDto.PostAllDto;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UploadFileRepository {

	private final EntityManager em;

	public void save(UploadFile uploadFile){
		em.persist(uploadFile);
	}

	public void deletePostFile(Post post){
			em.createQuery("delete from UploadFile u where u.post = :post")
			.setParameter("post",post).executeUpdate();
	}

	public List<UploadFile> findByPost(Long post) {
		return em.createQuery("select u from UploadFile u where u.post = :post",UploadFile.class)
			.setParameter("post",post).getResultList();
	}

	public String findIdPath(Long id){
		return em.createQuery("select u from UploadFile u where u.id = :id",UploadFile.class)
			.setParameter("id",id).getSingleResult().getPath();
	}

	public Long findIdFirst(Long id) {
		TypedQuery<UploadFile> query;
		try {
			query = em.createQuery(
					"select u from UploadFile u where u.post.id = :id", UploadFile.class)
				.setParameter("id", id);
			return query.setMaxResults(1).getSingleResult().getId();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Long> findIdAll(Long id){
		List<UploadFile> query = em.createQuery("select u from UploadFile u where u.post.id = :id", UploadFile.class)
			.setParameter("id", id).getResultList();
		List<Long> result = new ArrayList<>();
		for( UploadFile queryOne : query){
			result.add(queryOne.getId());
		}
		if (result.isEmpty()){
			return null;
		} else {
			return result;
		}
	}
}
