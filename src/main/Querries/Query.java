package main.Querries;

import fileio.Input;
import java.util.ArrayList;

/**
 * Aceasta clasa seamana cu QuerryFilter, doar ca seteaza id-ul actiunii,
 * cealalta clasa primea direct id-ul actiunii ca parametru, functionalitatea
 * este la fel. Se va afisa raspunul querry si o lista ce se va pune in JSON
 */
public class Query {
    private int actionId;
    private final ArrayList<String> arrayList;

    /**
     *
     * @param input primit de la fisier
     * @param actionId id actiune
     * @param arrayList lista de afisat
     */
    Query(final Input input, final int actionId,
          final ArrayList<String> arrayList) {
        this.actionId = input.getCommands().get(actionId).getActionId();
        this.arrayList = arrayList;
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
     * @param actionId setare id actiune
     */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }

    /**
     *
     * @return se va afisa in format JSON array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"Query result: "
                + arrayList + "\"" + "}";
    }
}
