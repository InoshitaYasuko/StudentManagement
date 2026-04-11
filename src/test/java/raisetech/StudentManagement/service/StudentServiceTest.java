package raisetech.StudentManagement.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.conveter.StudentConverter;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せている事(){
    StudentService sut = new StudentService(repository, converter);
    List<StudentDetail> actual = sut.searchStudentList();
    //List<StudentDetail> expected = new ArrayList<>();
    Mockito.verify(repository, Mockito.times(1)).search();
    Mockito.verify(repository, Mockito.times(1)).searchCourseList();
    Mockito.verify(converter, Mockito.times(1)).convertStudentDetails();
    //Assertions.assertEquals(expected, actual);
  }
}