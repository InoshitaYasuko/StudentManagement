package raisetech.StudentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行出来て空のリストが返ってくること() throws Exception {
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索が実行できて空のリストが返ってくること() throws Exception {
    when(service.searchStudent("999")).thenReturn(new StudentDetail());
    mockMvc.perform(get("/student/999"))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent("999");
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("1");
    student.setFullName("井上　愛 ");
    student.setFurigana("イノウエ　マナ");
    student.setNickname("まーちゃん");
    student.setEmail("ai.inoue@outlook.com");
    student.setCity("東京都世田谷区");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックに掛かること() {
    Student student = new Student();
    student.setId("テストです");
    student.setFullName("井上　愛 ");
    student.setFurigana("イノウエ　マナ");
    student.setNickname("まーちゃん");
    student.setEmail("ai.inoue@outlook.com");
    student.setCity("東京都世田谷区");
    student.setGender("女性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("IDは数字のみで入力してください");
  }

  @Test
  void 受講生情報詳細が1件検索し情報を取得できること() throws Exception {
    StudentDetail detail = new StudentDetail();

    Student student = new Student();
    student.setId("1");
    student.setFullName("井上　愛");
    student.setFurigana("イノウエ　マナ");
    student.setNickname("まーちゃん");
    student.setEmail("ai.inoue@outlook.com");
    student.setCity("東京都世田谷区");
    student.setGender("女性");

    detail.setStudent(student);

    when(service.searchStudent("1")).thenReturn(detail);

    mockMvc.perform(get("/student/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json"))
        .andExpect(jsonPath("$.student.id").value("1"))
        .andExpect(jsonPath("$.student.fullName").value("井上　愛"));

    //IDが不正の場合
    mockMvc.perform(get("/student/0"))
        .andExpect(status().isBadRequest());

    //IDが未入力の場合
    mockMvc.perform(get("/student"))
        .andExpect(status().isBadRequest());

    verify(service, times(1)).searchStudent("1");
  }

  @Test
  void 受講生情報の登録が実行ができること() throws Exception {
    String json = """
        {
          "student": {
            "id": "1",
            "fullName": "井上 愛",
            "furigana": "イノウエ マナ",
            "nickname": "まーちゃん",
            "email": "ai.inoue@outlook.com",
            "city": "東京都世田谷区",
            "gender": "女性"
          },
          "studentCourseList": [
          {
            "courseName": "ExcelVBA入門コース",
            "startDate": "2024-01-01",
            "endDate": "2024-03-31",
            "studentId": "1"
          }
         ]
        }
        """;

    mockMvc.perform(post("/registerStudent")
            .contentType("application/json")
            .content(json))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生情報の更新が実行できること() throws Exception {
    String json = """
        {
          "student": {
            "id": "1",
            "fullName": "井上 愛",
            "furigana": "イノウエ マナ",
            "nickname": "まーちゃん",
            "email": "ai.inoue@outlook.com",
            "city": "東京都世田谷区",
            "gender": "女性"
          },
          "studentCourseList": []
        }
        """;

    mockMvc.perform(put("/updateStudent")
            .contentType("application/json")
            .content(json))
        .andExpect(status().isOk())
        .andExpect(content().string("更新処理が成功しました。"));

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の例外APIが実行できてステータスが400で返ってくること() throws Exception {
    mockMvc.perform(get("/exception"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string(
            "現在、このAPIは利用できません。URLは「studentList」ではなく、「students」を利用してください"));
  }

  @Test
  void コース名が未入力の場合は400になること() throws Exception {
    String json = """
        {
          "student": {
            "id": "1",
            "fullName": "井上 愛",
            "furigana": "イノウエ マナ",
            "nickname": "まーちゃん",
            "email": "ai.inoue@outlook.com",
            "city": "東京都世田谷区",
            "gender": "女性"
          },
          "studentCourseList": [
            {
              "courseName": "",
              "startDate": "2024-01-01",
              "endDate": "2024-03-01"
            }
          ]
        }
        """;

    mockMvc.perform(post("/registerStudent")
            .contentType("application/json")
            .content(json))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(
            org.hamcrest.Matchers.containsString("コース名は必須です")
        ));
  }
}