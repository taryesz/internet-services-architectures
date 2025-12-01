package aui.laboratorium_5.REST.Responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MovieResponse {

    private UUID id;
    private String title;
    private int rating;
    private boolean pegi;

    // Prywatny konstruktor z racji jego uzytku przez buildera
    private MovieResponse(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.rating = builder.rating;
        this.pegi = builder.pegi;
    }

    public static class Builder {

        private UUID id;
        private String title;
        private int rating;
        private boolean pegi;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder pegi(boolean pegi) {
            this.pegi = pegi;
            return this;
        }

        public MovieResponse build() {
            return new MovieResponse(this);
        }

    }

}
