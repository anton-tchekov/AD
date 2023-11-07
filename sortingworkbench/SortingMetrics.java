package sortingworkbench;

/**
 * <p>Hilfsklasse zum Sammeln der Metriken eines Sortierlaufs.
 * Speichert die Anzahl der Bewegungen und Vergleiche.</p>
 *
 * <p>Bei einem Vergleich im Sortierverfahren muss {@link SortingMetrics#incrementCompares()} aufgerufen werden.</p>
 *
 * <p>Bei einer Vertauschung oder Bewegung {@link SortingMetrics#getNumMoves()}} </p>
 */
public class SortingMetrics {
    /**
     * Typisierung von Listen nach ihren eigenschaften.
     * Wird bei der Erzeugung der Listen angegeben (siehe {@link DataRetriever#createIntegerList(ListType, int)}.
     */
    public enum ListType {
        /**
         * Liste mit zufälligen Werten
         */
        RANDOM,
        /**
         * Aufsteigend sortierte Liste
         */
        ORDERED,
        /**
         * Absteigend sortierte Liste
         */
        REVERSE_ORDERED,
        /**
         * Teilsortierte Liste
         */
        PARTIAL_ORDERED
    }

    private final ListType listType;

    private int numCompares = 0;

    private int numMoves = 0;

    public SortingMetrics(ListType listType) {
        this.listType = listType;
    }

    /**
     * Liefert den Listentyp, für den die Metriken gespeichert wurden.
     * @return Den Listentyp dieser Metriken
     */
    public ListType getListType() {
        return listType;
    }

    /**
     * Liefert die Anzahl der Vergleiche.
     *
     * @return Die Anzahl der Vergleiche
     */
    public int getNumCompares() {
        return numCompares;
    }

    /**
     * Liefert die Anzahl der Bewegungen
     *
     * @return Die Anzahl der Bewegungen.
     */
    public int getNumMoves() {
        return numMoves;
    }

    /**
     * Erhöht die Anzahl der Vergleiche um eins.
     */
    public void incrementCompares() {
        this.numCompares++;
    }

    /**
     * Erhöht die Anzahl der Bewegungen um eins.
     */
    public void incrementMoves() {
        this.numMoves++;
    }
}
