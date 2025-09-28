public class Library {
    // Define a constant
    public static final int BOOK_LIMIT = 20;
    public static final int MEMBER_LIMIT = 5;
    String name;
    Book[] books = new Book[BOOK_LIMIT];
    Member[] members = new Member[MEMBER_LIMIT];

    private static int bookCounter;
    private static int OverallBookCounter;
    private static int MemberCounter;
    private int lbCounter;

    //Library constructor
    Library(String libraryName) {
        name = libraryName;
        bookCounter = 0;
        OverallBookCounter = 0;
        MemberCounter = 0;
    }

    // add a new book to the library
    public void addBook(String title, Genre genre, String authorName, String authorBib) {
        try {
            if (bookCounter >= BOOK_LIMIT) {
                // print error
                throw new Exception("Library is full, cannot add more books.");
            }

            //New book,
            //Checking if author exist
            Author currentAuthor =  getAuthorByNameAndBib(authorName, authorBib);
            if (currentAuthor == null) {
                //author not exists
                currentAuthor = new Author(authorName, authorBib);
            }

            Book book = new Book(title, genre, currentAuthor, getBookNumberIdString());
            books[bookCounter] = book;
            bookCounter++;
            OverallBookCounter++;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // remove book from the library
    public void removeBook(String title, Genre genre, String authorName, String authorBib) {
        try {
            if (!isBookExist(title)) {
                // print error
                throw new Exception("No such book exists.");
            }
            if (isBookLoaned(title)) {
                throw new Exception("No such book exists.");
            }
            books[bookCounter - 1] = null;
            bookCounter--;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // print the list of all the book in the library
    public void printBooks() {
        try {
            if (bookCounter == 0) {
                throw new Exception("No books in the library currently.");
            }
            for (int i = 0; i < bookCounter; i++) {
                Book current_book = books[i];
                current_book.printBook(false);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    // add a new member to the library
    public void addMember(String name, int bookLimit) {
        try {
            if (MemberCounter >= MEMBER_LIMIT) {
                throw new Exception("Library is full, cannot add more members.");
            }
            LibraryCard currentlibraryCard = new LibraryCard(bookLimit, lbCounter++);
            Member currentmember = new Member(name, currentlibraryCard);
            members[MemberCounter] = currentmember;
            MemberCounter++;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // remove member from the library
    public void removeMember(String MemberID) {
        try {
            // check if member exists
            int CurrentMemberIndex = isMemberExistsIndex(MemberID);
            if (CurrentMemberIndex == -1){
                throw new Exception("No such member exists.");
            }
            // check loaned books of member
            releaseLoanedBook(members[CurrentMemberIndex].GetAllBooks());
            // remove member
            members[CurrentMemberIndex] = null;

            for (int i = CurrentMemberIndex + 1; i < MemberCounter; i++) {
                members[i-1] = members[i];
            }
            MemberCounter--;

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // print member details and his book
    public void printMember(String MemberID) {
        try {
            // check if member exists
            int CurrentMemberIndex = isMemberExistsIndex(MemberID);


            if (CurrentMemberIndex == -1){
                throw new Exception("No member exists.");
            }
            members[CurrentMemberIndex].printMemberName();
            LibraryCard MemberLC = members[CurrentMemberIndex].getID();
            MemberLC.printLoanedBooks();
            }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // borrow a book
    public void checkOutBook(String BookID, String LibraryCardID) {
        try {
            int CurrentBookIndex = isBookExistsIndex(BookID);
            if (CurrentBookIndex == -1) {
                throw new Exception("No such book exists.");
            }
            int CurrentMemberIndex = isMemberExistsIndex(LibraryCardID);
            if (CurrentMemberIndex == -1){
                throw new Exception("No such member exists.");
            }

            if(books[CurrentBookIndex].IsLoaned()) {
                throw new Exception("The book is already checked-out.");
            }
            members[CurrentMemberIndex].getID().addLoanedBook(books[CurrentBookIndex]);
            books[CurrentBookIndex].setLoaned(true);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // return a book
    public void returnBook(String BookID, String LibraryCardID) {
        try {
            int CurrentMemberIndex = isMemberExistsIndex(LibraryCardID);
            int CurrentBookIndex = isBookExistsIndex(BookID);
            if (CurrentMemberIndex == -1){
                throw new Exception("No such member exists.");
            }
            books[CurrentBookIndex].setLoaned(false);
            members[CurrentMemberIndex].getID().removeLoanedBook(books[CurrentBookIndex]);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    // get the author object by his book id
    public Author getAuthor(String BN) {
        for (int i = 0; i < BOOK_LIMIT; i++)
            if (books[i].getUnique_identifier().equals(BN))
                return books[i].getauthor();
        return null;
    }

    /////////////////////////////////////
////// Private Methods //////////////

    // create book id
    private String getBookNumberIdString() {
        return "BN" + OverallBookCounter;
    }

    // get the object author by his name
    private Author getAuthorByName(String author) {
        for (int i = 0; i < bookCounter; i++)
            if (books[i].getauthor().getName().equals(author))
                return books[i].getauthor();
        return null;
    }

    // get the object author by his Bibliography
    private Author getAuthorByNameAndBib(String AuthorName, String AuthorBib) {
        for (int i = 0; i < bookCounter; i++)
            if (books[i].getauthor().getName().equals(AuthorName) && books[i].getauthor().getBibliography().equals(AuthorBib))
                return books[i].getauthor();
        return null;
    }

    // checking if book exist by title
    private boolean isBookExist(String title) {
        for (int i = 0; i < bookCounter; i++)
            if (books[i].getTitle().equals(title))
                return true;
        return false;
    }

    //checking if book already loaned
    private boolean isBookLoaned(String title) {
        for (int i = 0; i < bookCounter; i++)
            if (books[i].getTitle().equals(title) && books[i].IsLoaned()) {
                return true;
            }
        return false;
    }

    // checking if member exist
    private int isMemberExistsIndex(String MemberID) {
        for (int i = 0; i < MemberCounter; i++) {
            if (members[i].getID().getLibraryCardID().equals(MemberID)) {
                return i;
            }
        }
        return -1;
    }

   // checking if book exist by id
    private int isBookExistsIndex(String BookID) {
        for (int i = 0; i < bookCounter; i++) {
            if (books[i].getUnique_identifier().equals(BookID)) {
                return i;
            }
        }
        return -1;
    }

    // return book
    private void releaseLoanedBook(Book[] loanedBooks) {
        for (int i = 0; i < loanedBooks.length; i++) {
            if (loanedBooks[i] != null) {
                for (int j = 0; j < bookCounter; j++) {
                    if (loanedBooks[i].getUnique_identifier() == books[j].getUnique_identifier()) {
                        books[j].setLoaned(false);
                    }
                }
            }

        }
    }

}