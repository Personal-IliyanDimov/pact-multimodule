package org.imd.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PactConsumerApplication {

	@Autowired
	private ProfileClient profileClient;

	public static void main(String[] args) {
		SpringApplication.run(PactConsumerApplication.class, args);
	}

	// Enable for testing
	// @Bean
	ApplicationRunner run() {
		return args -> {
			log.info("{}", this.profileClient.getAllProfiles());

			log.info("{}", this.profileClient.getSingleProfile(1));
		};
	}

}
