import java.lang.reflect.Method;

/**
 * @Person
 */
public class Person implements Cloneable {

    /**
     * Person's attributes
     */
    private final String name;
    private final int id;
    private Person friend;

    /**
     * Person's constructor
     */
    public Person(String name, int id, Person friend) {
        this.name = name;
        this.id = id;
        this.friend = friend;
    }

    /**
     * Return the Person's friend
     */
    public Person getFriend() {
        return friend;
    }

    /**
     * Return Person name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Check if person equals to another
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Person)) {
            return false;
        }
        Person otherPerson = (Person) other;
        return this.id == otherPerson.id;
    }

    /**
     * Clone's Override
     */
    @Override
    protected Person clone() {
        try {
            // Perform a shallow copy
            Person clonedPerson = (Person) super.clone();

            // Use reflection to clone the friend object if it exists
            if (this.friend != null) {
                Method cloneMethod = this.friend.getClass().getMethod("clone");
                clonedPerson.friend = (Person) cloneMethod.invoke(this.friend);
            }

            return clonedPerson;
        } catch (CloneNotSupportedException e) {
            // Handle cloning exception
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            // Handle any reflection-related exceptions
            e.printStackTrace();
            return null;
        }
    }
}


