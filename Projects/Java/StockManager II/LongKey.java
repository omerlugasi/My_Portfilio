

public class LongKey implements Key<LongKey>{
    private long timestamp;
    private boolean leftSentinel;
    private boolean rightSentinel;

    public LongKey(long timestamp) {
        this.timestamp = timestamp;
    }
    public LongKey(boolean leftSentinel, boolean rightSentinel) {
        this.leftSentinel = leftSentinel;
        this.rightSentinel = rightSentinel;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public boolean getLeftSentinel() {
        return leftSentinel;
    }
    @Override
    public void setLeftSentinel(boolean value) {
        this.leftSentinel = value;
    }
    public boolean getRightSentinel() {
        return rightSentinel;
    }
    @Override
    public void setRightSentinel(boolean value) {
        this.rightSentinel = value;
    }

    @Override
    public int compareSmallerKey(LongKey other) {
        if (this.getLeftSentinel() || other.getRightSentinel()) {
            return -1;
        }
        if (this.getRightSentinel() || other.getLeftSentinel()) {
            return 1;
        }
        return Long.compare(timestamp ,other.timestamp);
    }

}


