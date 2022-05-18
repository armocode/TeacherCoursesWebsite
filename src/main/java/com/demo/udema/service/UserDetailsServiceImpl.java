package com.demo.udema.service;

import com.demo.udema.entity.User;
import com.demo.udema.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        char[] userA = user.getRole();
//
//        int kk = 0;
//        for (char user2 : userA) {
//            kk = kk + 1;
//            String role = "ROLE_";
//            String userS= String.valueOf(user2);
//            String userSrole = role + userS;
//            grantedAuthorities.add(new SimpleGrantedAuthority(userSrole));
//        }


        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));


        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
