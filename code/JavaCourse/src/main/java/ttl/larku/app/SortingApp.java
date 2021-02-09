package ttl.larku.app;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import ttl.larku.dao.inmemory.InMemoryCourseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Course;
import ttl.larku.domain.Student;
import ttl.larku.service.CourseService;
import ttl.larku.service.StudentService;

public class SortingApp {

	int value;

	StudentService studentService = new StudentService();
	public static void main(String[] args) {
	    SortingApp sortingApp = new SortingApp();
		init(sortingApp.studentService);
//		 sortingApp.postRequestToAddAStudent();
		// sortingApp.sortSimple();
		sortingApp.sortByName();
	}

	public void sortSimple() {
		List<Student> students = studentService.getAllStudents();

		Collections.sort(students);

		System.out.println("All Students: " + students.size());
		students.forEach(System.out::println);
	}

	public void sortByName() {
		List<Student> students = studentService.getAllStudents();

		NameComparator nc = new NameComparator();
		Comparator<Student> reversedNc = new NameComparator().reversed();

		Comparator<Student> nc2 = new Comparator<Student>() {
			@Override
			public int compare(Student student1, Student student2) {
				return student1.getName().compareTo(student2.getName());
			}
		};

		Comparator<Student> nc3 = (Student student1, Student student2) -> {
				return student1.getName().compareTo(student2.getName());
			};

		Comparator<Student> nc4 = (student1, student2) -> {
			return student1.getName().compareTo(student2.getName());
		};

		Comparator<Student> nc5 = (student1, student2) -> student1.getName().compareTo(student2.getName());

		Collections.sort(students, nc3);

		System.out.println("All Students: " + students.size());
		students.forEach(System.out::println);
	}

	public class NameComparator implements Comparator<Student> {

		@Override
		public int compare(Student student1, Student student2) {
			return student1.getName().compareTo(student2.getName());
		}
	}


	public static <T extends Comparable<T>> void sort(List<T> list) {

	}

	public static <T> void sort(List<T> list, Comparator<T> c){}


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
