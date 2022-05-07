package main.Recommandation;

import fileio.Input;

/**
 * The class used for an error message when a recommendation
 * popular cannot be applied for various reasons
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
