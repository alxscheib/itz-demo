package ipu.example.demo.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ipu.example.demo.BaseTutorialTest;
import ipu.example.demo.model.Tutorial;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Integration tests for the {@link ipu.example.demo.controller.TutorialController}.
 *
 * <p>This test class uses Spring Boot, JUnit 5, and MockMvc to verify
 * the REST API endpoints exposed by the TutorialController.</p>
 *
 * <p>The tests include:
 * <ul>
 *   <li>Retrieving all tutorials</li>
 *   <li>Retrieving a specific tutorial by its ID</li>
 *   <li>Setup of test data before each test</li>
 * </ul>
 *
 * <p>The class runs with the SpringRunner and a mock web environment.</p>
 *
 * @author Alexander
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class TutorialControllerTest extends BaseTutorialTest {

  /**
   * Base URL of the tutorial API.
   */
  private static final String BASE_URL = "/api/tutorials";

  @Autowired
  private MockMvc mockMvc;

  /**
   * Tests the GET endpoint for retrieving all tutorials.
   *
   * @throws Exception if MockMvc fails
   */
  @Test
  void getTutorials() throws Exception {
    mockMvc
        .perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.size()", is(3)));
  }

  /**
   * Tests the retrieval of a tutorial by its ID.
   *
   * <p>First retrieves all tutorials, then fetches one
   * by its ID and verifies the response.</p>
   *
   * @throws Exception if MockMvc or JSON handling fails
   */
  @Test
  void getTutorialById() throws Exception {
    MvcResult result = mockMvc
        .perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andReturn();

    String json = result.getResponse().getContentAsString();

    // Parse JSON array into a list of Tutorial objects
    TypeToken<List<Tutorial>> typeToken = new TypeToken<>(){};
    List<Tutorial> tutorials = new Gson().fromJson(json, typeToken.getType());
    assertEquals(3, tutorials.size());

    Tutorial testTutorial = tutorials.get(0);
    Long id = testTutorial.getId();

    result = mockMvc
        .perform(get(BASE_URL + "/" + id).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("id", is(id.intValue())))
        .andReturn();

    json = result.getResponse().getContentAsString();
    Tutorial resultTutorial = new Gson().fromJson(json, Tutorial.class);

    assertEquals(testTutorial.getTitle(), resultTutorial.getTitle());
    assertEquals(testTutorial.getDescription(), resultTutorial.getDescription());
  }

  /**
   * Placeholder test for creating a tutorial.
   */
  @Test
  void createTutorial() {
    // TODO: should be implementet in real project
    assertTrue(true);
  }

  /**
   * Placeholder test for updating a tutorial.
   */
  @Test
  void updateTutorial() {
    // TODO: should be implementet in real project
    assertTrue(true);
  }

  /**
   * Placeholder test for deleting all tutorials.
   */
  @Test
  void deleteAllTutorials() {
    // TODO: should be implementet in real project
    assertTrue(true);
  }

  /**
   * Placeholder test for deleting a specific tutorial.
   */
  @Test
  void deleteTutorial() {
    // TODO: should be implementet in real project
    assertTrue(true);
  }
}

