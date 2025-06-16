package ipu.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ipu.example.demo.model.Tutorial;
import ipu.example.demo.repository.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class BaseTutorialTest {
  // Sample titles and descriptions for test data
  protected static final String TITLE_1 = "JDBC";
  protected static final String DESCRIPTION_1 = "Java Database Connectivity";

  protected static final String TITLE_2 = "JSP";
  protected static final String DESCRIPTION_2 = "Java Server Pages";

  protected static final String TITLE_3 = "Kafka";
  protected static final String DESCRIPTION_3 = "Apache Kafka";

  protected static final String TITLE_4 = "'JSF'";
  protected static final String DESCRIPTION_4 = "JavaServer Faces";

  @Autowired
  protected TutorialRepository tutorialRepository;

  protected Tutorial testTutorial;

  protected long firstId;


  /**
   * Prepares the test environment by clearing the database
   * and inserting three sample tutorials.
   */
  @BeforeEach
  void setUp() {
    tutorialRepository.deleteAll();
    Tutorial firstTutorial =  tutorialRepository.save(Tutorial.builder().title(TITLE_1).description(DESCRIPTION_1).build());
    firstId = firstTutorial.getId();
    tutorialRepository.save(Tutorial.builder().title(TITLE_2).description(DESCRIPTION_2).build());
    tutorialRepository.save(Tutorial.builder().title(TITLE_3).description(DESCRIPTION_3).build());
    assertEquals(3, tutorialRepository.count());

    testTutorial = Tutorial.builder()
        .title(TITLE_4)
        .description(DESCRIPTION_4)
        .build();
  }
}
