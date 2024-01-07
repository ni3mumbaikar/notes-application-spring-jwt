package com.example.notes.notes.service;

import com.example.notes.notes.entity.UserInfo;
import com.example.notes.notes.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.loadUserByEmail(username);
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userInfoOptional = repository.findByEmail(email);
        // Converting userDetail to UserDetails
        return userInfoOptional.map(UserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }


}
