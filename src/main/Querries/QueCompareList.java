package main.Querries;

/**
 * The class is used to sort the maps in which I have various names
 * things and values ​​for them. Depending on these values ​​I will sort times
 * ascending, or lexical
 */
public class QueCompareList implements Comparable<QueCompareList> {
    private final String key;
    private final int value;

    /**
     * Constructor
     * @param key it could be the name of an actor
     * @param value it may be how many prizes he holds
     */
    public QueCompareList(final String key, final int value) {
        this.key = key;
        this.value = value;
    }

    /**
     * retorn name
     * @return
     */
    public String getName() {
        return key;
    }

    /**
     * return number of awards
     * @return
     */
    public int getId() {
        return value;
    }

    /**
     * depending on the comparison either by the number of prizes or by lexicon
     * @param key1 name1
     * @param key2 name2
     * @return return or a pozitive value, or negative or 0 for equality
     * de nume
     */
    public static int compareString(final String key1, final String key2) {
        for (int i = 0; i < key1.length() && i < key2.length(); i++) {
            if ((int) key1.charAt(i) != (int) key2.charAt(i)) {
                return (int) key1.charAt(i) - (int) key2.charAt(i);
            }
        }

        if (key1.length() > key2.length()) {
            return (key1.length() - key2.length());
        } else if (key1.length() < key2.length()) {
            return (key1.length() - key2.length());
        }

        return 0;
    }

    /**
     * The comparison function has been modified to sort my maps
     * for querry functions
     * @param o an object that I will compare with this
     * @return returns either a positive value, or a negative value, or 0 to equal names and
     * values
     */
    @Override
    public int compareTo(final QueCompareList o) {
        if (value == o.value) {
            // if they have the same name I have to compare them lexically
            return compareString(key, o.key);
        }
        return this.value - o.value;
    }
}
