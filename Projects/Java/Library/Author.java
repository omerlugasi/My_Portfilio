public class Author {
    private final String name;
    private final String bibliography;

    // Author constructor
    Author(String name, String bibliography){
        this.name = name;
        this.bibliography = bibliography;
    }

    // get author name
    public String getName() {
        return name;
    }

    // get author bibliography
    public String getBibliography() {
        return bibliography;
    }
}