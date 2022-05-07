package main.Recommandation;

import fileio.Input;

/**
 * The class is used for when the favorite recommendation command cannot
 * be applied
 */
public class RecError {
    private int actionId;
    /**
     *
     * @param input from file
     * @param indexAction action id
     */
    public RecError(final Input input, final int indexAction) {
        this.actionId = input.getCommands().get(indexAction).getActionId();
    }

    /**
     *
     * @return action id
     */
    public int getActionId() {
        return actionId;
    }
    /**
     *
     * @param actionId set action id
     */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }

    /**
     *
     * @return for JSON array to display
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":"
                + "\"FavoriteRecommendation cannot be applied!" + "\"" + "}";
    }
}
