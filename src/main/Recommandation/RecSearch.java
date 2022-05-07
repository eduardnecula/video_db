package main.Recommandation;

import fileio.Input;
import java.util.ArrayList;

/**
 * this class returns a search recommendation, in JSON format
 */
public class RecSearch {
    private int actionId;
    private ArrayList<String> seriesList;

    /**
     * @param input de la fisier
     * @param actionId id actiune
     * @param seriesList lista de seriale
     */
    RecSearch(final Input input, final int actionId,
              final ArrayList<String> seriesList) {
        this.actionId = input.getCommands().get(actionId).getActionId();
        this.seriesList = seriesList;
    }

    /**
     *
     * @return id actiune
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
     * @return format json
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"SearchRecommendation result: "
                + seriesList + "\"" + "}";

    }
}
