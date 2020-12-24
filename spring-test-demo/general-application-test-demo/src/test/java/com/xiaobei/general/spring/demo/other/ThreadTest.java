package com.xiaobei.general.spring.demo.other;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/12/24 17:55
 */
public class ThreadTest {

    @Test
    public void thread() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                ReflectionTestUtils.invokeMethod(ThreadLocalRandom.class, "localInit");
                int result = ReflectionTestUtils.invokeMethod(ThreadLocalRandom.class, "getProbe");
                System.out.println(ToBinaryUtils.intToBinary32(result, 32));
            }).start();
        }
        TimeUnit.SECONDS.sleep(5);
    }


    //i 期望转换的整数 bitNum 期望转换的二进制字符串位数
    static class ToBinaryUtils {
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

        public static String LongToBinary64(long i){
            return LongToBinary64(i, 64);
        }

        private static String LongToBinary64(long i, int bitNum){
            String binaryStr = Long.toBinaryString(i);
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
}
