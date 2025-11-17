package aui.laboratorium_4.Entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
public class Movie implements Serializable {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "rating")
    private int rating;

    @Column(name = "pegi", nullable = false)
    private boolean pegi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private SimplifiedGenre genre;

    // Prywatny konstruktor z racji jego uzytku przez Builder
    private Movie(Builder builder) {
        this.id = UUID.randomUUID();
        this.title = builder.title;
        this.rating = builder.rating;
        this.pegi = builder.pegi;
        this.genre = builder.genre;
    }

    public static class Builder {
        private String title;
        private int rating;
        private boolean pegi;
        private SimplifiedGenre genre;

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

        public Builder genre(SimplifiedGenre genre) {
            this.genre = genre;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }

    }

}
