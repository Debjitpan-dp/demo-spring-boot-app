package in.debjitpan.demo.studentService.student;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import in.debjitpan.demo.studentService.domain.Gender;
import in.debjitpan.demo.studentService.domain.Student;
import in.debjitpan.demo.studentService.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class StudentRepositoryTest {

  @Autowired private StudentRepository studentRepository;

  @AfterEach
  void tearDown() {
    studentRepository.deleteAll();
  }

  @Test
  void itShouldCheckWhenStudentEmailExists() {

    String email = "akash@gmail.com";
    Student student =
        Student.builder().name("Akash").email(email).age(23).gender(Gender.MALE).build();
    studentRepository.save(student);

    boolean expected = studentRepository.selectExistsEmail(email);

    assertThat(expected).isTrue();
  }

  @Test
  void itShouldCheckWhenStudentEmailDoesNotExists() {
    // given
    String email = "akash@gmail.com";

    // when
    boolean expected = studentRepository.selectExistsEmail(email);

    // then
    assertThat(expected).isFalse();
  }
}
