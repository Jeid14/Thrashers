package com.auth;

import com.model.User;
import com.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DataBaseUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    private final UserDetailsMapper userDetailsMapper;

    public DataBaseUserDetailsService(UserRepo userRepo, UserDetailsMapper userDetailsMapper){
        this.userRepo = userRepo;
        this.userDetailsMapper = userDetailsMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUsersByLogin(username);
        return userDetailsMapper.toUserDetails(user);
    }
}
