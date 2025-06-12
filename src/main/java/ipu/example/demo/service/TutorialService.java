package ipu.example.demo.service;

import ipu.example.demo.model.Tutorial;
import ipu.example.demo.repository.TutorialRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorialService {
  @Autowired
  TutorialRepository tutorialRepository;


  public List<Tutorial> getTutorials() {
    List<Tutorial> tutorials = new ArrayList<Tutorial>();
    tutorialRepository.findAll().forEach(tutorials::add);

    return tutorials;
  }


  public Optional<Tutorial> getTutorialById(long id) {
    Optional<Tutorial> tutorial = tutorialRepository.findById(id);
    return tutorial;
  }


  public Tutorial createTutorial(Tutorial tutorial) {
    try {
      Tutorial newTutorial = tutorialRepository.save(tutorial);
      return newTutorial;
    } catch (Exception ex) {
      throw new RuntimeException("internal error while creating a tutorial", ex);
    }
  }


  public Optional<Tutorial> updateTutorial( long id, Tutorial tutorial) {
    Optional<Tutorial> tutorialData = tutorialRepository.findById(id);

    try {
      if (tutorialData.isPresent()) {
        Tutorial _tutorial = tutorialData.get();
//        _tutorial.setTitle(tutorial.getTitle());
//        _tutorial.setDescription(tutorial.getDescription());
        _tutorial = tutorialRepository.save(_tutorial);
        return Optional.ofNullable(_tutorial);
      } else {
        return Optional.empty();
      }
    }  catch (Exception ex) {
      throw new RuntimeException("internal error while creating a tutorial", ex);
    }
  }


  public void deleteTutorial(long id) {
    try {
      tutorialRepository.deleteById(id);
    } catch (Exception ex) {
      throw new RuntimeException("internal error while deleting a tutorial", ex);
    }
  }


  public void deleteAllTutorials() {
    try {
      tutorialRepository.deleteAll();
    } catch (Exception ex) {
      throw new RuntimeException("internal error while deleting all tutorials", ex);
    }

  }

  public List<Tutorial> findByTitleContaining(String text) {
    try {
      List<Tutorial> tutorials = tutorialRepository.findByTitleContaining(text);
      return tutorials;
    } catch (Exception ex) {
      throw new RuntimeException("internal error while searching by title", ex);
    }
  }

  public List<Tutorial> findByDescriptionContaining(String text) {
    try {
      List<Tutorial> tutorials = tutorialRepository.findByDescriptionContaining(text);
      return tutorials;
    } catch (Exception ex) {
      throw new RuntimeException("internal error while searching by description", ex);
    }
  }
}
