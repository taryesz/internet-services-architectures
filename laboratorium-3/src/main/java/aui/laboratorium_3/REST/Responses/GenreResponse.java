package aui.laboratorium_3.REST.Responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/*
Implementation of DTO classes for creating/updating and >>>> READING <<<< all entity classes.

Additionally, separate DTO classes for >>>> READING <<<< collections of entity classes.

DTO classes for creating/updating need to contain only those fields which can be set.
For example: primary keys and categories are set by sending request to specified
resource and are not present in the request body.

>>>> DTO classes for reading collections should not contain all original fields but only
identifiers and some user-friendly name or description. <<<<

(3 points)
*/

@Getter
@Setter
@NoArgsConstructor
public class GenreResponse {

    private UUID id;
    private String name;

    // prywatny konstruktor z racji jego uzytku przez buildera
    private GenreResponse(GenreResponse.Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
    }

    public static class Builder {

        private UUID id;
        private String name;

        public GenreResponse.Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public GenreResponse.Builder name(String name) {
            this.name = name;
            return this;
        }

        public GenreResponse build() {
            return new GenreResponse(this);
        }

    }

}
