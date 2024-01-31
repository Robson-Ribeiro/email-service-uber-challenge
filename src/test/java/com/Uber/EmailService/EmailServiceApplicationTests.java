package com.Uber.EmailService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.Uber.EmailService.entity.EmailEntity;
import com.Uber.EmailService.repository.EmailRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
class EmailServiceApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private EmailRepository emailRepository;

	@Test
	void getAllEmailsSuccess() {
		webTestClient
			.get()
			.uri("/email")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(0);

		EmailEntity email = new EmailEntity("noreply@gmail.com", "1911", "Lorem ipsum dolor sit amet");
		emailRepository.save(email);

		webTestClient
			.get()
			.uri("/email")
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isArray()
			.jsonPath("$.length()").isEqualTo(1)
			.jsonPath("$[0].receiverEmail").isEqualTo(email.getReceiverEmail())
			.jsonPath("$[0].subject").isEqualTo(email.getSubject())
			.jsonPath("$[0].body").isEqualTo(email.getBody());
	}

}
