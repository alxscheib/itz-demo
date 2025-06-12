package ipu.example.demo.service;

import ipu.example.demo.model.ServiceException;
import ipu.example.demo.model.Tutorial;
import ipu.example.demo.repository.TutorialRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorialService {

  TutorialRepository tutorialRepository;

  @Autowired
  public TutorialService(TutorialRepository tutorialRepository) {
    this.tutorialRepository = tutorialRepository;
  }

  public List<Tutorial> getAllTutorials() {
    return new ArrayList<>(tutorialRepository.findAll());
  }


  public Optional<Tutorial> getTutorialById(long id) {
    return tutorialRepository.findById(id);
  }


  public Tutorial createTutorial(Tutorial tutorial) throws ServiceException {
    try {
      return tutorialRepository.save(tutorial == null ? new Tutorial() : tutorial);
    } catch (Exception ex) {
      throw new ServiceException("internal error while creating a tutorial", ex);
    }
  }


  public Optional<Tutorial> updateTutorial( long id, Tutorial tutorial) throws ServiceException {
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

    try {
      if (tutorialData.isPresent()) {
        Tutorial tutorial2update = tutorialData.get();
        tutorial2update.setTitle(tutorial.getTitle());
        tutorial2update.setDescription(tutorial.getDescription());
        tutorial2update = tutorialRepository.save(tutorial2update);
        return Optional.of(tutorial2update);
      } else {
        return Optional.empty();
      }
    }  catch (Exception ex) {
      throw new ServiceException("internal error while creating a tutorial", ex);
    }
  }


  public void deleteTutorial(long id) throws ServiceException {
    try {
      tutorialRepository.deleteById(id);
    } catch (Exception ex) {
      throw new ServiceException("internal error while deleting a tutorial", ex);
    }
  }


  public void deleteAllTutorials() throws ServiceException {
    try {
      tutorialRepository.deleteAll();
    } catch (Exception ex) {
      throw new ServiceException("internal error while deleting all tutorials", ex);
    }

  }

  public List<Tutorial> findByTitleContaining(String text) throws ServiceException {
    try {
      return tutorialRepository.findByTitleContaining(text);
    } catch (Exception ex) {
      throw new ServiceException("internal error while searching by title", ex);
    }
  }

  public List<Tutorial> findByDescriptionContaining(String text) throws ServiceException {
    try {
      return tutorialRepository.findByDescriptionContaining(text);
    } catch (Exception ex) {
      throw new ServiceException("internal error while searching by description", ex);
    }
  }
}
