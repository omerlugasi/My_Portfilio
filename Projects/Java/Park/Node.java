import java.lang.reflect.Method;

/**
 * @Node
 */
public class Node<E> implements Cloneable {

    /**
     * Node attributes
     */
    private E value;
    private Node<E> next;

    /**
     * Node constructor (with next node)
     */
    public Node(E value, Node<E> next) {
        this.value = value;
        this.next = next;
    }

    /**
     * Node constructor (withou next node)
     */
    public Node(E value) {
        this(value, null);
    }

    /**
     * Return Node's value
     */
    public E getValue() {
        return value;
    }

    /**
     * Returns Node's next node
     */
    public Node<E> getNext() {
        return next;
    }

    /**
     * Change Node's value
     */
    public void setValue(E value) {
        this.value = value;
    }

    /**
     * Change Node's next node
     */
    public void setNext(Node<E> next) {
        this.next = next;
    }

    /**
     * Check if an object is the line of the nodes
     */
    public boolean isContained(E other) {
        if (this.value.equals(other))
            return true;
        if (this.next == null)
            return false;
        return next.isContained(other);
    }

    /**
     * Clone's Override
     */
    @Override
    protected Node<E> clone() {
        try {
            // Perform a shallow copy
            Node<E> clonedNode = (Node<E>) super.clone();

            // Deep clone the 'next' node if it exists
            if (this.next != null) {
                clonedNode.next = this.next.clone(); // Recursively clone the next node
            }

            return clonedNode;
        } catch (CloneNotSupportedException e) {
            // Handle the exception if cloning is not supported
            return null;
        }
    }

    /**
     * Method for deep cloning using reflection
     */
    protected Node<E> deepClone() {
        try {
            Node<E> clonedNode = (Node<E>) super.clone();

            // Deep clone the 'value' if it is cloneable
            if (this.value instanceof Cloneable) {
                Method cloneMethod = this.value.getClass().getMethod("clone");
                clonedNode.value = (E) cloneMethod.invoke(this.value);
            }

            // Deep clone the 'next' node if it exists
            if (this.next != null) {
                clonedNode.next = this.next.deepClone(); // Recursively deep clone the next node
            }

            return clonedNode;
        } catch (Exception e) {
            // Handle exceptions that may occur during reflection-based cloning
            e.printStackTrace();
            return null;
        }
    }
}





