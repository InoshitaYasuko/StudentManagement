package raisetech.StudentManagement.controller.conveter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter converter = new StudentConverter();

  @Test
  void 受講生情報とコース情報が正しく紐づくこと() {
    Student student = new Student();
    student.setId("1");

    StudentCourse course = new StudentCourse();
    course.setStudentId("1");

    List<StudentDetail> result = converter.convertStudentDetails(
        List.of(student),
        List.of(course)
    );

    assertEquals(1, result.size());

    StudentDetail detail = result.get(0);
    assertEquals(student, detail.getStudent());
    assertEquals(1, detail.getStudentCourseList().size());
  }
  @Test
  void 紐づくコースがない場合は空リストになること() {
    Student student = new Student();
    student.setId("1");

    StudentCourse course = new StudentCourse();
    course.setStudentId("999");

    List<StudentDetail> result = converter.convertStudentDetails(
        List.of(student),
        List.of(course)
    );

    assertEquals(0, result.get(0).getStudentCourseList().size());
  }
  @Test
  void 複数のコースが紐づくこと() {
    Student student = new Student();
    student.setId("1");

    StudentCourse course1 = new StudentCourse();
    course1.setStudentId("1");

    StudentCourse course2 = new StudentCourse();
    course2.setStudentId("1");

    List<StudentDetail> result = converter.convertStudentDetails(
        List.of(student),
        List.of(course1, course2)
    );

    assertEquals(2, result.get(0).getStudentCourseList().size());
  }
}