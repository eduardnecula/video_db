package main.Recommandation;

import fileio.Input;

/**
 * Clasa este folosita pentru a intoarce un obiect ce va fi folosit in
 * comanda standard recommendation best unseen. Cu alte cuvinte intoarce cea
 * mai buna recomandare de film pe care un user nu l-a vazut.
 */
public class RecBestUnseen {
    private Input input;
    private String movie;
    private int actionId;
    private String user;

    /**
     *
     * @param input primit din fisier
     * @param movie filmul
     * @param indexAction id-ul actiunii
     * @param user numele utilizatorului
     */
    RecBestUnseen(final Input input, final String movie, final int indexAction,
                  final String user) {
        this.input = input;
        this.movie = movie;
        this.actionId = input.getCommands().get(indexAction).getActionId();
        this.user = user;
    }

    /**
     *
     * @return returneaza input-ul
     */
    public Input getInput() {
        return input;
    }
    /**
     *
     * @param input seteaza input
     */
    public void setInput(final Input input) {
        this.input = input;
    }
    /**
     *
     * @return intoarce film
     */
    public String getMovie() {
        return movie;
    }
    /**
     *
     * @param movie seteaza film
     */
    public void setMovie(final String movie) {
        this.movie = movie;
    }
    /**
     *
     * @return intoarce id actiune
     */
    public int getActionId() {
        return actionId;
    }
    /**
     *
     * @param actionId seteaza id actiune
     */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }
    /**
     *
     * @return intoarce user
     */
    public String getUser() {
        return user;
    }
    /**
     *
     * @param user seteaza user
     */
    public void setUser(final String user) {
        this.user = user;
    }

    /**
     *
     * @return verifica daca este valid obiectul
     */
    public boolean checkValid() {
        if (movie == null || user == null) {
            return false;
        }
        return true;
    }
    /**
     *
     * @return pentru a fi folost in JSON array, la afisares
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"BestRatedUnseenRecommendation result: "
                + movie + "\"" + "}";
    }
}
