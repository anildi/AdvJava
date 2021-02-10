package ttl.larku.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ttl.larku.dao.inmemory.InMemoryCourseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.CourseService;
import ttl.larku.service.StudentService;

public class FlatMapApp {

	int value;

	StudentService studentService = new StudentService();
	public static void main(String[] args) {
	    FlatMapApp sortingApp = new FlatMapApp();
		init(sortingApp.studentService);

		sortingApp.findAllPhones();
	}


	public void findAllPhones() {
		List<Student> students = studentService.getAllStudents();

		Stream<String> ss1 = students.stream().map(s -> s.getName());

		Stream<String> ss2 = students.stream()
				.flatMap(s -> s.getPhoneNumbers().stream());

		List<String> ss3 = students.stream()
                .peek(s -> System.out.println("Peek 1 with: " + s))
				.flatMap(s -> s.getPhoneNumbers().stream())
				.peek(s -> System.out.println("Peek 2 After FlatMap with: " + s))
				.collect(Collectors.toList());

	}

	public void fun() {
		List<Student> students = studentService.getAllStudents();

		List<String> allPhones = new ArrayList<>();
		for(Student student : students) {
			List<String> phones = student.getPhoneNumbers();
			for(String phone : phones) {
				allPhones.add(phone);
			}
		}
	}



	public static void init(StudentService ss) {
		((InMemoryStudentDAO)ss.getStudentDAO()).createStore();
		ss.createStudent("Manoj", LocalDate.of(1988, 10, 2), Student.Status.FULL_TIME, "282 939 9944", "383 939 93939");
		ss.createStudent("Charlene", LocalDate.of(1999, 8, 14), Student.Status.FULL_TIME, "282 898 2145", "298 75 83833");
		//ss.createStudent("Firoze", LocalDate.of(2002, 5, 2), Student.Status.HIBERNATING, "228 678 8765", "220 8795 26795");
		ss.createStudent("Firoze", LocalDate.of(2002, 5, 2), Student.Status.HIBERNATING);
		ss.createStudent("Joe", LocalDate.of(1948, 9, 26), Student.Status.PART_TIME, "3838 678 3838");
	}

	public static void init(CourseService cs) {
		((InMemoryCourseDAO)cs.getCourseDAO()).createStore();
		cs.createCourse("Math-101", "Intro To Math");
		cs.createCourse("Math-201", "More Math");
		cs.createCourse("Phys-101", "Baby Physics");
	}

}
