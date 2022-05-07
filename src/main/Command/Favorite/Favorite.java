package main.Command.Favorite;

import fileio.Input;

/**
 * this class adds a video to the user's favorites list
 */
public class Favorite {
    private final Input input;
    private final String nameUser;
    private final String movieToAdd;
    private final int actionId;
    /**
     *
     * @param input from the file
     * @param nameUser user name
     * @param movieToAdd movie to add
     * @param actionId action id
     */
    public Favorite(final Input input, final String nameUser, final String movieToAdd,
                    final int actionId) {
        this.input = input;
        this.nameUser = nameUser;
        this.movieToAdd = movieToAdd;
        this.actionId = actionId;
    }
    /**
     *
     * @return return input from file
     */
    public Input getInput() {
        return input;
    }
    /**
     *
     * @return return current action id
     */
    public int getActionId() {
        return actionId;
    }
    /**
     * message to displat in JSON array
     * @return
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"success -> " + movieToAdd
                + " was added as favourite" + "\"" + "}";
    }
}
