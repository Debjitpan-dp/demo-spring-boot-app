package in.debjitpan.demo.studentService.student;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import in.debjitpan.demo.studentService.controller.StudentController;
import in.debjitpan.demo.studentService.domain.Gender;
import in.debjitpan.demo.studentService.domain.Student;
import in.debjitpan.demo.studentService.service.StudentService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest {

  @MockBean StudentService studentService;

  @Autowired MockMvc mockMvc;

  @Test
  void testCreateStudent() throws Exception {
    Student student =
        Student.builder()
            .name("Debjit")
            .email("debjit@mail.com")
            .age(26)
            .gender(Gender.MALE)
            .build();

    when(studentService.getAllStudents()).thenReturn(List.of(student));

    mockMvc
        .perform(get("/students"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .string(
                    containsString(
                        "[{\"id\":null,\"name\":\"Debjit\",\"email\":\"debjit@mail.com\",\"age\":26,\"gender\":\"MALE\"}]")))
        .andDo(print());
  }
}
