package ipu.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for the {@link ipu.example.demo.model.Tutorial} entity.
 * <p>
 * This class is used to transfer tutorial data between the client and server,
 * without exposing the internal entity directly.
 */
@Builder
@Data
public class TutorialDto {

  /**
   * Unique identifier of the tutorial.
   */
  private Long id;

  /**
   * Title of the tutorial.
   * <p>
   * This field is required and must not be empty.
   */
  @NotEmpty
  private String title;

  /**
   * Optional description of the tutorial.
   */
  private String description;
}
