package com.community.numble.app.board.dto;

import lombok.Data;

public class MemberDto {
	@Data
	public static class CreateMemberResponse {
		private Long id;

		public CreateMemberResponse(Long id) {
			this.id = id;
		}
	}

	@Data
	public static class CreateMemberRequest {
		private String name;
	}



}
