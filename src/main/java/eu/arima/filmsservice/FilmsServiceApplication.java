package eu.arima.filmsservice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.postgresql.util.PGobject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@EnableJdbcRepositories
@SpringBootApplication
public class FilmsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmsServiceApplication.class, args);
	}

	@Configuration
	public class WebMvcConfig implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**");
		}
	}

	@Configuration
	public static class DataJdbcConfiguration extends AbstractJdbcConfiguration {

		@Override
		public JdbcCustomConversions jdbcCustomConversions() {
			return new JdbcCustomConversions(Collections.singletonList(PgobjectToStringListConverter.INSTANCE));
		}

		@ReadingConverter
		enum PgobjectToStringListConverter implements Converter<PGobject, List<String>> {

			INSTANCE;

			@Override
			public List<String> convert(PGobject pGobject) {
				Type type = new TypeToken<List<String>>(){}.getType();
				return new Gson().fromJson(pGobject.getValue(), type);
			}
		}
	}
}
