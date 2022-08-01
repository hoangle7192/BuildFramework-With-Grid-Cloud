package demoJava;

import java.util.*;

public class CalendarDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        list.add(46);
        list.add(67);
        list.add(24);
        list.add(16);
        list.add(8);
        list.add(12);
        System.out.println("Maximum value: " + Collections.max(list));
        System.out.println("Minimum value: " + Collections.min(list));
        System.out.println("Index of value 24 : " + Collections.binarySearch(list, 24));
        System.out.println("Index of value 10 : " + Collections.binarySearch(list, 10));

        Collections.sort(list);
        System.out.println("Sorted ASC: " + list);

        Collections.reverse(list);
        System.out.println("Sorted DESC: " + list);

        Comparator<Integer> compareDesc = Collections.reverseOrder();
        list.sort(compareDesc);
        System.out.println("Sorted ASC: " + list);

        Comparator<Integer> compareAsc = Collections.reverseOrder(compareDesc);
        list.sort(compareAsc);
        System.out.println("Sorted DESC: " + list);
    }
}
