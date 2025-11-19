package com.techieplanet.application.modules.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techieplanet.application.libs.common.exception.ResourceNotFoundException;
import com.techieplanet.application.modules.core.dto.request.ScoreRequest;
import com.techieplanet.application.modules.core.model.Score;
import com.techieplanet.application.modules.core.model.Student;
import com.techieplanet.application.modules.core.model.Subject;
import com.techieplanet.application.modules.core.repository.ScoreRepository;
import com.techieplanet.application.modules.core.repository.StudentRepository;
import com.techieplanet.application.modules.core.repository.SubjectRepository;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public Score saveScore(ScoreRequest scoreRequest) {
        Student student = studentRepository.findById(scoreRequest.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + scoreRequest.getStudentId()));

        Subject subject = subjectRepository.findById(scoreRequest.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + scoreRequest.getSubjectId()));

        Score scoreToSave = scoreRepository.findByStudentIdAndSubjectId(student.getId(), subject.getId())
                .map(existingScore -> {
                    existingScore.setScore(scoreRequest.getScore());
                    return existingScore;
                })
                .orElse(new Score(student, subject, scoreRequest.getScore()));

        return scoreRepository.save(scoreToSave);
    }
}