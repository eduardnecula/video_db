package main.Command.Favorite;

/**
 * Atunci cand primesc comanda favorite, dar am vazut deja filmul
 * voi afisa un mesaj de eroare corespunzator
 */
public class FavDuplicate {

    private final String movieToAdd;
    private final int actionId;

    /**
     * favorite primeste input, titlul pe care sa-l adauge
     * si filmul de adugat in lista de filme
     * @param movieToAdd filmul de adaugat
     * @param actionId id-ul actiunii
     */
    public FavDuplicate(final String movieToAdd, final int actionId) {
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
     * @return mesaj de afisat in JSON array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"error -> " + movieToAdd
                + " is already in favourite list" + "\"" + "}";
    }
}
