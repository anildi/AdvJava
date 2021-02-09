package ttl.larku.app;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import ttl.larku.dao.inmemory.InMemoryCourseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.CourseService;
import ttl.larku.service.StudentService;

public class FuntionatorApp {

	int value;

	StudentService studentService = new StudentService();
	public static void main(String[] args) {
	    FuntionatorApp sortingApp = new FuntionatorApp();
		init(sortingApp.studentService);
		//sortingApp.callBadExtractor();
		//sortingApp.callSlightlyBetterExtractor();
		//sortingApp.callMoreBetterExtractor();
		sortingApp.mixAndMatch();
	}

	public void callBadExtractor() {
		List<Student> students = studentService.getAllStudents();

		List<String> names = getNames(students);
		System.out.println("All Student Names: " + names);
		for(String s : names) {
			System.out.println(s);
		}
	}

	public void callSlightlyBetterExtractor() {
		List<Student> students = studentService.getAllStudents();

		Extractor ne = new NameExtractor();
		List<String> names = slightLyBetterExtractor(students, ne);

		List<String> descriptions = slightLyBetterExtractor(students, s -> s.getDescr());

		System.out.println("All Student Names: " + names);
		for(String s : descriptions) {
			System.out.println(s);
		}
	}

	public void callMoreBetterExtractor() {
		List<Student> students = studentService.getAllStudents();

		GenericExtractor<Student, String> ne = s -> s.getName();

		List<String> names = moreBetterExtractor(students, ne);

		List<String> descriptions = moreBetterExtractor(students, s -> s.getDescr());

		System.out.println("All Student Names: " + names);
		for(String s : descriptions) {
			System.out.println(s);
		}

		List<LocalDate> dobs = moreBetterExtractor(students, s -> s.getDob());
		System.out.println("All Student DOB: " + dobs);
	}

	public void mixAndMatch() {
		List<Student> students = studentService.getAllStudents();

		Predicate<Student> fullTimers = s -> s.getStatus() == Student.Status.FULL_TIME;
		GenericExtractor<Student, String> ne = s -> s.getName();

		List<Student> fullTimeStudents = bestChecker(students, fullTimers);
		List<String> fullTimeNames = bestExtractor(fullTimeStudents, s -> s.getName());


		List<String> fullTimeNames2 = bestExtractor(bestChecker(students, fullTimers),  s -> s.getName());

		System.out.println("FullTime Student Names: " + fullTimeNames);
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

	@FunctionalInterface
	interface GenericExtractor<T, R>
	{
		public R extract(T input);
	}


	public <T, R> List<R> moreBetterExtractor(List<T> input, GenericExtractor<T, R> extractor) {
		List<R> result = new ArrayList<>();
		for(T s : input) {
			result.add(extractor.extract(s));
		}
		return result;
	}

	interface Extractor
	{
		public String extract(Student student);
	}


	public List<String> slightLyBetterExtractor(List<Student> input, Extractor extractor) {
		List<String> result = new ArrayList<>();
		for(Student s : input) {
			result.add(extractor.extract(s));
		}
		return result;
	}

	class NameExtractor implements Extractor
	{
		@Override
		public String extract(Student student) {
			return student.getName();
		}
	}

	public List<String> getNames(List<Student> input) {
		List<String> result = new ArrayList<>();
		for(Student s : input) {
			result.add(s.getName());
		}
		return result;
	}

	public List<String> getDescr(List<Student> input) {
		List<String> result = new ArrayList<>();
		for(Student s : input) {
			result.add(s.getDescr());
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
