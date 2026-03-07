package raisetech.StudentManagement.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.conveter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository,StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行わないものになります。
   *
   * @return 受講生一覧(全件)
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchCourseList();
    return converter.convertStudentDetails(studentList, studentCourseList);
  }

  /**
   * 受講生詳細の検索です。
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id　受講生ID
   * @return 受講生詳細
   */
  public StudentDetail searchStudent(String id) {
    return searchStudentDetailById(Integer.parseInt(id));
  }
  public StudentDetail searchStudentDetailById(int id) {
    Student student = repository.findStudentById(id);
    List<StudentCourse> courses = repository.findStudentCourseByStudentId(id);
    return new StudentDetail(student, courses, false);
  }

  /***
   * 受講生詳細の登録を行います。
   * 受講生と受講生コース情報を個別に登録し、受講生コース情報には受講生情報を紐づける値やコース開始日、コース終了日を設定します。
   *
   * @param studentDetail　受講生詳細
   * @return　登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.insertStudent(student);
    Integer studentId = student.getId();
    studentDetail.getStudentCourseList().forEach(course -> {
      initStudentsCourse(course, studentId);
      repository.insertStudentCourse(course);
    });
    return studentDetail;
  }

  /***
   * 受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param course　受講生コース情報
   * @param studentId　受講生
   */
  private void initStudentsCourse(StudentCourse course, Integer studentId) {
    course.setStudentId(studentId);
    course.setStartDate(LocalDate.now());
    course.setEndDate(LocalDate.now().plusYears(1));
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    if (Boolean.TRUE.equals(studentDetail.getCancel())) {
      return;
    }
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourse course : studentDetail.getStudentCourseList()) {
      course.setStudentId(studentDetail.getStudent().getId());
      repository.updateStudentCourse(course);
    }
  }
}