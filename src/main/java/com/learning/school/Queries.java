/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school;

public class Queries {

    final static String Q1 = "SELECT student.* FROM student JOIN class ON number = number_class JOIN teacher AS t ON t.id = id_teacher WHERE t.name = ?";
    final static String Q2 = "SELECT schedule.* FROM schedule JOIN day ON day.id = id_day WHERE day.day = ? ";
    final static String Q3 = "SELECT class.number,teacher.name FROM class LEFT JOIN teacher ON teacher.id = id_teacher WHERE number LIKE '%";
    final static String Q4 = "SELECT * FROM event WHERE description LIKE '%";
    final static String Q5 = "SELECT * FROM event WHERE date BETWEEN '%s' AND '%s'";
    final static String Q6 = "SELECT t.*,a.date_next,s.description FROM teacher AS t JOIN attestation AS a ON t.id = id_teacher JOIN speciality AS s ON s.id = id_speciality WHERE date_next BETWEEN '%s' AND '%s'";
    final static String Q7 = "SELECT COUNT(id) FROM event WHERE date BETWEEN '%s' AND '%s'";
    final static String Q8 = "SELECT COUNT(t.id) FROM teacher AS t JOIN attestation AS a ON t.id = id_teacher JOIN speciality AS s ON s.id = id_speciality WHERE s.description = ? AND category >= ?";
    final static String Q9 = "SELECT s.number_class, AVG(grade) FROM student AS s\n"
            + "JOIN journal ON s.id = id_student\n"
            + "JOIN lesson ON lesson.id = id_lesson\n"
            + "JOIN schedule ON schedule.id = id_schedule\n"
            + "JOIN subject ON subject.id = id_subject\n"
            + "WHERE subject.description = ? "
            + "GROUP BY s.number_class";
    final static String Q10 = "SELECT number, COUNT(s.id) FROM student AS s\n"
            + "JOIN class ON number = number_class\n"
            + "GROUP BY number";
    final static String Q11 = "SELECT name, AVG(grade) FROM student AS s\n"
            + "JOIN journal ON s.id = id_student\n"
            + "JOIN lesson ON lesson.id = id_lesson\n"
            + "JOIN schedule ON schedule.id = id_schedule\n"
            + "JOIN subject ON subject.id = id_subject\n"
            + "WHERE subject.description = ? \n"
            + "AND s.number_class = ?\n"
            + "GROUP BY name\n"
            + "HAVING AVG(grade) >= all(SELECT AVG(grade) FROM student AS s\n"
            + "	JOIN journal ON s.id = id_student\n"
            + "	JOIN lesson ON lesson.id = id_lesson\n"
            + "	JOIN schedule ON schedule.id = id_schedule\n"
            + "	JOIN subject ON subject.id = id_subject\n"
            + "	WHERE subject.description = ? \n"
            + "	AND s.number_class = ?\n"
            + "	GROUP BY name)";
    final static String Q12 = "SELECT name, COUNT(s.id) FROM schedule AS s\n"
            + "JOIN teacher AS t ON t.id = id_teacher\n"
            + "GROUP BY name\n"
            + "HAVING COUNT(s.id) >= all(SELECT COUNT(s.id) FROM schedule AS s\n"
            + "	JOIN teacher AS t ON t.id = id_teacher\n"
            + "	GROUP BY name)";
    final static String Q13 = "SELECT s.number_class,name, AVG(grade) FROM student AS s\n"
            + "JOIN journal ON s.id = id_student\n"
            + "GROUP BY s.number_class,name\n"
            + "HAVING AVG(grade) >= all(SELECT AVG(grade) FROM student\n"
            + "	JOIN journal ON student.id = id_student\n"
            + "	WHERE s.number_class = student.number_class\n"
            + "	GROUP BY name)";
    final static String Q14 = "SELECT s.description,name, MAX(category) FROM teacher\n"
            + "JOIN attestation ON teacher.id = id_teacher\n"
            + "JOIN speciality AS s ON s.id = id_speciality\n"
            + "GROUP BY s.description,name, s.id\n"
            + "HAVING MAX(category) >= all(SELECT MAX(category) FROM teacher\n"
            + "	JOIN attestation ON teacher.id = id_teacher\n"
            + "	JOIN speciality ON speciality.id = id_speciality\n"
            + "	WHERE speciality.id = s.id\n"
            + "	GROUP BY s.description)";
    final static String Q15 = "SELECT name FROM student\n"
            + "WHERE student.id NOT IN(\n"
            + "SELECT id_student FROM journal\n"
            + "	JOIN lesson ON lesson.id = id_lesson\n"
            + "	WHERE date = '2022-05-20')";
    final static String Q16 = "SELECT number FROM class\n"
            + "WHERE number NOT IN (\n"
            + "	SELECT number_class FROM class_event\n"
            + "	JOIN event ON event.id = id_event\n"
            + "	WHERE EXTRACT('week' FROM current_date) =\n"
            + "	EXTRACT('week' FROM date)\n"
            + "	);";
    final static String Q17 = "SELECT name, AVG(grade), 'excellent' AS performance FROM student AS s\n"
            + "JOIN journal ON s.id = id_student\n"
            + "WHERE number_class = ?\n"
            + "GROUP BY name\n"
            + "HAVING AVG(grade) >= 10\n"
            + "UNION\n"
            + "SELECT name, AVG(grade), 'good' AS performance FROM student AS s\n"
            + "JOIN journal ON s.id = id_student\n"
            + "WHERE number_class = ?\n"
            + "GROUP BY name\n"
            + "HAVING AVG(grade) BETWEEN 7 AND 9\n"
            + "UNION\n"
            + "SELECT name, AVG(grade), 'loser' AS performance FROM student AS s\n"
            + "JOIN journal ON s.id = id_student\n"
            + "WHERE number_class = ?\n"
            + "GROUP BY name\n"
            + "HAVING AVG(grade) <= 6";
    final static String Q18 = "SELECT name, COUNT(*), 'expert' AS experience FROM teacher\n"
            + "JOIN attestation ON teacher.id = id_teacher\n"
            + "GROUP BY name\n"
            + "HAVING COUNT(*) >= 5\n"
            + "UNION\n"
            + "SELECT name, COUNT(*), 'good' AS experience FROM teacher\n"
            + "JOIN attestation ON teacher.id = id_teacher\n"
            + "GROUP BY name\n"
            + "HAVING COUNT(*) BETWEEN 3 AND 4\n"
            + "UNION\n"
            + "SELECT name, COUNT(*), 'recruit' AS experience FROM teacher\n"
            + "JOIN attestation ON teacher.id = id_teacher\n"
            + "GROUP BY name\n"
            + "HAVING COUNT(*) <=2";

    final static String Q19 = "UPDATE attestation SET category = category + 1, \n"
            + "date_passed = date_next, date_next = '2023-05-10'\n"
            + "WHERE id = (SELECT a.id FROM teacher AS t\n"
            + "	  JOIN attestation AS a ON t.id = id_teacher\n"
            + "	  JOIN speciality AS s ON s.id = id_speciality\n"
            + "	  WHERE s.description = ?\n"
            + "	  AND t.name = ?)";
    final static String Q20 = "UPDATE journal SET grade = ? \n"
            + "WHERE id = (SELECT j.id FROM student AS s\n"
            + "JOIN journal AS j ON s.id = id_student\n"
            + "JOIN lesson AS l ON l.id  = id_lesson\n"
            + "JOIN schedule AS sch ON sch.id = id_schedule\n"
            + "JOIN subject ON subject.id = id_subject\n"
            + "WHERE name = ?\n"
            + "AND date = '%s'\n"
            + "AND subject.description = ?)";

}
