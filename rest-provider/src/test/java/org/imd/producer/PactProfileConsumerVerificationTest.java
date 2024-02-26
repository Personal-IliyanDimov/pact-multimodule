package org.imd.producer;


import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;

import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("ProfileProvider")
@PactBroker()
public class PactProfileConsumerVerificationTest {
    @MockBean
    ProfileController profileController;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State(value = "profiles 1 exists")
    void profilesWithId1() {
        when(profileController.getProfile(1))
            .thenReturn(Optional.of(
                    buildJosephProfile()));
    }

    private static Profile buildJosephProfile() {
        return Profile.builder()
                .id(1)
                .name("Joseph")
                .age(22)
                .email("jose@gmail.com")
                .dob(LocalDate.of(2000, 1, 1))
                .build();
    }

    @State(value = "profiles exists")
    void profiles() {
         Profile p1 = buildJosephProfile();
         when(profileController.getAllProfiles())
             .thenReturn(List.of(p1));
    }
}
