package ipu.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import ipu.example.demo.BaseTutorialTest;
import ipu.example.demo.model.ServiceException;
import ipu.example.demo.model.Tutorial;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests for the {@link TutorialService}.
 *
 * <p>This class tests the service layer methods for managing {@link Tutorial} entities.
 * It ensures the correctness of all CRUD operations and search functionality.</p>
 *
 * <p>Tests include:
 * <ul>
 *   <li>Fetching all tutorials</li>
 *   <li>Fetching a tutorial by ID</li>
 *   <li>Creating a new tutorial</li>
 *   <li>Updating an existing tutorial</li>
 *   <li>Deleting tutorials (single and all)</li>
 *   <li>Searching tutorials by title and description</li>
 * </ul>
 * </p>
 *
 * <p>Spring Boot's test context is used to inject real service and repository beans.</p>
 *
 * @author Alexander
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@TestMethodOrder(OrderAnnotation.class)
class TutorialServiceTest  extends BaseTutorialTest {

  @Autowired
  private TutorialService tutorialService;

  /**
   * Tests retrieval of all tutorials.
   */
  @DisplayName("JUnit test for getAllTutorials() method")
  @Test
  @Order(1)
  void getAllTutorials() {
    List<Tutorial> tutorials = tutorialService.getAllTutorials();
    assertEquals(3, tutorials.size() );
  }

  /**
   * Tests retrieval of a tutorial by its ID.
   */
  @DisplayName("JUnit test for getTutorialById() method")
  @Test
  @Order(2)
  void getTutorialById() {
    Optional<Tutorial> tutorialData = tutorialService.getTutorialById(firstId);
    assertTrue(tutorialData.isPresent());
    assertEquals(BaseTutorialTest.TITLE_1, tutorialData.get().getTitle());
    assertEquals(BaseTutorialTest.DESCRIPTION_1, tutorialData.get().getDescription());
  }

  /**
   * Tests the creation of a new tutorial.
   */
  @DisplayName("JUnit test for createTutorial() method")
  @Test
  @Order(3)
  void createTutorial() throws ServiceException {
    Tutorial newTutorial = tutorialService.createTutorial(testTutorial);
    assertEquals(4, tutorialService.getAllTutorials().size());
    assertEquals(BaseTutorialTest.TITLE_4, newTutorial.getTitle());
    assertEquals(BaseTutorialTest.DESCRIPTION_4, newTutorial.getDescription());
  }

  /**
   * Tests updating an existing tutorial.
   */
  @DisplayName("JUnit test for updateTutorial() method")
  @Test
  @Order(4)
  void updateTutorial() throws ServiceException {
    Optional<Tutorial> tutorialData = tutorialService.updateTutorial(firstId, testTutorial);
    if (tutorialData.isPresent()) {
      Tutorial tut = tutorialData.get();
      assertEquals(TITLE_4, tut.getTitle());
      assertEquals(DESCRIPTION_4, tut.getDescription());
    } else {
      fail();
    }
  }

  /**
   * Tests deleting a single tutorial by ID.
   */
  @DisplayName("JUnit test for deleteTutorial() method")
  @Test
  @Order(5)
  void deleteTutorial() {
    try {
      Tutorial newTutorial = tutorialService.createTutorial(testTutorial);
      assertEquals(4, tutorialService.getAllTutorials().size());

      long newId = newTutorial.getId();
      assertEquals(TITLE_4, newTutorial.getTitle());

      tutorialService.deleteTutorial(newId);
      assertEquals(3, tutorialService.getAllTutorials().size());
    } catch (ServiceException e) {
      fail();
    }
  }

  /**
   * Tests deleting all tutorials.
   */
  @Test
  @Order(6)
  void deleteAllTutorials() {
    try {
      assertEquals(3, tutorialService.getAllTutorials().size());

      tutorialService.deleteAllTutorials();
      assertEquals(0, tutorialService.getAllTutorials().size());
    } catch (ServiceException e) {
      fail();
    }
  }

  /**
   * Tests searching tutorials by part of their title.
   */
  @Test
  @Order(7)
  void findByTitleContaining() {
    try {
      tutorialService.deleteAllTutorials();
      tutorialService.createTutorial(Tutorial.builder().title(TITLE_1).description(DESCRIPTION_1).build());
      tutorialService.createTutorial(Tutorial.builder().title(TITLE_2).description(DESCRIPTION_2).build());
      tutorialService.createTutorial(Tutorial.builder().title(TITLE_3).description(DESCRIPTION_3).build());

      assertEquals(1, tutorialService.findByTitleContaining("JSP").size());
      assertEquals(2, tutorialService.findByTitleContaining("J").size());
      assertEquals(1, tutorialService.findByTitleContaining("Ka").size());
      assertEquals(0, tutorialService.findByTitleContaining("Servlet").size());
    } catch (ServiceException e) {
      fail();
    }
  }

  /**
   * Tests searching tutorials by part of their description.
   */
  @Test
  @Order(8)
  void findByDescriptionContaining() {
    try {
      tutorialService.deleteAllTutorials();
      tutorialService.createTutorial(Tutorial.builder().title(TITLE_1).description(DESCRIPTION_1).build());
      tutorialService.createTutorial(Tutorial.builder().title(TITLE_2).description(DESCRIPTION_2).build());
      tutorialService.createTutorial(Tutorial.builder().title(TITLE_3).description(DESCRIPTION_3).build());

      assertEquals(2, tutorialService.findByDescriptionContaining("Java").size());
      assertEquals(3, tutorialService.findByDescriptionContaining("A").size());
      assertEquals(1, tutorialService.findByDescriptionContaining("KAFKA").size());
      assertEquals(0, tutorialService.findByDescriptionContaining("Services").size());
    } catch (ServiceException e) {
      fail();
    }
  }
}
