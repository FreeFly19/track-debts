package com.freefly19.trackdebts.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void savedEntityShouldBeAvailableForRetrieving() {
        User user = User.builder().email("asdsa@sadsa.com").password("sdasdas").build();
        User save = userRepository.save(user);

        Long oldId = save.getId();

        boolean found = userRepository.findAll()
                .stream()
                .anyMatch(p -> p.getEmail().equals("asdsa@sadsa.com") && p.getId().equals(oldId));

        Assert.assertTrue(found);
    }
}
