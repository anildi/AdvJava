package ttl.larku.app;

import ch.qos.logback.core.status.Status;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import ttl.larku.dao.inmemory.InMemoryCourseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.CourseService;
import ttl.larku.service.StudentService;

public class PredicateApp {

	int value;

	StudentService studentService = new StudentService();
	public static void main(String[] args) {
	    PredicateApp sortingApp = new PredicateApp();
		init(sortingApp.studentService);

		//sortingApp.callBadChecker();
		//sortingApp.callSlightlyBetterChecker();
		//sortingApp.callSlightlyBetterCheckerWithLambdas();
		//sortingApp.callMoreBetterCheckerWithLambdas();
		sortingApp.callBestChecker();
	}

	public void callBadChecker() {
		List<Student> students = studentService.getAllStudents();

		List<Student> withM = badChecker(students, "M");

		System.out.println("All Students: " + withM.size());
		for(Student s : withM) {
			System.out.println(s);
		}
	}

	public void callSlightlyBetterChecker() {
		List<Student> students = studentService.getAllStudents();

		Checker checker = new StartingWithMChecker();

		Checker statusChecker = new StatusChecker();

		//List<Student> withM = slightlyBetterChecker(students, checker);
		List<Student> withM = slightlyBetterChecker(students, statusChecker);

		System.out.println("All Students: " + withM.size());
		for(Student s : withM) {
			System.out.println(s);
		}
	}

	public void callSlightlyBetterCheckerWithLambdas() {
		List<Student> students = studentService.getAllStudents();

		Checker checkFirstNameWithM = (s) -> s.getName().startsWith("M");

		Checker statusFullTimeChecker = (s) -> s.getStatus() == Student.Status.FULL_TIME;

		//List<Student> withM = slightlyBetterChecker(students, checker);
//		List<Student> withM = slightlyBetterChecker(students, statusFullTimeChecker);


		List<Student> withM = slightlyBetterChecker(students,
				s ->  {
					return s.getDob().until(LocalDate.now(), ChronoUnit.YEARS) > 20;
				});

		System.out.println("All Students: " + withM.size());
		for(Student s : withM) {
			System.out.println(s);
		}
	}

	public void callMoreBetterCheckerWithLambdas() {
		List<Student> students = studentService.getAllStudents();

		GenericChecker<Student> nameChecker = s -> s.getName().startsWith("M");

		Predicate<Student> namePredicate = s -> s.getName().startsWith("M");

		List<Student> withM = moreBetterChecker(students, nameChecker);

		System.out.println("All Students: " + withM.size());
		for(Student s : withM) {
			System.out.println(s);
		}

		List<String> strings = Arrays.asList("one", "two", "threeeeeee");
		List<String> gtthan3 = moreBetterChecker(strings, s -> s.length() > 3);

		System.out.println("Greater than 3: " + gtthan3);
	}

	public void callBestChecker() {
		List<Student> students = studentService.getAllStudents();

		Predicate<Student> namePredicate = s -> s.getName().startsWith("M");

		List<Student> withM = bestChecker(students, namePredicate);

		System.out.println("All Students: " + withM.size());
		for(Student s : withM) {
			System.out.println(s);
		}

		List<String> strings = Arrays.asList("one", "two", "threeeeeee");
		List<String> gtthan3 = bestChecker(strings, s -> s.length() > 3);

		System.out.println("Greater than 3: " + gtthan3);
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

	@FunctionalInterface
	interface GenericChecker<T> {
		public boolean check(T s);
	}


	public <T> List<T> moreBetterChecker(List<T> input, GenericChecker<T> checker) {
		List<T> result = new ArrayList<>();
		for(T obj : input) {
			if(checker.check(obj)) {
				result.add(obj);
			}
		}

		return result;
	}

	class GenImplPred implements Predicate<Student> {
		@Override
		public boolean test(Student s) {
			return false;
		}
	}

	class GenImpl implements GenericChecker<Student> {
		@Override
		public boolean check(Student s) {
			return false;
		}
	}

	@FunctionalInterface
	interface Checker {
		public boolean check(Student s);
	}

	public List<Student> slightlyBetterChecker(List<Student> input, Checker checker) {
		List<Student> result = new ArrayList<>();
		for(Student student : input) {
			if(checker.check(student)) {
				result.add(student);
			}
		}

		return result;
	}

	class StatusChecker implements Checker {
		@Override
		public boolean check(Student student) {
			return student.getStatus() == Student.Status.FULL_TIME;
		}
	}

	class StartingWithMChecker implements Checker {
		@Override
		public boolean check(Student student) {
			return student.getName().startsWith("M");
		}
	}


	public List<Student> badChecker(List<Student> input, String prefix) {
		List<Student> result = new ArrayList<>();
		for(Student student : input) {
			if(student.getName().startsWith(prefix)) {
				result.add(student);
			}
		}

		return result;
	}

	public List<Student> studentsWithStatus(List<Student> input, Student.Status inputStatus) {
		List<Student> result = new ArrayList<>();
		for(Student student : input) {
			if(student.getStatus() == inputStatus){
				result.add(student);
			}
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
