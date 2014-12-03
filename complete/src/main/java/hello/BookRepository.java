package hello;

public interface BookRepository {

    public Book getByIsbn(String isbn);

	public Book getByIsbn2(String isdn);

}
