package ipu.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TutorialDto {
  @NotEmpty
  private long id;

  @NotEmpty
  private String title;

  private String description;
}
