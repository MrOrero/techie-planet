package com.techieplanet.application.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techieplanet.application.modules.core.dto.request.ScoreRequest;
import com.techieplanet.application.modules.core.model.Student;
import com.techieplanet.application.modules.core.model.Subject;
import com.techieplanet.application.modules.core.repository.ScoreRepository;
import com.techieplanet.application.modules.core.repository.StudentRepository;
import com.techieplanet.application.modules.core.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@DisplayName("API Integration Tests")
class ApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        scoreRepository.deleteAll();
        studentRepository.deleteAll();
        subjectRepository.deleteAll();
    }

    @Test
    @DisplayName("Should get all students")
    void shouldGetAllStudents() throws Exception {
        studentRepository.save(new Student("John Doe"));
        studentRepository.save(new Student("Jane Smith"));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    @DisplayName("Should get all subjects")
    void shouldGetAllSubjects() throws Exception {
        subjectRepository.save(new Subject("Mathematics"));
        subjectRepository.save(new Subject("Physics"));

        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Mathematics"))
                .andExpect(jsonPath("$[1].name").value("Physics"));
    }

    @Test
    @DisplayName("Should save new score successfully")
    void shouldSaveNewScore() throws Exception {
        Student student = studentRepository.save(new Student("Alice"));
        Subject subject = subjectRepository.save(new Subject("Chemistry"));

        ScoreRequest scoreRequest = new ScoreRequest();
        scoreRequest.setStudentId(student.getId());
        scoreRequest.setSubjectId(subject.getId());
        scoreRequest.setScore(92);

        mockMvc.perform(post("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scoreRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.score").value(92))
                .andExpect(jsonPath("$.student.name").value("Alice"))
                .andExpect(jsonPath("$.subject.name").value("Chemistry"));
    }

    @Test
    @DisplayName("Should update existing score")
    void shouldUpdateExistingScore() throws Exception {
        Student student = studentRepository.save(new Student("Bob"));
        Subject subject = subjectRepository.save(new Subject("History"));

        ScoreRequest firstRequest = new ScoreRequest();
        firstRequest.setStudentId(student.getId());
        firstRequest.setSubjectId(subject.getId());
        firstRequest.setScore(75);

        mockMvc.perform(post("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstRequest)))
                .andExpect(status().isCreated());

        ScoreRequest updateRequest = new ScoreRequest();
        updateRequest.setStudentId(student.getId());
        updateRequest.setSubjectId(subject.getId());
        updateRequest.setScore(88);

        mockMvc.perform(post("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.score").value(88))
                .andExpect(jsonPath("$.student.name").value("Bob"))
                .andExpect(jsonPath("$.subject.name").value("History"));
    }

    @Test
    @DisplayName("Should return 404 when student not found")
    void shouldReturn404WhenStudentNotFound() throws Exception {
        Subject subject = subjectRepository.save(new Subject("Biology"));

        ScoreRequest scoreRequest = new ScoreRequest();
        scoreRequest.setStudentId(9999L);
        scoreRequest.setSubjectId(subject.getId());
        scoreRequest.setScore(80);

        mockMvc.perform(post("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scoreRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 404 when subject not found")
    void shouldReturn404WhenSubjectNotFound() throws Exception {
        Student student = studentRepository.save(new Student("Charlie"));

        ScoreRequest scoreRequest = new ScoreRequest();
        scoreRequest.setStudentId(student.getId());
        scoreRequest.setSubjectId(9999L);
        scoreRequest.setScore(80);

        mockMvc.perform(post("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scoreRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 400 for invalid score (negative)")
    void shouldReturn400ForNegativeScore() throws Exception {
        Student student = studentRepository.save(new Student("David"));
        Subject subject = subjectRepository.save(new Subject("English"));

        ScoreRequest scoreRequest = new ScoreRequest();
        scoreRequest.setStudentId(student.getId());
        scoreRequest.setSubjectId(subject.getId());
        scoreRequest.setScore(-10);

        mockMvc.perform(post("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scoreRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return 400 for invalid score (over 100)")
    void shouldReturn400ForScoreOver100() throws Exception {
        Student student = studentRepository.save(new Student("Eve"));
        Subject subject = subjectRepository.save(new Subject("Art"));

        ScoreRequest scoreRequest = new ScoreRequest();
        scoreRequest.setStudentId(student.getId());
        scoreRequest.setSubjectId(subject.getId());
        scoreRequest.setScore(150);

        mockMvc.perform(post("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scoreRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should complete full workflow: create student, subject, and score")
    void shouldCompleteFullWorkflow() throws Exception {
        Student student = studentRepository.save(new Student("Frank"));
        Subject subject = subjectRepository.save(new Subject("Music"));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Frank"));

        mockMvc.perform(get("/api/subjects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Music"));

        ScoreRequest scoreRequest = new ScoreRequest();
        scoreRequest.setStudentId(student.getId());
        scoreRequest.setSubjectId(subject.getId());
        scoreRequest.setScore(95);

        mockMvc.perform(post("/api/scores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(scoreRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.score").value(95))
                .andExpect(jsonPath("$.student.name").value("Frank"))
                .andExpect(jsonPath("$.subject.name").value("Music"));
    }
}
