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
    // given
    String email = "akash@gmail.com";
    Student student = new Student("Akash", email, Gender.MALE);
    studentRepository.save(student);

    // when
    boolean expected = studentRepository.selectExistsEmail(email);

    // then
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
