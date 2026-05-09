CREATE TABLE IF NOT EXISTS students
 (
  id int NOT NULL AUTO_INCREMENT,
  full_name varchar(100) NOT NULL,
  furigana varchar(100) DEFAULT NULL,
  nickname varchar(50) DEFAULT NULL,
  email varchar(255) NOT NULL,
  city varchar(100) DEFAULT NULL,
  age int DEFAULT NULL,
  gender varchar(50) DEFAULT NULL,
  remark varchar(255) DEFAULT NULL,
  is_deleted boolean DEFAULT FALSE,
  PRIMARY KEY (id),
  UNIQUE (email)
  );

   CREATE TABLE IF NOT EXISTS students_courses
    (
    id int NOT NULL AUTO_INCREMENT,
    student_id int NOT NULL,
    course_name varchar(100) NOT NULL,
    start_date date NOT NULL,
    end_date date DEFAULT NULL,
    PRIMARY KEY (id)
    );