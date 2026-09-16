
import java.util.concurrent.Semaphore;

public class ReaderWriter {

    static int data = 0;
    static int readers = 0;

    static Semaphore mutex = new Semaphore(1);
    static Semaphore rw = new Semaphore(1);

    static class Reader extends Thread {
        public void run() {
            try {
                mutex.acquire();
                readers++;

                if (readers == 1)
                    rw.acquire();

                mutex.release();

                System.out.println("Reading data: " + data);

                mutex.acquire();
                readers--;

                if (readers == 0)
                    rw.release();

                mutex.release();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    static class Writer extends Thread {
        public void run() {
            try {
                rw.acquire();

                data++;
                System.out.println("Writing data: " + data);

                rw.release();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {

        Reader r1 = new Reader();
        Reader r2 = new Reader();

        Writer w1 = new Writer();
        Writer w2 = new Writer();

        r1.start();
        r2.start();

        w1.start();
        w2.start();
    }
}

