package br.com.barbermanager.barbershopmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class BarbershopmanagementApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> BarbershopmanagementApplication.main(new String[] {}));
	}

}
