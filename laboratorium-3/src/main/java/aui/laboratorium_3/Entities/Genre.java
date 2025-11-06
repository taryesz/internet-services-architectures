package aui.laboratorium_3.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genres")
@Getter
@Setter
@NoArgsConstructor
public class Genre implements Comparable<Genre>, Serializable {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "action_index", nullable = false)
    private int actionIndex;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Movie> movies = new ArrayList<>();

    // prywatny konstruktor z racji jego uzytku przez buildera
    private Genre(Genre.Builder builder) {
        this.id = UUID.randomUUID();
        this.name = builder.name;
        this.actionIndex = builder.actionIndex;
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

}