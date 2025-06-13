package ipu.example.demo.service;

import ipu.example.demo.model.ServiceException;
import ipu.example.demo.model.Tutorial;
import ipu.example.demo.repository.TutorialRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing {@link Tutorial} entities.
 * Provides business logic for CRUD operations and custom queries.
 */
@Service
public class TutorialServiceImpl implements TutorialService {

  /** Repository used for accessing Tutorial data. */
  private final TutorialRepository tutorialRepository;

  /**
   * Constructor injecting the {@link TutorialRepository}.
   *
   * @param tutorialRepository the repository for tutorial persistence operations
   */
  public TutorialServiceImpl(TutorialRepository tutorialRepository) {
    this.tutorialRepository = tutorialRepository;
  }

  /**
   * Retrieves all tutorials from the database.
   *
   * @return a list of all tutorials
   */
  @Override
  public List<Tutorial> getAllTutorials() {
    return new ArrayList<>(tutorialRepository.findAll());
  }

  /**
   * Finds a tutorial by its ID.
   *
   * @param id the ID of the tutorial to retrieve
   * @return an {@link Optional} containing the tutorial if found, otherwise empty
   */
  @Override
  public Optional<Tutorial> getTutorialById(long id) {
    return tutorialRepository.findById(id);
  }

  /**
   * Creates a new tutorial.
   *
   * @param tutorial the tutorial data to create
   * @return the saved tutorial entity
   * @throws ServiceException if an internal error occurs during creation
   */
  @Override
  public Tutorial createTutorial(Tutorial tutorial) throws ServiceException {
    try {
      Tutorial newTutorial;
      if (tutorial == null) {
        newTutorial = new Tutorial();
      } else {
        newTutorial = Tutorial.builder()
            .title(tutorial.getTitle())
            .description(tutorial.getDescription())
            .build();
      }
      return tutorialRepository.save(newTutorial);
    } catch (Exception ex) {
      throw new ServiceException("Internal error while creating a tutorial", ex);
    }
  }

  /**
   * Updates an existing tutorial by ID.
   *
   * @param id the ID of the tutorial to update
   * @param tutorial the updated tutorial data
   * @return an {@link Optional} containing the updated tutorial if successful, otherwise empty
   * @throws ServiceException if an internal error occurs during update
   */
  @Override
  public Optional<Tutorial> updateTutorial(long id, Tutorial tutorial) throws ServiceException {
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

    try {
      if (tutorialData.isPresent()) {
        Tutorial tutorialToUpdate = tutorialData.get();
        tutorialToUpdate.setTitle(tutorial.getTitle());
        tutorialToUpdate.setDescription(tutorial.getDescription());
        tutorialToUpdate = tutorialRepository.save(tutorialToUpdate);
        return Optional.of(tutorialToUpdate);
      } else {
        return Optional.empty();
      }
    } catch (Exception ex) {
      throw new ServiceException("Internal error while updating a tutorial", ex);
    }
  }

  /**
   * Deletes a tutorial by its ID.
   *
   * @param id the ID of the tutorial to delete
   * @throws ServiceException if an internal error occurs during deletion
   */
  @Override
  public void deleteTutorial(long id) throws ServiceException {
    try {
      tutorialRepository.deleteById(id);
    } catch (Exception ex) {
      throw new ServiceException("Internal error while deleting a tutorial", ex);
    }
  }

  /**
   * Deletes all tutorials from the database.
   *
   * @throws ServiceException if an internal error occurs during deletion
   */
  @Override
  public void deleteAllTutorials() throws ServiceException {
    try {
      tutorialRepository.deleteAll();
    } catch (Exception ex) {
      throw new ServiceException("Internal error while deleting all tutorials", ex);
    }
  }

  /**
   * Finds tutorials whose title contains the specified text.
   *
   * @param text the text to search for in the title
   * @return a list of matching tutorials
   * @throws ServiceException if an internal error occurs during search
   */
  @Override
  public List<Tutorial> findByTitleContaining(String text) throws ServiceException {
    try {
      return tutorialRepository.findByTitleContainingIgnoreCase(text);
    } catch (Exception ex) {
      throw new ServiceException("Internal error while searching by title", ex);
    }
  }

  /**
   * Finds tutorials whose description contains the specified text.
   *
   * @param text the text to search for in the description
   * @return a list of matching tutorials
   * @throws ServiceException if an internal error occurs during search
   */
  @Override
  public List<Tutorial> findByDescriptionContaining(String text) throws ServiceException {
    try {
      return tutorialRepository.findByDescriptionContainingIgnoreCase(text);
    } catch (Exception ex) {
      throw new ServiceException("Internal error while searching by description", ex);
    }
  }
}
