package com.servises;

import com.model.Person;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public interface PersonService {
    Person register(Person person);

    List<Person> getAll();

    Person findByLogin(String login);

    Person findById(Integer id);

    void delete(Integer id);

    BCryptPasswordEncoder getPasswordEncoder();
}
