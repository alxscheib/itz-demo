package ipu.example.demo.repository;

import ipu.example.demo.model.Tutorial;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Tutorial} entities.
 * <p>
 * Extends JpaRepository to provide CRUD operations and
 * includes custom finder methods for searching by title or description.
 */
public interface TutorialRepository extends JpaRepository<Tutorial, Long> {

  /**
   * Retrieves all tutorials where the title contains the given text (case insensitive).
   *
   * @param text partial text to search for within tutorial titles
   * @return list of tutorials with titles containing the given text
   */
  List<Tutorial> findByTitleContainingIgnoreCase(String text);

  /**
   * Retrieves all tutorials where the description contains the given text (case insensitive).
   *
   * @param text partial text to search for within tutorial descriptions
   * @return list of tutorials with descriptions containing the given text
   */
  List<Tutorial> findByDescriptionContainingIgnoreCase(String text);
}
