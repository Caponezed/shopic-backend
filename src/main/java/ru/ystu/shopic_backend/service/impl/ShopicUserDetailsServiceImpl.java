package ru.ystu.shopic_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ystu.shopic_backend.entity.User;
import ru.ystu.shopic_backend.entity.UserPrincipal;
import ru.ystu.shopic_backend.repository.UserRepository;

@Service
public class ShopicUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() ->  new UsernameNotFoundException("Пользователь с email" + userEmail + " не зарегистрирован"));

        return new UserPrincipal(user);
    }

}
