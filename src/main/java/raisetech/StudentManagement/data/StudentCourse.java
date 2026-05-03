package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

  @Schema(description = "受講生コース情報")
  @Getter
  @Setter

  public class StudentCourse {
    private Integer id;

    @NotBlank(message = "受講生IDは必須です")
    private String studentId;

    @NotBlank(message = "コース名は必須です")
    private String courseName;

    @NotNull(message = "開始日は必須です")
    private LocalDate startDate;

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
  }
