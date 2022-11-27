package com.community.numble.app.follow.service;

import com.community.numble.app.follow.domain.Follow;
import com.community.numble.app.follow.dto.FollowDto;
import com.community.numble.app.follow.repository.FollowRepository;
import com.community.numble.app.user.domain.*;
import com.community.numble.app.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    private final UserRepository userRepository;

    public List<FollowDto> getFollowList(Long userId) {

        List<Follow> followList = followRepository.findFollowsByUserId(userId);
        List<FollowDto> result = new ArrayList<>();

        followList.forEach(follow -> {
            User user = userRepository.findByUserId(follow.getUserId()).orElseThrow();
            FollowDto followDto = FollowDto.builder()
                    .nickname(user.getNickname())
                    .userId(user.getUserId())
                    .imageCode(user.getImageCode())
                    .build();
            result.add(followDto);
        });

        return result;

    }

    public List<FollowDto> getFollowerList(long followId) {
        List<Follow> followerList = followRepository.findFollowsByFollowId(followId);
        List<FollowDto> result = new ArrayList<>();
        if(!followerList.isEmpty()){
            followerList.forEach(follow -> {
                User user = userRepository.findByUserId(follow.getFollowId()).orElse(null);
                if(user != null){
                    FollowDto followDto = FollowDto.builder()
                            .nickname(user.getNickname())
                            .userId(user.getUserId())
                            .imageCode(user.getImageCode())
                            .build();
                    result.add(followDto);
                }
            });
        }

        return result;
    }

    public void insertFollow(FollowDto followDto) {

        Follow follow = followDto.toEntity();
        followRepository.save(follow);
    }

    public void deleteFollow(FollowDto followDto) {
        Follow follow = followDto.toEntity();
        followRepository.delete(follow);
    }
}
