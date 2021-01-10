package com.xiaobei.sort.demo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;

import java.util.Arrays;

/**
 * 常见的10种排序算法
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2021-01-09 23:04:04
 */
public class CommonSortingAlgorithmsTest {

    private static int[] arr;

    /**
     * 初始化数组
     */
    @BeforeAll
    public static void initArr() {
        arr = new int[]{1, 10, 3, 5, 9, 12, 7, 11, 14, 19, 17};
    }

    @AfterEach
    public void printResult() {
        // 打印排序结果
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 1. 冒泡排序
     * <p>时间复杂度</p>
     * O(n^2)
     */
    @Test
    void bubbleSort() {
        int temp;
        for(int i = 0; i < arr.length - 1; i++) {
            for(int j = 0; j < arr.length - i - 1; j++) {
                // 相邻两个元素相比
                if(arr[j] > arr[j + 1]) {
                    // 元素交换
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 2. 选择排序
     * <p>时间复杂度</p>
     * O(n^2)
     */
    @Test
    void selectionSort() {
        int temp;
        for(int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for(int j = i + 1; j < arr.length; j++) {
                if(arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            // 交换
            temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }

    /**
     * 3. 插入排序
     * <p>时间复杂度</p>
     * O(n^2)
     */
    @Test
    void insertionSort() {
        int length = arr.length;
        int current, preIndex;
        for (int i = 1; i < length; i++) {
            preIndex = i - 1;
            current = arr[i];
            while(preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
    }

    /**
     * 4. 希尔排序
     * <p>时间复杂度</p>
     * O(n^(1.3—2))
     */
    @Test
    void shellSort() {
        int length = arr.length;
        int gap = length / 2, current, preIndex;
        while(gap > 0) {
            for(int i = gap; i < length; i++) {
                current = arr[i];
                preIndex = i - gap;
                while(preIndex >= 0 && arr[preIndex] > current) {
                    arr[preIndex + gap] = arr[preIndex];
                    preIndex -= gap;
                }
                arr[preIndex + gap] = current;
            }
            gap /= 2;
        }
    }

    /**
     * TODO 5. 归并排序
     *
     */
    @Test
    void mergeSort() {

    }

    @Test
    void quickSort() {
        QuickSort.quickSort(arr, 0, 9);
    }

    @Test
    void quickSort2() {
        int[] array = new int[]{72, 6, 57, 88, 60, 42, 83, 73, 48, 85};
        QuickSort.quickSort2(array);
        System.out.printf("排序后的结果为：%s%n", Arrays.toString(array));
    }

    /**
     * 快速排序
     */
    @Nested
    @DisplayName("快速排序示例")
    static class QuickSort {

        /**
         * 参见：https://www.runoob.com/w3cnote/quick-sort.html
         *
         * @param array
         */
        public static void quickSort2(int[] array) {
            quickSort2(array, 0, array.length - 1);
        }

        private static void quickSort2(int[] array, int l, int r) {
            if(l < r) {
                int i = l, j = r, x = array[l];
                while(i < j) {
                    while (i < j && array[j] >= x)//从右向左找第一个小于x的数
                        j--;
                    if(i < j)
                        array[i++] = array[j];
                    while(i < j && array[i] < x)//从左向右找第一个大于等于x的数
                        i++;
                    if(i < j)
                        array[j--] = array[i];
                }
                array[i] = x;
                quickSort2(array, l, i - 1);// 递归调用
                quickSort2(array, i + 1, r);// 递归调用
            }
        }

        /**
         * 快速排序方法
         * @param array
         * @param start
         * @param end
         * @return
         */
        public static void quickSort(int[] array, int start, int end) {
            if(array.length < 1 || start < 0 || end >= array.length || start > end) {
                return;
            }
            int smallIndex = partition(array, start, end);
            if(smallIndex > start) {
                quickSort(array, start, smallIndex - 1);
            }
            if(smallIndex < end) {
                quickSort(array, smallIndex + 1, end);
            }
        }

        /**
         * 快速排序算法——partition
         * @param array
         * @param start
         * @param end
         * @return
         */
        private static int partition(int[] array, int start, int end) {
            int pivot = (int) (start + Math.random() * (end - start + 1));
            int smallIndex = start - 1;
            swap(array, pivot, end);
            for (int i = start; i <= end; i++) {
                if(array[i] <= array[end]) {
                    smallIndex++;
                    if(i > smallIndex) {
                        swap(array, i, smallIndex);
                    }
                }
            }
            return smallIndex;
        }

        /**
         * 交换数组内的两个元素
         * @param array
         * @param i
         * @param j
         */
        private static void swap(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }


    }
}
