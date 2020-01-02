package com.thread.test.threadtest.semaphore.studentsCook;

import java.util.Random;

public class RandomTest {
    public static void main(String[] args) {

        /**
         * 使用有参数构造生成Random对象
         * 不管执行多少次，每次得到的结果都相同
         *  -1157793070
         *   1913984760
         *   1107254586
         */
        Random random = new Random(10);
        for (int i = 0; i < 3; i++) {
            System.out.println(random.nextInt());
        }

        //java8
        //10,86,56,47,68  .每次都相同

        random.ints(5, 10, 100).forEach(System.out :: println); //这句话和下面三句话功能相同
    }
}
