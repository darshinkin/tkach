package ru.home.practice;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by dima on 28.01.15.
 */
public class SimpleQueue {
    public static void main(String[] args) {
        Queue<Integer> linkedList = new LinkedList<Integer>();
        fullQueue(linkedList);
        System.out.println("--------LinkedList---------");
        printQueue(linkedList);

        Queue<Integer> priority = new PriorityQueue<Integer>();
        fullQueue(priority);
        System.out.println("-----PriorityQueue-------");
        printQueue(priority);

        Queue<Integer> queue = new PriorityQueue<Integer>(5, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 % 2 == 0 && o2 % 2 != 0) {
                    return -1;
                } else if (o1 % 2 != 0 && o2 % 2 == 0) {
                    return 1;
                } else if (o1 % 2 == 0 && o2 % 2 == 0) {
                    o1.compareTo(o2);
                }
                return 0;
            }
        });
        queue.add(5);
        queue.add(2);
        queue.add(1);
        queue.add(4);
        System.out.println("-------------Queue------------");
        printQueue(queue);
    }

    private static void printQueue(Queue<Integer> q) {
        while (!q.isEmpty()) {
            System.out.println(q.poll());
        }
    }

    private static void fullQueue(Queue<Integer> q) {
        q.offer(5);
        q.offer(4);
        q.offer(3);
        q.offer(2);
        q.offer(1);
    }
}
