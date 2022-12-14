package com.community.numble.app.user.service;

import com.community.numble.app.user.domain.User;
import com.community.numble.app.user.domain.*;
import com.community.numble.app.user.dto.*;
import com.community.numble.app.user.repository.*;
import com.community.numble.common.*;
import com.community.numble.common.response.*;
import com.community.numble.config.jwt.*;
import com.community.numble.exception.*;
import lombok.*;
import org.apache.commons.lang3.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;

import javax.transaction.*;
import java.util.*;
import java.util.stream.*;

@Service("userService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        if (ObjectUtils.isEmpty(user)) {
            throw new LoginFailedException(ResponseMessage.AUTH_USER.getMessage());
        }

        final Collection<GrantedAuthority> authorities = user.getRole().stream().map(
            role -> new SimpleGrantedAuthority(role.getRole()))
            .collect(Collectors.toList());

        user.setAuthorities(authorities);

        return user;
    }

    public void register(UserCreateDto userCreateDto) {

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        User user = userCreateDto.toEntity();
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.builder().role(Auth.ROLE_USER.name()).build());
        user.setRole(roleList);
        userRepository.save(user);

    }

    @Transactional
    public TokenDto login(LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();

        // 2. ????????? ?????? (????????? ???????????? ??????) ??? ??????????????? ??????
        //    authenticate ???????????? ????????? ??? ??? CustomUserDetailsService ?????? ???????????? loadUserByUsername ???????????? ?????????
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. ?????? ????????? ???????????? JWT ?????? ??????
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken ??????
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. ?????? ??????
        return tokenDto;
    }

    @Transactional
    public TokenDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. Refresh Token ??????
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token ??? ???????????? ????????????.");
        }

        // 2. Access Token ?????? Member ID ????????????
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. ??????????????? Member ID ??? ???????????? Refresh Token ??? ?????????
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("???????????? ??? ??????????????????."));

        // 4. Refresh Token ??????????????? ??????
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("????????? ?????? ????????? ???????????? ????????????.");
        }

        // 5. ????????? ?????? ??????
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. ????????? ?????? ????????????
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // ?????? ??????
        return tokenDto;
    }

    public void updateUserInfo(UserUpdateDto userUpdateDto) {

        User user = UserUpdateDto.toEntity(userUpdateDto);

        userRepository.save(user);
    }
}

