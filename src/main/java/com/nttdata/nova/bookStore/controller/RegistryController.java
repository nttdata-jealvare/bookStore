package com.nttdata.nova.bookStore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.nova.bookStore.dto.BookDTOJsonResponse;
import com.nttdata.nova.bookStore.dto.BookRegistryDTOJson;
import com.nttdata.nova.bookStore.service.IBookRegistryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/registry")
public class RegistryController {

	@Autowired
	private IBookRegistryService bookRegistryService;
	
	@GetMapping("/allRegistries")
	@Operation(summary = "Get all registries if there're any stored")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Found the registries", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = BookDTOJsonResponse.class)) })})
	public HttpEntity<List<BookRegistryDTOJson>> getAll(){
		
		return new ResponseEntity<List<BookRegistryDTOJson>>(this.bookRegistryService.getAll(), HttpStatus.OK);
	}
	
}
