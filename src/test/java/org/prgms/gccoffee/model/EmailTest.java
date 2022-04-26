package org.prgms.gccoffee.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EmailTest {

	@Test
	void testEmail() {
		assertThrows(IllegalArgumentException.class, () -> new Email("access"));
	}

	@Test
	void testValidEmail() {
		final Email email = new Email("hello@gmail.com");
		assertTrue(email.getAddress().equals("hello@gmail.com"));
	}

	@Test
	void testEqEmail() {
		final Email email1 = new Email("hello@gmail.com");
		final Email email2 = new Email("hello@gmail.com");

		assertEquals(email1,email2);
	}
}
