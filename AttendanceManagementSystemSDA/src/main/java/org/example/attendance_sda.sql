CREATE DATABASE attendance_sda;

USE attendance_sda;

CREATE TABLE attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    roll_number VARCHAR(20),
    student_id int,
    attendance_status VARCHAR(10),
    attendance_date DATE,
	FOREIGN KEY (student_id) REFERENCES students(id)

);
show tables;
describe attendance;

CREATE TABLE courses (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100)
);

CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

INSERT INTO courses (name) VALUES 
('Computer Science'),
('Mathematics'),
('Physics'),
('English Literature'),
('Psychology'),
('Electrical Engineering'),
('Software Engineering'),
('Data Science'),
('Business Administration'),
('Philosophy');


INSERT INTO students (name, course_id) VALUES 
('Alice', 1),
('Bob', 1),
('Charlie', 2),
('David', 3),
('Eve', 4),
('Fahad', 5),
('Gina', 6),
('Hassan', 7),
('Iqra', 8),
('Junaid', 9),
('Kiran', 10),
('Laraib', 1),
('Mariam', 2),
('Nabeel', 3),
('Omer', 4);

INSERT INTO attendance (student_id, attendance_date, attendance_status) VALUES
(1, '2025-06-17', 'Present'),
(1, '2025-06-18', 'Absent'),
(1, '2025-06-19', 'Present'),

(2, '2025-06-17', 'Absent'),
(2, '2025-06-18', 'Absent'),
(2, '2025-06-19', 'Present'),

(3, '2025-06-17', 'Present'),
(3, '2025-06-18', 'Present'),
(3, '2025-06-19', 'Present'),

(4, '2025-06-17', 'Absent'),
(4, '2025-06-18', 'Present'),
(4, '2025-06-19', 'Absent'),

(5, '2025-06-17', 'Present'),
(5, '2025-06-18', 'Present'),
(5, '2025-06-19', 'Absent');



SELECT * FROM courses;
SELECT * FROM students;

SELECT * FROM attendance ORDER BY attendance_date DESC;

SELECT COUNT(*) FROM students;

SELECT students.name FROM students JOIN attendance ON students.id = attendance.student_id 
WHERE attendance_status = ? AND attendance_date = ?;