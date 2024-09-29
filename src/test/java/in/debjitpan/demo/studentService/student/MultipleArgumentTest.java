package in.debjitpan.demo.studentService.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class MultipleArgumentTest {

  //
  // https://www.baeldung.com/parameterized-tests-junit-5#:~:text=1.%20Overview.%20JUnit%205,%20the%20next%20generation%20of%20JUnit,%20facilitates

  public class Numbers {
    public static boolean isOdd(int number) {
      return number % 2 != 0;
    }
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 3, 5, -3, 15, Integer.MAX_VALUE})
  void isOdd_ShouldReturnTrueForOddNumbers(int number) {
    assertTrue(Numbers.isOdd(number));
  }

  // CSV source
  @ParameterizedTest
  @CsvSource({"test,TEST", "tEst,TEST", "Java,JAVA", "pa$$word, PA$$WORD"})
  void toUpperCase_ShouldGenerateTheExpectedUppercaseValue(String input, String expected) {
    String actualValue = input.toUpperCase();
    assertEquals(expected, actualValue);
  }

  // Method Resource
  public class Strings {
    public static boolean isBlank(String input) {
      return input == null || input.trim().isEmpty();
    }
  }

  @ParameterizedTest
  @MethodSource("provideStringsForIsBlank")
  void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected) {
    assertEquals(expected, Strings.isBlank(input));
  }

  private static Stream<Arguments> provideStringsForIsBlank() {
    return Stream.of(
        Arguments.of(null, true),
        Arguments.of("", true),
        Arguments.of("  ", true),
        Arguments.of("not blank", false));
  }
}
