package ipu.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a tutorial entity stored in the database.
 * <p>
 * This class is mapped to the {@code tutorials} table using JPA annotations.
 * It contains basic fields such as {@code id}, {@code title}, and {@code description}.
 */
@Entity
@Table(name = "tutorials")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tutorial {

  /**
   * The unique identifier for the tutorial.
   * <p>
   * This ID is automatically generated.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  /**
   * The title of the tutorial.
   */
  @Column(name = "title")
  private String title;

  /**
   * The description of the tutorial.
   */
  @Column(name = "description")
  private String description;
}
