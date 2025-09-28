

public class StringKey implements Key<StringKey> {
    private String stockID; // המזהה של המניה
    private boolean leftSentinel;
    private boolean rightSentinel;

    public StringKey(String stockID ) {
        this.stockID = stockID;
    }
    //כנראה אין צורך בבנאי הזה כי הפכנו את המפתחות הרלוונטיים להיות סנטינלים עי הגדרה במחלקת עץ לפי פונקציית set
    public StringKey(String stockID, boolean leftSentinel, boolean rightSentinel) {
        this.stockID = stockID;
        this.leftSentinel = leftSentinel;
        this.rightSentinel = rightSentinel;
    }

    public String getStockID() {
        return stockID;
    }
    public void setStockID(String stockID) {
        this.stockID = stockID;
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

// הפונקציה compareStockID מחזירה את הצומת המתאים יותר מבין שניים, לפי מצבם כסנטינלים או לפי הערך של המפתח שלהם
    @Override
    public int compareSmallerKey(StringKey other) {
        if (this.getLeftSentinel() || other.getRightSentinel()) {
            return -1;
        }
        if (this.getRightSentinel() || other.getLeftSentinel()) {
            return 1;
        }
        return this.getStockID().compareTo(other.getStockID());
    }
}
