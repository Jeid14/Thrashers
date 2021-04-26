package com.servises;

import com.model.Person;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public interface PersonService {
    Person register(Person person);

    Person findByLogin(String login);

    BCryptPasswordEncoder getPasswordEncoder();
}
