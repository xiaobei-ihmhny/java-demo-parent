package com.xiaobei.java.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 排序算法
 * <a href="https://www.cnblogs.com/onepixel/articles/7674659.html">十大经典排序算法（动图演示）</a>
 *
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-03-30 21:52:52
 */
@SuppressWarnings("ALL")
public class SortingAlgorithmTest {

    private int[] arr;

    @BeforeAll
    public void init() {
        arr = new int[]{1, 10, 3, 5, 9, 12, 7, 11, 14, 19, 17};
    }

    /**
     * 冒泡排序
     * 概述：
     * <p>冒泡排序是一种简单的排序算法。
     * 它重复地走访过要排序的数列，一次比较两个元素，
     * 如果它们的顺序错误就把它们交换过来。
     * 走访数列的工作是重复地进行直到没有再需要交换，
     * 也就是说该数列已经排序完成。
     * 这个算法的名字由来是因为越小的元素会经由交换慢慢“浮”到数列的顶端。
     * </p>
     * <p>
     * 1. 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
     * 2. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
     * 3. 针对所有的元素重复以上的步骤，除了最后一个；
     * 4. 重复步骤1~3，直到排序完成。
     * </p>
     *
     *
     */
    @Test
    public void bubbleSort() {
        int length = arr.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - i - 1; j++) {
                // 相邻两个元素相比
                if (arr[j] > arr[j + 1]) {
                    // 元素交换
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 选择排序
     * <p>
     *     选择排序(Selection-sort)是一种简单直观的排序算法。
     *     它的工作原理：首先在未排序序列中找到最小（大）元素，
     *     存放到排序序列的起始位置，然后，
     *     再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     *     以此类推，直到所有元素均排序完毕。
     * </p>
     * 算法描述：
     * n个记录的直接选择排序可经过n-1趟直接选择排序得到有序结果。具体算法描述如下：
     *
     * 1. 初始状态：无序区为R[1..n]，有序区为空；
     * 2. 第i趟排序(i=1,2,3…n-1)开始时，当前有序区和无序区分别为R[1..i-1]和R(i..n）。
     * 该趟排序从当前无序区中-选出关键字最小的记录 R[k]，
     * 将它与无序区的第1个记录R交换，
     * 使R[1..i]和R[i+1..n)分别变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；
     * n-1趟结束，数组有序化了。
     */
    @Test
    public void selectionSort() {
        int length = arr.length;
        int minIndex, temp;
        for(int i = 0; i < length - 1; i++) {
            minIndex = i;
            for(int j = i + 1; j < length; j++) {
                if(arr[j] < arr[minIndex]) {
                    minIndex = j;//将最小数的索引保存
                }
            }
            temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 插入排序
     * <p>插入排序（Insertion-Sort）的算法描述是一种简单直观的排序算法。
     * 它的工作原理是通过构建有序序列，
     * 对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入</p>
     *
     * 一般来说，插入排序都采用in-place在数组上实现。具体算法描述如下：
     * 1. 从第一个元素开始，该元素可以认为已经被排序；
     * 2. 取出下一个元素，在已经排序的元素序列中从后向前扫描；
     * 3. 如果该元素（已排序）大于新元素，将该元素移到下一位置；
     * 4. 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
     * 5. 将新元素插入到该位置后；
     * 6. 重复步骤2~5。
     */
    @Test
    public void insertionSort() {
        int length = arr.length;
        int current, preIndex;
        for(int i = 1; i < length; i++) {
            preIndex = i - 1;
            current = arr[i];
            while(preIndex >= 0 && arr[preIndex] > current) {
                arr[preIndex + 1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex + 1] = current;
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 插入排序
     * 自己实现
     */
    @Test
    public void insertionSort2() {
        int length = arr.length;
        int current;
        for(int i = 0; i < length - 1; i++ ) {
            current = arr[i + 1];
            for(int j = i; j >= 0; j--) {
                if(arr[j] > current) {
                    arr[j + 1] = arr[j];
                } else {
                    arr[j + 1] = current;
                    break;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 4、希尔排序（Shell Sort）
     *
     */
    @Test
    public void shellSort() {
        int length = arr.length;
        int gap = length / 2;
        int current, preIndex;
        while(gap > 0) {
            for(int i = gap; i < length; i++) {
                preIndex = i - gap;
                current = arr[i];
                while(preIndex >= 0 && arr[preIndex] > current) {
                    arr[preIndex + gap] = arr[preIndex];
                    preIndex -= gap;
                }
                arr[preIndex + gap] = current;
            }
            gap /= 2;
        }
        System.out.println(Arrays.toString(arr));
    }



}