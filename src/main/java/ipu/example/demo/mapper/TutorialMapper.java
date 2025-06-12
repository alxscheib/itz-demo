package ipu.example.demo.mapper;

import ipu.example.demo.dto.TutorialDto;
import ipu.example.demo.model.Tutorial;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper
public interface TutorialMapper {

 TutorialDto tutorialToDto(Tutorial tutorial);

  List<TutorialDto> tutorialsToDtos(@NotNull List<Tutorial> tutorials);


  Tutorial dtoToTutorial(@NotNull TutorialDto dto);

}
