import java.util.Iterator;

class MyCloneable implements Cloneable{
    private int num;

    public MyCloneable(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "MyCloneable: " + num;
    }

    @Override
    public int hashCode() {
        return num;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MyCloneable)){
            return false;
        }
        MyCloneable other = (MyCloneable) obj;
        return num == other.num;
    }

    @Override
    public MyCloneable clone() {
        try {
            return (MyCloneable) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Test 1 starts");
            test1();
        }catch (Exception e){
            System.out.println("exception " + e);
        }finally {
            System.out.println("Test 1 done");
            System.out.println("--------------------------------------------");
        }

        try {
            System.out.println("Test 2 starts");
            test2();
        }catch (Exception e){
            System.out.println("exception " + e);
        }finally {
            System.out.println("Test 2 done");
            System.out.println("--------------------------------------------");
        }

        try {
            System.out.println("Test 3 starts");
            test3();
        }catch (Exception e){
            System.out.println("exception " + e);
        }finally {
            System.out.println("Test 3 done");
            System.out.println("--------------------------------------------");
        }

        try {
            System.out.println("Test 4 starts");
            test4();
        }catch (Exception e){
            System.out.println("exception " + e);
        }finally {
            System.out.println("Test 4 done");
            System.out.println("--------------------------------------------");
        }
    }

    public static void test1() throws InvalidInputException {
        IsraeliQueue<MyCloneable> queue = new IsraeliQueue<>();
        queue.add(new MyCloneable(1));
        queue.add(new MyCloneable(2));
        queue.add(new MyCloneable(3));

        IsraeliQueue<MyCloneable> clonedQueue = queue.clone();

        Iterator<MyCloneable> iterator = queue.iterator();
        Iterator<MyCloneable> clonedIterator = clonedQueue.iterator();

        while (iterator.hasNext() && clonedIterator.hasNext()){
            MyCloneable fromOrg = iterator.next();
            MyCloneable fromCloned = clonedIterator.next();

            System.out.println(fromOrg);
            System.out.println(fromCloned);
            System.out.println(fromOrg.equals(fromCloned));
            System.out.println(fromOrg == fromCloned);
        }
    }

    public static void test2(){
        IsraeliQueue<MyCloneable> queue = new IsraeliQueue<>();
        try {
            queue.add(null);
        }catch (InvalidInputException e){
            System.out.println("Exception caught: " + e);
        }
    }

    public static void test3() throws InvalidInputException {
        IsraeliQueue<MyCloneable> queue = new IsraeliQueue<>();
        queue.add(new MyCloneable(1), new MyCloneable(4));
        queue.add(new MyCloneable(2));
        queue.add(new MyCloneable(3));
        queue.add(new MyCloneable(4), new MyCloneable(2));

        for (MyCloneable element : queue){
            System.out.println(element);
        }
        System.out.println("Second iteration");
        for (MyCloneable element : queue){
            System.out.println(element);
        }
    }

    public static void test4(){
        Park park = new Park("Super-Park");
        AmusementRide ride1 = new AmusementRide("Ferris Wheel", 3);
        AmusementRide ride2 = new AmusementRide("Gravity Falls", 2);
        AmusementRide ride3 = new AmusementRide("Roller Coaster", 5);

        park.add(ride1);
        park.add(ride2);
        park.add(ride3);

        park.addPerson(ride1, new Person("Ploni Almoni 1", 1, null));
        park.addPerson(ride1, new Person("Ploni Almoni 2", 2, null));
        park.addPerson(ride1, new Person("Ploni Almoni 3", 3, new Person("Ploni", 1, null)));
        park.addPerson(ride1, new Person("Ploni Almoni 4", 4, new Person("Ploni Almoni", 1, null)));

        park.addPerson(ride3, new Person("Ploni Almoni 5", 5, null));
        park.addPerson(ride3, new Person("Ploni Almoni 6", 6, null));
        park.addPerson(ride3, new Person("Ploni Almoni 7", 7, null));
        park.startRides();
        System.out.println("Starting yet again.");

        park.startRides();
        System.out.println("Again?");

        park.startRides();
    }
}
