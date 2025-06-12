package ipu.example.demo.repository;

import ipu.example.demo.model.Tutorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

  /**
   * returns all Tutorials which title contains input text
   * @param text  part of the title to be filtered on
   * @return list of Tutorials
   */
  List<Tutorial> findByTitleContaining(String text);

  /**
   * returns all Tutorials which description contains input text
   * @param text  part of the title to be filtered on
   * @return list of Tutorials
   */
  List<Tutorial> findByDescriptionContaining(String text);
}
