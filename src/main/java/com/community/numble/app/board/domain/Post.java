package com.community.numble.app.board.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter @Setter
public class Post extends Time{

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id")
	private Long id;

	private String title;
	private String content;

	private String location;

	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private List<UploadFile> uploadFile = new ArrayList<>();



	private String type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private List<Comment> comment = new ArrayList<>();

	@OneToMany(mappedBy = "post")
	private List<PostLike> postlikes = new ArrayList<>();


	public void setMember(Member member){
		this.member = member;
		member.getPosts().add(this);
	}



	public void createType(String type){
		if ("새로 생겼어요".equals(type)) this.setType(PostCategory.NEW.getValue());
		else if("행사/축제".equals(type)) this.setType(PostCategory.EVENT.getValue());
		else if("세일/이벤트".equals(type)) this.setType(PostCategory.SAIL.getValue());
		else if("소모임".equals(type)) this.setType(PostCategory.MEETING.getValue());
		else if("잃어버렸어요".equals(type)) this.setType(PostCategory.LOST.getValue());
		else if("자유게시판".equals(type)) this.setType(PostCategory.FREE.getValue());
		else throw new IllegalStateException("잘못된 카테고리정보 입니다.");
		
	}


}
