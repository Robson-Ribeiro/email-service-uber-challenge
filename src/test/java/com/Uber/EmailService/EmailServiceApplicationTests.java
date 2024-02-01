package com.Uber.EmailService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.Uber.EmailService.dto.EmailDto;
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

	@Test
	void sendEmailSuccess() {
		EmailDto emailDto = new EmailDto("noreply@gmail.com", "999", "O número da sorte!");

		webTestClient
			.post()
			.uri("email")
			.bodyValue(emailDto)
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("$").isNotEmpty()
			.jsonPath("$").isEqualTo("Your e-mail has been sent successfully!");

		Optional<EmailEntity> email = emailRepository.findById( (long) 1);

		assertTrue(email.isPresent());

		assertEquals(email.get().getReceiverEmail(), emailDto.getReceiverEmail());
		assertEquals(email.get().getSubject(), emailDto.getSubject());
		assertEquals(email.get().getBody(), emailDto.getBody());
	}

	@Test
	void sendEmailFailure() {
		EmailDto emailDto = new EmailDto("", "", "");

		webTestClient
			.post()
			.uri("email")
			.bodyValue("")
			.exchange()
			.expectStatus().isEqualTo(415);

		webTestClient
			.post()
			.uri("email")
			.bodyValue(emailDto)
			.exchange()
			.expectStatus().isEqualTo(400);

		emailDto = new EmailDto("noreply@gmail.com", "", "");

		webTestClient
			.post()
			.uri("email")
			.bodyValue(emailDto)
			.exchange()
			.expectStatus().isEqualTo(400);

		emailDto = new EmailDto("noreply@gmail.com", "Topic 1", "");

		webTestClient
			.post()
			.uri("email")
			.bodyValue(emailDto)
			.exchange()
			.expectStatus().isEqualTo(400);

		emailDto = new EmailDto("", "Topic 1", "noreply");

		webTestClient
			.post()
			.uri("email")
			.bodyValue(emailDto)
			.exchange()
			.expectStatus().isEqualTo(400);

		emailDto = new EmailDto("noreply@gmail.com", "", "noreply");

		webTestClient
			.post()
			.uri("email")
			.bodyValue(emailDto)
			.exchange()
			.expectStatus().isEqualTo(400);
	}
}
