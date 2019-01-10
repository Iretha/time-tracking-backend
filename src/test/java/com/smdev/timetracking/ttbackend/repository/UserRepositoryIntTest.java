package com.smdev.timetracking.ttbackend.repository;

import com.smdev.timetracking.ttbackend.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findById() {
        User user = new User();
        user.setName("Alex");

        User persistedUser = entityManager.persist(user);

        Optional<User> found = userRepository.findById(persistedUser.getId());
        assertThat(found != null && found.get() != null);
        assertThat(found.get().getId() == persistedUser.getId());
        assertThat(user.getName().equals(found.get().getName()));
    }
}
