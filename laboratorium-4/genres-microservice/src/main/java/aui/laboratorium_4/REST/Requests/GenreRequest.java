package aui.laboratorium_4.REST.Requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GenreRequest {

    private String name;

    private Integer actionIndex;

    // Prywatny konstruktor z racji jego uzytku przez buildera
    private GenreRequest(Builder builder) {
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

        public GenreRequest build() {
            return new GenreRequest(this);
        }

    }

}
