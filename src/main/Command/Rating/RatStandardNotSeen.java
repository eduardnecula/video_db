package main.Command.Rating;

import fileio.Input;

/**
 * Clasa este utila atunci cand primesc comanda de rating pentru un film
 * pe care nu l-am vazut, caz in care afisez un mesaj de eroare
 */
public class RatStandardNotSeen {
    private final Input input;
    private final int index;
    private final String movieToRecommend;

    /**
     *
     * @param input de la fisier
     * @param index index actiune
     * @param movie film de adaugat
     */
    public RatStandardNotSeen(final Input input, final int index,
                              final String movie) {
        this.input = input;
        this.index = input.getCommands().get(index).getActionId();
        this.movieToRecommend = movie;
    }

    /**
     *
     * @return input
     */
    public Input getInput() {
        return input;
    }

    /**
     *
     * @return intorc id actiune
     */
    public int getIndex() {
        return index;
    }

    /**
     *
     * @return se va afisa in JSON array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + index + ","
                + "\"message\"" + ":"
                + "\"error -> " + movieToRecommend
                + " is not seen" + "\"" + "}";
    }
}
