package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(11);
  }
  @Test
  void IDによる受講生の検索ができること(){
    Student actual = sut.findStudentById(1);

    assertThat(actual).isNotNull();
    assertThat(actual.getId()).isEqualTo("1");
  }
  @Test
  void 受講生の登録が行えること(){
    Student student = new Student();
    student.setFullName("三上　ネル");
    student.setFurigana("ミカミ　ネル");
    student.setNickname("ネルネル");
    student.setEmail("Spinel.Mikami@outlook.com");
    student.setCity("東京都八王子市");
    student.setAge(25);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.insertStudent(student);

    List<Student> actual = sut.search();

    assertThat(actual.size()).isEqualTo(12);

    Student registeredStudent = actual.get(actual.size() - 1);

    assertThat(registeredStudent.getFullName())
        .isEqualTo("三上　ネル");
  }
  @Test
  void コース情報の登録が行えること(){
    StudentCourse course = new StudentCourse();
    course.setStudentId("1");
    course.setCourseName("Javaコース");
    course.setStartDate(LocalDate.now());
    course.setEndDate(LocalDate.now().plusMonths(3));

    sut.insertStudentCourse(course);


    List<StudentCourse> courses = sut.findStudentCourseByStudentId(1);

    assertThat(courses).isNotEmpty();
  }
  @Test
  void 受講生情報の更新が行えること(){
    Student student = sut.findStudentById(1);
    student.setNickname("更新後");
    sut.updateStudent(student);
    Student updated = sut.findStudentById(1);

    assertThat(updated.getNickname())
        .isEqualTo("更新後");
  }
}