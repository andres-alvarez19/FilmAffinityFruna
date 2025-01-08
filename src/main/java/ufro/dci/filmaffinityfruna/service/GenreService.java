package ufro.dci.filmaffinityfruna.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ufro.dci.filmaffinityfruna.model.entity.GenreEntity;
import ufro.dci.filmaffinityfruna.repository.GenreRepository;
import ufro.dci.filmaffinityfruna.utils.MessageConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public void register(GenreEntity genreEntity) {
        if (genreRepository.existsByName(genreEntity.getName())) {
            throw new IllegalArgumentException("El género ya está registrado");
        } else {
            genreRepository.save(genreEntity);
        }
    }

  public void update(String name, GenreEntity updatedGenre) {
        if (genreRepository.existsByName(name)) {
            Optional<ufro.dci.filmaffinityfruna.model.dto.GenreDTO> genreOptional = genreRepository.findByName(name);
            if (genreOptional.isPresent()) {
                GenreEntity genre = genreOptional.get().toEntity();
                genre.setName(updatedGenre.getName());
                genreRepository.save(genre);
            } else {
                throw new IllegalArgumentException(MessageConstant.GENRE_NOT_FOUND);
            }
        } else {
            throw new IllegalArgumentException(MessageConstant.GENRE_NOT_FOUND);
        }
    }

    public void deleteGenreByName(String name) {
        if (!genreRepository.existsByName(name)) {
            throw new IllegalArgumentException(MessageConstant.GENRE_NOT_FOUND);
        } else {
            genreRepository.deleteByName(name);
        }
    }

    public ufro.dci.filmaffinityfruna.model.dto.GenreDTO searchByName(String name) {
        Optional<ufro.dci.filmaffinityfruna.model.dto.GenreDTO> genreOptional = genreRepository.findByName(name);
        if (genreOptional.isPresent()) {
            return genreOptional.get();
        } else {
            throw new IllegalArgumentException(MessageConstant.GENRE_NOT_FOUND);
        }
    }

        public List<ufro.dci.filmaffinityfruna.model.dto.GenreDTO> getAllGenres() {
            List<GenreEntity> genres = new ArrayList<>();
            genreRepository.findAll().forEach(genres::add);
            return genres.stream().map(ufro.dci.filmaffinityfruna.model.dto.GenreDTO::fromEntity).toList();
        }
    }