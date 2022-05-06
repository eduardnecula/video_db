package main.Command.Rating;

import fileio.Input;
import main.Actions;

import java.util.ArrayList;

/**
 * Clasa este utila pentru afisarea unui mesaj de succes atunci cand dau
 * rating unui film pe care l-am vazut, si este o comanda unica,
 * adica nu am mai dat comanda rating pe acelasi film
 */
public class Rating {
    private final Input input;
    private final String nameUser;
    private final String movieToAdd;
    private int actionId;
    private double grade;
    private boolean corect = true;

    /**
     *
     * @param input de la fisier
     * @param nameUser numele utilizatorului
     * @param movieToAdd filmul de adaugat
     * @param actionId id-ul actiunii
     */
    public Rating(final Input input, final String nameUser, final String movieToAdd,
                  final int actionId) {
        this.input = input;
        this.nameUser = nameUser;
        this.movieToAdd = movieToAdd;
        this.actionId = actionId;
    }
    /**
     *
     * @return verifica daca rating-ul este null
     */
    public boolean chechRating() {
        if (input == null || nameUser == null || movieToAdd == null) {
            corect = false;
        }
        return corect;
    }
    /**
     *
     * @return verifica daca am mai vazut filmul
     */
    public boolean checkSeen() {
        Actions actions = new Actions(input);
        if (nameUser != null) {
            ArrayList<String> list = actions.getHistoryList(nameUser);
            if (movieToAdd != null && list != null) {
                for (String s : list) {
                    if (movieToAdd.compareTo(s) == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * functia intoarce grade
     */
    public void applyRating() {
        this.grade = input.getCommands().get(actionId).getGrade();
        this.actionId = input.getCommands().get(actionId).getActionId();
     }

    /**
     *
     * @param actionId setez id-ul actiunii curente
     */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }

    /**
     *
     * @return input-ul
     */
    public Input getInput() {
        return input;
    }

    /**
     *
     * @return id-ul actiunii
     */
    public int getActionId() {
        return actionId;
    }

    /**
     *
     * @return intorc nota primita
     */
    public double getGrade() {
        return grade;
    }

    /**
     *
     * @param grade setez o valoare
     */
    public void setGrade(final double grade) {
        this.grade = grade;
    }

    /**
     *
     * @return pentru Json array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"success -> " + movieToAdd
                + " was rated with " + grade + " by " + nameUser + "\"" + "}";

    }
}
