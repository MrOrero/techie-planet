package com.techieplanet.application.modules.core.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectReport {
    private String subjectName;
    private Map<String, Integer> studentScores;
    private double mean;
    private double median;
    private List<Integer> mode;
}
