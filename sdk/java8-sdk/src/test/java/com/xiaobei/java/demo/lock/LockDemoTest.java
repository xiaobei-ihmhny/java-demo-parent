package com.xiaobei.java.demo.lock;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

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
        System.out.println(ToBinaryUtils.intToBinary32(-1 << 29, 32));
        System.out.println(ToBinaryUtils.intToBinary32(0 << 29, 32));
        System.out.println(ToBinaryUtils.intToBinary32(1 << 29, 32));
        System.out.println(ToBinaryUtils.intToBinary32(2 << 29, 32));
        System.out.println(ToBinaryUtils.intToBinary32(3 << 29, 32));
        System.out.println(ToBinaryUtils.intToBinary32((-1 << 29) | 0, 32));
    }

    @Test
    public void forkJoinPool() {
        System.out.println(ToBinaryUtils.intToBinary32(0xffff, 32));
        System.out.println(ToBinaryUtils.intToBinary32(0x7fff, 32));
        System.out.println(ToBinaryUtils.intToBinary32(0xfffe, 32));
        System.out.println(ToBinaryUtils.intToBinary32(0x007e, 32));
        System.out.println(ToBinaryUtils.intToBinary32(1 << 31, 32));
        System.out.println(ToBinaryUtils.intToBinary32(1 << 16, 32));
        System.out.println(ToBinaryUtils.intToBinary32(0xffff << 16, 32));
        System.out.println(ToBinaryUtils.intToBinary32(1 << 16, 32));
        System.out.println(ToBinaryUtils.intToBinary32(1 << 31, 32));
    }

    // 0000 0000 0000 0000 0000 0000 0000 0000 1111 1111 1111 1111 1111 1111 1111 1111
    private static final long SP_MASK    = 0xffffffffL;
    // 1111 1111 1111 1111 1111 1111 1111 1111 0000 0000 0000 0000 0000 0000 0000 0000
    private static final long UC_MASK    = ~SP_MASK;

    // Active counts
    private static final int  AC_SHIFT   = 48;
    // 0000 0000 0000 0001 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000
    private static final long AC_UNIT    = 0x0001L << AC_SHIFT;
    // 1111 1111 1111 1111 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000
    private static final long AC_MASK    = 0xffffL << AC_SHIFT;

    // Total counts
    private static final int  TC_SHIFT   = 32;
    // 0000 0000 0000 0000 0000 0000 0000 0001 0000 0000 0000 0000 0000 0000 0000 0000
    private static final long TC_UNIT    = 0x0001L << TC_SHIFT;
    // 0000 0000 0000 0000 1111 1111 1111 1111 0000 0000 0000 0000 0000 0000 0000 0000
    private static final long TC_MASK    = 0xffffL << TC_SHIFT;
    // 0000 0000 0000 0000 1000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000
    private static final long ADD_WORKER = 0x0001L << (TC_SHIFT + 15); // sign

    // runState bits: SHUTDOWN must be negative, others arbitrary powers of two
    private static final int  RSLOCK     = 1;
    // 0000 0000 0000 0000 0000 0000 0000 0010
    private static final int  RSIGNAL    = 1 << 1;
    // 0000 0000 0000 0000 0000 0000 0000 0100
    private static final int  STARTED    = 1 << 2;
    // 0010 0000 0000 0000 0000 0000 0000 0000
    private static final int  STOP       = 1 << 29;
    // 0100 0000 0000 0000 0000 0000 0000 0000
    private static final int  TERMINATED = 1 << 30;
    // 1000 0000 0000 0000 0000 0000 0000 0000
    private static final int  SHUTDOWN   = 1 << 31;

    @Test
    public void forkJoinPool3() {
        System.out.println(ToBinaryUtils.intToBinary32(1 << 1, 32));
        System.out.println(ToBinaryUtils.intToBinary32(1 << 2, 32));
        System.out.println(ToBinaryUtils.intToBinary32(1 << 29, 32));
        System.out.println(ToBinaryUtils.intToBinary32(1 << 30, 32));
        System.out.println(ToBinaryUtils.intToBinary32(1 << 31, 32));
    }

    @Test
    public void forkJoin () {
        System.out.println(ToBinaryUtils.LongToBinary64(SP_MASK));
        System.out.println(ToBinaryUtils.LongToBinary64(UC_MASK));
        System.out.println(ToBinaryUtils.LongToBinary64(AC_UNIT));
        System.out.println(ToBinaryUtils.LongToBinary64(AC_MASK));
        System.out.println(ToBinaryUtils.LongToBinary64(TC_UNIT));
        System.out.println(ToBinaryUtils.LongToBinary64(TC_MASK));
        System.out.println(ToBinaryUtils.LongToBinary64(ADD_WORKER));
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