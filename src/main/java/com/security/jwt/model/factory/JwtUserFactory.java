package com.security.jwt.model.factory;

import com.model.Person;
import com.security.jwt.model.JwtPerson;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtPerson create(Person person) {
        return new JwtPerson(person.getId(), person.getLogin(), person.getPassword());
    }

}
