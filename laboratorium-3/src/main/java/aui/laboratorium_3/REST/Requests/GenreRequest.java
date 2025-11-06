package aui.laboratorium_3.REST.Requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class GenreRequest {

    private String name;

    private Integer actionIndex;

    // prywatny konstruktor z racji jego uzytku przez buildera
    private GenreRequest(GenreRequest.Builder builder) {
        this.name = builder.name;
        this.actionIndex = builder.actionIndex;
    }

    public static class Builder {
        private String name;
        private int actionIndex;

        public GenreRequest.Builder name(String name) {
            this.name = name;
            return this;
        }

        public GenreRequest.Builder actionIndex(int actionIndex) {
            this.actionIndex = actionIndex;
            return this;
        }

        public GenreRequest build() {
            return new GenreRequest(this);
        }

    }

}
