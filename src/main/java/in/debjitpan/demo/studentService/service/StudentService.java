package in.debjitpan.demo.studentService.service;

import in.debjitpan.demo.studentService.domain.Student;
import in.debjitpan.demo.studentService.repository.StudentRepository;
import in.debjitpan.demo.studentService.service.exception.BadRequestException;
import in.debjitpan.demo.studentService.service.exception.StudentNotFoundException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  public Student addStudent(Student student) {
    Boolean existsEmail = studentRepository.selectExistsEmail(student.getEmail());

    if (student.getAge() < 5 || student.getAge() > 35) {
      throw new BadRequestException("Age is should be between 5 and 35");
    }
    if (existsEmail) {
      throw new BadRequestException("Email " + student.getEmail() + " taken");
    }

    return studentRepository.save(student);
  }

  public void deleteStudent(Long studentId) {
    if (!studentRepository.existsById(studentId)) {
      throw new StudentNotFoundException("Student with id " + studentId + " does not exists");
    }
    studentRepository.deleteById(studentId);
  }
}
