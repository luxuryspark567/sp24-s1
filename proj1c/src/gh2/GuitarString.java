package gh2;

import deque.Deque61B;
import deque.LinkedListDeque61B;


public class GuitarString {
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    private final Deque61B<Double> buffer;


    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency);
        buffer = new LinkedListDeque61B<>();
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(0.0);
        }
    }

    public void pluck() {
        for (int i = 0; i < buffer.size(); i++) {
            buffer.removeFirst();
            double r = Math.random() - 0.5;
            buffer.addLast(r);
        }
    }

    public void tic() {
        double first = buffer.removeFirst();
        double second = buffer.get(0);
        double newS = DECAY * .5 * (first + second);
        buffer.addLast(newS);

    }

    public double sample() {
        return buffer.get(0);
    }
}

