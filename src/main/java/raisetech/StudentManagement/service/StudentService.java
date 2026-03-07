package raisetech.StudentManagement.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  /**
   * 受講生一覧検索です。
   * 全件検索を行うので、条件指定は行わないものになります。
   *
   * @return 受講生一覧(全件)
   */
  public List<Student> searchStudentList() {
    return repository.search();
  }

  /**
   * 受講コース検索です
   *
   * @return 受講コース検索
   */
  public List<StudentCourse> searchStudentCourseList() {
    return repository.searchCourseList();
  }

  /**
   * 受講生検索です。
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id　受講生ID
   * @return 受講生
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourses = repository.findStudentCourseByStudentId(student.getId());
    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourse(studentCourses);
    return detail;
  }

  public StudentDetail searchStudentDetailById(int id) {
    Student student = repository.findStudentById(id);
    List<StudentCourse> courses = repository.findStudentCourseByStudentId(id);

    StudentDetail detail = new StudentDetail();
    detail.setStudent(student);
    detail.setStudentCourse(courses);
    return detail;
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    repository.insertStudent(studentDetail.getStudent());
    Integer studentId = studentDetail.getStudent().getId();
    for (StudentCourse course : studentDetail.getStudentCourse()) {
      course.setStudentId(studentId);
      course.setStartDate(LocalDate.now());
      repository.insertStudentCourse(course);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    if (Boolean.TRUE.equals(studentDetail.getCancel())) {
      return;
    }
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourse course : studentDetail.getStudentCourse()) {
      course.setStudentId(studentDetail.getStudent().getId());
      repository.updateStudentCourse(course);
    }
  }
}