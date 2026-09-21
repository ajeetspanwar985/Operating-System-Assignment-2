import java.util.concurrent.Semaphore;

public class DiningPhilosophers {

    static int N = 5;

    static Semaphore mutex = new Semaphore(1);
    static Semaphore[] chopstick = new Semaphore[N];

    static class Philosopher extends Thread {
        int i;

        Philosopher(int i) {
            this.i = i;
        }

        public void run() {
            try {
                System.out.println("Philosopher " + i + " is thinking");

                mutex.acquire();

                if (i % 2 == 0) {
                    chopstick[i].acquire();
                    chopstick[(i + 1) % N].acquire();
                } else {
                    chopstick[(i + 1) % N].acquire();
                    chopstick[i].acquire();
                }

                mutex.release();

                System.out.println("Philosopher " + i + " is eating");

                chopstick[i].release();
                chopstick[(i + 1) % N].release();

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public static void main(String[] args) {

        for (int i = 0; i < N; i++)
            chopstick[i] = new Semaphore(1);

        for (int i = 0; i < N; i++)
            new Philosopher(i).start();
    }
}
