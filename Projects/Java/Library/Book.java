public class Book {
    private final String title;
    private final Genre genre;
    private final Author author;
    private final String unique_identifier;
    private boolean loaned;

    // Book constructor
    Book(String title, Genre genre, Author author, String unique_identifier) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.unique_identifier = unique_identifier;
        this.loaned = false;

    }

    // set the loaned attribute
    public void setLoaned(boolean loaned) {
        this.loaned = loaned;
    }

    // get the book title
    public String getTitle() {
        return title;
    }

    // get the book's author
    public Author getauthor() {
        return author;
    }

    // get BN (book id)
    public String getUnique_identifier() {
        return unique_identifier;
    }

    // check if book is in loan
    public boolean IsLoaned() {
        return loaned;
    }

    // print book details
    public void printBook(boolean force){
            if (force || !loaned )
                System.out.println("Title: " + title + ", Genre: " + genre + ", Author: " + author.getName() + ".");
    }
}