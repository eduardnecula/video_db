package main.Command.Favorite;

/**
 *  clasa se ocupa cu cautarea prin history
 *  daca nu gasesc filmul in history nu pot sa il adaug la favorite
 *  si afisez un mesaj corespunzator in format JSON
 */
public class FavNo {

    private final String movieToAdd;
    private final int actionId;

    /**
     *
     * @param movieToAdd film de adaugat
     * @param actionId id-ul actiunii curente
     */
    public FavNo(final String movieToAdd, final int actionId) {
        this.movieToAdd = movieToAdd;
        this.actionId = actionId;
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
     * @return mesaj de afisat pentru JSON array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"error -> " + movieToAdd
                + " is not seen" + "\"" + "}";
    }
}
