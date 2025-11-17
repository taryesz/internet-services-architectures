package aui.laboratorium_4.REST.Requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class MovieRequest {

    private String title;
    private Integer rating;
    private Boolean pegi;
    private UUID genreId;

    // Prywatny konstruktor z racji jego uzytku przez Builder
    private MovieRequest(Builder builder) {
        this.title = builder.title;
        this.rating = builder.rating;
        this.pegi = builder.pegi;
        this.genreId = builder.genreId;     // ID gatunku nie moze byc teraz losowy, tylko wstrzykniety
    }

    public static class Builder {
        private String title;
        private int rating;
        private boolean pegi;
        private UUID genreId;

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

        public Builder genreId(UUID genreId) {
            this.genreId = genreId;
            return this;
        }

        public MovieRequest build() {
            return new MovieRequest(this);
        }

    }

}
