package ttl.larku.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ttl.trywrap.TryWrap;

/**
 * @author whynot
 */
public class ExceptionsLambdas {

    private static Logger logger = LoggerFactory.getLogger(ExceptionsLambdas.class);

    public static void main(String[] args) {
        List<String> fileNames = Arrays.asList(".gitignore", "doesNotExist", "pom.xml");
//        callFileTheOldWay(fileNames);
//        callFileWithOptionals(fileNames);
//        callFileWithExceptionWrapper(fileNames);
        callFileWithTryWrap(fileNames);
    }

    public static void callFileTheOldWay(List<String> fileNames) {
        try {
            //List<String> firstLine = filesTheOldWay(fileNames);
            List<String> firstLine = filesWithLambdasTheWrongWay(fileNames);
            firstLine.stream().map(String::toUpperCase).forEach(ch -> {
                //put into DB
                System.out.println(ch);
            });
        } catch (IOException e) {
            //log exception
            logger.error(e.toString());
            System.out.println(e);
        }
    }

    public static List<String> filesTheOldWay(List<String> fileNames) throws IOException {
        List<String> firstLines = new ArrayList<>();
        for (String fileName : fileNames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line = reader.readLine();
                firstLines.add(line);
            }
        }
        return firstLines;
    }

    public static List<String> filesWithLambdasTheWrongWay(List<String> fileNames) throws IOException {
        List<String> firstLines = fileNames.stream()
                .map(fileName -> {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                        String line = reader.readLine();
                        return line;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        return firstLines;
    }

    public static void callFileWithOptionals(List<String> fileNames) {
        List<Optional<String>> resultOps = filesWithOptionals(fileNames);
        resultOps.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(System.out::println);
    }

    public static List<Optional<String>> filesWithOptionals(List<String> fileNames) {
        List<Optional<String>> firstLines = fileNames.stream()
                .map(fileName -> {
//                    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    try (FileReader fr = new FileReader(fileName);
                         BufferedReader reader = new BufferedReader(fr)) {

                        String line = reader.readLine();
                        return Optional.of(line);

                    } catch (IOException e) {
                        return Optional.<String>empty();
                    }
                })
                .collect(Collectors.toList());

        return firstLines;
    }

    public static void callFileWithExceptionWrapper(List<String> fileNames) {
        List<ExceptionWrapper> resultOps = filesWithExceptionWrapper(fileNames);
        resultOps.stream()
                .filter(ew -> ew.isResult())
                .map(ew -> ew.result())
                .forEach(System.out::println);

        resultOps.forEach(ew -> ew.ifResultOrElse(
                str -> System.out.println("Result: " + str),
                ex -> System.out.println("Exception: " + ex)
        ));
    }

    public static List<ExceptionWrapper> filesWithExceptionWrapper(List<String> fileNames) {
        List<ExceptionWrapper> firstLines = fileNames.stream()
                .map(fileName -> {
//                    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    try (FileReader fr = new FileReader(fileName);
                         BufferedReader reader = new BufferedReader(fr)) {

                        String line = reader.readLine();
                        return ExceptionWrapper.ofResult(line);

                    } catch (IOException e) {
                        return ExceptionWrapper.ofException(e);
                    }
                })
                .collect(Collectors.toList());

        return firstLines;
    }

    public static void callFileWithTryWrap(List<String> fileNames) {
        List<TryWrap<String>> resultOps = filesWithTryWrap(fileNames);

        resultOps.stream()
                .map(tw -> tw.map(str -> str.toUpperCase()))
                .forEach(tw -> tw.ifPresentOrElseConsume(System.out::println, System.out::println));
    }

    public static List<TryWrap<String>> filesWithTryWrap(List<String> fileNames) {
        List<TryWrap<String>> firstLines = fileNames.stream()
                .map(fileName -> {
                    return TryWrap.of(() -> {
                        BufferedReader reader = new BufferedReader(new FileReader(fileName));
                        String line = reader.readLine();
                        return line;
                    });
                })
                .collect(Collectors.toList());

        return firstLines;
    }

}

class ExceptionWrapper
{
    private String result;
    private Exception exception;

    private ExceptionWrapper(String r, Exception e) {
        this.result = r;
        this.exception = e;
    }

    public static ExceptionWrapper ofResult(String s) {
        return new ExceptionWrapper(s, null);
    }

    public static ExceptionWrapper ofException(Exception e) {
        return new ExceptionWrapper(null, e);
    }

    public void ifResultOrElse(Consumer<String> resultConsumer, Consumer<Exception> exceptConsumer) {
        if(isResult()) {
            resultConsumer.accept(result);
        }
        else {
            exceptConsumer.accept(exception);
        }
    }

    public boolean isResult() {
        return result != null;
    }

    public String result() {
       return result;
    }

    public Exception exception() {
        return exception;
    }
}
