package main.Command.Favorite;

import fileio.Input;

/**
 * aceasta clasa adauga in lista de favorite a user-ului un video
 */
public class Favorite {
    private final Input input;
    private final String nameUser;
    private final String movieToAdd;
    private final int actionId;
    /**
     *
     * @param input de la fisier
     * @param nameUser numele utilizatorului
     * @param movieToAdd filmul de adaugat
     * @param actionId id-ul actiunii
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
     * @return intorc input-ul din fiser
     */
    public Input getInput() {
        return input;
    }
    /**
     *
     * @return intorc id-ul actiunii curente
     */
    public int getActionId() {
        return actionId;
    }
    /**
     * Mesaj de afisat pentru Json Array
     * @return
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"success -> " + movieToAdd
                + " was added as favourite" + "\"" + "}";
    }
}
