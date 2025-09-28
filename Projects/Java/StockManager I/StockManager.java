//package main.structures;

//import main.structures.Stock;
//import main.structures.PriceIdKey;
//import main.structures.Treap;
//import main.structures.TreapMap;

public class StockManager {
    private TreapMap<String, Stock> stocksById;
    private Treap<PriceIdKey> priceIndex;

    public StockManager() {
        initStocks();
    }

    public void initStocks() {
        stocksById = new TreapMap<>();
        priceIndex = new Treap<>();
    }

    public void addStock(String stockId, long timestamp, Float price) {
        if (stockId == null || price == null) throw new IllegalArgumentException();
        if (price <= 0) throw new IllegalArgumentException();
        if (timestamp < 0) throw new IllegalArgumentException();
        if (stocksById.containsKey(stockId)) throw new IllegalArgumentException();
        Stock s = new Stock(price);
        stocksById.put(stockId, s);
        priceIndex.insert(new PriceIdKey(s.getCurrentPriceCents(), stockId));
    }

    public void removeStock(String stockId) {
        if (!stocksById.containsKey(stockId)) throw new IllegalArgumentException();
        Stock s = stocksById.get(stockId);
        priceIndex.remove(new PriceIdKey(s.getCurrentPriceCents(), stockId));
        stocksById.remove(stockId);
    }

    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        if (stockId == null || priceDifference == null) throw new IllegalArgumentException();
        if (priceDifference == 0) throw new IllegalArgumentException();
        if (timestamp < 0) throw new IllegalArgumentException();
        if (!stocksById.containsKey(stockId)) throw new IllegalArgumentException();
        Stock s = stocksById.get(stockId);
        priceIndex.remove(new PriceIdKey(s.getCurrentPriceCents(), stockId));
        s.addUpdate(timestamp, priceDifference);
        priceIndex.insert(new PriceIdKey(s.getCurrentPriceCents(), stockId));
    }

    public Float getStockPrice(String stockId) {
        if (stockId == null) throw new IllegalArgumentException();
        if (!stocksById.containsKey(stockId)) throw new IllegalArgumentException();
        return stocksById.get(stockId).getCurrentPrice();
    }

    public void removeStockTimestamp(String stockId, long timestamp) {
        if (!stocksById.containsKey(stockId)) throw new IllegalArgumentException();

        Stock s = stocksById.get(stockId);

        priceIndex.remove(new PriceIdKey(s.getCurrentPriceCents(), stockId));
        s.removeUpdate(timestamp);
        priceIndex.insert(new PriceIdKey(s.getCurrentPriceCents(), stockId));
    }

    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        if (price1 == null || price2 == null) throw new IllegalArgumentException();
        if (price1 > price2) throw new IllegalArgumentException();
        long lowCents = Math.round(price1 * 100);
        long highCents = Math.round(price2 * 100);
        PriceIdKey lower = new PriceIdKey(lowCents, "");
        PriceIdKey upper = new PriceIdKey(highCents, "\uFFFF");
        return priceIndex.countInRange(lower, upper);
    }

    public String[] getStocksInPriceRange(Float price1, Float price2) {
        if (price1 == null || price2 == null) throw new IllegalArgumentException();
        if (price1 > price2) return new String[0];
        long lowCents = Math.round(price1 * 100);
        long highCents = Math.round(price2 * 100);
        PriceIdKey lower = new PriceIdKey(lowCents, "");
        PriceIdKey upper = new PriceIdKey(highCents, "\uFFFF");
        Object[] raw = priceIndex.keysInRange(lower, upper);
        PriceIdKey[] keys = new PriceIdKey[raw.length];
        for (int i = 0; i < raw.length; i++) {
            keys[i] = (PriceIdKey) raw[i];
        }
        String[] result = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            result[i] = keys[i].getStockId();
        }
        return result;
    }
}