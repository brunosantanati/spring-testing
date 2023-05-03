package example.person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/*
In order to check the H2 database during the integration test execution (debug mode),
you can access this URL:
http://localhost:35707/h2-console
Don't forget to change the port because it is random. Check the logs to get the correct port.
You need also to change the JDBC URL to something like: jdbc:h2:mem:dfc57daf-4eb1-488a-a157-0f86cead685d
Look at the logs to get the correct JDBC URL.

You need also to add some properties to the application.properties file and
verify that the breakpoints you set, suspend the Thread only and not all threads.
For further information look here:
https://hrrbrt.medium.com/using-h2-during-test-debugging-in-spring-f6a3db355e3a
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
//@DataJpaTest
public class PersonRepositoryIntegrationTest {

    @Autowired
    private PersonRepository subject;

    @AfterEach
    public void tearDown() throws Exception {
        subject.deleteAll();
    }

    @Test
    public void shouldSaveAndFetchPerson() throws Exception {
        var peter = new Person("Peter", "Pan");
        subject.save(peter);

        var maybePeter = subject.findByLastName("Pan");

        assertThat(maybePeter, is(Optional.of(peter)));
    }
}