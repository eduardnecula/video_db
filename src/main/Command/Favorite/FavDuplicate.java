package main.Command.Favorite;

/**
 * When I receive my favorite order, but I've already seen the movie
 * I will display an appropriate error message
 */
public class FavDuplicate {

    private final String movieToAdd;
    private final int actionId;

    /**
     * favorites receive input, the title to add
     * and the movie to add to the movie list
     * @param movieToAdd movie to add
     * @param actionId action id
     */
    public FavDuplicate(final String movieToAdd, final int actionId) {
        this.movieToAdd = movieToAdd;
        this.actionId = actionId;
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
     * @return message to displat in JSON array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"error -> " + movieToAdd
                + " is already in favourite list" + "\"" + "}";
    }
}
