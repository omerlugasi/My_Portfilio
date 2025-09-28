/**
 * @Group
 */

public class Group <E> implements Cloneable{

    /**
     * Group's attributes
     */
    private Node<E> leader;
    private Node<E> tail;
    private int groupSize;
    private Group<E> nextGroup;

    /**
     * Group's Constructor
     */
    public Group() {
        this.leader = null;
        this.tail = null;
        groupSize = 0;
        nextGroup = null;
    }

    /**
     * Return the leader of the group (first person in the queue from this group)
     */
    public Node<E> getLeader() {
        return leader;
    }

    /**
     * Add person to the group (add him to the queue after all the people of his group)
     */
    public void addToGroup(E person) {
        Node<E> newNode = new Node<>(person);
        if (leader == null) {
            leader = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        groupSize++;
    }

    /**
     * Remove a person from the group (also from the queue)
     */
    public E removeFromGroup() {
        if (leader == null) {
            return null;
        }
        E value = leader.getValue();
        leader = leader.getNext();
        groupSize--;
        return value;
    }

    /**
     * Check if friend is in the group
     */
    public boolean isFriendinGroup(E friend) {
        if (leader == null || friend == null) {
            return false;
        }
        return leader.isContained(friend);
    }

    /**
     * Return the next group in the queue
     */
    public Group<E> getNextGroup() {
        return nextGroup;
    }

    /**
     * Change the next group
     */
    public void setNextGroup(Group<E> newNextGroup) {
        nextGroup = newNextGroup;
    }

    /**
     * Clone's Override
     */
    @Override
    protected Group<E> clone() {
        try {
            Group<E> clonedGroup = (Group<E>) super.clone();

            // Deep clone the leader node and its linked nodes
            if (this.leader != null) {
                clonedGroup.leader = this.leader.deepClone();
            }

            // Update the tail reference in the cloned group
            if (clonedGroup.leader != null) {
                Node<E> current = clonedGroup.leader;
                while (current.getNext() != null) {
                    current = current.getNext();
                }
                clonedGroup.tail = current;
            }

            clonedGroup.groupSize = this.groupSize;

            return clonedGroup;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}


