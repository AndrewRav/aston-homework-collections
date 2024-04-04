package org.andrew;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

/*                                              Дз коллекции
Необходимо написать свою реализацию коллекции на выбор LinkedList или ArrayList (можно оба варианта).
— Должны быть основные методы add, get, remove, addAll(ДругаяКоллекция параметр), остальное на ваше усмотрение.
— Плюс написать реализацию сортировки пузырьком с флагом, который прекращает сортировку, если коллекция уже отсортирована.
— Задание с *: На тему дженериков. Для этих коллекций сделать конструктор,
который будет принимать другую коллекцию в качестве параметров и инициализироваться с элементами из этой коллекции.
— Вторая часть - сделать метод сортировки статическим, этот метод также будет принимать какую-то коллекцию и сортировать ее.
(Аналогия Collections.sort()). Т.е подумать на тему какое ключевое слово(extends или super) будет лучше применить для этих двух задач.
*/

public class ArrayListDemo<E extends Comparable<E>> {
    private static final int DEFAULT_CAPACITY = 10; // Дефолтный размер в 10 символов
    private static final Object[] EMPTY_ELEMENT_DATA = {}; // Пустой массив
    public static final int SOFT_MAX_ARRAY_LENGTH = Integer.MAX_VALUE - 8;
    Object[] elementData;
    private int size; // Действительное количество элементов в списочном массиве

    // Конструктор, который создаёт списочный массив, имеющий заданную начальную ёмкость
    public ArrayListDemo(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENT_DATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+ initialCapacity);
        }
    }

    // Конструктор, который по дефолту создаёт пустой списочный массив объёмом в 10 элементов
    public ArrayListDemo() {
        this.elementData = new Object[DEFAULT_CAPACITY];
    }

    // Конструктор, который создаёт коллекцию на базе другой коллекции.
    // Инициализируется элементами из переданной коллекции
    public ArrayListDemo(Collection <? extends E> newCollection) {
        Object[] newArray = newCollection.toArray();
        if ((size = newArray.length) != 0) {
            elementData = Arrays.copyOf(newArray, size, Object[].class);
        } else {
            elementData = EMPTY_ELEMENT_DATA;
        }
    }

    private Object[] grow(int minCapacity) { // Увеличение массива
        int oldCapacity = elementData.length;
        if (oldCapacity > 0) {
            int newCapacity = newLength(oldCapacity, minCapacity - oldCapacity, oldCapacity >> 1);
            return elementData = Arrays.copyOf(elementData, newCapacity);
        } else {
            return elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }

    public static int newLength(int oldLength, int minGrowth, int prefGrowth) {
        int prefLength = oldLength + Math.max(minGrowth, prefGrowth);
        if (0 < prefLength && prefLength <= SOFT_MAX_ARRAY_LENGTH) {
            return prefLength;
        } else {
            int minLength = oldLength + minGrowth;
            if (minLength < 0) {
                throw new OutOfMemoryError("Длина массива " + oldLength + " + " + minGrowth + " меньше нуля");
            } else
                return Math.max(minLength, SOFT_MAX_ARRAY_LENGTH);
        }
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    private void add(E newElement, Object[] elementData, int realSize) { // Простое добавление элемента в конец
        if (realSize == elementData.length) {
            elementData = grow();
        }

        elementData[realSize] = newElement;
        size = realSize + 1;
    }

    public void add(E newElement) {
        add(newElement, elementData, size);
    }

    public void add(E newElement, int index) { // Добавление элемента в место по индексу
        rangeCheck(index);
        final int s;
        Object[] elementData;
        if ((s = size) == (elementData = this.elementData).length) {
            elementData = grow();
        }

        System.arraycopy(elementData, index, elementData, index + 1, s - index);
        elementData[index] = newElement;
        size = s + 1;
    }

    private void rangeCheck(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    public E get(int index) {
        Objects.checkIndex(index, size); // Проверка, что индекс находится в диапазоне
        return (E) elementData[index];
    }

    // Сдвигаем элементы влево и удаляем элемент определяя его как null
    private void fastRemove(Object[] elements, int i) {
        final int newSize;
        if ((newSize = size - 1) > i) {
            System.arraycopy(elements, i + 1, elements, i, newSize - i);
        }
        elements[size = newSize] = null;
    }

    public E remove(int index) { // Удаление по индексу
        Objects.checkIndex(index, size);
        final Object[] elements = elementData;
        E oldValue = (E) elements[index];
        fastRemove(elements, index);
        return oldValue;
    }

    public void remove(E elementToDelete) { // Удаление по объекту
        final Object[] elements = elementData;
        final int size = this.size;
        int i = 0;
            if (elementToDelete == null) {
                for (; i < size; i++)
                    if (elements[i] == null)
                        break;
            } else {
                for (; i < size; i++)
                    if (elementToDelete.equals(elements[i]))
                        break;
            }
        fastRemove(elements, i);
    }

    public boolean addAll(Collection <? extends E> newCollection) { // Добавление коллекции
        Object[] newArray = newCollection.toArray();
        int numNew = newArray.length;
        if (numNew == 0)
            return false;

        Object[] elementData;
        final int s;
        if (numNew > (elementData = this.elementData).length - (s = size)) {
            elementData = grow(s + numNew);
        }

        System.arraycopy(newArray, 0, elementData, s, numNew);
        size = s + numNew;
        return true;
    }

    public boolean addAll(Collection <? extends E> newCollection, int index) { // Добавление коллекции по индексу
        rangeCheck(index);
        Object[] newArray = newCollection.toArray();
        int numNew = newArray.length;
        if (numNew == 0)
            return false;

        Object[] elementData;
        final int s;
        if (numNew > (elementData = this.elementData).length - (s = size)) {
            elementData = grow(s + numNew);
        }

        int numMoved = s - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
        }
        System.arraycopy(newArray, 0, elementData, index, numNew);
        size = s + numNew;
        return true;
    }

    // Сортировка массива пузырьком (только Number)
    public static <E extends Comparable<? super E>> E[] sort(Collection <? extends Number> collectionToSort) {
        E[] array = (E[]) collectionToSort.toArray();

        boolean isSorted = false;
        E temp;
        while(!isSorted) {
            isSorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if ((array[i].compareTo(array[i + 1])) > 0) {
                    isSorted = false;
                    temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                }
            }
        }
        return array;
    }

    // Метод для проверки, отсортирован ли массив
    public static <E extends Comparable<? super E>> boolean checkIsSorted(
            Collection <? extends Number> collectionToCheck) {

        E[] array = (E[]) collectionToCheck.toArray();
        for (int i = 0; i < array.length - 1; i++) {
            if ((array[i].compareTo(array[i + 1])) > 0)
                return false;
        }
        return true;
    }

}
