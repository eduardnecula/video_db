package main.Command.Favorite;

/**
 *  the class deals with history search
 * if I can't find the movie in history I can't add it to my favorites
 * and display a corresponding message in JSON format
 */
public class FavNo {

    private final String movieToAdd;
    private final int actionId;

    /**
     *
     * @param movieToAdd movie to add
     * @param actionId current action id
     */
    public FavNo(final String movieToAdd, final int actionId) {
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
                + " is not seen" + "\"" + "}";
    }
}
