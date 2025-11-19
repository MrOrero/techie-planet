package com.techieplanet.application.modules.reports.service;

import com.techieplanet.application.modules.core.dto.response.SubjectReport;
import com.techieplanet.application.modules.core.model.Score;
import com.techieplanet.application.modules.core.model.Student;
import com.techieplanet.application.modules.core.model.Subject;
import com.techieplanet.application.modules.core.repository.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ReportService Unit Tests")
class ReportServiceTest {

    @Mock
    private ScoreRepository scoreRepository;

    @InjectMocks
    private ReportService reportService;

    private Subject mathSubject;
    private Subject physicsSubject;
    private Student student1;
    private Student student2;
    private Student student3;
    private Student student4;
    private Student student5;

    @BeforeEach
    void setUp() {
        mathSubject = new Subject("Mathematics");
        mathSubject.setId(1L);

        physicsSubject = new Subject("Physics");
        physicsSubject.setId(2L);

        student1 = new Student("Alice");
        student1.setId(1L);

        student2 = new Student("Bob");
        student2.setId(2L);

        student3 = new Student("Charlie");
        student3.setId(3L);

        student4 = new Student("David");
        student4.setId(4L);

        student5 = new Student("Eve");
        student5.setId(5L);
    }

    @Test
    @DisplayName("Should generate subject report with correct mean calculation")
    void shouldGenerateSubjectReportWithCorrectMean() {
        Score score1 = new Score(student1, mathSubject, 80);
        Score score2 = new Score(student2, mathSubject, 90);
        Score score3 = new Score(student3, mathSubject, 70);

        List<Score> mathScores = Arrays.asList(score1, score2, score3);
        Page<Score> scorePage = new PageImpl<>(Arrays.asList(score1), PageRequest.of(0, 10), 1);

        when(scoreRepository.findAll(any(Pageable.class))).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);

        Page<SubjectReport> reports = reportService.getSubjectReports(PageRequest.of(0, 10));

        assertThat(reports).isNotNull();
        assertThat(reports.getContent()).hasSize(1);
        
        SubjectReport report = reports.getContent().get(0);
        assertThat(report.getSubjectName()).isEqualTo("Mathematics");
        assertThat(report.getMean()).isEqualTo(80.0);
        
