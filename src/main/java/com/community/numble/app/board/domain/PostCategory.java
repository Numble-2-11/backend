package com.community.numble.app.board.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
public enum PostCategory {
	NEW("새로 생겼어요"),EVENT("행사/축제"),SAIL("세일/이벤트"),MEETING("소모임"),LOST("잃어버렸어요"),FREE("자유게시판");

	String value;

	PostCategory(String value) {
		this.value = value;
	}
}
