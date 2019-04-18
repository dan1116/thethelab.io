class FooThread extends Thread {
    static int sum;

    @Override
    public void run() {

        int i;
        int local = 0;

        for (i = 0; i < 1000000000 / Main.thread_count; ++i) {
            ++local;
        }
        synchronized (this) {
            sum += local;
        }
    }

}

public class Main {
    static int thread_count = 1;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();

        if (args.length == 1)
            thread_count = Integer.parseInt(args[0]);


        FooThread[] threads = new FooThread[thread_count];
        for (int i = 0; i < thread_count; ++i) {
            threads[i] = new FooThread();
        }

        for (int i = 0; i < thread_count; ++i) {
            threads[i].start();
        }

        for (int i = 0; i < thread_count; ++i) {

            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(FooThread.sum);

        long end = System.currentTimeMillis();

        System.out.println("time: " + (end - start) / 1000.0);
    }
}

//import java.util.concurrent.atomic.AtomicInteger;
//
//class FooThread extends Thread {
//
//    private int thread_count = 1;
//    static AtomicInteger sum = new AtomicInteger(0);
//    static final Object mutex = new Object();
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 1000000000 / thread_count; ++i) {
//            sum.incrementAndGet();
//        }
//
//    }
//}

//class FooThread extends Thread {
//
//    private int thread_count = 1;
//    static int sum = 0;
//    static final Object mutex = new Object();
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 1000000000 / thread_count; ++i) {
//            synchronized (mutex) {
//                sum++;
//            }
//        }
//    }
//}

//public class Main {
//    public static void main(String[] args) throws Exception {
//
//        long start = System.currentTimeMillis();
//
//        int thread_count = 1;
//
//        Thread[] threads = new Thread[]{
//                new FooThread(),
//        };
//
//        for (int i = 0; i < thread_count; ++i) {
//            threads[i].start();
//        }
//
//        for (int i = 0; i < thread_count; ++i) {
//            threads[i].join();
//        }
//
//        System.out.println(FooThread.sum);
//
//        long end = System.currentTimeMillis();
//
//        System.out.println("실행 시간 : " + (end - start) / 1000.0);
//
//    }
//}
