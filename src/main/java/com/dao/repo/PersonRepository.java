package com.dao.repo;

import com.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Person findUsersByLogin(String login);

    @NonNull
    @Override
    List<Person> findAll();

    Person findPersonById(Integer id);

    void deleteById(@NonNull Integer id);
}
