package ttl.larku.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author whynot
 */
public class GenericsDemo {

    public static void main(String[] args) {
        GenericsDemo gd = new GenericsDemo();
        //gd.typeErasure();
        gd.wildCards();
    }

    public void wildCards() {
        List<Number> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(22.3);
        numbers.add(1333.83838);
        numbers.add(50);

        double r1 = sum(numbers);
        System.out.println("r1: " + r1);

        List<Integer> ints = new ArrayList<>();
        ints.add(10);
        ints.add(20);
        ints.add(30);
        ints.add(40);

        double r2 = sum(ints);

        Integer [] iarr = { 0, 2, 4, 10 };
        addAll(ints, iarr);
        System.out.println(ints);

        addAll(numbers, iarr);

        List<Object> lo = new ArrayList<>();
        addAll(lo, iarr);

    }

    public void addAll(List<? super Integer> input, Integer [] iarr) {
        Object o = input.get(0);
        //Integer i = input.get(1);

        for(Integer it : iarr) {
            input.add(it);
        }
    }

    public double sum(List<? extends Number> input) {

//        input.add(22.333);

        double sum = 0;
        for(Number n : input) {
            sum += n.doubleValue();
        }
        return sum;
    }

    public double numberSum(List<Number> input) {
       return 2.0;
    }

    public double integerSum(List<Integer> input) {
        return 2.0;
    }


    public void foo(Number n) {

        List<String> ls = List.of("One", "two", "three");
        frequency(ls, "one");

        List<Integer> lint = List.of(1, 3, 3);
        frequency(lint, 3);
    }

    static int 	frequency(Collection<?> c, Object target) {
       int freq = 0;
       for(Object o : c) {
           if(o.equals(target)) {
               freq++;
           }
       }
       return freq;
    }

    public static <T> void copy(List<? super T> xyz, List<? extends T> abc){}















    public void typeErasure() {
        List<String> lStr = new ArrayList<>();
        lStr.add("one");
        lStr.add("two");

//        lStr.add(2);

        List badList = lStr;
        badList.add(2);

        List l2 = new ArrayList<String>();
        l2.add("one");
        l2.add(2);

        Iterator it = l2.iterator();
        while(it.hasNext()) {
            Object o = it.next();
            String s = (String)o;
            System.out.println(o);
        }

        for(String str : lStr) {
            System.out.println(str);
        }

    }
}
