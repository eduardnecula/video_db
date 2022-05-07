package main.Recommandation;

import fileio.Input;

/**
 * I will post a movie recommendation with this class
 */
public class RecFavorite {
    private final Input input;
    private final int index;
    private final String username;
    private final String recommandation;
    /**
     *
     * @param input de la fisier
     * @param index id actiune
     * @param username nume user
     * @param recommandation nume film de recomandat
     */
    public RecFavorite(final Input input, final int index,
                       final String username,
                       final String recommandation) {
        this.input = input;
        this.recommandation = recommandation;
        this.index = input.getCommands().get(index).getActionId();
        this.username = username;
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
     * @return id actiune
     */
    public int getIndex() {
        return index;
    }
    /**
     *
     * @return nume user
     */
    public String getUsername() {
        return username;
    }
    /**
     *
     * @return nume film
     */
    public String getRecommandation() {
        return recommandation;
    }
    /**
     *
     * @return pentru JSON ARRAY la afisare
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + index + ","
                + "\"message\"" + ":"
                + "\"FavoriteRecommendation result: " + recommandation
                + "\"" + "}";
    }
}