        verify(scoreRepository).findAll(any(Pageable.class));
        verify(scoreRepository).findBySubjectId(1L);
    }

    @Test
    @DisplayName("Should calculate median correctly for odd number of scores")
    void shouldCalculateMedianForOddNumberOfScores() {
        Score score1 = new Score(student1, mathSubject, 85);
        Score score2 = new Score(student2, mathSubject, 90);
        Score score3 = new Score(student3, mathSubject, 75);
        Score score4 = new Score(student4, mathSubject, 80);
        Score score5 = new Score(student5, mathSubject, 70);

        List<Score> mathScores = Arrays.asList(score1, score2, score3, score4, score5);
        Page<Score> scorePage = new PageImpl<>(Arrays.asList(score1), PageRequest.of(0, 10), 1);

        when(scoreRepository.findAll(any(Pageable.class))).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);

        Page<SubjectReport> reports = reportService.getSubjectReports(PageRequest.of(0, 10));

        SubjectReport report = reports.getContent().get(0);
        assertThat(report.getMedian()).isEqualTo(80.0);
    }

    @Test
    @DisplayName("Should calculate median correctly for even number of scores")
    void shouldCalculateMedianForEvenNumberOfScores() {
        Score score1 = new Score(student1, mathSubject, 70);
        Score score2 = new Score(student2, mathSubject, 80);
        Score score3 = new Score(student3, mathSubject, 90);
        Score score4 = new Score(student4, mathSubject, 100);

        List<Score> mathScores = Arrays.asList(score1, score2, score3, score4);
        Page<Score> scorePage = new PageImpl<>(Arrays.asList(score1), PageRequest.of(0, 10), 1);

        when(scoreRepository.findAll(any(Pageable.class))).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);

        Page<SubjectReport> reports = reportService.getSubjectReports(PageRequest.of(0, 10));

        SubjectReport report = reports.getContent().get(0);
        assertThat(report.getMedian()).isEqualTo(85.0);
    }

    @Test
    @DisplayName("Should calculate mode correctly with single mode")
    void shouldCalculateModeWithSingleMode() {
        Score score1 = new Score(student1, mathSubject, 80);
        Score score2 = new Score(student2, mathSubject, 80);
        Score score3 = new Score(student3, mathSubject, 90);
        Score score4 = new Score(student4, mathSubject, 70);

        List<Score> mathScores = Arrays.asList(score1, score2, score3, score4);
        Page<Score> scorePage = new PageImpl<>(Arrays.asList(score1), PageRequest.of(0, 10), 1);

        when(scoreRepository.findAll(any(Pageable.class))).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);

        Page<SubjectReport> reports = reportService.getSubjectReports(PageRequest.of(0, 10));

        SubjectReport report = reports.getContent().get(0);
        assertThat(report.getMode()).contains(80);
        assertThat(report.getMode()).hasSize(1);
    }

    @Test
    @DisplayName("Should calculate mode correctly with multiple modes")
    void shouldCalculateModeWithMultipleModes() {
        Score score1 = new Score(student1, mathSubject, 80);
        Score score2 = new Score(student2, mathSubject, 80);
        Score score3 = new Score(student3, mathSubject, 90);
        Score score4 = new Score(student4, mathSubject, 90);
        Score score5 = new Score(student5, mathSubject, 70);

        List<Score> mathScores = Arrays.asList(score1, score2, score3, score4, score5);
        Page<Score> scorePage = new PageImpl<>(Arrays.asList(score1), PageRequest.of(0, 10), 1);

        when(scoreRepository.findAll(any(Pageable.class))).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);

        Page<SubjectReport> reports = reportService.getSubjectReports(PageRequest.of(0, 10));

        SubjectReport report = reports.getContent().get(0);
        assertThat(report.getMode()).containsExactlyInAnyOrder(80, 90);
        assertThat(report.getMode()).hasSize(2);
    }

    @Test
    @DisplayName("Should include all student scores in report")
    void shouldIncludeAllStudentScoresInReport() {
        Score score1 = new Score(student1, mathSubject, 85);
        Score score2 = new Score(student2, mathSubject, 90);
        Score score3 = new Score(student3, mathSubject, 75);

        List<Score> mathScores = Arrays.asList(score1, score2, score3);
        Page<Score> scorePage = new PageImpl<>(Arrays.asList(score1), PageRequest.of(0, 10), 1);

        when(scoreRepository.findAll(any(Pageable.class))).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);

        Page<SubjectReport> reports = reportService.getSubjectReports(PageRequest.of(0, 10));

        SubjectReport report = reports.getContent().get(0);
        assertThat(report.getStudentScores()).hasSize(3);
        assertThat(report.getStudentScores()).containsEntry("Alice", 85);
        assertThat(report.getStudentScores()).containsEntry("Bob", 90);
        assertThat(report.getStudentScores()).containsEntry("Charlie", 75);
    }

    @Test
    @DisplayName("Should handle single score correctly")
    void shouldHandleSingleScore() {
        Score score1 = new Score(student1, mathSubject, 95);

        List<Score> mathScores = Arrays.asList(score1);
        Page<Score> scorePage = new PageImpl<>(Arrays.asList(score1), PageRequest.of(0, 10), 1);

        when(scoreRepository.findAll(any(Pageable.class))).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);

        Page<SubjectReport> reports = reportService.getSubjectReports(PageRequest.of(0, 10));

        SubjectReport report = reports.getContent().get(0);
        assertThat(report.getMean()).isEqualTo(95.0);
        assertThat(report.getMedian()).isEqualTo(95.0);
        assertThat(report.getMode()).contains(95);
        assertThat(report.getStudentScores()).hasSize(1);
    }

    @Test
    @DisplayName("Should handle pagination correctly")
    void shouldHandlePaginationCorrectly() {
        Score score1 = new Score(student1, mathSubject, 85);
        Score score2 = new Score(student2, physicsSubject, 90);

        List<Score> mathScores = Arrays.asList(score1);
        List<Score> physicsScores = Arrays.asList(score2);
        
        Page<Score> scorePage = new PageImpl<>(
            Arrays.asList(score1, score2), 
            PageRequest.of(0, 10), 
            2
        );

        when(scoreRepository.findAll(any(Pageable.class))).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);
        when(scoreRepository.findBySubjectId(2L)).thenReturn(physicsScores);

        Page<SubjectReport> reports = reportService.getSubjectReports(PageRequest.of(0, 10));

        assertThat(reports.getContent()).hasSize(2);
        assertThat(reports.getTotalElements()).isEqualTo(2);
        assertThat(reports.getContent().get(0).getSubjectName()).isEqualTo("Mathematics");
        assertThat(reports.getContent().get(1).getSubjectName()).isEqualTo("Physics");
    }

    @Test
    @DisplayName("Should calculate statistics for all unique scores")
    void shouldCalculateStatisticsForAllUniqueScores() {
        Score score1 = new Score(student1, mathSubject, 60);
        Score score2 = new Score(student2, mathSubject, 70);
        Score score3 = new Score(student3, mathSubject, 80);
        Score score4 = new Score(student4, mathSubject, 90);
        Score score5 = new Score(student5, mathSubject, 100);

        List<Score> mathScores = Arrays.asList(score1, score2, score3, score4, score5);
        Page<Score> scorePage = new PageImpl<>(Arrays.asList(score1), PageRequest.of(0, 10), 1);

        when(scoreRepository.findAll(any(Pageable.class))).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);

        Page<SubjectReport> reports = reportService.getSubjectReports(PageRequest.of(0, 10));

        SubjectReport report = reports.getContent().get(0);
        assertThat(report.getMean()).isEqualTo(80.0);
        assertThat(report.getMedian()).isEqualTo(80.0);
        assertThat(report.getMode()).hasSize(5);
    }

    @Test
    @DisplayName("Should calculate statistics for all same scores")
    void shouldCalculateStatisticsForAllSameScores() {
        Score score1 = new Score(student1, mathSubject, 85);
        Score score2 = new Score(student2, mathSubject, 85);
        Score score3 = new Score(student3, mathSubject, 85);

        List<Score> mathScores = Arrays.asList(score1, score2, score3);
        Page<Score> scorePage = new PageImpl<>(Arrays.asList(score1), PageRequest.of(0, 10), 1);

        when(scoreRepository.findAll(any(Pageable.class))).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);

        Page<SubjectReport> reports = reportService.getSubjectReports(PageRequest.of(0, 10));

        SubjectReport report = reports.getContent().get(0);
        assertThat(report.getMean()).isEqualTo(85.0);
        assertThat(report.getMedian()).isEqualTo(85.0);
        assertThat(report.getMode()).contains(85);
        assertThat(report.getMode()).hasSize(1);
    }

    @Test
    @DisplayName("Should handle two scores correctly")
    void shouldHandleTwoScores() {
        Score score1 = new Score(student1, mathSubject, 70);
        Score score2 = new Score(student2, mathSubject, 90);

        List<Score> mathScores = Arrays.asList(score1, score2);
        Page<Score> scorePage = new PageImpl<>(Arrays.asList(score1), PageRequest.of(0, 10), 1);

        when(scoreRepository.findAll(any(Pageable.class))).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);

        Page<SubjectReport> reports = reportService.getSubjectReports(PageRequest.of(0, 10));

        SubjectReport report = reports.getContent().get(0);
        assertThat(report.getMean()).isEqualTo(80.0);
        assertThat(report.getMedian()).isEqualTo(80.0);
        assertThat(report.getMode()).containsExactlyInAnyOrder(70, 90);
    }

    @Test
    @DisplayName("Should verify repository methods are called with correct parameters")
    void shouldVerifyRepositoryMethodCalls() {
        Score score1 = new Score(student1, mathSubject, 85);
        List<Score> mathScores = Arrays.asList(score1);
        Page<Score> scorePage = new PageImpl<>(Arrays.asList(score1), PageRequest.of(0, 10), 1);
        Pageable pageable = PageRequest.of(0, 10);

        when(scoreRepository.findAll(pageable)).thenReturn(scorePage);
        when(scoreRepository.findBySubjectId(1L)).thenReturn(mathScores);

        reportService.getSubjectReports(pageable);

        verify(scoreRepository, times(1)).findAll(pageable);
        verify(scoreRepository, times(1)).findBySubjectId(1L);
        verifyNoMoreInteractions(scoreRepository);
    }
}
