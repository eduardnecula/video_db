package main.Recommandation;

import fileio.Input;

/**
 * This class is used to return a popular recommendation
 */
public class RecPopular {
    private final int index;

    /**
     *
     * @param input de la fisier
     * @param index id actiune
     */
    public RecPopular(final Input input, final int index) {
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
                + "\"PopularRecommendation result: YOU"
                + "\"" + "}";
    }
}
