package main.Querries;

import java.util.List;

/**
 * With this class I display the results of querying a querry in JSON format
 */
public class QueFilter {
    private final int index;
    private final List<String> listaActori;

    /**
     * Constructor
     * @param index action id
     * @param list list to display for JSON array
     */
    public QueFilter(final int index,
                     final List<String> list) {
        this.listaActori = list;
        this.index = index;
    }

    /**
     *
     * @return for JSON arrays
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + index + ","
                + "\"message\"" + ":"
                + "\"Query result: " + listaActori
                + "\"" + "}";
    }
 }
