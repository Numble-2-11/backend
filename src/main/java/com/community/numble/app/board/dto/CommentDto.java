package com.community.numble.app.board.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class CommentDto {

	@Data
	public static class CommentResponse {
		private Long id;

		public CommentResponse(Long id) {
			this.id = id;
		}
	}

	@Data
	public static class CommentRequest {
		private String content;
		private String location;
		private Long member;
	}

	@Data
	@AllArgsConstructor
	public static class CommentResult<T> {
		private int count;
		private T data;
	}

	@Data
	@AllArgsConstructor
	@Builder
	public static class CommentListDto{
		private Long id;
		private String content;
		private String location;
		private Long memberId;
		private Long postId;
		private LocalDateTime createTime;
		private LocalDateTime modifiedTime;


	}

}
