package ipu.example.demo.mapper;

import ipu.example.demo.dto.TutorialDto;
import ipu.example.demo.model.Tutorial;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


public class TutorialMapper {

  public static TutorialDto tutorialToDto(@NotNull Tutorial tutorial){
    return TutorialDto.builder()
        .id(tutorial.getId())
        .title(tutorial.getTitle())
        .description(tutorial.getDescription())
        .build();
  }

  public static List<TutorialDto> tutorialsToDtos(@NotNull List<Tutorial> tutorials){
    List<TutorialDto> dtos = new ArrayList<TutorialDto>();
    tutorials.stream().map(this::tutorialToDto).forEach(dtos::add);
    return dtos;
  }


  public static Tutorial dtoToTutorial(@NotNull TutorialDto dto){
    return Tutorial.builder()
        .id(dto.getId())
        .title(dto.getTitle())
        .description(dto.getDescription())
        .build();
  }

  public static List<Tutorial> dtosToTutorials(@NotNull List<TutorialDto> dtos){
    List<Tutorial> tutorials = new ArrayList<Tutorial>();
    dtos.stream().map(this::dtoToTutorial).forEach(tutorials::add);
    return tutorials;
  }
}
