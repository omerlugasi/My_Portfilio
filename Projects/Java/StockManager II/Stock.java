
public class Stock {
    private Float currentPrice;                          // האם נמחוק תכונה זו?
    private Float  initPrice;                           //  לבדוק לגבי final
    private long initTimestamp;                         //  לבדוק לגבי final

    // בנאי עבור אירוע אתחול המנייה
    public Stock(Float initPrice, long timestamp) {
        this.initPrice = initPrice;
        this.currentPrice = initPrice;
        this.initTimestamp = timestamp;
    }

    public Float getInitPrice() {
        return initPrice;
    }
    public Float getCurrentPrice() {
        return currentPrice;
    }
    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }
    public long getInitTimestamp() {
        return initTimestamp;
    }


}
