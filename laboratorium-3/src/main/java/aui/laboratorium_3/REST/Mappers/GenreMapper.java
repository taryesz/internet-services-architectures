package aui.laboratorium_3.REST.Mappers;

import aui.laboratorium_3.Entities.Genre;
import aui.laboratorium_3.REST.Requests.GenreRequest;
import aui.laboratorium_3.REST.Responses.GenreResponse;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    // GenreDTO -> Genre
    public Genre toGenre(GenreRequest request) {
        return new Genre.Builder().name(request.getName()).actionIndex(request.getActionIndex()).build();
    }

    // Genre -> GenreDTO
    public GenreResponse toDTO(Genre genre) {
        return new GenreResponse.Builder().id(genre.getId()).name(genre.getName()).build();
    }

}