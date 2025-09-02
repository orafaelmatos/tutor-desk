package com.corespace.tutordesk;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.data.mongodb.host=localhost",
    "spring.data.mongodb.port=27017",
    "spring.data.mongodb.database=tutor_desk_test",
    "spring.flyway.enabled=false"
})
class TutorDeskApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring context loads successfully
    }

}
