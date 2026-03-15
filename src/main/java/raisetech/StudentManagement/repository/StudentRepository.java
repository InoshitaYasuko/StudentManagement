package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositroryです。
 */
@Mapper
public interface StudentRepository {
  List<Student> search();
  Student searchStudent(String id);
  List<StudentCourse> searchCourseList();
  Student findStudentById(int id);
  List<StudentCourse> findStudentCourseByStudentId(int studentId);
  void insertStudent(Student student);
  void insertStudentCourse(StudentCourse studentCourse);
  void updateStudent(Student student);
  void updateStudentCourse(StudentCourse studentCourse);
}