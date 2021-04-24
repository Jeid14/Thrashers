package com.security;

import com.model.Person;
import com.security.jwt.model.factory.JwtUserFactory;
import com.servises.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailService implements UserDetailsService {

    private final PersonService personService;

    @Autowired
    public JwtUserDetailService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personService.findByLogin(username);
        if (person == null) {
            throw new UsernameNotFoundException(String.format("User with username %s not found", username));
        }
        return JwtUserFactory.create(person);
    }
}
