package com.techieplanet.application.modules.reports.service;

import com.techieplanet.application.modules.core.dto.response.SubjectReport;
import com.techieplanet.application.modules.core.model.Score;
import com.techieplanet.application.modules.core.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ScoreRepository scoreRepository;

    public Page<SubjectReport> getSubjectReports(Pageable pageable) {
        Page<Score> scores = scoreRepository.findAll(pageable);
        return scores.map(score -> {
            List<Score> subjectScores = scoreRepository.findBySubjectId(score.getSubject().getId());
            return createSubjectReport(score.getSubject().getName(), subjectScores);
        });
    }

    private SubjectReport createSubjectReport(String subjectName, List<Score> scores) {
        Map<String, Integer> studentScores = scores.stream()
                .collect(Collectors.toMap(score -> score.getStudent().getName(), Score::getScore));

        List<Integer> scoreValues = new ArrayList<>(studentScores.values());
        
        double mean = scoreValues.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        
        Collections.sort(scoreValues);
        
        double median;

        if (scoreValues.size() % 2 == 0) {
            median = (scoreValues.get(scoreValues.size() / 2 - 1) + scoreValues.get(scoreValues.size() / 2)) / 2.0;
        } else {
            median = scoreValues.get(scoreValues.size() / 2);
        }

        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Integer s : scoreValues) {
            frequencyMap.put(s, frequencyMap.getOrDefault(s, 0) + 1);
        }

        int maxFrequency = 0;
        List<Integer> mode = new ArrayList<>();
        
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            int frequency = entry.getValue();
            if (frequency > maxFrequency) {
                maxFrequency = frequency;
                mode.clear();
                mode.add(entry.getKey());
            } else if (frequency == maxFrequency) {
                mode.add(entry.getKey());
            }
        }

        return new SubjectReport(subjectName, studentScores, mean, median, mode);
    }
}