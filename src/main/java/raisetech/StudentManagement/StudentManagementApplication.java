package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication

@RestController
//@RequestMapping("/student")
public class StudentManagementApplication {

  public static void main(String[] args){
    SpringApplication.run(StudentManagementApplication.class, args);
  }
  private Map<String, Integer> student = new HashMap<>();

  //GET=取得
  @GetMapping("/student")
  private Map<String, Integer> getstudent(){
    return student;
  }
  //POST=追加機能
  @PostMapping("/student")
  public String addstudent(@RequestParam String name, @RequestParam int age){
    student.put(name,age);
    return "追加しました：" + name + "(" + age + ")";
  }
//PUT=更新機能
  @PutMapping("/student")
  public String updatestudent(@RequestParam String name, @RequestParam int age){
    if (student.containsKey(name)){
      student.put(name,age);
      return "更新しました：" + name + "(" + age + ")";
    }else {
      return "データがありません：" + name;
    }
  }
  }