package main.Recommandation;

import fileio.Input;

/**
 * for when the search recommendation cannot be applied from various
 * reasons
 */
public class RecSearchError {
    private int actionId;

    /**
     *
     * @param input de la fisier
     * @param indexAction id actiune
     */
    RecSearchError(final Input input, final int indexAction) {
        this.actionId = input.getCommands().get(indexAction).getActionId();
    }
    /**
     *
     * @return intoarce id actiune
     */
    public int getActionId() {
        return actionId;
    }
    /**
     *
     * @param actionId seteaza id actiune
     */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }

    /**
     *
     * @return format JSON
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":"
                + "\"SearchRecommendation cannot be applied!" + "\"" + "}";

    }
}
