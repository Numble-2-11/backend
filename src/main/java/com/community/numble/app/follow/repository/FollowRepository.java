package com.community.numble.app.follow.repository;

import com.community.numble.app.follow.domain.Follow;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findFollowsByUserId(Long userId);

    List<Follow> findFollowsByFollowId(Long userId);
}
