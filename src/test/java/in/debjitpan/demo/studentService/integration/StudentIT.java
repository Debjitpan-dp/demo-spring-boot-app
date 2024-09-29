package in.debjitpan.demo.studentService.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.debjitpan.demo.studentService.domain.Gender;
import in.debjitpan.demo.studentService.domain.Student;
import in.debjitpan.demo.studentService.repository.StudentRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
public class StudentIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private StudentRepository studentRepository;

  @Test
  void canRegisterNewStudent() throws Exception {
    // given

    Student student = new Student("Debjit", "debjit@gmail.com", Gender.MALE);

    // when
    ResultActions resultActions =
        mockMvc.perform(
            post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
    // then
    resultActions.andExpect(status().isOk());
    List<Student> students = studentRepository.findAll();
    assertThat(students).usingElementComparatorIgnoringFields("id").contains(student);
  }

  @Test
  void canDeleteStudent() throws Exception {
    // given
    String name = "Debjit";
    String email = "debjit@gmail.com";

    Student student = new Student(name, email, Gender.FEMALE);

    mockMvc
        .perform(
            post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
        .andExpect(status().isOk());

    MvcResult getStudentsResult =
        mockMvc
            .perform(get("/students").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();

    String contentAsString = getStudentsResult.getResponse().getContentAsString();

    List<Student> students = objectMapper.readValue(contentAsString, new TypeReference<>() {});

    long id =
        students.stream()
            .filter(s -> s.getEmail().equals(student.getEmail()))
            .map(Student::getId)
            .findFirst()
            .orElseThrow(
                () -> new IllegalStateException("student with email: " + email + " not found"));

    // when
    ResultActions resultActions = mockMvc.perform(delete("/students/" + id));

    // then
    resultActions.andExpect(status().isOk());
    boolean exists = studentRepository.existsById(id);
    assertThat(exists).isFalse();
  }
}
