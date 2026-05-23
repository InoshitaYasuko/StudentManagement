package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Schema(description = "受講生コース情報")
  @Getter
  @Setter

  public class StudentCourse {
    private Integer id;

    //　受験IDのチェック
    @NotNull(message = "受講生IDは必須です")
    private Integer studentId;

    //　コース名のチェック
    @NotBlank(message = "コース名は必須です")
    private String courseName;

    //　受講開始日のチェック
    @NotNull(message = "開始日は必須です")
    private LocalDate startDate;

    //　受講終了日のチェック
    @NotNull(message = "終了日は必須です")
    private LocalDate endDate;

    // 日付の整合性チェック
    @AssertTrue(message = "終了日は開始日以降である必要があります")
    public boolean isValidDateRange() {
      if (startDate == null || endDate == null) {
        return true;
      }
      return !endDate.isBefore(startDate);
    }
    //　申込機能のチェック
    @AssertTrue(message = "申込ステータスを選択する必要があります")
    private ApplicationStatus applicationStatus;
    public ApplicationStatus getApplicationStatus(){
      return applicationStatus;
    }
    public void setApplicationStatus(ApplicationStatus applicationStatus){
      this.applicationStatus = applicationStatus;
    }
  }
