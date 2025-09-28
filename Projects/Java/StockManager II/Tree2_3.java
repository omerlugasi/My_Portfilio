

public class Tree2_3<K extends Key<K>> {
    private TNode<K> root;

    public Tree2_3(K lSentinelKey, K rSentinelKey) {
        root = new TNode<>(); //משתמש בבנאי לקודקוד פנימי שהוא דיפולטי אבל תכלס טוב גם לשורש
        TNode<K> lSentinel = new TNode<>(root, lSentinelKey);
        TNode<K> rSentinel = new TNode<>(root, rSentinelKey);
        //(lSentinel.getKey()).setLeftSentinel(true);
        //(rSentinel.getKey()).setRightSentinel(true);
        setChildren(this.root, lSentinel, rSentinel, null);
    }
    public TNode<K> getRoot() {
        return root;
    }


    public void setChildren(TNode<K> x, TNode<K> l, TNode<K> m, TNode<K> r) {
        x.setLeft(l); // האם שמים ככה שרירותי
        x.setMiddle(m);
        x.setRight(r);
        l.setParent(x);
        if (m != null){
            m.setParent(x);
        }
        if (r != null){
            r.setParent(x);
        }
        x.updateKey();
        x.updateSize();
    }

    public TNode<K> InsertAndSplit(TNode<K> x, TNode<K> z) {
        TNode<K> l = x.getLeft();
        TNode<K> m = x.getMiddle();
        TNode<K> r = x.getRight();
        if (r == null){
            if (z.getKey().compareSmallerKey(l.getKey()) < 0 ){
                setChildren(x,z,l,m);
            } else if (z.getKey().compareSmallerKey(m.getKey()) < 0 ){
                setChildren(x,l,z,m);
            } else {
                setChildren(x,l,m,z);
            }
            return null;
        }
        TNode<K> y = new TNode<>();
        if (z.getKey().compareSmallerKey(l.getKey()) < 0 ) {
            setChildren(x,z,l,null);
            setChildren(y,m,r,null);
        } else if (z.getKey().compareSmallerKey(m.getKey()) < 0 ) {
            setChildren(x,l,z,null);
            setChildren(y,m,r,null);
        } else if (z.getKey().compareSmallerKey(r.getKey()) < 0 ) {
            setChildren(x,l,m,null);
            setChildren(y,z,r,null);
        } else {
            setChildren(x, l, m, null);
            setChildren(y,r,z,null);
        }
        return y;
    }


    public void Insert(TNode<K> z) {
        TNode<K> y = this.root;
        while (y.getLeft() != null){
            if (z.getKey().compareSmallerKey(y.getLeft().getKey()) < 0) {
                y = y.getLeft();
            } else if (z.getKey().compareSmallerKey(y.getMiddle().getKey()) < 0) {
                y = y.getMiddle();
            }
            else {
                y = y.getRight();
            }
        }
        TNode<K> x = y.getParent();
        TNode<K> z1 = InsertAndSplit(x,z);
        while (x != this.root){
            x = x.getParent();
            if (z1 != null) {
                z1 = InsertAndSplit(x,z1);
            }
            else {
                x.updateKey();
                x.updateSize();
            }
        }
        if (z1 != null) {
            TNode<K> w = new TNode<>();
            setChildren(w,x,z1,null);
            this.root = w;
        }

        TNode<K> prevLeaf = predecessor(z);
        TNode<K> nextLeaf = successor(z);
        if (prevLeaf != null && !prevLeaf.getKey().getLeftSentinel()) {
            prevLeaf.setNext(z);
            z.setPrev(prevLeaf);
        }
        if (nextLeaf != null && !nextLeaf.getKey().getRightSentinel()) {
            nextLeaf.setPrev(z);
            z.setNext(nextLeaf);
        }
    }

    public TNode<K> BorrowOrMerge(TNode<K> y) {
        TNode<K> z = y.getParent();
        if(y == z.getLeft()) {
            TNode<K> x = z.getMiddle();
            if (x.getRight() != null){
                setChildren(y,y.getLeft(),x.getLeft(),null);
                setChildren(x,x.getMiddle(),x.getRight(),null);
            }
            else {
                setChildren(x,y.getLeft(),x.getLeft(),x.getMiddle());
                y.setParent(null);                                       // למחוק את Y  ????
                setChildren(z,x,z.getRight(),null);
            }
            return z;
        }
        if( y == z.getMiddle()) {
            TNode<K> x = z.getLeft();
            if (x.getRight() != null){
                setChildren(y,x.getRight(),y.getLeft(),null);
                setChildren(x,x.getLeft(),x.getMiddle(),null);
            }
            else {
                setChildren(x,x.getLeft(),x.getMiddle(),y.getLeft());
                y.setParent(null);  // למחוק את Y  ????
                setChildren(z,x,z.getRight(),null);
            }
            return z;
        }
        TNode<K> x = z.getMiddle();
        if (x.getRight() != null){
            setChildren(y,x.getRight(),y.getLeft(),null);
            setChildren(x,x.getLeft(),x.getMiddle(),null);
        }
        else {
            setChildren(x,x.getLeft(),x.getMiddle(),y.getLeft());
            y.setParent(null);  // למחוק את Y  ????
            setChildren(z,z.getLeft(),x,null);
        }
        return z;
    }



