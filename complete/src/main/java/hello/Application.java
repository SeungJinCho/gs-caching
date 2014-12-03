package hello;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.jcache.config.JCacheConfigurer;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import javax.cache.Caching;

@Configuration
@EnableCaching
@EnableAutoConfiguration
public class Application{

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	@Configuration
	static class Runner implements CommandLineRunner {
		@Autowired
		private BookRepository bookRepository;

		@Override
		public void run(String... args) throws Exception {
			log.info(".... Fetching books");
			log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
			log.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
			log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
			log.info("isbn-4567 -->" + bookRepository.getByIsbn("isbn-4567"));
			log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
			log.info("isbn-1234 -->" + bookRepository.getByIsbn("isbn-1234"));
			log.info("isbn-1234 -->" + bookRepository.getByIsbn2("isbn-1234"));
			log.info("isbn-4567 -->" + bookRepository.getByIsbn2("isbn-4567"));
			log.info("isbn-1234 -->" + bookRepository.getByIsbn2("isbn-1234"));
			log.info("isbn-4567 -->" + bookRepository.getByIsbn2("isbn-4567"));
			log.info("isbn-1234 -->" + bookRepository.getByIsbn2("isbn-1234"));
			log.info("isbn-1234 -->" + bookRepository.getByIsbn2("isbn-1234"));
		}
	}

	@Bean
	public BookRepository bookRepository() {
		return new SimpleBookRepository();
	}


	@Bean
	public CacheResolver caheResolver(){
		return new SimpleCacheResolver(cacheManager());
	}

	@Bean
	public CacheManager cacheManager() {
		return new CompositeCacheManager(concurrentCacheManger(), guavaCacheManager());
	}

	private CacheManager guavaCacheManager() {
		GuavaCacheManager gcm = new GuavaCacheManager("books2");
		gcm.setCacheLoader(
				new CacheLoader<Object , Object>() {
					@Override
					public Object load(Object key) throws Exception {
						return null;
					}
				}
		);
		gcm.setCacheBuilder(CacheBuilder.newBuilder().maximumSize(1000).refreshAfterWrite(5000, TimeUnit.MINUTES));
		return gcm;
	}

	private CacheManager concurrentCacheManger(){
		return new ConcurrentMapCacheManager("books");
	}


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
