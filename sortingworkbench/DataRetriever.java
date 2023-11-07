package sortingworkbench;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SplittableRandom;

public class DataRetriever {

    /**
     * Erzeugt eine Liste der Größe <code>numElements</code> mit zufällig generierten Integerwerten.
     *
     * @param numElements Anzahl der Elemente in der Liste
     *
     * @return Eine Liste mit <code>numElements</code> zufälligen Integerwerten.
     */
    public List<Integer> createRandomIntegerList(int numElements) {
        SplittableRandom splittableRandom = new SplittableRandom();

        List<Integer> list = new ArrayList<>(numElements);

        for (int i = 0; i < numElements; ++i) {
            list.add(splittableRandom.nextInt(0, numElements - 1));
        }

        return list;
    }

    /**
     * Erzeugt eine aufsteigend sortierte Liste mit <code>numElements</code> Integerwerten.
     * @param numElements Anzahl der Elemente in der Liste
     * @return Eine aufsteigend sortierte Liste mit <code>numElements</code> Integerwerten.
     */
    public List<Integer> createOrderedIntegerList(int numElements) {
        List<Integer> list = new ArrayList<>(numElements);

        for (int i = 0; i < numElements; ++i) {
            list.add(i);
        }

        return list;
    }

    /**
     * Erzeugt eine absteigend sortierte Liste mit <code>numElements</code> Integerwerten.
     * @param numElements Anzahl der Elemente in der Liste
     * @return Eine absteigend sortierte Liste mit <code>numElements</code> Integerwerten.
     */
    public List<Integer> createReverseOrderedIntegerList(int numElements) {
        List<Integer> list = new ArrayList<>(numElements);

        for (int i = numElements - 1; i >= 0; --i) {
            list.add(i);
        }

        return list;
    }

    /**
     * <p>Erzeugt eine partiell sortierte Liste mit <code>numElements</code> Elementen.
     * Die Elemente innerhalb der Teillisten mit Größe <code>partialOrderedSize</code> sind nicht sortiert, aber
     * größer oder gleich der Elemente aus der vorherigen Teilliste.</p>
     *
     * @param numElements Größe der zu erzeugenden Liste
     * @param partialOrderedSize Größe der Teillisten, die nicht sortiert sind.
     * @return Eine Liste mit teil-sortierten Unterlisten.
     */
    public List<Integer> createPartiallyOrderedIntegerList(int numElements, int partialOrderedSize) {
        if (partialOrderedSize < 0 || partialOrderedSize > numElements) {
            throw new IllegalArgumentException("Der Größe der teil-sortierten Listen muss kleiner sein als die Listengröße.");
        }

        List<Integer> list = createOrderedIntegerList(numElements);

        for (int i = 0; i < numElements; i += partialOrderedSize) {
            Collections.shuffle(list.subList(i, Math.min(numElements - 1, i + partialOrderedSize)));
        }

        return list;
    }

    /**
     * <p>Erzeugt eine Liste von Integerwerten, die je nach <code>listType</code> unterschiedlich aufgebaut sind:
     * <ul>
     *     <li><code>SortingMetrics.ListType.RANDOM</code>: Die Liste enthält zufällig erzeugte Werte</li>
     *     <li><code>SortingMetrics.ListType.ORDERED</code>: Die Liste enthält aufsteigend sortierte Werte</li>
     *     <li><code>SortingMetrics.ListType.REVERSE_ORDERED</code>: Die Liste enthält absteigend sortierte Werte</li>
     *     <li><code>SortingMetrics.ListType.PARTIAL_ORDERED</code>: Die Liste enthält teil-sortierte Werte</li>
     * </ul>
     * </p>
     * @param listType Aufbau der Liste
     * @param numElements Anzahl der Elemente in der Liste
     * @return Eine Liste mit <code>numElements</code> Werten, angeordnet entsprechend des <code>listType</code>.
     */
    public List<Integer> createIntegerList(SortingMetrics.ListType listType, int numElements) {
        return switch (listType) {
            case ORDERED -> createOrderedIntegerList(numElements);
            case REVERSE_ORDERED -> createReverseOrderedIntegerList(numElements);
            case PARTIAL_ORDERED -> createPartiallyOrderedIntegerList(numElements, 10);
            default -> createRandomIntegerList(numElements);
        };
    }
}
