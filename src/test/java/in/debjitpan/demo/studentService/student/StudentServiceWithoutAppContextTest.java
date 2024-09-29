package in.debjitpan.demo.studentService.student;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import in.debjitpan.demo.studentService.domain.Gender;
import in.debjitpan.demo.studentService.domain.Student;
import in.debjitpan.demo.studentService.repository.StudentRepository;
import in.debjitpan.demo.studentService.service.StudentService;
import in.debjitpan.demo.studentService.service.exception.BadRequestException;
import in.debjitpan.demo.studentService.service.exception.StudentNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class StudentServiceWithoutAppContextTest {

  @Mock private StudentRepository studentRepository;
  @InjectMocks private StudentService studentService;

  private static Student student;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @BeforeAll
  static void set() {
    student =
        Student.builder()
            .name("Akash")
            .email("akash@gmail.com")
            .age(23)
            .gender(Gender.MALE)
            .build();
  }

    @Test
    @Disabled
    void canGetAllStudents() {
      when(studentRepository.findAll()).thenReturn(null);
      studentService.getAllStudents();
      verify(studentRepository).findAll();
    }

  @Test
  @DisplayName("Student creation with success")
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

  @Test
  void testAddStudentFailedWhenEmailIsTaken() {
    Student student =
        Student.builder()
            .name("Akash")
            .email("akash@gmail.com")
            .age(23)
            .gender(Gender.MALE)
            .build();
    when(studentRepository.selectExistsEmail(anyString())).thenReturn(true);
    assertThatThrownBy(() -> studentService.addStudent(student))
        .isInstanceOf(BadRequestException.class)
        .hasMessageContaining("Email " + student.getEmail() + " taken");

    verify(studentRepository, never()).save(any());
  }

  @Test
  void testDeleteStudent() {
    long id = 10;
    given(studentRepository.existsById(id)).willReturn(true);
    studentService.deleteStudent(id);
    verify(studentRepository).deleteById(id);
  }

  @Test
  void willThrowWhenDeleteStudentNotFound() {
    long id = 10;
    given(studentRepository.existsById(id)).willReturn(false);
    assertThatThrownBy(() -> studentService.deleteStudent(id))
        .isInstanceOf(StudentNotFoundException.class)
        .hasMessageContaining("Student with id " + id + " does not exists");

    verify(studentRepository, never()).deleteById(any());
  }
}
