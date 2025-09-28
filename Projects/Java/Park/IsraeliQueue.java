import java.util.Iterator;

/**
 * @IsraeliQueue
 */

public class IsraeliQueue<E> implements Iterable<E>, Cloneable {

    /**
     * IsraeliQueue attributes
     */
    private Group<E> head;
    private Group<E> tail;
    private int size;

    /**
     * IsraeliQueue constructor
     */
    public IsraeliQueue() {
        head = null;
        tail = null;
        size = 0;

    }

    /**
     * Add person to the queue (with a friend inside)
     */
    public void add(E person, E friend) throws InvalidInputException {
        if (person == null) {
            throw new InvalidInputException();
        }
        if (head == null) {
            head = new Group<E>();
            tail = head;
            head.addToGroup(person);
            size++;
            return;
        }
        Group<E> current = head;
        while (current != null) {
            if (current.isFriendinGroup(friend)) {
                current.addToGroup(person);
                size++;
                return;
            }
            current = current.getNextGroup();
        }
        Group<E> newGroup = new Group<E>();
        tail.setNextGroup(newGroup);
        tail = newGroup;
        tail.addToGroup(person);
        size++;
    }

    /**
     * Add person to the queue (without a friend inside)
     */
    public void add(E person) throws InvalidInputException {
        if (person == null) {
            throw new InvalidInputException();
        }
        this.add(person, null);
    }

    /**
     * Removes the first person in the queue (the leader of the first group) and returns him
     */
    public E remove() throws EmptyQueueException {
        if (head == null) {
            throw new EmptyQueueException();
        }
        size--;
        E removedPerson = head.removeFromGroup();
        if (head.getLeader() == null)
            head = head.getNextGroup();
        return removedPerson;
    }

    /**
     * Return the first person in the queue (the leader of the first group)
     */
    public E peek() throws EmptyQueueException {
        if (head == null) {
            throw new EmptyQueueException();
        }
        E data = head.getLeader().getValue();
        return data;
    }

    /**
     * Return how many people are in the queue
     */
    public int size() {
        return size;
    }

    /**
     * Check if the queue is empty
     */
    public boolean isEmpty() {
        return head == null;  // Returns true if head is null, otherwise false
    }

    /**
     * Iterator's Override
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Group<E> currentGroup = head;
            private Node<E> current = (currentGroup == null) ? null : currentGroup.getLeader();

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E data = current.getValue();

                // Move to the next node in the group
                if (current.getNext() != null) {
                    current = current.getNext();
                } else {
                    // Move to the next group if current group is over
                    currentGroup = currentGroup.getNextGroup();
                    current = (currentGroup != null) ? currentGroup.getLeader() : null;
                }

                return data;
            }
        };
    }

    /**
     * Clone's Override
     */
    @Override
    protected IsraeliQueue<E> clone() {
        try {
            IsraeliQueue<E> clonedQueue = (IsraeliQueue<E>) super.clone();
            if (head == null) {
                return clonedQueue;
            }

            // Clone the head group deeply
            clonedQueue.head = head.clone();
            Group<E> currentOriginalGroup = head.getNextGroup();
            Group<E> currentClonedGroup = clonedQueue.head;

            // Deep clone the remaining groups
            while (currentOriginalGroup != null) {
                Group<E> newClonedGroup = currentOriginalGroup.clone();
                currentClonedGroup.setNextGroup(newClonedGroup);
                currentClonedGroup = newClonedGroup;
                currentOriginalGroup = currentOriginalGroup.getNextGroup();
            }

            clonedQueue.tail = currentClonedGroup;
            clonedQueue.size = this.size;

            return clonedQueue;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
