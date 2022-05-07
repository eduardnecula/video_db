package main.Querries;

import fileio.Input;
import java.util.Map;

/**
 * The class is used to give an error message when
 * I can't return a querry for an actor
 */
public class QueActors {
    private final Input input;
    private final int index;
    private final Map<String, Double> list;

    /**
     * Constructor
     * @param input from the file
     * @param index action id
     * @param list list of actors
     */
    public QueActors(final Input input, final int index, final Map<String,
            Double> list) {
        this.input = input;
        this.index = input.getCommands().get(index).getActionId();
        this.list = list;
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
    public int getIndex() {
        return index;
    }

    /**
     *
     * @return list actors
     */
    public Map<String, Double> getList() {
        return list;
    }

    /**
     *
     * @return for JSON array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + index + ","
                + "\"message\"" + ":"
                + "\"error -> " + list
                + "\"" + "}";
    }
}
