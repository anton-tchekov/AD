package de.hawhamburg.hamann.ad.trees;

import java.util.NoSuchElementException;

/**
 * Interface für einen Binary Search Tree.
 * Diese erlauben einen Zugriff über einen Schlüssel <code>K</code>
 * auf ein Element <code>E</code>.
 */
public interface BinarySearchTree<Key extends Comparable<Key>, Element> {
    /**
     * Fügt ein das Element <code>e</code> mit
     * dem Schlüssel <code>key</code> in die Symboltabelle ein.
     * Sollte es bereits einen Eintrag zu diesem Schlüssel
     * geben, dann wird das vorhandene Element überschrieben.
     * @param key Der Schlüssel des Elements
     * @param e Der einzufügende Wert
     */
    void insert(Key key, Element e);

    /**
     * Entfernt den Eintrag zum Schlüssel <code>key</code>.
     *
     * @param key Der zu entfernende Schlüssel
     * @throws NoSuchElementException Falls der Schlüssel nicht enthalten ist.
     */
    void remove(Key key) throws NoSuchElementException;

    /**
     * Gibt das Element, das zum Schlüssel <code>key</code>
     * gespeichert ist zurück.
     * Falls es keinen Eintrag gibt, wird eine Exception ausgelöst.
     * @param key Der Schlüssel des Elements
     * @return Das zum Schlüssel <cdoe>key</cdoe> gespeicherte Element
     * @throws NoSuchElementException Falls es keinen Eintrag zum Schlüssel <code>Key</code> gibt.
     */
    Element get(Key key) throws NoSuchElementException;

    /**
     * Gibt die Anzahl der gespeicherten Einträge wieder.
     *
     * @return Anzahl der gespeicherten Einträge.
     */
    int size();

    /**
     * Liefert <code>true</code>, falls es zum übergebenen Schlüssel <code>key</code> einen Eintrag gibt.
     * <code>false</code> sonst.
     *
     * @param key Der zu prüfende Schlüssel
     * @return <code>true</code>, wenn ein Eintrag zum Schlüssel existiert. Sonst <code>false</code>.
     */
    boolean contains(Key key);
}
