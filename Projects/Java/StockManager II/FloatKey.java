

public class FloatKey implements Key<FloatKey> {
    private Float priceStock;
    private String stockID;
    private boolean leftSentinel;
    private boolean rightSentinel;

    public FloatKey(Float price, String stockID) {
        this.priceStock = price;
        this.stockID = stockID;
    }

    //כנראה אין צורך בבנאי הזה כי הפכנו את המפתחות הרלוונטיים להיות סנטינלים עי הגדרה במחלקת עץ לפי פונקציית set
    public FloatKey (Float price, String stockID, boolean leftSentinel, boolean rightSentinel) {
        this.priceStock = price;
        this.stockID = stockID;
        this.leftSentinel = leftSentinel;
        this.rightSentinel = rightSentinel;
    }

    public Float getPrice() {
        return priceStock;
    }
    public String getStockID() {
        return stockID;
    }
    @Override
    public boolean getLeftSentinel() {
        return leftSentinel;
    }

    @Override
    public void setLeftSentinel(boolean value) {
        this.leftSentinel = value;
    }

    @Override
    public boolean getRightSentinel() {
        return rightSentinel;
    }

    @Override
    public void setRightSentinel(boolean value) {
        this.rightSentinel = value;
    }

    @Override
    public int compareSmallerKey(FloatKey other) {
        if (this.getLeftSentinel() || other.getRightSentinel()) {
            return -1;
        }
        if (this.getRightSentinel() || other.getLeftSentinel()) {
            return 1;
        }
        if (this.priceStock < other.priceStock)  {
            return -1;
        }
        if (this.priceStock > other.priceStock)  {
            return 1;
        }
        // אם המחירים שווים- נשווה לפי המזהה של המניות
        return this.stockID.compareTo(other.stockID);
    }
}
