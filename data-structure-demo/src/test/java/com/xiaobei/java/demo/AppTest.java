package com.xiaobei.java.demo;



import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
@SuppressWarnings("ALL")
public class AppTest {
    private int[] arr;

    @BeforeAll
    public void init() {
        arr = new int[]{1, 10, 3, 5, 9, 12, 7, 11, 14, 19, 17};
    }

    @Test
    public void bubbleSort() {
        int length = arr.length;
        int temp;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void selectionSort() {
        int length = arr.length;
        int minIndex, temp;
        for (int i = 0; i < length - 1; i++) {
            minIndex = i;
            for (int j = i; j < length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void insertionSort() {
        int length = arr.length;
        int preIndex, current;
        for (int i = 1; i < length; i++) {
            preIndex = i - 1;
            current = arr[i];
            while (preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
        System.out.println(Arrays.toString(arr));
    }
}
