package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、条件指定は行わないものになります。
   *
   * @return　受講生詳細一覧(全件)
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() throws TestException {
    return service.searchStudentList();
  }
  @GetMapping("/exception")
  public List<StudentDetail> getStudentListError() throws TestException {
    throw new TestException
        ("現在、このAPIは利用できません。URLは「studentList」ではなく、「students」を利用してください");
}

  /**
   * 受講生詳細検索です。 IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return　受講生詳細
   */
  @Operation(summary = "単体検索", description = "IDを指定して受講生を検索します")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "受講生情報の検索が完了しました"),
      @ApiResponse(responseCode = "400", description = "IDが不正です"),
      @ApiResponse(responseCode = "404", description = "該当受講生情報がありません")
  })
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id) {
    if (id == null || id.isEmpty() || id.equals("0")) {
      throw new IllegalArgumentException("IDが無効です");
  }
    return service.searchStudent(id);
  }
  @Operation(summary = "ID未入力エラー", description = "IDが未入力の時に発生するエラーです")
  @GetMapping("/student")
  public void getStudentEmpty() {
    throw new IllegalArgumentException("IDが未入力です");
  }

  /**
   * 受講生の登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return　実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "受講生情報の登録が完了しました"),
      @ApiResponse(responseCode = "400", description = "入力情報が未記入、または不正です")
  })
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @Valid @RequestBody StudentDetail studentDetail) {
    StudentDetail responseStuduntDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStuduntDetail);
  }

  /***
   * 受講生詳細の更新を行います。
   * キャンセルフラグの更新もここで行います。(論理削除)
   *
   * @param studentDetail　受講生詳細
   * @return　実行結果
   */
  @Operation(summary = "受講生更新", description = "受講生を更新します")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "受講生情報の更新が完了しました"),
      @ApiResponse(responseCode = "400", description = "入力値が不正です"),
      @ApiResponse(responseCode = "404", description = "該当受講生情報がありません")
  })
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@Valid @RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
