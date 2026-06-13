package raisetech.StudentManagement.domain;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.data.ApplicationStatus;

@Getter
@Setter
public class StudentSearchCondition {
  //受講生情報
  private String gender;
  private String fullName;
  private Integer age;
  private String email;
  private String city;

  //コース情報
  private String courseName;
  private LocalDate startDate;
  private LocalDate endDate;
  private ApplicationStatus applicationStatus;
}
