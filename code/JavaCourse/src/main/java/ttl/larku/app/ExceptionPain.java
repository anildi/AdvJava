package ttl.larku.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author whynot
 */
public class ExceptionPain {

    public static void main(String[] args) {
        ExceptionPain ep = new ExceptionPain();
        ep.lessPainWithTryWithResources();
    }

    public void painFullExceptions() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("pom.xml");
            int ch = fileReader.read();

            System.out.println("Ch: " + ch);

            int i = 0;
            int j = 20 / i;

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void lessPainWithTryWithResources() {

        try(FileReader fileReader = new FileReader("pom.xml");
            MyCloseable mc = new MyCloseable();) {

            int ch = fileReader.read();

            System.out.println("Ch: " + ch);

            int i = 0;
            int j = 20 / i;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class MyCloseable implements AutoCloseable {

    @Override
    public void close() {
        System.out.println("MyClosable.close");
    }
}
