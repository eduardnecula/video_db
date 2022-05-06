package main.Querries;

import java.util.List;

/**
 * Cu aceasta clasa afisez rezultatele interogarii unui querry in format JSON
 */
public class QueFilter {
    private final int index;
    private final List<String> listaActori;

    /**
     * Constructor
     * @param index id actiune
     * @param list lista de afisat pentru JSON array
     */
    public QueFilter(final int index,
                     final List<String> list) {
        this.listaActori = list;
        this.index = index;
    }

    /**
     *
     * @return pentru JSON arrays
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + index + ","
                + "\"message\"" + ":"
                + "\"Query result: " + listaActori
                + "\"" + "}";
    }
 }
