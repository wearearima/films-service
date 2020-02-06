package eu.arima.filmsservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers(disabledWithoutDocker = true)
@ContextConfiguration(initializers = FilmIntegrationTests.Initializer.class)
@AutoConfigureMockMvc
@SpringBootTest
public class FilmIntegrationTests {

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres:11.6").withExposedPorts(5454);

    @Autowired
    public MockMvc mockMvc;

    @Test
    public void shouldReturnAllFilms() throws Exception {
        this.mockMvc.perform(get("/film")).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnFilmById() throws Exception {
        this.mockMvc.perform(get("/film/432")).andDo(print())
                .andExpect(status().isOk());
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }

    }

}
