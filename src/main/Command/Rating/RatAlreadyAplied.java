package main.Command.Rating;

import fileio.Input;

/**
 * The class is useful when trying to rate a movie
 * I have already given this. An appropriate error message will be displayed
 */
public class RatAlreadyAplied {
    private final String movieToAdd;
    private int actionId;

    /**
     *
     * @param input from file
     * @param indexAction action id
     * @param movieToAdd movie to add
     */
    public RatAlreadyAplied(final Input input, final int indexAction,
                            final String movieToAdd) {
        this.actionId = input.getCommands().get(indexAction).getActionId();
        this.movieToAdd = movieToAdd;
    }

    /**
     *
     * @return current action id
     */
    public int getActionId() {
        return actionId;
    }

    /**
     *
     * @param actionId set current action id
     */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }

    /**
     *
     * @return for JSON arrray
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"error -> " + movieToAdd
                + " has been already rated" +  "\"" + "}";
    }
}
