package com.xiaobei.java.demo;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 比较三种方式性能的差异
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2019-12-02 11:20:20
 */
public class SumTaskTest {

    private long[] array;

    @Before
    public void init() {
        // 创建随机数组成的数组
//        array = new Random()
//            .longs(1, 4000)
//            .distinct()
//            .limit(400)
//            .toArray();
        array = new long[]{2485, 3826, 412, 1617, 575, 3476, 2230, 15, 3809, 1993, 1592, 3643, 581, 3632, 49, 2274, 3439, 3420, 672, 3287, 2146, 2048, 2555, 1471, 3741, 1351, 587, 3820, 3590, 1602, 1739, 673, 3466, 40, 2431, 1328, 1021, 2119, 2209, 2620, 745, 981, 521, 1027, 1200, 3688, 3091, 3234, 2172, 1174, 1686, 3367, 632, 2820, 2550, 3350, 435, 1451, 2478, 1603, 3000, 615, 1139, 3852, 182, 1509, 2484, 176, 1289, 3255, 424, 3158, 1389, 1261, 100, 3008, 3579, 1826, 2635, 2657, 1049, 594, 2312, 2179, 3756, 563, 965, 2282, 2185, 3094, 792, 837, 3752, 454, 3556, 752, 2361, 1375, 1233, 1702, 3908, 636, 3178, 2063, 3921, 1569, 3081, 309, 2577, 2732, 2581, 2079, 2670, 1291, 2027, 1561, 3410, 639, 3286, 276, 186, 3164, 2235, 3884, 351, 3719, 124, 1867, 543, 65, 3833, 93, 3101, 1491, 251, 2398, 3325, 853, 189, 3883, 1088, 3203, 2560, 1969, 690, 970, 2710, 370, 2768, 1391, 3873, 489, 3040, 2986, 2977, 1824, 3453, 1303, 1273, 3150, 709, 1052, 2174, 1079, 2380, 3199, 944, 1334, 918, 440, 1612, 748, 1134, 2448, 2995, 2327, 559, 1755, 1281, 3951, 2562, 167, 2237, 2908, 1379, 3338, 3986, 591, 2032, 3029, 3823, 1117, 3266, 1802, 446, 2527, 3023, 488, 3582, 3993, 3478, 393, 1402, 3670, 953, 260, 3954, 3362, 1295, 2280, 1146, 3877, 742, 784, 2766, 3108, 3827, 123, 2045, 1143, 1216, 47, 1020, 2457, 2703, 901, 3418, 3500, 456, 1546, 1766, 620, 3779, 3451, 329, 404, 2781, 1255, 177, 1068, 504, 2318, 1296, 3924, 2381, 1411, 181, 3480, 2120, 3174, 2721, 1459, 3963, 2107, 3563, 2463, 1923, 2040, 347, 1828, 377, 479, 3674, 3170, 2475, 542, 2634, 1307, 3141, 3044, 184, 2106, 1504, 2910, 3828, 982, 3228, 102, 3311, 466, 31, 3731, 754, 2016, 368, 2184, 1688, 2973, 3703, 335, 1163, 1131, 2673, 2104, 1926, 398, 1758, 990, 1311, 539, 279, 2154, 2065, 1431, 432, 1184, 1287, 330, 771, 3220, 1551, 1964, 3147, 1089, 3641, 808, 3390, 1784, 1607, 2647, 1333, 448, 2720, 3709, 1914, 3030, 613, 2238, 67, 1060, 2326, 3039, 945, 3540, 3941, 1044, 2364, 3504, 3753, 2708, 392, 889, 150, 3742, 971, 985, 1808, 1728, 3321, 1035, 2556, 179, 2386, 1988, 3638, 3525, 3901, 1421, 2564, 3055, 3071, 238, 1708, 2782, 537, 3106, 2558, 867, 2688, 3489, 2129, 1415, 3424, 2906, 1393, 2395, 1995, 1844, 2425, 1366, 1871, 2540, 510, 1468, 3353, 2054, 380, 3324, 2472, 3576, 2630, 2315, 2909, 809, 287, 1443, 198, 258, 1145, 914};
        System.out.println(Arrays.toString(array));
    }

    /**
     * directly sum: 814267 in 751 ms
     */
    @Test
    public void directly() {
        SumDirectly sumDemo = new SumDirectly(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = sumDemo.computeDirectly();
        long useTime = System.currentTimeMillis() - startTime;
        System.out.println(System.out.printf("directly sum: %d in %d ms", result, useTime));
        System.out.println("==========分隔线==========");
    }

    /**
     *
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void useThreadPoolDirectly() throws ExecutionException, InterruptedException {
        SumThreadPoolDirectly directly = new SumThreadPoolDirectly();
        SumDirectly sumDemo = new SumDirectly(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = directly.compute(sumDemo);
        long useTime = System.currentTimeMillis() - startTime;
        System.out.println(System.out.printf("useThreadPoolDirectly sum: %d in %d ms", result, useTime));
        System.out.println("==========分隔线==========");
    }

    /**
     * 关键代码在于{@code fjp.invoke(task) }来提交一个Fork/Join任务并发执行，
     * 然后获得异步执行的结果。
     * 我们设置任务的最小阀值是100，当提交一个400大小的任务时，在4核CPU上执行，
     * 会一分为二，再二分为四，每个最小子任务的执行时间是1秒，由于是并发4个子任务执行，
     * 整个任务最终执行时间大约为1秒。
     *
     * Fork/Join sum: 803336 in 192 ms
     */
    @Test
    public void testForkJoin() {
        // Fork/Join task
        ForkJoinPool fjp = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        ForkJoinTask<Long> task = new SumForkJoinTask(array, 0, array.length);
        long startTime = System.currentTimeMillis();
        Long result = fjp.invoke(task);
        long useTime = System.currentTimeMillis() - startTime;
        System.out.println(System.out.printf("Fork/Join sum: %d in %d ms", result, useTime));
        System.out.println("==========分隔线==========");
    }

}