package ipu.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ipu.example.demo.controller.TutorialController;
import ipu.example.demo.service.TutorialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private TutorialController tutorialController;

	@Autowired
	private TutorialService tutorialService;

	@Test
	void contextLoads() {
		assertNotNull(tutorialController);
		assertNotNull(tutorialService);
	}

}
