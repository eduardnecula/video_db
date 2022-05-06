package main.Recommandation;

import fileio.Input;
import main.Actions;

import java.util.ArrayList;

/**
 * Clasa utila pentru o recomdare pentru un utilizator standard
 */
public class RecStandard {
    private final Input input;
    private final int index;
    private  String movieToRecommend = null;
    private final String userName;
    private boolean corect = true;
    /**
     *
     * @param input de la fisier
     * @param index id actiune
     * @param username nume utilizator
     */
    public RecStandard(final Input input, final int index,
                final String username) {
        this.input = input;
        this.index = index;
        this.userName = username;
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
     * @return verifica daca obiectul este ok sau nu
     */
    public boolean checkStandard() {
        if (userName == null) {
            corect = false;
        }
        return corect;
    }

    /**
     * functia returneaza primul film nevazut de username
     */
    public String findMovie() {
        Actions actions = new Actions(input);
        int aux = actions.getNrHist(userName);
        ArrayList<String> list = actions.getHistoryList(userName);
            for (int i = 0; i < actions.getNrMovies(); i++) {
                String movies = input.getMovies().get(i).getTitle();
                for (Object o : list) {
                    if (movies.compareTo(o.toString()) != 0) {
                        aux--;
                    }
                }
                //  daca am toate filme diferite
                if (aux == 0) {
                    return movies;
                } else {
                    aux = actions.getNrHist(userName);
                }
            }
        return null;
    }

    /**
     * seteaza film
     */
    public void setMovie() {
        this.movieToRecommend = findMovie();
    }

    /**
     *
     * @return format JSON
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + (index + 1) + ","
                + "\"message\"" + ":"
                + "\"StandardRecommendation result: " + movieToRecommend
                + "\"" + "}";
    }
}
