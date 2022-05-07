package main.Command.Rating;

import fileio.Input;
import main.Actions;

import java.util.ArrayList;

/**
 *The class is useful for displaying a successful message when giving
 * rating of a movie I saw, and it's a unique order,
 * I mean I didn't order the same movie
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
     * @param input from file
     * @param nameUser user name
     * @param movieToAdd movie to add
     * @param actionId action id
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
     * @return verify that the rating is null
     */
    public boolean chechRating() {
        if (input == null || nameUser == null || movieToAdd == null) {
            corect = false;
        }
        return corect;
    }
    /**
     *
     * @return verify if I have seen the movie
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
     *  return grade
     */
    public void applyRating() {
        this.grade = input.getCommands().get(actionId).getGrade();
        this.actionId = input.getCommands().get(actionId).getActionId();
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
     * @return input
     */
    public Input getInput() {
        return input;
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
     * @return the grade received
     */
    public double getGrade() {
        return grade;
    }

    /**
     *
     * @param grade set a value
     */
    public void setGrade(final double grade) {
        this.grade = grade;
    }

    /**
     *
     * @return for Json array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"success -> " + movieToAdd
                + " was rated with " + grade + " by " + nameUser + "\"" + "}";

    }
}
