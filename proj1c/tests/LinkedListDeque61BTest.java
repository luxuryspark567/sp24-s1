import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;


public class LinkedListDeque61BTest {
    @Test
    public void testAddFirstAndLast() {
        Deque61B<String> deque = new LinkedListDeque61B<>();
        deque.addFirst("middle");
        deque.addFirst("front");
        deque.addLast("back");
        assertThat(deque.toList()).containsExactly("front", "middle", "back");
    }

    @Test
    public void equalsTest() {
        Deque61B<String> deque = new LinkedListDeque61B<>();
        deque.addLast("front");
        deque.addLast("middle");
        deque.addLast("back");

        Deque61B<String> deque2 = new LinkedListDeque61B<>();
        deque2.addLast("front");
        deque2.addLast("middle");
        deque2.addLast("back");

        assertThat(deque.equals(deque2)).isTrue();
    }

    @Test
    public void iteratorTest() {
        Deque61B<String> deque = new LinkedListDeque61B<>();
        deque.addLast("front");
        deque.addLast("middle");
        deque.addLast("back");
        Iterator<String> iterator = deque.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat("front").isEqualTo(iterator.next());
        assertThat(iterator.hasNext()).isTrue();
        assertThat("middle").isEqualTo(iterator.next());
        assertThat(iterator.hasNext()).isTrue();
        assertThat("back").isEqualTo(iterator.next());
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void removeSingleElementDequeTest() {
        LinkedListDeque61B<String> deque = new LinkedListDeque61B<>();
        deque.addFirst("only");
        String removedFirst = deque.removeFirst();
        assertThat(removedFirst).isEqualTo("only");
        assertThat(deque.isEmpty()).isTrue();
        deque.addLast("only");
        String removedLast = deque.removeLast();
        assertThat(removedLast).isEqualTo("only");
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void equalsDifferentTypesTest() {
        LinkedListDeque61B<String> deque = new LinkedListDeque61B<>();
        deque.addLast("element");

        // Test equals method with null
        assertThat(deque.equals(null)).isFalse();

        // Test equals method with a different type
        assertThat(deque.equals("string")).isFalse();
    }
}
