package compose.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Genre implements Comparable<Genre>, Serializable {
    private final String name;
    private final int actionIndex;
    private final List<Movie> movies;

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Genre movie = (Genre) other;

        if (actionIndex != movie.actionIndex) return false;
        return name != null ? name.equals(movie.name) : movie.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + actionIndex;
        return result;
    }

    // "Comparison mechanism" - porządek naturalny wg actionIndex
    @Override
    public int compareTo(Genre other) {
        return Integer.compare(this.actionIndex, other.actionIndex);
    }

    @Override
    public String toString() {
        return this.name + " " + this.actionIndex;
    }

    // prywatny konstruktor z racji jego uzytku przez buildera
    private Genre(Genre.Builder builder) {
        this.name = builder.name;
        this.actionIndex = builder.actionIndex;
        this.movies = new ArrayList<>();    // nie zastepuje ta zmienna przez zmienna w builderze poniewaz nie moge
                                            // stworzyc zawody bez osob i osoby bez zawodow - robie dlatego pusta
                                            // liste
    }

    public static class Builder {
        private String name;
        private int actionIndex;

        public Genre.Builder name(String name) {
            this.name = name;
            return this;
        }

        public Genre.Builder actionIndex(int actionIndex) {
            this.actionIndex = actionIndex;
            return this;
        }

        public Genre build() {
            return new Genre(this);
        }

    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public String getName() {
        return name;
    }

    public List<Movie> getMovies() {
        return movies;
    }

}

