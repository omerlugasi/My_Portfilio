public class LibraryCard {
    private final String LibraryCardID;
    private int BookLimit;
    private Book[] LoanedBooks;
    private int LoanBookCounter = 0;

    // LibraryCard constructor
    public LibraryCard(int bookLimit, int id ) {
        this.LibraryCardID = "LC" + id;
        this.LoanedBooks = new Book[bookLimit];
        this.BookLimit = bookLimit;
    }

    // get LC (LibraryCardID)
    public String getLibraryCardID() {
        return LibraryCardID;
    }

    // print all member's books
    public void printLoanedBooks() {
        for (int i = 0; i < LoanBookCounter; i++) {
            if (LoanedBooks[i] != null) {
                LoanedBooks[i].printBook(true);
            }
        }
    }

    // check if member is in his book limit
    public boolean checkLimit() {
        return LoanBookCounter < BookLimit;
    }

    // check if book is in loan
    public boolean isLoaned(Book book) {
        for (int i = 0; i < LoanBookCounter; i++) {
            if (LoanedBooks[i] == book) {
                return true;
            }
        }
        return false;
    }

    // borrow a book
    public void addLoanedBook(Book book) throws Exception {
        try {
            if (!checkLimit()) {
                throw new Exception( "The member reached the limit");
            }
            LoanedBooks[LoanBookCounter] = book;
            LoanBookCounter++;
        } catch (Exception e) {
          throw new Exception(e.getMessage());
        }
    }

    // return a book
    public void removeLoanedBook(Book book) {
        if (!isLoaned(book)) {
            System.out.println("Member cannot return the book");
            return;
        }
        for (int i = 0; i < LoanBookCounter; i++) {
            if (LoanedBooks[i] == book) {
                LoanedBooks[i] = null;
                for (int j = i + 1; j < LoanBookCounter; j++) {
                    LoanedBooks[j - 1] = LoanedBooks[j];
                }
                break;
            }
        }
    }

    // get all member's books
    public Book [] GetLoanedBooks() {
        return LoanedBooks;
    }

    // get book limit
    public int GetBookLimit() {
        return BookLimit;
    }
}