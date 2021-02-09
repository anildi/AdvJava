package ttl.larku.dao.inmemory;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryStudentDAO implements BaseDAO<Student>{

	private Map<Integer, Student> students = new ConcurrentHashMap<>();
	private static AtomicInteger nextId = new AtomicInteger(0);

	//TODO - fix check then act
	public void update(Student updateObject) {
		if(students.containsKey(updateObject.getId())) {
			students.put(updateObject.getId(), updateObject);
		}
	}

	public void delete(Student student) {
		students.remove(student.getId());
	}

	public Student create(Student newObject) {
		//Create a new Id
		int newId = nextId.incrementAndGet();
		newObject.setId(newId);
		students.put(newId, newObject);
		
		return newObject;
	}

	public Student get(int id) {
		return students.get(id);
	}

	//TODO - fix for List

	/**
	 *
	 * @return a List sorted by id
	 */
	public List<Student> getAll() {
//		List<Student> col = new ArrayList<Student>(students.values());

		var col = new ArrayList<Student>(students.values());
		Collections.sort(col, (s1, s2) -> Integer.compare(s1.getId(), s2.getId()));
		return col;

//        return students.values().stream()
//				.sorted((s1, s2) -> Integer.compare(s1.getId(), s2.getId()))
//                .collect(Collectors.toList());
	}

	public void deleteStore() {
		students = null;
		nextId.set(0);
	}

	public void createStore() {
		students = new HashMap<Integer, Student>();
		nextId.set(0);
	}

	public Map<Integer, Student> getStudents() {
		return students;
	}
}
