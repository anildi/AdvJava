package ttl.larku.app;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ttl.larku.dao.inmemory.InMemoryCourseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.service.CourseService;
import ttl.larku.service.StudentService;

public class GroupingApp {

	int value;

	StudentService studentService = new StudentService();
	public static void main(String[] args) {
	    GroupingApp sortingApp = new GroupingApp();
		init(sortingApp.studentService);

		sortingApp.makeStudentMap();
	}


	public void makeStudentMap() {
		List<Student> students = studentService.getAllStudents();

		students.forEach(s -> System.out.println(s));

//		Map<Integer, Student> sMap;
		Map<Integer, List<Student>> badMap = students.stream()
				.collect(Collectors.groupingBy(s -> s.getId()));

		Map<Integer, Student> sMap = students.stream()
				.collect(Collectors.toMap(Student::getId, s -> s));

		Map<Student.Status, Student> statusMap = students.stream()
				.collect(Collectors.toMap(Student::getStatus, s -> s, (s1, s2) -> s1));

		Map<Student.Status, List<Student>> statusMap2 = students.stream()
				//.collect(Collectors.groupingBy(s -> s.getStatus())
				.collect(Collectors.groupingBy(Student::getStatus));

		//DownStream
		Map<Student.Status, Set<Student>> statusMap3 = students.stream()
				.collect(Collectors.groupingBy(s -> s.getStatus(), Collectors.toSet()));

		//Count
		Map<Student.Status, Long> statusMap4 = students.stream()
				.collect(Collectors.groupingBy(s -> s.getStatus(), Collectors.counting()));

		//Partitioning - students less than and gt 20 years old.
		Map<Boolean, List<Student>> ltAndGt20 = students.stream()
                .collect(Collectors.partitioningBy(s -> s.getDob().until(LocalDate.now(), ChronoUnit.YEARS) > 20));



		statusMap.forEach((k, v) -> {
			System.out.println(k + "=" + v);
			System.out.println(k + "=" + v);
			//Lots of code over here
		});

		statusMap.forEach(this::doLotsOfWork);

//		System.out.println(statusMap);

		int i = 10, j = 20;
	}

	public void doLotsOfWork(Student.Status status, Student student) {
		//Do lots of work
	}

	public void methodReferences() {
		List<Student> students = studentService.getAllStudents();
		//students.forEach(s -> System.out.println(s));
		//students.forEach(System.out::println);
		//students.forEach(this::prettyPrint);
		students.forEach(GroupingApp::staticPrint);
	}

	public void prettyPrint(Student s) {
		System.out.println("{{{: " + s + " :}}}");
	}

	public static void staticPrint(Student s) {
		System.out.println("{{{: " + s + " :}}}");
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
