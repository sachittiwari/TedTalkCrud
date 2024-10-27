package com.sachit.tedtalk.controller;

import com.sachit.tedtalk.model.TedTalkRequestDTO;
import com.sachit.tedtalk.model.TedTalkResponseDTO;
import com.sachit.tedtalk.service.TedTalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/tedtalk")
@Slf4j
public class TedTalkController {

    @Autowired
    TedTalkService tedTalkService;

    @Operation(summary = "Get result of all ted talks",
            description = "This API returns all ted talks present in ted talk application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of ted talks",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = TedTalkResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unable to retrieve list of ted talks",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @GetMapping("/")
    public List<TedTalkResponseDTO> getAllTedTalkData() {
        try {
            List<TedTalkResponseDTO> tedTalkData = tedTalkService.getAllTedTalks();
            return tedTalkData;
        }
        catch(Exception e){
            log.error("Error occurred while fetching all ted talks", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to fetch the details");
        }
    }

    @Operation(summary = "Get specific ted talk",
            description = "This API returns ted talk based on the id specified")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the specific ted talk",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = TedTalkResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Ted Talk with requested id not found",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @GetMapping("/{id}")
    public TedTalkResponseDTO getTedTalkData(Long id) {
        try {
            TedTalkResponseDTO tedTalk = tedTalkService.getTedTalk(id);
            return tedTalk;
        }
        catch(Exception e){
            log.error("Error occurred while fetching the specific ted talk", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to fetch the details");
        }
    }

    @Operation(summary = "Create a new Ted Talk",
            description = "This API will create a new ted talk based on that information supplied")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created the ted talk",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = TedTalkResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Unable to create the ted talk",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @PostMapping("/")
    public TedTalkResponseDTO createTedTalk( @RequestBody TedTalkRequestDTO tedTalk) {
        try {
            TedTalkResponseDTO newTedTalk = tedTalkService.createTedTalk(tedTalk);
            return newTedTalk;
        }
        catch(Exception e){
            log.error("Unable to create the new ted talk", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to create new Ted Talk");
        }
    }

    @Operation(summary = "Update Ted Talk details for requested id",
            description = "This API will update the ted talk details for the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the ted talk details",
                    content=@Content(mediaType = "application/json",schema = @Schema(implementation = TedTalkResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "No ted talk with specified id available",
                    content=@Content(mediaType = "application/json",schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Unable to update the ted talk details",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @PutMapping("/{id}")
    public TedTalkResponseDTO updateTedTalk( @PathVariable Long id,@RequestBody TedTalkRequestDTO tedTalk) {
        try {
            TedTalkResponseDTO updatedTedTalk = tedTalkService.updateTedTalk(id,tedTalk);
            return updatedTedTalk;
        }
        catch(ArithmeticException e){
            log.error("No Ted Talk Available with the given requested id", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No ted talk available with requested id");
        }
        catch(Exception e){
            log.error("Unable to update the ted talk", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to update the ted talk");
        }
    }

    @Operation(summary = "Delete Ted Talk details for requested id",
            description = "This API will delete the ted talk details for the specified id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the ted talk details",
                    content=@Content(mediaType = "text",schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "No ted talk with specified id available",
                    content=@Content(mediaType = "application/json",schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Unable to delete the ted talk details",
                    content=@Content(mediaType = "application/json",schema = @Schema()))
    })
    @DeleteMapping("/{id}")
    public String deleteTedTalk(@PathVariable Long id) {
        try {
            tedTalkService.deleteTedTalk(id);
            return "Successfully deleted the ted talk details";
        }
        catch(Exception e){
            log.error("No Ted Talk Available with the given id", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No ted talk available with the given id");
        }
    }






}