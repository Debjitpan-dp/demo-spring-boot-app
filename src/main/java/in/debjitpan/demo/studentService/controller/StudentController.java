package in.debjitpan.demo.studentService.controller;

import in.debjitpan.demo.studentService.domain.Student;
import in.debjitpan.demo.studentService.service.StudentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/students")
@AllArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @GetMapping
  public List<Student> getAllStudents() {
    return studentService.getAllStudents();
  }

  @PostMapping
  public ResponseEntity<Student> addStudent(@Valid @RequestBody Student student) {
    return new ResponseEntity<>(studentService.addStudent(student), HttpStatus.CREATED);
  }

  @DeleteMapping(path = "{studentId}")
  public void deleteStudent(@PathVariable("studentId") Long studentId) {
    studentService.deleteStudent(studentId);
  }
}
