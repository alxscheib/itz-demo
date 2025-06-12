package ipu.example.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ipu.example.demo.dto.TutorialDto;
import ipu.example.demo.mapper.TutorialMapper;
import ipu.example.demo.model.Tutorial;
import ipu.example.demo.service.TutorialService;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.factory.Mappers;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Tutorial", description = "Tutorial management APIs")
@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

  private final TutorialService tutorialService;
  private final TutorialMapper tutorialMapper = Mappers.getMapper(TutorialMapper.class);

  @Autowired
  public TutorialController(TutorialService tutorialService) {
    this.tutorialService = tutorialService;
  }

  @Operation(summary = "View Tutorials",
      description = "Filters Tutorials by title or description. If no title or description defined return all tutorials.",
      tags = { "tutorials", "get", "filter" })
  @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TutorialDto.class), mediaType = "application/json") })
  @ApiResponse(responseCode = "204", description = "No Tutorials found", content = {@Content(schema = @Schema()) })
  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
  @GetMapping("")
  public ResponseEntity<List<TutorialDto>> getTutorials(@RequestParam(required = false) String title, @RequestParam(required = false) String description) {
    try {
      List<Tutorial> tutorials;
      List<TutorialDto> dtos;

      if (StringUtils.isNotEmpty(title)) {
        tutorials = tutorialService.findByTitleContaining(title);
      } else if (StringUtils.isNotEmpty(description)) {
        tutorials = tutorialService.findByDescriptionContaining(description);
      } else {
        tutorials = tutorialService.getAllTutorials();
      }

      if (tutorials.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      dtos = tutorialMapper.tutorialsToDtos(tutorials);
      return new ResponseEntity<>(dtos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(
      summary = "Find a Tutorial by Id",
      description = "Get a Tutorial object by sended id. The response is Tutorial object with id, title and description.",
      tags = { "tutorials", "get" })
  @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TutorialDto.class), mediaType = "application/json") })
  @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }, description = "No Tutorials found")
  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
  @GetMapping("/{id}")
  public ResponseEntity<TutorialDto> getTutorialById(@PathVariable("id") long id) {
    Optional<Tutorial> tutorial = tutorialService.getTutorialById(id);

    if (tutorial.isPresent()) {
      TutorialDto dto = tutorialMapper.tutorialToDto(tutorial.get());
      return new ResponseEntity<>(dto, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @Operation(
      summary = "Create a new Tutorial",
      description = "Create a new tutorial based on the passed tutorial information. If values are passed, create an empty tutorial.",
      tags = { "tutorials", "post" })
  @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = TutorialDto.class), mediaType = "application/json") })
  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
  @PostMapping("")
  public ResponseEntity<TutorialDto> createTutorial(@RequestBody(required = false) TutorialDto dto) {
    try {
      Tutorial tutorial = tutorialMapper.dtoToTutorial(dto);
      Tutorial newTutorial = tutorialService.createTutorial(tutorial);
      TutorialDto newDto = tutorialMapper.tutorialToDto(newTutorial);
      return new ResponseEntity<>(newDto, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(
      summary = "Update a Tutorial by Id",
      description = "Update the tutorial defined by ID.",
      tags = { "tutorials", "put" })
  @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TutorialDto.class), mediaType = "application/json") })
  @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }, description = "Tutorial not found")
  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
  @PutMapping("/{id}")
  public ResponseEntity<TutorialDto> updateTutorial(@PathVariable("id") long id, @RequestBody TutorialDto dto) {
    try{
      Tutorial tutorial = tutorialMapper.dtoToTutorial(dto);
      Optional<Tutorial> tutorialData = tutorialService.updateTutorial(id, tutorial);

      if (tutorialData.isPresent()) {
        TutorialDto newDto = tutorialMapper.tutorialToDto(tutorialData.get());
        return new ResponseEntity<>(newDto, HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(
      summary = "Delete all Tutorials",
      description = "Delete all Tutorials.",
      tags = { "tutorials", "delete" })
  @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) })
  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
  @DeleteMapping("")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      tutorialService.deleteAllTutorials();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @Operation(
      summary = "Delete a Tutorial by Id",
      description = "Delete a Tutorial defined by Id",
      tags = { "tutorials", "delete" })
  @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema()) })
  @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })
  @DeleteMapping("/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
    try {
      tutorialService.deleteTutorial(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
