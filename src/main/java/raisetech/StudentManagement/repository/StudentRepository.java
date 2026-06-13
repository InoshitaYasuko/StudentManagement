package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.StudentManagement.data.ApplicationStatus;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentSearchCondition;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositroryです。
 */
@Mapper
public interface StudentRepository {
  List<Student> search();
  Student searchStudent(int id);
  List<StudentCourse> searchCourseList();
  Student findStudentById(int id);
  List<StudentCourse> findStudentCourseByStudentId(int studentId);
  List<Student> findStudentsByCondition(StudentSearchCondition condition);
  StudentCourse findCourseById(int courseId);
  void insertStudent(Student student);
  void insertStudentCourse(StudentCourse studentCourse);
  void updateStudent(Student student);
  void updateStudentCourse(StudentCourse studentCourse);
  int updateApplicationStatus(int courseId, ApplicationStatus status);
}