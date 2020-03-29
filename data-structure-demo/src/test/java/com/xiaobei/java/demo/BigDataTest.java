package com.xiaobei.java.demo;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 参见：<a href="https://blog.csdn.net/zsah2011/article/details/82562767">java 大数字相加（如两个500位数字相加）</a>
 * <a href="https://www.cnblogs.com/printN/p/6769804.html">谈谈Java中整数类型（short int long）的存储方式</a>
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-03-26 08:46:46
 */
public class BigDataTest {

    @Test
    public void testAdd() {
        int a = 2000000000;
        int b = 2000000000;
        System.out.println(a+b);
    }

    /**
     * 计算
     */
    @Test
    public void testChar() {
        String a =  "24354765868776765455344234299999993243243241112";
        String b = "985435823435454354335888888888234324888888435499";
        String result  = cul(a, b);
        System.out.println(result);
        String result2 = clu2(a, b);
        System.out.println(result2);
        culByBigDecimal(a, b);
    }

    private void culByBigDecimal(String a, String b) {
        //使用BigDecimal，方便后面直接计算正确结果进行对比。
        BigDecimal b1 = new BigDecimal(a);
        BigDecimal b2 = new BigDecimal(b);
        System.out.println(b1.add(b2));
    }

    /**
     * 第二种方式，比第一种效率高
     * @param str1
     * @param str2
     * @return
     */
    public String clu2(String str1, String str2) {
        // 将数字转换成char数组，然后第一位进行运算
        char[] char1;
        char[] char2;
        // 计算前区分，令char1 为长的那个字符创转换成的数组.
        if(str1.length() > str2.length()) {
            char1 = str1.toCharArray();
            char2 = str2.toCharArray();
        } else {
            char1 = str2.toCharArray();
            char2 = str1.toCharArray();
        }
        int length1 = char1.length;
        int length2 = char2.length;
        // flag 进位标识，末位相加进位的话前面一位需要加上进位
        int flag = 0;
        // 创建一个list接收两个数组相加的结果
        List<Integer> resultList = new ArrayList<>();
        // 遍历相加
        for (int i = length1-1; i >= 0; i--) {
            // 字母与数字之间的类型转换
            int a = Integer.parseInt(String.valueOf(char1[i]));
            // 初始化，数组char2 短一些
            int b = 0;
            int char2Index;
            // 计算char2数组在当前位置处的值
            if((char2Index = i - (length1 - length2)) >= 0) {
                b = Integer.parseInt(String.valueOf(char2[char2Index]));
            }
            // 两个数字相加，同时加上进位
            // 注意：flag 的设置一定要放在相加后面，
            // 因为相加要使用当前的flag,如果提前设置，flag的作用就没有起到
            resultList.add((a + b + flag) % 10);
            // 计算本次累加是否有需要进位，如需进位，将flag设置为1
            flag = (a + b + flag) / 10;
        }
        // 最后进位处理
        if(flag != 0) {
            resultList.add(flag);
        }
        StringBuilder sb = new StringBuilder();
        // 逆向遍历，因为加的时候是从末位开始加的，首位的在后面。
        for (int i = resultList.size() - 1; i >= 0; i--) {
            sb.append(resultList.get(i));
        }
        // 相加结果字符串返回
        return sb.toString();
    }

    /**
     * 第一种计算方法
     * @param a
     * @param b
     * @return
     */
    private String cul(String a, String b) {
        char[] aChar = a.toCharArray();
        char[] bChar = b.toCharArray();
        int aLength = aChar.length;
        int bLength = bChar.length;
        int length = aLength > bLength ? aLength : bLength;
        int[] aInt = new int[length];
        int[] bInt = new int[length];
        // 先将char转化成int
        for (int i = aLength - 1, j = 0; i >= 0; i--,j++) {
            aInt[j] = Integer.parseInt(String.valueOf(aChar[i]));
        }
        for (int i = bLength - 1, j = 0; i >= 0; i--,j++) {
            bInt[j] = Integer.parseInt(String.valueOf(bChar[i]));
        }
        int bigLength = aInt.length;
        int smallLength = bInt.length;
        int[] tempInt = new int[bigLength+1];
        tempInt[0] = 0;
        int[] tempResult = new int[bigLength+1];
        for (int i = 0; i < smallLength; i++) {
            int temp = aInt[i] + bInt[i] + tempInt[i];
            if(temp >= 10) {
                tempInt[i+1] = 1;
                tempResult[i] = temp % 10;
            } else {
                tempInt[i+1] = 0;
                tempResult[i] = temp;
            }
            if(i == smallLength -1 && temp >= 10) {
                tempResult[i+1] = 1;
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = tempResult.length - 1; i >= 0; i--) {
            sb.append(tempResult[i]);
        }
        return sb.toString();
    }
}