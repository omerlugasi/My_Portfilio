
public class StockManager {
    private Tree2_3<StringKey> treeStockID;
    private Tree2_3<FloatKey> treePrice;

    public StockManager() {
        initStocks();
    }

    // 1. Initialize the system
    public void initStocks() {
        StringKey lSentinalKeyStockID = new StringKey(null, true,false);
        StringKey rSentinalKeyStockID = new StringKey(null, false,true);
        this.treeStockID = new Tree2_3<>(lSentinalKeyStockID, rSentinalKeyStockID);
        FloatKey lSentinalKeyPrice = new FloatKey(null,null,true,false);
        FloatKey rSentinalKeyPrice = new FloatKey(null,null,false,true);
        this.treePrice = new Tree2_3<>(lSentinalKeyPrice, rSentinalKeyPrice);
    }

    // 2. Add a new stock
    public void addStock(String stockId, long timestamp, Float price) {         // לשאול אם יכול להיות שמחיר של מניה הוא שלילי כבר באתחול שלו? כלומר בקלט שמקבלים
        if ((stockId != null) && (timestamp > 0L) && (price > 0)){   //ווידוא שהקלט עצמו תקין ושהמחיר הראשוני של המניה חיובי ממש (מבטיח שהוא גם לא NULL)
            StringKey sKey = new StringKey(stockId);
            TNode<StringKey> foundNode = treeStockID.Search(treeStockID.getRoot(),sKey);
            if (foundNode == null){     // ווידוא שלא מוסיפים מניה שכבר קיימת בעץ
                TNode<StringKey> stockIDNode = new TNode<>(sKey,price,timestamp,null);
                treeStockID.Insert(stockIDNode);
                FloatKey FKey = new FloatKey(price,stockId);
                TNode<FloatKey> priceNode = new TNode<>(FKey,null);
                treePrice.Insert(priceNode);
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // 3. Remove a stock
    public void removeStock(String stockId) {
        if (stockId != null){
            StringKey sKey = new StringKey(stockId);
            TNode<StringKey> foundNode = treeStockID.Search(treeStockID.getRoot(), sKey);
            if (foundNode != null){     // רק אם מצאתי את המניה
                FloatKey fKey = new FloatKey(foundNode.getData().getCurrentPrice(),stockId);    //יצרנו מפתח ששייך לעץ מחירים שהערך שלו זה המחיר העדכני של המניה שרוצים למחוק שמצאנו אותה בעץ לפי המזהה מניה
                treePrice.Delete(treePrice.Search(treePrice.getRoot() , fKey));     // מוחקים מהעץ של המחירים את הNODE המתאים (בעל המפתח מסוג FLOATKEY)
                //לשים לב שהערך של הפרמטר של מפתח שנותנים לפונקציה SEARCH הוא בעצם הקלט של הפונקציה הכללית פה REMOVE_STOCK **
                treeStockID.Delete(foundNode);  // יימחק גם את עץ החותמות זמן של אותו NODE- מניה
            }
            else {
                throw new IllegalArgumentException();   // במקרה של מחיקת מניה שלא קיימת
            }
        }
        else {
            throw new IllegalArgumentException();   // במקרה שהקלט לא תקין
        }

    }

    public void changePriceStock(String stockId,TNode<StringKey> foundStockIDNode, int priceDiffSign, Float priceDifference) {
        // מחיקת הNODE עם המחיר הישן מעץ המחירים (לא ווידאנו שלא חוזר מהSEARCH ערך NULL כי זה בהתאם להוספה שלנו- מתואם עם העץ STOCKID***
        FloatKey fKey = new FloatKey(foundStockIDNode.getData().getCurrentPrice(),stockId);     // כדי למצוא את הNODE עם המפתח בעל המחיר הנוכחי שאותו ארצה למחוק אחכ
        //יצירת מפתח חדש לעץ מחירים עם המחיר החדש (שמשתמש במחיר הישן של המניה ולכן שמרנו אותו במשתנה לפני שנמחק את המניה ונאבד את המידע)
        FloatKey newFKey = new FloatKey(foundStockIDNode.getData().getCurrentPrice() + (priceDiffSign * priceDifference) ,stockId);
        treePrice.Delete(treePrice.Search(treePrice.getRoot(), fKey));  // מחיקת הNODE עם המחיר הישן מעץ המחירים
        TNode<FloatKey> newPriceNode = new TNode<>(newFKey,null);   // הוספת הNODE החדש (עם המחיר המעודכן) לעץ מחירים
        treePrice.Insert(newPriceNode);
        // עדכון המחיר הנוכחי בתכונה של מחיר נוכחי במחלקת STOCK (כלומר בתכונה של המניה בעץ הSTOCKID)
        foundStockIDNode.getData().setCurrentPrice(foundStockIDNode.getData().getCurrentPrice() + (priceDiffSign * priceDifference));
    }


    // 4. Update a stock price
    public void updateStock(String stockId, long timestamp, Float priceDifference) {
        if ((stockId != null) && (timestamp > 0L) && (priceDifference != 0)) {   //ווידוא שהקלט עצמו תקין ושעדכון המחיר שונה מאפס
            StringKey sKey = new StringKey(stockId);
            TNode<StringKey> foundStockIDNode = treeStockID.Search(treeStockID.getRoot(), sKey);
            if (foundStockIDNode != null){
                if (foundStockIDNode.getData().getInitTimestamp() != timestamp) {
                    LongKey lKey = new LongKey(timestamp);
                    Tree2_3<LongKey> treeUpdates = foundStockIDNode.getTreeUpdateEvents();
                    TNode<LongKey> foundTimestampNode = treeUpdates.Search(treeUpdates.getRoot(), lKey);
                    if (foundTimestampNode == null){
                        TNode<LongKey> updateEventNode = new TNode<>(lKey,priceDifference,null);
                        treeUpdates.Insert(updateEventNode);    // הוספת אירוע עדכון מחיר לעץ החותמות זמן של המניה המבוקשת- במקרה שלא מצאנו את האירוע זה בעץ
                        changePriceStock(stockId,foundStockIDNode, 1, priceDifference);
                    }
                    else{
                        throw new IllegalArgumentException();   // במידה ומצאנו שאירוע עדכון המחיר עם החותמת זמן המבוקשת כבר קיים בעץ החותמות זמן של מניה זו
                    }
                }
                else {
                    throw new IllegalArgumentException();   // במקרה וחותמת זמן של האירוע עדכון מחיר היא אותה חותמת זמן של אירוע האתחול של המניה- לא אפשרי
                }
            }
            else {
                throw new IllegalArgumentException();   // אם לא נמצא המניה שעבורה יש להוסיף אירוע עדכון מחיר
            }
        }
        else {
            throw new IllegalArgumentException();   // אם הקלט לא תקין
        }
    }

    // 5. Get the current price of a stock
    public Float getStockPrice(String stockId) {
        if (stockId != null) {
            StringKey sKey = new StringKey(stockId);
            TNode<StringKey> foundNode = treeStockID.Search(treeStockID.getRoot(), sKey);
            if (foundNode != null) {     // רק אם מצאתי את המניה
                return foundNode.getData().getCurrentPrice();
            }
            throw new IllegalArgumentException();   // במקרה שהמניה לא קיימת בעץ STOCKID

        }
        throw new IllegalArgumentException();   // במקרה שהקלט לא תקין
    }

    // 6. Remove a specific timestamp from a stock's history
    public void removeStockTimestamp(String stockId, long timestamp) {
        if ((stockId != null) && (timestamp > 0L)) {                           //לבדוק לגבי 0L ?
            StringKey sKey = new StringKey(stockId);
            TNode<StringKey> foundStockIDNode = treeStockID.Search(treeStockID.getRoot(), sKey);
            if (foundStockIDNode != null){      // רק אם מצאתי את המניה
                LongKey lKey = new LongKey(timestamp);
                Tree2_3<LongKey> treeUpdates = foundStockIDNode.getTreeUpdateEvents();
                TNode<LongKey> foundTimestampNode = treeUpdates.Search(treeUpdates.getRoot(), lKey);
                if (foundTimestampNode != null) {   // במקרה שמצאנו את האירוע עדכון מחיר המבוקש במניה המבוקשת
                   Float priceDifference = foundTimestampNode.getPriceDifference();
                    treeUpdates.Delete(foundTimestampNode); // מחיקת אירוע העדכון מחיר המבוקש מהמניה המבוקשת
                    changePriceStock(stockId,foundStockIDNode, -1, priceDifference);    // עדכון המחיר הנוכחי של המניה המבוקשת
                }
                else {
                    throw new IllegalArgumentException();   // אם האירוע עדכון מחיר המבוקש לא קיים בעץ החותמות זמן של המניה המבוקשת
                }

            }
            else {
                throw new IllegalArgumentException();   //במקרה שהמניה לא קיימת בעץ STOCKID
            }
        }
        else {
            throw new IllegalArgumentException();   // אם אחד הקלטים לא תקין
        }

    }



    // 7. Get the amount of stocks in a given price range
    public int getAmountStocksInPriceRange(Float price1, Float price2) {
        if (price1 <= price2 ) { // לוודא לגבי בדיקת הקלט
            FloatKey price1Key = new FloatKey(price1,"");
            FloatKey price2Key = new FloatKey(price2,"\uFFFF\uFFFF");
            TNode<FloatKey> price1Node = new TNode<>(price1Key,null);
            treePrice.Insert(price1Node);
            TNode<FloatKey> price2Node = new TNode<>(price2Key,null);
            treePrice.Insert(price2Node);
            int statisticPrice1 = treePrice.rank(price1Node);
            int statisticPrice2 = treePrice.rank(price2Node);
            treePrice.Delete(price1Node);
            treePrice.Delete(price2Node);

            return statisticPrice2 - statisticPrice1 - 1;
        } else {
            throw new IllegalArgumentException(); // הקלט לא תקין
        }
    }

    // 8. Get a list of stock IDs within a given price range
    public String[] getStocksInPriceRange(Float price1, Float price2) {
        if (price1 <= price2 ) {      // לוודא לגבי בדיקת הקלט
            int k = getAmountStocksInPriceRange(price1,price2);
            String[] stocksIdArr = new String[k];
            FloatKey price1Key = new FloatKey(price1,"");
            TNode<FloatKey> price1Node = new TNode<>(price1Key,null);
            treePrice.Insert(price1Node);
            TNode<FloatKey> closestToPrice1Node = treePrice.successor(price1Node);
            treePrice.Delete(price1Node);
            int i = 0;
            TNode<FloatKey> tempNode = closestToPrice1Node;
            while ((tempNode != null && i <stocksIdArr.length && !tempNode.getKey().getRightSentinel()) && (tempNode.getKey().getPrice() <= price2)) {
                stocksIdArr[i] = tempNode.getKey().getStockID();
                tempNode = tempNode.getNext();      //אולי צריך המרה ?
                i++;
            }
            return stocksIdArr;
        } else {
        throw new IllegalArgumentException(); // הקלט לא תקין
        }

    }
   
}


