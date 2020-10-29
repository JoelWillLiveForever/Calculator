package joel;

public class Main {

    public static void main(String[] args) {
        long n1 = (long)(Math.random()*Math.log(Math.PI));
        long n2 = (long)(Math.random()*Math.log(Math.PI));
        double count = 1e8;

        long avg1 = 0;
        for (int i = (int)count; i > 0; i--) {
            long time = System.nanoTime();
            long n3 = n1 + n2;
            time = System.nanoTime() - time;
            avg1 += time;
        }

        long avg2 = 0;
        for (int i = (int)count; i > 0; i--) {
            long time = System.nanoTime();
            long n4 = n1 + n2;
            time = System.nanoTime() - time;
            avg2 += time;
        }

        System.out.println("n1 + n2: " + " Avg Time: " + (avg1/count) + " ns");
        System.out.println("n1 | n2: " + " Avg Time: " + (avg2/count) + " ns");
    }
}
