package com.techieplanet.application.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.techieplanet.application.modules.core.model.Student;
import com.techieplanet.application.modules.core.model.Subject;
import com.techieplanet.application.modules.core.repository.StudentRepository;
import com.techieplanet.application.modules.core.repository.SubjectRepository;

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public DataSeeder(StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedStudents();
        seedSubjects();
    }

    private void seedStudents() {
        List<Student> students = Arrays.asList(
                new Student("John Doe"),
                new Student("Jane Smith"),
                new Student("Peter Jones"),
                new Student("Mary Williams"),
                new Student("David Brown")
        );

        for (Student student : students) {
            if (studentRepository.findByName(student.getName()).isEmpty()) {
                studentRepository.save(student);
            }
        }
    }

    private void seedSubjects() {
        List<Subject> subjects = Arrays.asList(
                new Subject("Mathematics"),
                new Subject("Science"),
                new Subject("History"),
                new Subject("English"),
                new Subject("Art")
        );

        for (Subject subject : subjects) {
            if (subjectRepository.findByName(subject.getName()).isEmpty()) {
                subjectRepository.save(subject);
            }
        }
    }
}
