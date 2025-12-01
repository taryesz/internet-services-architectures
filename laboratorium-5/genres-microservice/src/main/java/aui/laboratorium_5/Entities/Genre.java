package aui.laboratorium_5.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "genres")
@Getter
@Setter
@NoArgsConstructor
public class Genre implements Serializable {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "action_index", nullable = false)
    private int actionIndex;

    // Prywatny konstruktor z racji jego uzytku przez buildera
    private Genre(Builder builder) {
        this.id = UUID.randomUUID();
        this.name = builder.name;
        this.actionIndex = builder.actionIndex;
    }

    public static class Builder {
        private String name;
        private int actionIndex;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder actionIndex(int actionIndex) {
            this.actionIndex = actionIndex;
            return this;
        }

        public Genre build() {
            return new Genre(this);
        }

    }

}