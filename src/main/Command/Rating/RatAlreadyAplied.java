package main.Command.Rating;

import fileio.Input;

/**
 * Clas este utila atunci cand incerc sa aplic rating pentru un film la care
 * am dat deja acest lucru. Se va afisa un mesaj de eroare corespunzator
 */
public class RatAlreadyAplied {
    private final String movieToAdd;
    private int actionId;

    /**
     *
     * @param input de la fisier
     * @param indexAction id-ul actiunii
     * @param movieToAdd filmul de adaugat
     */
    public RatAlreadyAplied(final Input input, final int indexAction,
                            final String movieToAdd) {
        this.actionId = input.getCommands().get(indexAction).getActionId();
        this.movieToAdd = movieToAdd;
    }

    /**
     *
     * @return id-ul actiunii curente
     */
    public int getActionId() {
        return actionId;
    }

    /**
     *
     * @param actionId setez id-ul actiunii curente
     */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }

    /**
     *
     * @return pentru JSON arrray
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"error -> " + movieToAdd
                + " has been already rated" +  "\"" + "}";
    }
}
