package raisetech.StudentManagement.controller.converter;

import raisetech.StudentManagement.data.ApplicationStatus;

public class ApplicationStatusRequest {
  private ApplicationStatus status;

  public ApplicationStatus getStatus(){
    return status;
  }
  public void setStatus(ApplicationStatus status){
    this.status = status;
  }
}
