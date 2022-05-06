package main;

import common.Constants;
import fileio.Input;
import fileio.MovieInputData;
import main.Command.CommandMain;
import main.Querries.QueryMain;
import main.Recommandation.RecommendationMain;
import org.json.simple.JSONArray;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Aceasta este clasa principala ce va apela restul de clase.
 * In functie de tipul comenzi, se va crea un obiect pentru fiecare actiune
 * Iar apoi fiecare obiect va crea alte obiecte in functie de tipul comenzii
 */
public class Actions {
    private Input input;

    /**
     * Aici incepe tot algoritmu
     * Se decide ce fel de actiune se va executa
     * @param arrayResult pentru a pune in el obiecte pt afisare
     */
    public void runActions(final JSONArray arrayResult) {
        for (int i = 0; i < getNrActions(); i++) {
            String actionType = getActionType(i);
            if (actionType == null) {
                return;
            }
            switch (actionType) {
                case Constants.COMMAND:
                    CommandMain cm = new CommandMain(input);
                    cm.command(i, arrayResult);
                    break;
                case Constants.QUERY:
                    QueryMain queryMain = new QueryMain(input);
                    queryMain.query(i, arrayResult);
                    break;
                case Constants.RECOMMENDATION:
                    RecommendationMain rm = new RecommendationMain(input);
                    rm.recommendation(i, arrayResult);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     *
     * @param input de la fisier
     */
    public Actions(final Input input) {
        this.input = input;
    }

    /**
     *
     * @return intoarce input
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
     * @return returneaza nr de actiuni
     */
    public int getNrActions() {
        return input.getCommands().size();
    }

    /**
     *
     * @return returneaza nr de utilizatori
     */
    public int getNrUsers() {
        return  input.getUsers().size();
    }

    /**
     * Intoarce nr de filme pe care un user le are in istoric
     * @param name nume utilizator
     * @return nr de filme
     */
    public int getNrHist(final String name) {
        int nrHis = 0;
        // cauta prin useri
        for (int i = 0; i < getNrUsers(); i++) {
            try {
                String findUser = input.getUsers().get(i).getUsername();
                if (name.compareTo(findUser) == 0) {
                    Map<String, Integer> history
                            = input.getUsers().get(i).getHistory();
                    for (String ignored : history.keySet()) {
                        nrHis++;
                    }
                }
            } catch (NullPointerException ignored) { }
        }
        return nrHis;
    }

    /**
     * functia intoarce istoricul listei
     * @param name nume utilizator
     * @return intoarce lista de istoric a unui utilizator
     */
    public ArrayList<String> getHistoryList(final String name) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < getNrUsers(); i++) {
            try {
                String searchUser = input.getUsers().get(i).getUsername();
                if (name.compareTo(searchUser) == 0) {
                    Map<String, Integer> history
                            = input.getUsers().get(i).getHistory();
                    list.addAll(history.keySet());
                }
            } catch (NullPointerException ignored) { }
        }
        return list;
    }

    /**
     *
     * @return nr de filme
     */
    public int getNrMovies() {
        int nr = 0;
        List<MovieInputData> a = input.getMovies();
        for (MovieInputData ignored : a) {
            nr++;
        }
        return  nr;
    }

    /**
     * @param index id actiune
     * @return tipul actiunii
     */
    public String getActionType(final int index) {
        //  ex: command, query
        return input.getCommands().get(index).getActionType();
    }
}
