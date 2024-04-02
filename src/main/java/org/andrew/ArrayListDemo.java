package org.andrew;

import java.util.Collection;
import java.util.Objects;

public class ArrayListDemo<E> {
    private static final int DEFAULT_CAPACITY = 10;
    E[] elementData;
    private int size;

    private E[] grow(int minCapacity) { // Увеличение массива

        return elementData;
    }

    private E[] grow() {
        return grow(size + 1);
    }

    private void add(E newElement, E[] elementData, int realSize) {

    }

    public boolean add(E newElement) { // Простое добавление элемента в конец

        return true;
    }

    public void add(E newElement, int index) { // Добавление элемента в место по индексу

    }

    public E get(int index) {
        Objects.checkIndex(index, size); // Проверка, что индекс находится в диапазоне
        return elementData[index];
    }

    public void remove(int index) { // Удаление по индексу
        Objects.checkIndex(index, size);
    }

    public void remove(E elementToDelete) { // Удаление по объекту

    }

    public void addAll(Collection <? extends E> newCollection) { // Добавление коллекции

    }

    public void addAll(Collection <? extends E> newCollection, int index) { // Добавление коллекции по индексу

    }
}
