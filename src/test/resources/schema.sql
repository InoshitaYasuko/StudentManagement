CREATE TABLE IF NOT EXISTS students
 (
  id int NOT NULL AUTO_INCREMENT,
  full_name varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  furigana varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  nickname varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  email varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  city varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  age int DEFAULT NULL,
  gender varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  remark varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'remark',
  is_deleted tinyint(1) NOT NULL DEFAULT '0' COMMENT 'is_Deleted',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
  );

   CREATE TABLE IF NOT EXISTS students_courses
    (
    id int NOT NULL AUTO_INCREMENT,
    student_id int NOT NULL,
    course_name varchar(100) NOT NULL,
    start_date date NOT NULL,
    end_date date DEFAULT NULL,
    PRIMARY KEY (`id`)
    );