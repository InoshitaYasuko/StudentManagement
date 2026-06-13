package raisetech.StudentManagement.controller.converter;

import lombok.Getter;
import lombok.Setter;
import raisetech.StudentManagement.data.ApplicationStatus;


@Getter
@Setter
public class ApplicationStatusRequest {
  private ApplicationStatus status;

  public ApplicationStatus getStatus(){
    return status;
  }
  public void setStatus(ApplicationStatus status){
    this.status = status;
  }
}
