package ufro.dci.filmaffinityfruna.service;

import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.repository.ActorRepository;
import ufro.dci.filmaffinityfruna.repository.DirectorRepository;
import ufro.dci.filmaffinityfruna.repository.GenreRepository;
import ufro.dci.filmaffinityfruna.repository.MovieRepository;
import ufro.dci.filmaffinityfruna.repository.UserRepository;

@Service
public class ValidationService {

    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public ValidationService(ActorRepository actorRepository, DirectorRepository directorRepository, GenreRepository genreRepository, MovieRepository movieRepository, UserRepository userRepository) {
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public boolean existsActor(String name, String dateOfBirth) {
        return actorRepository.existsByNameAndDateOfBirth(name, dateOfBirth);
    }

    public boolean existsDirector(String name) {
        return directorRepository.existsByName(name);
    }

    public boolean existsGenre(String name) {
        return genreRepository.existsByName(name);
    }

    public boolean existsMovie(String name) {
        return movieRepository.existsByName(name);
    }
    public boolean existsUserByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    public boolean existsUserByUsername(String username){
        return userRepository.existsByUsername(username);
    }
}
