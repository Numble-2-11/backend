package com.community.numble.app.user.dto;

import com.community.numble.app.user.bean.UserBean;
import com.community.numble.common.utils.DateUtils;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class UserCreateDto {

    private String username;

    private String nickname;

    private String password;

    private String cellPhone;

    private String location;

    public UserBean toEntity() {

        return UserBean.builder()
            .username(username)
            .nickname(nickname)
            .password(password)
            .cellPhone(cellPhone)
            .location(location)
            .createDate(DateUtils.format(LocalDateTime.now(), "yyyyMMddHHmmss"))
            .build();

    }
}
