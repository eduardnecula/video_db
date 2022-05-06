package main.Recommandation;

import fileio.Input;

/**
 * Clasa este folosita pentru cand comanda favorite recommendation nu poate
 * fi aplicata
 */
public class RecError {
    private int actionId;
    /**
     *
     * @param input de la fisier
     * @param indexAction id actiune
     */
    public RecError(final Input input, final int indexAction) {
        this.actionId = input.getCommands().get(indexAction).getActionId();
    }

    /**
     *
     * @return id actiune
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
     * @return pentru JSON array la afisare
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":"
                + "\"FavoriteRecommendation cannot be applied!" + "\"" + "}";
    }
}
