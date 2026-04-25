package raisetech.StudentManagement.data;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter

public class Student {

  /**
   * IDの入力チェックです。
   */
  @Pattern(regexp = "\\d+$", message = "IDは数字のみで入力してください")
  private String id;

  /**
   * フルネームの入力チェックです
   */
  @NotBlank(message = "氏名は必須です")
  @Size(max = 20,message = "氏名はフルネームで20文字以内です")
  private String fullName;

  /**
   * フリガナの入力チェックです
   */
  @NotBlank(message = "フリガナはカタカナで入力して下さい")
  private String furigana;

  /**
   * ニックネームの入力チェックです
   */
  @Size(min = 5, max = 10,message = "ニックネームは5文字以上、10文字以下で記載してください")
  private String nickname;

  /**
   * メールアドレスの入力チェックです
   */
  @NotBlank(message = "メールアドレスは必須です")
  @Email(message = "アドレス形式が正しくありません")
  private String email;
  /**
   * 住所の入力チェックです
   */
  @Size(max = 80, message = "住所は80文字以内で入力してください")
  private String city;

  /**
   * 年齢の入力チェックです
   */
  @Min(value = 0, message = "年齢は0以上で入力してください")
  @Max(value = 100, message = "年齢は100以下で入力してください")
  private Integer age;

  /**
   * 性別の入力チェックです
   */
  @Pattern(regexp = "^(男性|女性|回答しない)$",message = "性別は「男性・女性・回答しない」から選択してください")
  private String gender;

  /**
   * 備考欄の入力チェックです
   */
  @Size(max = 200, message = "備考は200文字以内で入力してください")
  private String remark;
  private boolean isDeleted;
}