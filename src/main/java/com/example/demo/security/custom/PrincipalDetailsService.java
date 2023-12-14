package com.example.demo.security.custom;

import com.example.demo.global.exception.customexception.UserCustomException;
import com.example.demo.model.user.User;
import com.example.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // email, password 로그인이므로 email 값으로 회원 조회
        User user =
                userRepository
                        .findByEmail(username)
                        .orElseThrow(() -> UserCustomException.INVALID_AUTHENTICATION);

        return new PrincipalDetails(user);
    }
}
