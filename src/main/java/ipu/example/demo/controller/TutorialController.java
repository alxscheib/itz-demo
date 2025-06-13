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

/**
 * REST controller for managing {@link Tutorial} resources.
 * Provides endpoints to create, read, update, delete, and search tutorials.
 */
@RestController
@RequestMapping("/api/tutorials")
@Tag(name = "Tutorial", description = "Tutorial management APIs")
public class TutorialController {

  private final TutorialService tutorialService;
  private final TutorialMapper tutorialMapper = Mappers.getMapper(TutorialMapper.class);

  /**
   * Constructor with dependency injection.
   *
   * @param tutorialService service layer for handling tutorial operations
   */
  public TutorialController(TutorialService tutorialService) {
    this.tutorialService = tutorialService;
  }

  /**
   * Retrieves tutorials filtered by optional title or description.
   * If neither is specified, returns all tutorials.
   *
   * @param title optional title filter
   * @param description optional description filter
   * @return list of matching {@link TutorialDto} or 204 if none found
   */
  @Operation(
      summary = "View Tutorials",
      description = "Filters Tutorials by title or description. If no title or description defined, returns all tutorials.",
      tags = { "tutorials", "get", "filter" })
  @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TutorialDto.class), mediaType = "application/json")})
  @ApiResponse(responseCode = "204", description = "No Tutorials found")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  @GetMapping("")
  public ResponseEntity<List<TutorialDto>> getTutorials(@RequestParam(required = false) String title,
      @RequestParam(required = false) String description) {
    try {
      List<Tutorial> tutorials;
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

      List<TutorialDto> dtos = tutorialMapper.tutorialsToDtos(tutorials);
      return new ResponseEntity<>(dtos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Retrieves a tutorial by its ID.
   *
   * @param id the tutorial ID
   * @return the tutorial if found, or 404 if not
   */
  @Operation(
      summary = "Find a Tutorial by Id",
      description = "Returns a single tutorial by ID.",
      tags = { "tutorials", "get" })
  @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = TutorialDto.class))})
  @ApiResponse(responseCode = "404", description = "Tutorial not found")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  @GetMapping("/{id}")
  public ResponseEntity<TutorialDto> getTutorialById(@PathVariable("id") long id) {
    Optional<Tutorial> tutorial = tutorialService.getTutorialById(id);
    return tutorial.map(value -> new ResponseEntity<>(tutorialMapper.tutorialToDto(value), HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  /**
   * Creates a new tutorial. If the request body is null, an empty tutorial is created.
   *
   * @param dto the tutorial data to create
   * @return the created tutorial
   */
  @Operation(
      summary = "Create a new Tutorial",
      description = "Creates a new tutorial from provided data or an empty tutorial if no data is provided.",
      tags = { "tutorials", "post" })
  @ApiResponse(responseCode = "201", description = "Tutorial successfully created",
      content = {@Content(schema = @Schema(implementation = TutorialDto.class))})
  @ApiResponse(responseCode = "500", description = "Internal server error")
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

  /**
   * Updates an existing tutorial by ID.
   *
   * @param id the ID of the tutorial to update
   * @param dto the new data for the tutorial
   * @return the updated tutorial, or 404 if not found
   */
  @Operation(
      summary = "Update a Tutorial by Id",
      description = "Updates a tutorial based on its ID.",
      tags = { "tutorials", "put" })
  @ApiResponse(responseCode = "200", description = "Tutorial successfully updated",
      content = {@Content(schema = @Schema(implementation = TutorialDto.class))})
  @ApiResponse(responseCode = "404", description = "Tutorial not found")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  @PutMapping("/{id}")
  public ResponseEntity<TutorialDto> updateTutorial(@PathVariable("id") long id, @RequestBody TutorialDto dto) {
    try {
      Tutorial tutorial = tutorialMapper.dtoToTutorial(dto);
      Optional<Tutorial> updated = tutorialService.updateTutorial(id, tutorial);
      return updated.map(value -> new ResponseEntity<>(tutorialMapper.tutorialToDto(value), HttpStatus.OK))
          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Deletes all tutorials from the database.
   *
   * @return HTTP 204 if successful
   */
  @Operation(
      summary = "Delete all Tutorials",
      description = "Removes all tutorials from the database.",
      tags = { "tutorials", "delete" })
  @ApiResponse(responseCode = "204", description = "All tutorials deleted")
  @ApiResponse(responseCode = "500", description = "Internal server error")
  @DeleteMapping("")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      tutorialService.deleteAllTutorials();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Deletes a tutorial by its ID.
   *
   * @param id the ID of the tutorial to delete
   * @return HTTP 204 if successful, or 500 on error
   */
  @Operation(
      summary = "Delete a Tutorial by Id",
      description = "Deletes the tutorial with the specified ID.",
      tags = { "tutorials", "delete" })
  @ApiResponse(responseCode = "204", description = "Tutorial successfully deleted")
  @ApiResponse(responseCode = "500", description = "Internal server error")
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
