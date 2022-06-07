package com.nttdata.nova.bookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.nova.bookStore.dto.EditorialDTOJsonRequest;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonRequestExtended;
import com.nttdata.nova.bookStore.dto.EditorialDTOJsonResponse;
import com.nttdata.nova.bookStore.exception.InvalidNameExtensionException;
import com.nttdata.nova.bookStore.exception.editorialNotFoundException;
import com.nttdata.nova.bookStore.service.IEditorialService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/editorial")
public class EditorialController {

	@Autowired
	private IEditorialService editorialService;

	public void addLinksIntoResponse(EditorialDTOJsonResponse editorial) {
		editorial.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(EditorialController.class).getEditorialById(editorial.getId()))
				.withSelfRel());
	}

	@GetMapping("/editorials")
	@Operation(summary = "Get all editorials if there're any stored")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the editorials", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = EditorialDTOJsonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content) })
	public HttpEntity<List<EditorialDTOJsonResponse>> getEditorials() {
		List<EditorialDTOJsonResponse> editorials = this.editorialService.getAllEditorials();

		for (EditorialDTOJsonResponse e : editorials) {
			addLinksIntoResponse(e);
		}

		return new ResponseEntity<List<EditorialDTOJsonResponse>>(editorials, HttpStatus.OK);
	}

	@GetMapping("/editorial/{id}")
	@Operation(summary = "Get a editorial by ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the editorial", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = EditorialDTOJsonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Editorial not found") })
	public HttpEntity<EditorialDTOJsonResponse> getEditorialById(@PathVariable Long id) {

		EditorialDTOJsonResponse response = this.editorialService.getEditorialById(id);

		if (response == null) {
			throw new editorialNotFoundException();
		}

		addLinksIntoResponse(response);

		return new ResponseEntity<EditorialDTOJsonResponse>(response, HttpStatus.OK);
	}

	@GetMapping("/editorialName/{name}")
	@Operation(summary = "Get a editorial by name")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the editorial", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = EditorialDTOJsonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid name supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Editorial not found") })
	public HttpEntity<EditorialDTOJsonResponse> getEditorialByName(@PathVariable String name) {

		EditorialDTOJsonResponse response = this.editorialService.getEditorialByName(name);
		addLinksIntoResponse(response);

		return new ResponseEntity<EditorialDTOJsonResponse>(response, HttpStatus.OK);
	}

	@DeleteMapping("/editorial/{id}")
	@Operation(summary = "Delete all editorials")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Delete succesfully"),
			@ApiResponse(responseCode = "404", description = "Editorial not found") })
	public HttpEntity<String> deleteAEditorial(@PathVariable Long id) {
		this.editorialService.deleteById(id);
		return new ResponseEntity<String>("Delete succesfully", HttpStatus.OK);
	}

	@DeleteMapping("/editorials")
	@Operation(summary = "Delete all editorials")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Delete succesfully") })
	public HttpEntity<String> deleteAllEditorials() {
		this.editorialService.deleteAll();
		return new ResponseEntity<String>("Delete all editorials", HttpStatus.OK);
	}

	@PostMapping("/editorial")
	@Operation(summary = "Add a editorial")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Editorial add", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = EditorialDTOJsonResponse.class)) }), })
	public HttpEntity<EditorialDTOJsonResponse> addAEditorial(@RequestBody EditorialDTOJsonRequest editorial) {
		if (editorial.getName().length() <= 4) {
			throw new InvalidNameExtensionException();
		}

		EditorialDTOJsonResponse response = this.editorialService.create(editorial);
		addLinksIntoResponse(response);

		return new ResponseEntity<EditorialDTOJsonResponse>(response, HttpStatus.CREATED);
	}

	@PutMapping("/editorial")
	@Operation(summary = "Update a editorial")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Editorial updated", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = EditorialDTOJsonResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "Editorial not found") })
	public HttpEntity<EditorialDTOJsonResponse> updateAEditorial(@RequestBody EditorialDTOJsonRequestExtended editorial) {
		EditorialDTOJsonResponse response = this.editorialService.update(editorial);

		if (response == null) {
			throw new editorialNotFoundException();
		}

		addLinksIntoResponse(response);
		return new ResponseEntity<EditorialDTOJsonResponse>(response, HttpStatus.OK);
	}
}
