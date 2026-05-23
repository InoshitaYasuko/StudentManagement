package raisetech.StudentManagement.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentSearchCondition {

  private String gender;
  private String fullName;
  private Integer age;
  private String email;
  private String city;

}
