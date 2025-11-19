package com.techieplanet.application.modules.core.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScoreRequest {

    @NotNull(message = "Student ID is mandatory")
    private Long studentId;

    @NotNull(message = "Subject ID is mandatory")
    private Long subjectId;

    @NotNull(message = "Score is mandatory")
    @Min(value = 0, message = "Score must be between 0 and 100")
    @Max(value = 100, message = "Score must be between 0 and 100")
    private Integer score;
}