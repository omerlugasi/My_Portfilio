

public class TNode < K extends Key<K> > {
    private K key;
    private TNode<K> left;
    private TNode<K> middle;
    private TNode<K> right;
    private TNode<K> parent;
    private Stock data;
    private Float priceDifference;
    private Tree2_3<LongKey> treeUpdateEvents;
    private int size;
    private TNode<K> next;
    private TNode<K> prev;

    //בנאי עבור שורש והסנטינליים
    public TNode(TNode<K> parent, K key) {
        this.parent = parent;
        size = 0;
        this.key = key;
    }

    //בנאי עבור קודקוד פנימי
    public TNode(){
        this.data = null;
    }

    // בנאי עבור יצירת העלים בעץ STOCKID
    public TNode(K key, Float price,long timestamp, TNode<K> parent) {
        this.key = key;
        this.data = new Stock(price, timestamp);
        this.left = null;
        this.middle = null;
        this.right = null;
        this.parent = parent;
        LongKey lSentinalKeyEvent = new LongKey(true,false);
        LongKey rSentinalKeyEvent = new LongKey(false,true);
        this.treeUpdateEvents = new Tree2_3<>(lSentinalKeyEvent, rSentinalKeyEvent);
        this.size = 1;
    }
    // בנאי עבור יצירת העלים בעץ PRICE
    public TNode(K key, TNode<K> parent) {
        this.key = key;
        this.data = null;
        this.left = null;
        this.middle = null;
        this.right = null;
        this.parent = parent;
        this.treeUpdateEvents = null;
        this.size = 1;
    }
    // בנאי עבור יצירת העלים בעץ UPDATE_EVENTS
    public TNode(K key,Float priceDifference, TNode<K> parent){
        this.key = key;
        this.data = null;
        this.left = null;
        this.middle = null;
        this.right = null;
        this.parent = parent;
        this.priceDifference = priceDifference;
        this.treeUpdateEvents = null;
        this.size = 1;
    }

    public TNode<K> getLeft() {
        return left;
    }
    public void setLeft(TNode<K> left) {
        this.left = left;
    }
    public TNode<K> getMiddle() {
        return middle;
    }
    public void setMiddle(TNode<K> middle) {
        this.middle = middle;
    }
    public TNode<K> getRight() {
        return right;
    }
    public void setRight(TNode<K> right) {
        this.right = right;
    }
    public TNode<K> getParent() {
        return parent;
    }
    public void setParent(TNode<K> parent) {
        this.parent = parent;
    }
    public K getKey() {
        return key;
    }
    public void setKey(K key) {
        this.key = key;
    }
    public Stock getData() {
        return data;
    }
    public void setData(Stock data) {
        this.data = data;
    }
    public boolean isLeaf() {
        return (left == null);
    }
    public Tree2_3<LongKey> getTreeUpdateEvents() {
        return treeUpdateEvents;
    }
    public void setTreeUpdateEvents(Tree2_3<LongKey> treeUpdateEvents) {
        this.treeUpdateEvents = treeUpdateEvents;
    }
    public Float getPriceDifference() {
        return priceDifference;
    }
    public void setPriceDifference(Float priceDifference) {
        this.priceDifference = priceDifference;
    }
    public int getSize(){
        return size;
    }
    public void setSize(int size){
        this.size = size;
    }
    public TNode<K> getNext() {
        return next;
    }
    public void setNext(TNode<K> next) {
        this.next = next;
    }
    public TNode<K> getPrev() {
        return prev;
    }
    public void setPrev(TNode<K> prev) {
        this.prev = prev;
    }


    public void updateKey() {
        if (this.getLeft() != null && !this.getLeft().getKey().getLeftSentinel() && !this.getLeft().getKey().getRightSentinel()) {
            this.setKey(this.getLeft().getKey());
        }
        if (this.getMiddle() != null && !this.getMiddle().getKey().getLeftSentinel() && !this.getMiddle().getKey().getRightSentinel()) {
            this.setKey(this.getMiddle().getKey()) ;
        }
        if (this.getRight() != null && !this.getRight().getKey().getLeftSentinel() && !this.getRight().getKey().getRightSentinel()) {
            this.setKey( this.getRight().getKey());
        }
        TNode<K> rightmostChild;
        if (this.right != null) {
            rightmostChild = this.right;
        } else {
            rightmostChild = this.middle;
        }
        if (rightmostChild != null && rightmostChild.getKey().getRightSentinel()) {
            this.setKey(rightmostChild.getKey());
        }
    }

    public void updateSize() {
        this.size = this.getLeft().getSize();
        if (this.getMiddle() != null) {
            this.size += this.getMiddle().getSize();
        }
        if (this.getRight() != null) {
            this.size += this.getRight().getSize();
        }
    }

}