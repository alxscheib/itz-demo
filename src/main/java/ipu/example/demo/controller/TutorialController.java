package ipu.example.demo.controller;

import ipu.example.demo.dto.TutorialDto;
import ipu.example.demo.mapper.TutorialMapper;
import ipu.example.demo.model.Tutorial;
import ipu.example.demo.service.TutorialService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

  @Autowired
  TutorialService tutorialService;

  @GetMapping("")
  public ResponseEntity<List<TutorialDto>> getTutorials() {
    try {
      List<Tutorial> tutorials = tutorialService.getTutorials();
      List<TutorialDto> dtos = new ArrayList<TutorialDto>();

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      dtos = TutorialMapper.tutorialsToDtos(tutorials);
      return new ResponseEntity<>(dtos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<TutorialDto> getTutorialById(@PathVariable("id") long id) {
    Optional<Tutorial> tutorial = tutorialService.getTutorialById(id);

    if (tutorial.isPresent()) {
      TutorialDto dto = TutorialMapper.tutorialToDto(tutorial.get());
      return new ResponseEntity<>(dto, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("")
  public ResponseEntity<TutorialDto> createTutorial(@RequestBody TutorialDto dto) {
    try {
      Tutorial tutorial = TutorialMapper.dtoToTutorial(dto);
      Tutorial newTutorial = tutorialService.createTutorial(tutorial);
      TutorialDto newDto = TutorialMapper.tutorialToDto(newTutorial);
      return new ResponseEntity<>(newDto, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<TutorialDto> updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
    try{
      Optional<Tutorial> tutorialData = tutorialService.updateTutorial(id, tutorial);

      if (tutorialData.isPresent()) {
        TutorialDto dto = TutorialMapper.tutorialToDto(tutorialData.get());
        return new ResponseEntity<>(dto, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
    try {
      tutorialService.deleteTutorial(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      tutorialService.deleteAllTutorials();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/title/{text}")
  public ResponseEntity<List<TutorialDto>> findByTitleContaining(@PathVariable("text") String txt) {
    try {
      List<Tutorial> tutorials = tutorialService.findByTitleContaining(txt);

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(TutorialMapper.tutorialsToDtos(tutorials), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/description/{text}")
  public ResponseEntity<List<TutorialDto>> findByDescriptionContaining(@PathVariable("text") String txt) {
    try {
      List<Tutorial> tutorials = tutorialService.findByDescriptionContaining(txt);

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(TutorialMapper.tutorialsToDtos(tutorials), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
