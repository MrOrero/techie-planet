package com.techieplanet.application.modules.core.service;

import com.techieplanet.application.libs.common.exception.ResourceNotFoundException;
import com.techieplanet.application.modules.core.dto.request.ScoreRequest;
import com.techieplanet.application.modules.core.model.Score;
import com.techieplanet.application.modules.core.model.Student;
import com.techieplanet.application.modules.core.model.Subject;
import com.techieplanet.application.modules.core.repository.ScoreRepository;
import com.techieplanet.application.modules.core.repository.StudentRepository;
import com.techieplanet.application.modules.core.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ScoreService Unit Tests")
class ScoreServiceTest {

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private ScoreService scoreService;

    private Student student;
    private Subject subject;
    private ScoreRequest scoreRequest;

    @BeforeEach
    void setUp() {
        student = new Student("John Doe");
        student.setId(1L);

        subject = new Subject("Mathematics");
        subject.setId(1L);

        scoreRequest = new ScoreRequest();
        scoreRequest.setStudentId(1L);
        scoreRequest.setSubjectId(1L);
        scoreRequest.setScore(85);
    }

    @Test
    @DisplayName("Should save new score successfully")
    void shouldSaveNewScore() {
        Score expectedScore = new Score(student, subject, 85);
        expectedScore.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(scoreRepository.findByStudentIdAndSubjectId(1L, 1L)).thenReturn(Optional.empty());
        when(scoreRepository.save(any(Score.class))).thenReturn(expectedScore);

        Score savedScore = scoreService.saveScore(scoreRequest);

        assertThat(savedScore).isNotNull();
        assertThat(savedScore.getId()).isEqualTo(1L);
        assertThat(savedScore.getScore()).isEqualTo(85);
        assertThat(savedScore.getStudent()).isEqualTo(student);
        assertThat(savedScore.getSubject()).isEqualTo(subject);

        verify(studentRepository).findById(1L);
        verify(subjectRepository).findById(1L);
        verify(scoreRepository).findByStudentIdAndSubjectId(1L, 1L);
        verify(scoreRepository).save(any(Score.class));
    }

    @Test
    @DisplayName("Should update existing score")
    void shouldUpdateExistingScore() {
        Score existingScore = new Score(student, subject, 75);
        existingScore.setId(1L);

        Score updatedScore = new Score(student, subject, 85);
        updatedScore.setId(1L);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(scoreRepository.findByStudentIdAndSubjectId(1L, 1L)).thenReturn(Optional.of(existingScore));
        when(scoreRepository.save(existingScore)).thenReturn(updatedScore);

        Score savedScore = scoreService.saveScore(scoreRequest);

        assertThat(savedScore).isNotNull();
        assertThat(savedScore.getScore()).isEqualTo(85);
        verify(scoreRepository).save(existingScore);
    }

    @Test
    @DisplayName("Should throw exception when student not found")
    void shouldThrowExceptionWhenStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scoreService.saveScore(scoreRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student not found with id: 1");

        verify(studentRepository).findById(1L);
        verify(subjectRepository, never()).findById(anyLong());
        verify(scoreRepository, never()).save(any(Score.class));
    }

    @Test
    @DisplayName("Should throw exception when subject not found")
    void shouldThrowExceptionWhenSubjectNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> scoreService.saveScore(scoreRequest))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Subject not found with id: 1");

        verify(studentRepository).findById(1L);
        verify(subjectRepository).findById(1L);
        verify(scoreRepository, never()).save(any(Score.class));
    }
}