    public void Delete(TNode<K> x) {

        TNode<K> prevLeaf = x.getPrev();
        TNode<K> nextLeaf = x.getNext();


//       if (prevLeaf != null) { // האם להוסיף סנטינל
//           prevLeaf.setNext(nextLeaf);
//        }
//       if (nextLeaf != null) {
//            nextLeaf.setPrev(prevLeaf);
//        }
/////
       if (prevLeaf != null && nextLeaf != null) {
           prevLeaf.setNext(nextLeaf);
           nextLeaf.setPrev(prevLeaf);
       } else if (prevLeaf != null) {
           prevLeaf.setNext(nextLeaf); // nextLeaf = null
       } else if (nextLeaf != null) {
          nextLeaf.setPrev(prevLeaf);  // prevLeaf = null
       }


        TNode<K> y = x.getParent();
        if (x == y.getLeft()) {
            setChildren(y, y.getMiddle(), y.getRight(), null);
        } else if (x == y.getMiddle()) {
            setChildren(y, y.getLeft(), y.getRight(), null);

        } else {
            setChildren(y, y.getLeft(), y.getMiddle(), null);
        }
        x.setParent(null); // למחוק את x  ????

        while (y != null) {
            if (y.getMiddle() != null) {
                y.updateKey();
                y.updateSize();
                y = y.getParent();
            } else {
                if (y != this.root) {
                    y = BorrowOrMerge(y);
                } else {
                    this.root = y.getLeft();
                    (y.getLeft()).setParent(null);
                     y.setParent(null);// למחוק את Y  ???
                    return;
                }
            }
        }
    }


    public TNode<K> Search(TNode<K> x, K k){
        if (x != null && x.isLeaf()) {
            if (x.getKey().compareSmallerKey(k) == 0) {
                return x;
            } else {
                return null;
            }
        }
        if (k.compareSmallerKey(x.getLeft().getKey()) <= 0 ){
            return Search(x.getLeft(),k);
        } else if (k.compareSmallerKey(x.getMiddle().getKey()) <= 0){
            return Search(x.getMiddle(),k);
        } else if (x.getRight() != null) {
            return Search(x.getRight(),k);
        }
        return null;
    }

    public int rank(TNode<K> x) {
        int rank = 1;
        TNode<K> y = x.getParent();
        while (y != null) {
            if (x == y.getMiddle()) {
                rank = rank + y.getLeft().getSize();
            } else if (x == y.getRight()) {
                rank = rank + y.getLeft().getSize() + y.getMiddle().getSize();
            }
            x = y;
            y = y.getParent();
        }
        return rank;
    }

    public TNode<K> successor(TNode<K> x) {
        TNode<K> z = x.getParent();
        while (z != null && (x == z.getRight()  || (z.getRight() == null && x == z.getMiddle()))) {
            x = z;
            z = z.getParent();
        }
        if (z == null) {
            return null;
        }
        TNode<K> y;
        if (x == z.getLeft()) {
            y = z.getMiddle();
        }
        else {
            y = z.getRight();
        }
        while (!y.isLeaf()) {
            y = y.getLeft();
        }
        if (!(y.getKey().getRightSentinel())) {
            return y;
        }
        else {
            return null;
        }
    }

    public TNode<K> Orgpredecessor(TNode<K> x) {
        TNode<K> z = x.getParent();
        while (z != null && (x == z.getLeft() || (z.getLeft() == null && x == z.getMiddle()))) {
            x = z;
            z = z.getParent();
        }
        if (z == null) {
            return null;
        }
        TNode<K> y;
        if (x == z.getRight()) {
            y = z.getMiddle();
        } else {
            y = z.getLeft();
        }
        while (y != null && !y.isLeaf()) {
            y = y.getRight();
        }
        if (y == null) {
            return null;
        }
        if (!(y.getKey().getLeftSentinel())) {
            return y;
        } else {
            return null;
        }
    }

    public TNode<K> predecessor(TNode<K> x) {
        TNode<K> z = x.getParent();
        while (z != null && x == z.getLeft() || (x.getLeft() == null && z == z.getMiddle())){
            x = z;
            z = z.getParent();
        }
        if (z == null) {
            return null;
        }
        TNode<K> y;
        if (x == z.getRight()) {
            y = z.getMiddle();
        }
        else {
            y = z.getLeft();
        }
        while (!y.isLeaf()) {
            if (y.getRight() == null)
                y = y.getMiddle();
            else
                y = y.getRight();
        }
        if (!(y.getKey().getLeftSentinel())) {
            return y;
        } else {
            return null;
        }
    }
}
