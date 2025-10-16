package compose.project;

import java.io.Serializable;

public class Movie implements Comparable<Movie>, Serializable {
    private final String name;
    private final int rating;
    private final boolean pegi;
    private final Genre genre;

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Movie movie = (Movie) other;

        if (rating != movie.rating) return false;
        if (pegi != movie.pegi) return false;
        return name != null ? name.equals(movie.name) : movie.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
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
        return this.name + " " + this.rating + " " + this.pegi;
    }

    // prywatny konstruktor z racji jego uzytku przez buildera
    private Movie(Builder builder) {
        this.name = builder.name;
        this.rating = builder.rating;
        this.pegi = builder.pegi;
        this.genre = builder.genre;
    }

    public static class Builder {
        private String name;
        private int rating;
        private boolean pegi;
        private Genre genre;

        public Builder name(String name) {
            this.name = name;
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

    public int getRating() {
        return rating;
    }

    public String getName() {
        return name;
    }

    public Genre getGenre() {
        return genre;
    }

    public boolean isPegi() {
        return pegi;
    }

}
