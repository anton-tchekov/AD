package de.hawhamburg.hamann.ad.trees.impl;

import de.hawhamburg.hamann.ad.trees.BinarySearchTree;

import java.util.NoSuchElementException;

public class BSTree<K extends Comparable<K>,E> implements BinarySearchTree<K,E> {

    @Override
    public void insert(K k, E e) {

    }

    @Override
    public void remove(K k) throws NoSuchElementException {

    }

    @Override
    public E get(K k) throws NoSuchElementException {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean contains(K k) {
        return false;
    }
}
