package raisetech.StudentManagement.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.ApplicationStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentSearchCondition;

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
  void IDによる受講生の検索ができること() {
    Student actual = sut.findStudentById(1);

    assertThat(actual).isNotNull();
    assertThat(actual.getId()).isEqualTo("1");
  }

  @Test
  void コース情報の全件検索できること() {
    List<StudentCourse> courseList = sut.searchCourseList();
    assertThat(courseList).isNotNull();
  }

  @Test
  void 受講生IDに紐づくコース情報の検索ができること() {
    List<StudentCourse> actual = sut.findStudentCourseByStudentId(1);

    assertThat(actual).isNotNull();
    assertThat(actual).isNotEmpty();
  }

  @Test
  void 受講生の登録が行えること() {
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
  void コース情報の登録が行えること() {
    StudentCourse course = new StudentCourse();
    course.setStudentId(1);
    course.setCourseName("Javaコース");
    course.setStartDate(LocalDate.now());
    course.setEndDate(LocalDate.now().plusMonths(3));

    sut.insertStudentCourse(course);

    List<StudentCourse> courses = sut.findStudentCourseByStudentId(1);

    assertThat(courses).isNotEmpty();
  }

  @Test
  void 受講生情報の更新が行えること() {
    Student student = sut.findStudentById(1);
    student.setNickname("更新後");
    sut.updateStudent(student);
    Student updated = sut.findStudentById(1);

    assertThat(updated.getNickname())
        .isEqualTo("更新後");
  }

  @Test
  void コース情報の更新が行えること() {
    List<StudentCourse> courses = sut.findStudentCourseByStudentId(1);
    StudentCourse course = courses.get(0);
    course.setCourseName("更新後コース");
    sut.updateStudentCourse(course);
    List<StudentCourse> updatedCourses = sut.findStudentCourseByStudentId(1);

    assertThat(updatedCourses.get(0).getCourseName()).isEqualTo("更新後コース");
  }

  @Test
  void 申込状況を更新できること() {
    List<StudentCourse> courses = sut.findStudentCourseByStudentId(1);
    StudentCourse course = courses.get(0);

    sut.updateApplicationStatus(
        course.getId(),
        ApplicationStatus.COMPLETED);

    List<StudentCourse> updatedCourses =
        sut.findStudentCourseByStudentId(1);

    StudentCourse updatedCourse = updatedCourses.stream()
        .filter(c -> c.getId().equals(course.getId()))
        .findFirst()
        .orElseThrow();

    assertThat(updatedCourse.getApplicationStatus())
        .isEqualTo(ApplicationStatus.COMPLETED);
  }

  @Test
  void 存在しないコースIDを更新しても更新件数は0件であること() {

    int count = sut.updateApplicationStatus(
        9999,
        ApplicationStatus.COMPLETED);

    assertThat(count).isZero();
  }

  @Test
  void 検索条件なしの場合は全件取得できること() {
    StudentSearchCondition condition = new StudentSearchCondition();

    List<Student> actual = sut.findStudentsByCondition(condition);

    assertThat(actual.size()).isEqualTo(11);
  }

  @Test
  void 申込状況が受講中の受講生で検索できること() {
    StudentSearchCondition condition = new StudentSearchCondition();
    condition.setApplicationStatus(ApplicationStatus.TAKING);

    List<Student> actual = sut.findStudentsByCondition(condition);

    actual.forEach(student ->
        System.out.println(
            "id=" + student.getId()
                + ", name=" + student.getFullName()));

    assertThat(actual)
        .extracting(Student::getId)
        .contains("1");
  }

  @Test
  void 条件に一致する受講生が存在しない場合は空リストが返ること() {
    StudentSearchCondition condition = new StudentSearchCondition();
    condition.setCourseName("スポーツクラス");

    List<Student> actual = sut.findStudentsByCondition(condition);

    assertThat(actual).isEmpty();
  }

  @Test
  void 氏名で部分一致検索できること() {
    StudentSearchCondition condition = new StudentSearchCondition();
    condition.setFullName("井上");

    List<Student> actual = sut.findStudentsByCondition(condition);

    assertThat(actual).isNotEmpty();

    assertThat(actual)
        .allMatch(student ->
            student.getFullName().contains("井上"));

  }

  @Test
  void 性別と申込状況を組み合わせて検索できること() {
    StudentSearchCondition condition =
        new StudentSearchCondition();

    condition.setGender("女性");
    condition.setApplicationStatus(ApplicationStatus.TAKING);

    List<Student> actual =
        sut.findStudentsByCondition(condition);

    assertThat(actual)
        .allMatch(student ->
            student.getGender().equals("女性"));
  }

  @Test
  void 存在しない受講生IDを検索した場合はnullが返ること() {
    Student actual = sut.findStudentById(9999);

    assertThat(actual).isNull();
  }
}