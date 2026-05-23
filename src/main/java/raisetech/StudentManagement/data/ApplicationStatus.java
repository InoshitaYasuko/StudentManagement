package raisetech.StudentManagement.data;

public enum ApplicationStatus {

  TEMPORARY("仮申込"),
  OFFICIAL("本申込"),
  TAKING("受講中"),
  COMPLETED("受講終了");

  private final String label;

  ApplicationStatus(String label){
    this.label = label;
  }
  public String getLabel(){
    return label;
  }
}
