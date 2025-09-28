//package main.structures;

public class PriceIdKey implements Comparable<PriceIdKey> {
    private final long priceCents;
    private final String stockId;

    public PriceIdKey(long priceCents, String stockId) {
        if (stockId == null) throw new IllegalArgumentException();
        this.priceCents = priceCents;
        this.stockId = stockId;
    }

    public long getPriceCents() {
        return priceCents;
    }

    public String getStockId() {
        return stockId;
    }

    @Override
    public int compareTo(PriceIdKey o) {
        int c = Long.compare(this.priceCents, o.priceCents);
        if (c != 0) return c;
        return this.stockId.compareTo(o.stockId);
    }
}

