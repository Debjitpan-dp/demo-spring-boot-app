package in.debjitpan.demo.studentService.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table
public class Student {
  @Id
  @SequenceGenerator(
      name = "student_sequence",
      sequenceName = "student_sequence",
      allocationSize = 1)
  @GeneratedValue(generator = "student_sequence", strategy = GenerationType.SEQUENCE)
  private Long id;

  @NotBlank
  @Column(nullable = false)
  private String name;

  @Email
  @Column(nullable = false, unique = true)
  private String email;

  @Column
  private int age;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Gender gender;

  public Student(String name, String email, Gender gender) {
    this.name = name;
    this.email = email;
    this.gender = gender;
  }

  public Student(String name, String email, int age, Gender gender) {
    this.name = name;
    this.email = email;
    this.age = age;
    this.gender = gender;
  }

}
