package main.Querries;

/**
 * Clasa este folosita pentru a-mi sorta map-urile in care am nume de diverse
 * lucuri si valori pentru ele. In functie de aceste valori voi sorta ori
 * crescator, ori lexic
 */
public class QueCompareList implements Comparable<QueCompareList> {
    private final String key;
    private final int value;

    /**
     * Constructor
     * @param key poate sa fie numele unui actor
     * @param value poate sa fie cate premii detine
     */
    public QueCompareList(final String key, final int value) {
        this.key = key;
        this.value = value;
    }

    /**
     * intoarce un nume de exemplu
     * @return
     */
    public String getName() {
        return key;
    }

    /**
     * intoarce numarul de premii de exemplu
     * @return
     */
    public int getId() {
        return value;
    }

    /**
     * functie de comparare ori dupa numarul de premii, ori lexic
     * @param key1 numele1
     * @param key2 numele2
     * @return intoarce  ori o valoare poz, ori neg ori 0 pentru egalitate
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
     * Functia de comparare a fost modificata pentru a-mi sorta map-urile
     * pentru functiile de querry
     * @param o un obiect pe care il voi compara cu this
     * @return intoarce ori o val poz, ori neg ori 0 la egalitate de nume si
     * valori
     */
    @Override
    public int compareTo(final QueCompareList o) {
        if (value == o.value) {
            // daca au acelasi nume trebuie sa compar lexic
            return compareString(key, o.key);
        }
        return this.value - o.value;
    }
}
