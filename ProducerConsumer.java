
import java.util.concurrent.Semaphore;

public class ProducerConsumer {

    static int size = 10;
    static int[] buffer = new int[size];

    static int in = 0;
    static int out = 0;

    static Semaphore empty = new Semaphore(size);
    static Semaphore full = new Semaphore(0);
    static Semaphore mutex = new Semaphore(1);

    static class Producer extends Thread {
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {

                    empty.acquire();
                    mutex.acquire();

                    buffer[in] = i;
                    System.out.println("Produced: " + i);
                    in++;

                    mutex.release();
                    full.release();
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    static class Consumer extends Thread {
        public void run() {
            try {
                for (int i = 1; i <= 10; i++) {

                    full.acquire();
                    mutex.acquire();

                    int item = buffer[out];
                    System.out.println("Consumed: " + item);
                    out++;

                    mutex.release();
                    empty.release();
                }
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {

        Producer p = new Producer();
        Consumer c = new Consumer();

        p.start();
        c.start();
    }
}

