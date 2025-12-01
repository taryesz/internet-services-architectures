package aui.laboratorium_5.REST.Responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GenreResponse {

    private UUID id;
    private String name;
    private int actionIndex;

    // Prywatny konstruktor z racji jego uzytku przez buildera
    private GenreResponse(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.actionIndex = builder.actionIndex;
    }

    public static class Builder {

        private UUID id;
        private String name;
        private int actionIndex;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder actionIndex(int actionIndex) {
            this.actionIndex = actionIndex;
            return this;
        }

        public GenreResponse build() {
            return new GenreResponse(this);
        }

    }

}
