package eu.arima.filmsservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/film")
@RestController
public class FilmController {

    private final FilmRepository filmRepository;

    public FilmController(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @GetMapping
    public Iterable<Film> findAll() {
        Iterable<Film> list = this.filmRepository.findAll();
        return list;
    }

    @GetMapping("/{id}")
    public Film findId(@PathVariable long id) {
        return this.filmRepository.findById(id).get();
    }

}
