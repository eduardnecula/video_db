package main.Command.View;

import fileio.Input;
import java.util.Map;

/**
 * This class is for when I receive the view command for a movie.
 * I will display a corresponding success message.
 */
public class View {
    private final String nameUser;
    private final String movieToAdd;
    private int actionId;
    private int noViews = 1;
    private boolean corect = true;
    private Input input;
    private Map<String, Integer> history;
    /**
     *
     * @param input from file
     * @param nameUser user name
     * @param movieToAdd movie to add
     * @param actionId action id
     */
    public View(final Input input, final String nameUser, final String movieToAdd,
                final int actionId) {
        this.nameUser = nameUser;
        this.movieToAdd = movieToAdd;
        this.actionId = actionId;
        this.input = input;

    }

    /**
     * I set the number of views a user has for a particular movie
     */
    public void setNrView() {
        // I have to search for the movie in the user list
        int nrusers = input.getUsers().size();
        for (int i = 0; i < nrusers; i++) {
            String name = input.getUsers().get(i).getUsername();
            if (name.compareTo(nameUser) == 0) {
                //  if I find the user, I search his list of histories
                history = input.getUsers().get(i).getHistory();
                if (history.containsKey(movieToAdd)) {
                    for (int j = 0; j < history.size(); j++) {
                        Integer nrViews = history.get(movieToAdd);
                        noViews = nrViews + 1;
                    }
                }
            }
        }
        history.put(movieToAdd, noViews);
    }

    /**
     *
     * @return I check to make sure that these values ​​are not null
     */
    public boolean checkView() {
        if (nameUser == null || movieToAdd == null) {
            corect = false;
        }
        return corect;
    }

    /**
     *
     * @param actionId action id
     */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }

    /**
     *
     * @return input received
     */
    public Input getInput() {
        return input;
    }

    /**
     *
     * @param input input from the file
     */
    public void setInput(final Input input) {
        this.input = input;
    }

    /**
     * set current action id
     */
    public void setActionId() {
        this.actionId = input.getCommands().get(actionId).getActionId();
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
     * @return history of a user
     */
    public Map<String, Integer> getHistory() {
        return history;
    }

    /**
     *
     * @param history set history user
     */
    public void setHistory(final Map<String, Integer> history) {
        this.history = history;
    }

    /**
     *
     * @return for JSON array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"success -> " + movieToAdd
                + " was viewed with total views of " + noViews + "\"" + "}";
    }
}
