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

public class Stream1App {

	int value;

	StudentService studentService = new StudentService();
	public static void main(String[] args) {
	    Stream1App sortingApp = new Stream1App();
		init(sortingApp.studentService);
//		sortingApp.mixAndMatch();
		sortingApp.streams1();
	}

	public void mixAndMatch() {
		List<Student> students = studentService.getAllStudents();

		Predicate<Student> fullTimers = s -> s.getStatus() == Student.Status.FULL_TIME;
		Function<Student, String> ne = s -> s.getName();

		List<Student> fullTimeStudents = bestChecker(students, fullTimers);
		List<String> fullTimeNames = bestExtractor(fullTimeStudents, s -> s.getName());


		List<String> fullTimeNames2 = bestExtractor(bestChecker(students, fullTimers),  s -> s.getName());

		System.out.println("FullTime Student Names: " + fullTimeNames);
	}

	public void streams1() {
		List<Student> students = studentService.getAllStudents();

//		List<String> fullTimeNames = students.stream()
//				.peek(s -> System.out.println("Peek 1 with: " + s))
//				.filter(s -> s.getStatus() == Student.Status.FULL_TIME)
//				.peek(s -> System.out.println("Peek 2 with: " + s))
//				.map(s -> s.getName())
//				.peek(s -> System.out.println("Peek 3 with: " + s))
//				.collect(Collectors.toList());
//		System.out.println("FullTime Student Names: " + fullTimeNames);



	}

	public void streams2() {
		List<Student> students = studentService.getAllStudents();

		long count = students.stream()
				.filter(s -> s.getStatus() == Student.Status.FULL_TIME)
				.collect(Collectors.counting());

		String csv = students.stream()
				.filter(s -> s.getStatus() == Student.Status.FULL_TIME)
				.map(s -> s.getName())
				.collect(Collectors.joining(", "));


		System.out.println("csv: " + csv);
	}

	public <T> List<T> bestChecker(List<T> input, Predicate<T> checker) {
		List<T> result = new ArrayList<>();
		for(T obj : input) {
			if(checker.test(obj)) {
				result.add(obj);
			}
		}

		return result;
	}

	public <T, R> List<R> bestExtractor(List<T> input, Function<T, R> extractor) {
		List<R> result = new ArrayList<>();
		for(T s : input) {
			result.add(extractor.apply(s));
		}
		return result;
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
