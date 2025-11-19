package com.techieplanet.application.modules.core.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techieplanet.application.modules.core.dto.request.ScoreRequest;
import com.techieplanet.application.modules.core.model.Score;
import com.techieplanet.application.modules.core.service.ScoreService;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @Operation(summary = "Save a new score")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Score created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Score> saveScore(@Valid @RequestBody ScoreRequest scoreRequest) {
        Score savedScore = scoreService.saveScore(scoreRequest);
        return new ResponseEntity<>(savedScore, HttpStatus.CREATED);
    }
}