package raisetech.StudentManagement.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentCourse> studentCourse;

  private Boolean cancel;
  public Boolean getCancel(){
    return cancel;
  }
  public void setCancel(Boolean cancel){
    this.cancel = cancel;
  }
}
