package aui.laboratorium_3.REST.Requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/*
Implementation of DTO classes for >>>> CREATING/UPDATING <<<< and reading all entity classes.

Additionally, separate DTO classes for reading collections of entity classes.

>>>> DTO classes for creating/updating need to contain only those fields which can be set.
For example: primary keys and categories are set by sending request to specified
resource and are not present in the request body. <<<<

DTO classes for reading collections should not contain all original fields but only
identifiers and some user-friendly name or description.

(3 points)
*/

@Getter
@Setter
@NoArgsConstructor
public class MovieRequest {

    private String title;
    private Integer rating;
    private Boolean pegi;

    // prywatny konstruktor z racji jego uzytku przez Builder
    private MovieRequest(MovieRequest.Builder builder) {
        this.title = builder.title;
        this.rating = builder.rating;
        this.pegi = builder.pegi;
    }

    public static class Builder {
        private String title;
        private int rating;
        private boolean pegi;

        public MovieRequest.Builder title(String title) {
            this.title = title;
            return this;
        }

        public MovieRequest.Builder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public MovieRequest.Builder pegi(boolean pegi) {
            this.pegi = pegi;
            return this;
        }

        public MovieRequest build() {
            return new MovieRequest(this);
        }

    }


}
