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

@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    return repository.search();
  }

  public List<StudentCourse> searchStudentCourseList() {
    return repository.searchCourseList();
  }

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
  public void registerStudent(StudentDetail studentDetail) {
    repository.insertStudent(studentDetail.getStudent());
    Integer studentId = studentDetail.getStudent().getId();
    for (StudentCourse course : studentDetail.getStudentCourse()) {
      course.setStudentId(studentId);
      course.setStartDate(LocalDate.now());
      repository.insertStudentCourse(course);
    }
  }
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    for (StudentCourse course : studentDetail.getStudentCourse()) {
      repository.updateStudentCourse(course);
    }
  }
}