package com.security;

import com.model.Person;
import com.security.jwt.model.factory.JwtUserFactory;
import com.servises.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailService implements UserDetailsService {
    private static final String EXCEPTION_MESSAGE = "User with username %s not found";
    private final PersonService personService;

    public JwtUserDetailService(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personService.findByLogin(username);
        if (person == null) {
            throw new UsernameNotFoundException(String.format(EXCEPTION_MESSAGE, username));
        }
        return JwtUserFactory.create(person);
    }
}
