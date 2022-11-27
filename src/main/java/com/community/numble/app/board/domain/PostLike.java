package com.community.numble.app.board.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class PostLike {

	@Id
	@GeneratedValue
	@Column(name = "postlike_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	public void setMember(Member member){
		this.member = new Member();
		member.getPostlikes().add(this);
	}

	public void setPost(Post post){
		this.post = new Post();
		post.getPostlikes().add(this);

	}

}
