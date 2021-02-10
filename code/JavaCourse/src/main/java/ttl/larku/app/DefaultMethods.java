package ttl.larku.app;

import java.util.Iterator;

/**
 * @author whynot
 */

interface MyList {
    public void add(String s);
    public Iterable<String> getAll();

    default public void foo() {
        throw new UnsupportedOperationException("Needs implementing");
    }

    default public void addAll(MyList ml) {
       for(String s : ml.getAll())  {
           add(s);
       }
    }
}

abstract class AbstractList implements MyList {
    private String [] arr;
    private int currIndex;
    private int currUsed;

    @Override
    public void add(String s) {

    }

}


class DefaultImpl extends AbstractList {

    @Override
    public Iterable<String> getAll() {
        return null;
    }

    @Override
    public void addAll(MyList ml) {

    }
}

class YourImpl implements MyList {
    private String [] arr;
    @Override
    public void add(String s) {

    }

    @Override
    public Iterable<String> getAll() {
        return null;
    }
}

class App
{
    public static void main(String[] args) {
        MyList ml = new YourImpl();

        ml.add("boo");
        ml.getAll();;

        MyList ml2 = new YourImpl();
        ml.addAll(ml);
    }
}