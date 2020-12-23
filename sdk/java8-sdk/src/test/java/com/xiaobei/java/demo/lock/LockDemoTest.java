package com.xiaobei.java.demo.lock;

import org.junit.Test;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-10-12 15:31:31
 */
public class LockDemoTest {

    @Test
    public void testLockCancel() {
        concatString("a","b","c");
    }

    public String concatString(String a, String b, String c) {
        return a + b + c;
    }

    @Test
    public void covert() {
        System.out.println(IntToBinary32Utils.intToBinary32(-1 << 29, 32));
        System.out.println(IntToBinary32Utils.intToBinary32(0 << 29, 32));
        System.out.println(IntToBinary32Utils.intToBinary32(1 << 29, 32));
        System.out.println(IntToBinary32Utils.intToBinary32(2 << 29, 32));
        System.out.println(IntToBinary32Utils.intToBinary32(3 << 29, 32));
        System.out.println(IntToBinary32Utils.intToBinary32((-1 << 29) | 0, 32));
    }

    //i 期望转换的整数 bitNum 期望转换的二进制字符串位数
    static class IntToBinary32Utils {
        public static String intToBinary32(int i, int bitNum){

            String binaryStr = Integer.toBinaryString(i);
            while(binaryStr.length() < bitNum){
                binaryStr = "0" + binaryStr;
            }
            char[] binaryCharArray = binaryStr.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < binaryCharArray.length; j++) {
                sb.append(binaryCharArray[j]);
                if((j+1) % 4 == 0) {
                    sb.append(" ");
                }
            }
            return sb.toString();
        }
    }

    @Test
    public void retry() {
        aaaa:
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                System.out.printf("i = %d, j = %d\n", i, j);
                if(j == 50) {
                    break aaaa;
                }
            }
        }
    }

}