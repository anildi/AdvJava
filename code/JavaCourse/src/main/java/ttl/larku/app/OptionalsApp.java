package ttl.larku.app;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import ttl.larku.dao.inmemory.InMemoryCourseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.CourseService;
import ttl.larku.service.StudentService;

public class OptionalsApp {

	int value;

	StudentService studentService = new StudentService();
	public static void main(String[] args) {
	    OptionalsApp sortingApp = new OptionalsApp();
		init(sortingApp.studentService);
		sortingApp.optionalOfT();
	}


	public void optionalLongs() {
		List<Student> students = studentService.getAllStudents();

		var today = LocalDate.now();

		long sumAge = students.stream()
				.mapToLong(s -> s.getDob().until(today, ChronoUnit.YEARS))
				.sum();

//		OptionalLong optResult = students.stream()
//				.mapToLong(s -> s.getDob().until(today, ChronoUnit.YEARS))
//				.max();
//
//
//		if (optResult.isPresent()) {
//			System.out.println("Got value " + optResult.getAsLong());
//		} else {
//		}

		students.stream()
				.mapToLong(s -> s.getDob().until(today, ChronoUnit.YEARS))
				.max().ifPresentOrElse(
						l -> System.out.println("got a value: " + value),
						() -> System.out.println("NoValue")
		);
	}

	public void optionalOfT() {
		List<Student> students = studentService.getAllStudents();

		Optional<Student> optStudent = students.stream()
				.filter(s -> s.getName().startsWith("Z"))
				.findAny();

		//Student s = optStudent.get();

		System.out.println("Starting Test *************");

		Student student = students.stream()
				.filter(s -> s.getName().startsWith("M"))
				.findAny()
                .orElse(new Student("DefaultGuy", LocalDate.of(1999, 2, 5), Student.Status.FULL_TIME));

		Student student2 = students.stream()
				.filter(s -> s.getName().startsWith("M"))
				.findAny()
				.orElseGet(() -> new Student("OrElseGetDefaultGuy", LocalDate.of(1999, 2, 5), Student.Status.FULL_TIME));


	}


	public static void init(StudentService ss) {
		((InMemoryStudentDAO)ss.getStudentDAO()).createStore();
		ss.createStudent("Manoj", LocalDate.of(1988, 10, 2), Student.Status.FULL_TIME, "282 939 9944");
		ss.createStudent("Charlene", LocalDate.of(1999, 8, 14), Student.Status.FULL_TIME, "282 898 2145", "298 75 83833");
		ss.createStudent("Firoze", LocalDate.of(2002, 5, 2), Student.Status.HIBERNATING, "228 678 8765", "220 8795 26795");
		ss.createStudent("Joe", LocalDate.of(1948, 9, 26), Student.Status.PART_TIME, "3838 678 3838");
	}

	public static void init(CourseService cs) {
		((InMemoryCourseDAO)cs.getCourseDAO()).createStore();
		cs.createCourse("Math-101", "Intro To Math");
		cs.createCourse("Math-201", "More Math");
		cs.createCourse("Phys-101", "Baby Physics");
	}

}
