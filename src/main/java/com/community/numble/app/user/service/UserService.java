package com.community.numble.app.user.service;

import com.community.numble.app.user.domain.Role;
import com.community.numble.app.user.domain.User;
import com.community.numble.app.user.dto.UserCreateDto;
import com.community.numble.app.user.repository.UserRepository;
import com.community.numble.common.Auth;
import com.community.numble.common.response.ResponseMessage;
import com.community.numble.exception.LoginFailedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service("userService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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

    public void join(UserCreateDto userCreateDto) {
        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        User user = userCreateDto.toEntity();
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.builder().role(Auth.ROLE_USER.name()).build());
        user.setRole(roleList);
        userRepository.save(user);
    }

}
