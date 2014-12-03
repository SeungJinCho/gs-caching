package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import javax.cache.annotation.CacheResult;

public class SimpleBookRepository implements BookRepository {

	private static final Logger log = LoggerFactory.getLogger(SimpleBookRepository.class);


	@Override
    @CacheResult(cacheName = "books")
    public Book getByIsbn(String isbn) {
        simulateSlowService();
        return new Book(isbn, "Some book");
    }

//	@Override
//	@Cacheable(value = "books2" , cacheResolver = "caheResolver")

	@Override
	@CacheResult(cacheName = "books2")
	public Book getByIsbn2(String isbn) {
		simulateSlowService();
		return new Book(isbn, "Some book2");
	}



    // Don't do this at home
    private void simulateSlowService() {
        try {
            long time = (3000L);
	        log.info("sleeping.......................");
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

}
