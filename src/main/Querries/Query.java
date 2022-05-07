package main.Querries;

import fileio.Input;
import java.util.ArrayList;

/**
 * This class looks like QuerryFilter, only it sets the action id,
 * the other class received directly the id of the action as a parameter, the functionality
 * It's the same. The querry answer and a JSON list will be displayed
 */
public class Query {
    private int actionId;
    private final ArrayList<String> arrayList;

    /**
     *
     * @param input from file
     * @param actionId action id
     * @param arrayList list to display
     */
    Query(final Input input, final int actionId,
          final ArrayList<String> arrayList) {
        this.actionId = input.getCommands().get(actionId).getActionId();
        this.arrayList = arrayList;
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
     * @param actionId set action id
     */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }

    /**
     *
     * @return display in format JSON array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + actionId + ","
                + "\"message\"" + ":" + "\"Query result: "
                + arrayList + "\"" + "}";
    }
}
