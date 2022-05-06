package main.Querries;

import fileio.Input;
import java.util.Map;

/**
 * Clasa este folosita pentru a da un mesaj de eroare atunci cand
 * nu pot sa intorc un querry pentru un actor
 */
public class QueActors {
    private final Input input;
    private final int index;
    private final Map<String, Double> list;

    /**
     * Constructor
     * @param input de la fisit
     * @param index id actiune
     * @param list lista cu actori
     */
    public QueActors(final Input input, final int index, final Map<String,
            Double> list) {
        this.input = input;
        this.index = input.getCommands().get(index).getActionId();
        this.list = list;
    }

    /**
     *
     * @return intoarce inputul pe care il am
     */
    public Input getInput() {
        return input;
    }

    /**
     *
     * @return intoarce id-ul actiunii curente
     */
    public int getIndex() {
        return index;
    }

    /**
     *
     * @return intoarce lista de actori
     */
    public Map<String, Double> getList() {
        return list;
    }

    /**
     *
     * @return pentru JSON array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + index + ","
                + "\"message\"" + ":"
                + "\"error -> " + list
                + "\"" + "}";
    }
}
