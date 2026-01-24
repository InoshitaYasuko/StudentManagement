package raisetech.StudentManagement.data;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {

  private Integer id;
  private String fullName;
  private String furigana;
  private String nickname;
  private String email;
  private String city;
  private Integer age;
  private String gender;
  private String remark;
  private boolean isDeleted;
}