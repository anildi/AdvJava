package ttl.larku.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ttl.larku.domain.Student;

/**
 * @author whynot
 */
public class WordCounter {

    public static void main(String[] args) throws IOException {
        WordCounter wc = new WordCounter();
//        wc.go();
        wc.constructorReferences();
    }

    public void go() throws IOException {

        Stream<String> s1 = Files.lines(Paths.get("JavaCourse/src/main/resources/ImportanceOfBeingEarnest.txt"));

        Stream<String []> s2 = Files.lines(Paths.get("JavaCourse/src/main/resources/ImportanceOfBeingEarnest.txt"))
                .filter(line -> !line.matches("^\\s*$"))
                .map(line -> line.split("\\W"));

       Stream<Stream<String>> s3 = Files.lines(Paths.get("JavaCourse/src/main/resources/ImportanceOfBeingEarnest.txt"))
                .filter(line -> !line.matches("^\\s*$"))
                .map(line -> line.split("\\W"))
                //.map(sarr -> Stream.of(sarr))
               .map(sarr -> Arrays.stream(sarr));

        Stream<String> s4 = Files.lines(Paths.get("JavaCourse/src/main/resources/ImportanceOfBeingEarnest.txt"))
                .filter(line -> !line.matches("^\\s*$"))
                .map(line -> line.split("\\W"))
                //.map(sarr -> Stream.of(sarr))
                .flatMap(sarr -> Arrays.stream(sarr));

//        Map<String, Long> result;
        Map<String, List<String>> m1 = Files.lines(Paths.get("JavaCourse/src/main/resources/ImportanceOfBeingEarnest.txt"))
                .filter(line -> !line.matches("^\\s*$"))
                .map(line -> line.split("\\W"))
                //.map(sarr -> Stream.of(sarr))
                .flatMap(sarr -> Arrays.stream(sarr))
                .collect(Collectors.groupingBy(word -> word));

        //m1.forEach((k, v) -> System.out.println(k + "=" + v));

        Map<String, Long> m2 = Files.lines(Paths.get("JavaCourse/src/main/resources/ImportanceOfBeingEarnest.txt"))
                .filter(line -> !line.matches("^\\s*$"))
                .map(line -> line.split("\\W"))
                //.map(sarr -> Stream.of(sarr))
                .flatMap(sarr -> Arrays.stream(sarr))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        //m2.forEach((k, v) -> System.out.println(k + "=" + v));

        Map<String, Long> m3 = Files.lines(Paths.get("JavaCourse/src/main/resources/ImportanceOfBeingEarnest.txt"))
                .filter(line -> !line.matches("^\\s*$"))
                .map(line -> line.split("\\W"))
                //.map(sarr -> Stream.of(sarr))
                .flatMap(sarr -> Arrays.stream(sarr))
                .collect(Collectors.groupingBy(word -> word, () -> new TreeMap<>(), Collectors.counting()));

        m3.forEach((k, v) -> System.out.println(k + "=" + v));

        Map<String, Long> m4 = Files.lines(Paths.get("JavaCourse/src/main/resources/ImportanceOfBeingEarnest.txt"))
                .filter(line -> !line.matches("^\\s*$"))
                .map(line -> line.split("\\W"))
                //.map(sarr -> Stream.of(sarr))
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(word -> word, TreeMap::new, Collectors.counting()));
    }



    public void constructorReferences() {
        Supplier<Student> st = Student::new;

        fun(st);
        fun(() -> new Student());
        fun(Student::new);

        fun((str) -> new Student(str), "Charlie");
        Function<String, Student> f2 = (str) -> new Student(str);
        Function<String, Student> f3 = Student::new;

        fun(Student::new, "Joe");
    }

    public void fun(Function<String, Student> studentCreator, String name) {
        Student s = studentCreator.apply(name);
        System.out.println("s with Function: " + s);
    }

    //Supplier :  () -> Student
    public void fun(Supplier<Student> supplier) {
        Student s = supplier.get();
        System.out.println("s: " + s);
    }
}
