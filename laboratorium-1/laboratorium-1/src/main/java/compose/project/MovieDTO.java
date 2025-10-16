package compose.project;

public class MovieDTO implements Comparable<MovieDTO> {
    private final String name;
    private final int rating;
    private final String genre;

    public MovieDTO(String name, int level, String genre) {
        this.name = name;
        this.rating = level;
        this.genre = genre;
    }

    @Override
    public int compareTo(MovieDTO other) {
        return Integer.compare(this.rating, other.rating);
    }

    @Override
    public String toString() {
        return "I'm a DTO object: " + name + " " + genre + " " + rating;
    }
}
