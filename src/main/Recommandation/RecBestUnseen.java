package main.Recommandation;

import fileio.Input;

/**
 * The class is used to return an object that will be used in
 * command standard recommendation best unseen. In other words, return that
 * best movie recommendation that a user has not seen.
 */
public class RecBestUnseen {
    private Input input;
    private String movie;
    private int actionId;
    private String user;

    /**
     *
     * @param input from file
     * @param movie the movie
     * @param indexAction action id
     * @param user user name
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
     * @return input
     */
    public Input getInput() {
        return input;
    }
    /**
     *
     * @param input set input
     */
    public void setInput(final Input input) {
        this.input = input;
    }
    /**
     *
     * @return movie
     */
    public String getMovie() {
        return movie;
    }
    /**
     *
     * @param movie set movie
     */
    public void setMovie(final String movie) {
        this.movie = movie;
    }
    /**
     *
     * @return action id
     */
    public int getActionId() {
        return actionId;
    }
    /**
     *
     * @param actionId set action id
     */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }
    /**
     *
     * @return return user
     */
    public String getUser() {
        return user;
    }
    /**
     *
     * @param user set user
     */
    public void setUser(final String user) {
        this.user = user;
    }

    /**
     *
     * @return check if the object is valid
     */
    public boolean checkValid() {
        if (movie == null || user == null) {
            return false;
        }
        return true;
    }
    /**
     *
     * @return for JSON ARRAY
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"BestRatedUnseenRecommendation result: "
                + movie + "\"" + "}";
    }
}
