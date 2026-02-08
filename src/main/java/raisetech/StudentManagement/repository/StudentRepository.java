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
 * 受講生情報を扱うリポジトリ。
 *
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 全件検索します。
   *
   * @return 全件検索した受講生情報の一覧
   */
  @Select("SELECT * FROM students WHERE is_deleted= 0")
  List<Student> search();

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student searchStudent(String id);

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchCourseList();

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  StudentCourse searchstudentcourse(String id);

  @Select("SELECT * FROM students WHERE id = #{id}")
  Student findStudentById(int id);

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourse> findStudentCourseByStudentId(int studentId);

  @Insert(
      "INSERT INTO students (full_name, furigana, nickname, email, city, age, gender, remark,is_deleted)"
          + " VALUES (#{fullName}, #{furigana}, #{nickname}, #{email}, #{city}, #{age}, #{gender}, #{remark},#{isDeleted})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertStudent(Student student);

  @Insert("INSERT INTO students_courses (student_id,course_name,start_date,end_date)"
      + " VALUES (#{studentId},#{courseName},#{startDate},#{endDate})")
  void insertStudentCourse(StudentCourse studentCourse);

  @Update("""
      UPDATE students 
      SET full_name = #{fullName},
      furigana = #{furigana},
      nickname = #{nickname},
      email = #{email},
      city = #{city},
      age = #{age},
      gender = #{gender},
      remark = #{remark},
      is_deleted = #{isDeleted}
      WHERE id = #{id}
      """)
  void updateStudent(Student student);

  @Update("""
            UPDATE students_courses
            SET
            course_name = #{courseName}
            WHERE student_id = #{studentId}
      """)
  void updateStudentCourse(StudentCourse studentCourse);
}