package raisetech.StudentManagement.data;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

  @Getter
  @Setter

  public class StudentCourse {
    private Integer id;
    private Integer studentId;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;
  }
