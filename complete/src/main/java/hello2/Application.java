package hello2;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created by Coupang on 2014-12-03.
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@Controller
public class Application extends WebMvcConfigurationSupport {

	@RequestMapping(value = "/simple", method = RequestMethod.GET)
	@JsonView(User.WithoutPasswordView.class)
	@ResponseBody
	public  User getUser() {
		return new User("eric", "7!jd#h23");
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	@ResponseBody
	@JsonView(User.WithPasswordView.class)
	public User getUserByAdmin() {
		return new User("eric", "7!jd#h23");
	}

	@RequestMapping(value = "m/{id}", produces = "text/plain")
	public String execute(@PathVariable Integer id,
		@MatrixVariable Optional<Integer> p,
		@MatrixVariable Optional<Integer> q) {

		StringBuilder result = new StringBuilder();
		result.append("p: ");
		p.ifPresent(value -> result.append(value));
		result.append(", q: ");
		q.ifPresent(value -> result.append(value));
		return result.toString();
	}

	@Bean
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(customJackson2HttpMessageConverter());
		super.addDefaultHttpMessageConverters(converters);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}
