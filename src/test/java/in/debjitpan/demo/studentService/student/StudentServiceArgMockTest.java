package in.debjitpan.demo.studentService.student;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import in.debjitpan.demo.studentService.domain.Gender;
import in.debjitpan.demo.studentService.domain.Student;
import in.debjitpan.demo.studentService.repository.StudentRepository;
import in.debjitpan.demo.studentService.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StudentService.class)
public class StudentServiceArgMockTest {

  @MockBean private StudentRepository studentRepository;
  @Autowired private StudentService studentService;

  @Test
  void testAddStudentSucceed() {

    Student student =
        Student.builder()
            .name("Akash")
            .email("akash@gmail.com")
            .age(23)
            .gender(Gender.MALE)
            .build();
    when(studentRepository.selectExistsEmail("akash@gmail.com")).thenReturn(false);
    when(studentRepository.save(student)).thenReturn(student);

    Student result = studentService.addStudent(student);

    assertThat(result.getName()).isEqualTo("Akash");
    assertThat(result.getAge()).isEqualTo(23);
    //    assertThat(result.getId()).isNotNull();

    verify(studentRepository).save(student);
  }
}
