package aui.laboratorium_3.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "movies")
@Getter
@Setter
@NoArgsConstructor
public class Movie implements Comparable<Movie>, Serializable {

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
    private Genre genre;

    // prywatny konstruktor z racji jego uzytku przez Builder
    private Movie(Builder builder) {
        this.id = UUID.randomUUID();        // generowany automatycznie przez klienta
        this.title = builder.title;
        this.rating = builder.rating;
        this.pegi = builder.pegi;
        this.genre = builder.genre;
    }

    public static class Builder {
        private String title;
        private int rating;
        private boolean pegi;
        private Genre genre;

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

        public Builder genre(Genre genre) {
            this.genre = genre;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }

    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Movie movie = (Movie) other;

        if (rating != movie.rating) return false;
        if (pegi != movie.pegi) return false;
        return title != null ? title.equals(movie.title) : movie.title == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        int pegiNum = pegi ? 1 : 0;
        result = 31 * result + rating + pegiNum;
        return result;
    }

    // "Comparison mechanism" - porządek naturalny wg rating
    @Override
    public int compareTo(Movie other) {
        return Integer.compare(this.rating, other.rating);
    }

    @Override
    public String toString() {
        return this.title + " " + this.rating + " " + this.pegi;
    }

}
