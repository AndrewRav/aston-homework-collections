package org.andrew;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayListDemo<Integer> myArrayList = new ArrayListDemo<>();

        List<Integer> listInteger = new ArrayList<>(11);

        myArrayList.add(12);
        myArrayList.add(15);
        myArrayList.add(7);
        myArrayList.add(21);
        myArrayList.add(14);
        myArrayList.add(14);
        myArrayList.add(14);

        listInteger.add(1);
        listInteger.add(7);
        listInteger.add(1);
        listInteger.add(5);
        listInteger.add(1);
        listInteger.add(1);
        listInteger.add(10);

        myArrayList.addAll(listInteger, 2);
        myArrayList.addAll(listInteger);

        myArrayList.add(55, 2);

        myArrayList.remove(1);

        ArrayListDemo<String> myArrayListStr = new ArrayListDemo<>();

        myArrayListStr.add("Andrew");
        myArrayListStr.add("Nikita");
        myArrayListStr.add("Alex");

        myArrayListStr.remove("Alex"); // Удаление по объекту

        for (int i = 0; i < myArrayListStr.size; i++) {
            System.out.print(myArrayListStr.get(i) + " ");
        }
        System.out.println();

        for (int i = 0; i < myArrayList.size; i++) {
            System.out.print(myArrayList.get(i) + " ");
        }
        System.out.println();

        System.out.println(Arrays.toString(listInteger.toArray()) + " — Не отсортированный");
        System.out.println(Arrays.toString(ArrayListDemo.sort(listInteger)) + " — Отсортированный");
        System.out.println(ArrayListDemo.checkIsSorted(ArrayListDemo.sort(listInteger))); // Проверка, отсортирован ли массив

        System.out.close();
    }
}