package main.Recommandation;

import fileio.Input;

/**
 * Clasa folosita pentru un mesaj de eroare atunci cand o recomandare
 * populara nu poate sa fie aplicata din diverse motive
 */
public class RecStandardInvalid {
    private final int index;

    /**
     *
     * @param input de la fisier
     * @param index id actiune
     */
    public RecStandardInvalid(final Input input, final int index) {
        this.index = input.getCommands().get(index).getActionId();
    }

    /**
     *
     * @return format json
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + index + ","
                + "\"message\"" + ":"
                + "\"PopularRecommendation cannot be applied!"
                +  "\"" + "}";
    }
}
