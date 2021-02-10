package ttl.larku.app;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

/**
 * @author whynot
 */
public class LabSolutions {
    StudentService studentService = new StudentService();
    public static void main(String[] args) {
        FlatMapApp sortingApp = new FlatMapApp();
        init(sortingApp.studentService);
    }


    public List<Student> findBy(Predicate<Student> pred) {
        List<Student> students = studentService.getAllStudents();

        return students.stream().filter(pred).collect(Collectors.toList());
    }

    public void labs5And6() {
        List<Student> students = studentService.getAllStudents();

        var today = LocalDate.now();
        //Lab 5
        List<Long> ages = students.stream()
                .map(s -> s.getDob().until(today, ChronoUnit.YEARS))
                .collect(Collectors.toList());

        long olderThan20 = students.stream()
                .filter(s -> s.getDob().until(today, ChronoUnit.YEARS) >= 20)
                .count();

    }

    public static void init(StudentService ss) {
        ((InMemoryStudentDAO)ss.getStudentDAO()).createStore();
        ss.createStudent("Manoj", LocalDate.of(1988, 10, 2), Student.Status.FULL_TIME, "282 939 9944");
        ss.createStudent("Charlene", LocalDate.of(1999, 8, 14), Student.Status.FULL_TIME, "282 898 2145", "298 75 83833");
        ss.createStudent("Firoze", LocalDate.of(2002, 5, 2), Student.Status.HIBERNATING, "228 678 8765", "220 8795 26795");
        ss.createStudent("Joe", LocalDate.of(1948, 9, 26), Student.Status.PART_TIME, "3838 678 3838");
    }
}
