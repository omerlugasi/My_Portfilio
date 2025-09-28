//package main.structures;

public class Stock {
    private long currentPriceCents;
    private TreapMap<Long, Long> updates;

    public Stock(float price) {
        if (price <= 0) throw new IllegalArgumentException();
        this.currentPriceCents = Math.round(price * 100);
        this.updates = new TreapMap<>();
    }

    public void addUpdate(long timestamp, Float diff) {
        if (diff == null || diff == 0) throw new IllegalArgumentException();
        if (updates.containsKey(timestamp)) throw new IllegalArgumentException();
        long diffCents = Math.round(diff * 100);
        updates.put(timestamp, diffCents);
        currentPriceCents += diffCents;
    }

    public void removeUpdate(long timestamp) {
        if (!updates.containsKey(timestamp)) throw new IllegalArgumentException();
        long diffCents = updates.get(timestamp);
        updates.remove(timestamp);
        currentPriceCents -= diffCents;
    }

    public float getCurrentPrice() {
        return currentPriceCents / 100.0f;
    }

    public long getCurrentPriceCents() {
        return currentPriceCents;
    }
}