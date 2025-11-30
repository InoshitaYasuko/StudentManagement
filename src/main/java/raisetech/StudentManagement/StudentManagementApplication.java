package raisetech.StudentManagement;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
public class StudentManagementApplication {

  @Autowired
  private StudentRepository repository;

  private String name = "Inoshita Yasuko";
  private String age = "32";

  public static void main(String[] args){
    SpringApplication.run(StudentManagementApplication.class, args);
  }
  @GetMapping("/studentInfo")
  public String getstdentInfo() {
    Student student = repository.searchByName("Utiharyou");
    return student.getName() + " " + student.getAge() + "歳";
  }
  @PostMapping("/studentInfo")
  public void setstudentInfo(String name,String age) {
    this.name = name;
    this.age = age;
  }
  @PostMapping("/studentName")
  public void updatsStundentName(String nama) {
    this.name = nama;
  }
  }