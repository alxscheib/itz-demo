package ipu.example.demo.service;

import ipu.example.demo.model.ServiceException;
import ipu.example.demo.model.Tutorial;
import java.util.List;
import java.util.Optional;

/**
 * Service interface defining operations for managing {@link Tutorial} entities.
 * <p>
 * This service abstracts the business logic and data access layer.
 */
public interface TutorialService {

  /**
   * Retrieves all tutorials.
   *
   * @return a list of all tutorials
   */
  List<Tutorial> getAllTutorials();

  /**
   * Retrieves a tutorial by its ID.
   *
   * @param id the ID of the tutorial
   * @return an {@link Optional} containing the tutorial if found, or empty if not found
   */
  Optional<Tutorial> getTutorialById(long id);

  /**
   * Creates a new tutorial.
   *
   * @param tutorial the tutorial entity to create
   * @return the created tutorial with generated ID
   * @throws ServiceException if any error occurs during creation
   */
  Tutorial createTutorial(Tutorial tutorial) throws ServiceException;

  /**
   * Updates an existing tutorial identified by ID.
   *
   * @param id the ID of the tutorial to update
   * @param tutorial the tutorial data to update
   * @return an {@link Optional} containing the updated tutorial if the tutorial exists, or empty if not found
   * @throws ServiceException if any error occurs during update
   */
  Optional<Tutorial> updateTutorial(long id, Tutorial tutorial) throws ServiceException;

  /**
   * Deletes a tutorial by its ID.
   *
   * @param id the ID of the tutorial to delete
   * @throws ServiceException if any error occurs during deletion
   */
  void deleteTutorial(long id) throws ServiceException;

  /**
   * Deletes all tutorials.
   *
   * @throws ServiceException if any error occurs during deletion
   */
  void deleteAllTutorials() throws ServiceException;

  /**
   * Finds tutorials whose titles contain the specified text.
   *
   * @param text the partial title text to search for
   * @return a list of tutorials with titles containing the specified text
   * @throws ServiceException if any error occurs during the search
   */
  List<Tutorial> findByTitleContaining(String text) throws ServiceException;

  /**
   * Finds tutorials whose descriptions contain the specified text.
   *
   * @param text the partial description text to search for
   * @return a list of tutorials with descriptions containing the specified text
   * @throws ServiceException if any error occurs during the search
   */
  List<Tutorial> findByDescriptionContaining(String text) throws ServiceException;
}
