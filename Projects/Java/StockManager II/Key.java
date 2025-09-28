

public interface Key<K> {
    int compareSmallerKey(K other);

    boolean getLeftSentinel();
    void setLeftSentinel(boolean value);

    boolean getRightSentinel();
    void setRightSentinel(boolean value);

}
