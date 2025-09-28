
public class Member {
    private final String name;
    private final LibraryCard MemberID;

    // Member constructor
    public Member(String name, LibraryCard libraryCard) {
        this.name = name;
        this.MemberID = libraryCard;
    }

    // get member id
    public LibraryCard getID() {
        return MemberID;
    }

    //print member name
    public void printMemberName() {
        System.out.println("Name: " + this.name + ", Checked-out books:");
    }

    // get all member's book
    public Book [] GetAllBooks() {
        return  MemberID.GetLoanedBooks();
    }

    // get member's book limit
    public int GetBookLimit() {
        return MemberID.GetBookLimit();
    }
}