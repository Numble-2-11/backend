package com.community.numble.app.follow.dto;

import com.community.numble.app.follow.domain.*;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FollowDto {

    private long followId;

    private long userId;

    private String nickname;

    private String imageCode;

    public Follow toEntity() {

        return Follow.builder().followId(followId)
                .userId(userId)
                .build();
    }
}
