package ttl.larku.app;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

/**
 * @author whynot
 */
public class LabSolutions {
    StudentService studentService = new StudentService();
    public static void main(String[] args) {
        LabSolutions sortingApp = new LabSolutions();
        init(sortingApp.studentService);
        sortingApp.lab9();
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

//    Write a method to calculate the average age of Customers who have the status
//    Restricted.
    public void lab7() {
        List<Student> students = studentService.getAllStudents();

        OptionalDouble average = students.stream()
                .mapToLong(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS))
                .average();

        if(average.isPresent()) {
            double result = average.getAsDouble();
        } else {
            //Get value from DB
            double result = 0.0; //From DB
        }

        //Get Value from DB for Default
        double defVal = 0.0;  //From DB
        double d = students.stream()
                .mapToLong(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS))
//                .average().getAsDouble();

//                .average().orElse(defVal);
                .average().orElseGet(() -> 0.0 ); //Get from DB


    }

//    Write a method to return all the phone numbers of all customers. Make sure your test
//    data includes at least some customers with phone numbers.
    public void lab8() {
        List<Student> students = studentService.getAllStudents();
        Stream<String> s1 = students.stream()
                //.map(student -> student.getPhoneNumbers().stream());
                .flatMap(student -> student.getPhoneNumbers().stream());

        List<String> result = students.stream()
                //.map(student -> student.getPhoneNumbers().stream());
                .flatMap(student -> student.getPhoneNumbers().stream())
                .collect(Collectors.toList());

    }

//    Write a method to return only the first phone number, if any, for all customers. For
//    your test data, make sure that some of your customers have multiple phone numbers,
//    and at least one customer has no phone numbers.
    public void lab9() {
        List<Student> students = studentService.getAllStudents();

        Stream<Optional<String>> opts = students.stream()
                .map(s -> s.getPhoneNumbers().stream().findFirst());

        Stream<String> opts2 = students.stream()
                .map(s -> s.getPhoneNumbers().stream().findFirst())
                .map(opt -> opt.get());

        Stream<String> opts8 = students.stream()
                .map(s -> {
                    if(s.getPhoneNumbers() == null) {
                        return Optional.<String>empty();
                    } else {
                        return s.getPhoneNumbers().stream().findFirst();
                    }
                })
                .map(opt -> opt.get());

        //JDK 8
        List<String> opts3 = students.stream()
                .map(s -> s.getPhoneNumbers().stream().findFirst())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        //JDK11
        List<String> opts4 = students.stream()
                .map(s -> s.getPhoneNumbers().stream().findFirst())
                .flatMap(opt -> opt.stream())
                .collect(Collectors.toList());

        opts3.forEach(System.out::println);


    }


    public static void init(StudentService ss) {
        ((InMemoryStudentDAO)ss.getStudentDAO()).createStore();
        ss.createStudent("Manoj", LocalDate.of(1988, 10, 2), Student.Status.FULL_TIME);
        ss.createStudent("Charlene", LocalDate.of(1999, 8, 14), Student.Status.FULL_TIME, "282 898 2145", "298 75 83833");
        ss.createStudent("Firoze", LocalDate.of(2002, 5, 2), Student.Status.HIBERNATING, "228 678 8765", "220 8795 26795");
        ss.createStudent("Joe", LocalDate.of(1948, 9, 26), Student.Status.PART_TIME, "3838 678 3838");
    }
}
