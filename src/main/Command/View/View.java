package main.Command.View;

import fileio.Input;
import java.util.Map;

/**
 * Clasa aceasta este pentru cand primesc comanda view la un film.
 * Voi afisa un mesaj de succes corespunzator.
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
     * @param input de la fisier
     * @param nameUser nume utilizator
     * @param movieToAdd film de adaugat
     * @param actionId id actiune
     */
    public View(final Input input, final String nameUser, final String movieToAdd,
                final int actionId) {
        this.nameUser = nameUser;
        this.movieToAdd = movieToAdd;
        this.actionId = actionId;
        this.input = input;

    }

    /**
     * setez nr de view-uri pe care un user, le are la un anumit film
     */
    public void setNrView() {
        // trebuie sa caut filmul in lista user-ului
        int nrusers = input.getUsers().size();
        for (int i = 0; i < nrusers; i++) {
            String name = input.getUsers().get(i).getUsername();
            if (name.compareTo(nameUser) == 0) {
                //  daca gasesc user-ul, caut in lista lui de histori
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
     * @return verific sa am grija ca aceste valori sa nu fie null
     */
    public boolean checkView() {
        if (nameUser == null || movieToAdd == null) {
            corect = false;
        }
        return corect;
    }

    /**
     *
     * @param actionId id actiune
     */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }

    /**
     *
     * @return input primit
     */
    public Input getInput() {
        return input;
    }

    /**
     *
     * @param input input fisier
     */
    public void setInput(final Input input) {
        this.input = input;
    }

    /**
     * setare id actiune curenta
     */
    public void setActionId() {
        this.actionId = input.getCommands().get(actionId).getActionId();
    }

    /**
     *
     * @return id actiune curenta
     */
    public int getActionId() {
        return actionId;
    }

    /**
     *
     * @return istoricul unui utilizator
     */
    public Map<String, Integer> getHistory() {
        return history;
    }

    /**
     *
     * @param history setare istoric utilizator
     */
    public void setHistory(final Map<String, Integer> history) {
        this.history = history;
    }

    /**
     *
     * @return pentru JSON array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"success -> " + movieToAdd
                + " was viewed with total views of " + noViews + "\"" + "}";
    }
}
