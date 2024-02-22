import deque.ArrayDeque61B;
import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {
    @Test
    public void testAddFirstAndLast() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addFirst("middle");
        deque.addFirst("front");
        deque.addLast("back");
        assertThat(deque.toList()).containsExactly("front", "middle", "back");
    }

    @Test
    public void equalsTest() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("front");
        deque.addLast("middle");
        deque.addLast("back");

        Deque61B<String> deque2 = new ArrayDeque61B<>();
        deque2.addLast("front");
        deque2.addLast("middle");
        deque2.addLast("back");

        assertThat(deque.equals(deque2)).isTrue();

    }

    @Test
    public void iteratorNextTest() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("first");
        deque.addLast("second");
        deque.addLast("third");
        Iterator<String> iterator = deque.iterator();
        assertThat(iterator.hasNext()).isTrue();
        assertThat("first").isEqualTo(iterator.next());
        assertThat(iterator.hasNext()).isTrue();
        assertThat("second").isEqualTo(iterator.next());
        assertThat(iterator.hasNext()).isTrue();
        assertThat("third").isEqualTo(iterator.next());
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    public void removeSingleElementDequeTest() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
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
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("element");

        // Test equals method with null
        assertThat(deque == null).isFalse();

        // Test equals method with a different type
        assertThat(deque.equals("string")).isFalse();
    }

    @Test
    public void listNotEqual() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        ArrayDeque61B<String> deque2 = new ArrayDeque61B<>();
        deque.addLast("element");
        deque2.addLast("hello");
        assertThat(deque == deque2).isFalse();
    }

    @Test
    public void testLinkedListDequeEqualToArrayDeque() {
        LinkedListDeque61B<String> linkedListDeque = new LinkedListDeque61B<>();
        ArrayDeque61B<String> arrayDeque = new ArrayDeque61B<>();
        linkedListDeque.addLast("hello");
        linkedListDeque.addLast("world");
        arrayDeque.addLast("hello");
        arrayDeque.addLast("world");
        assertThat(arrayDeque).isEqualTo(linkedListDeque);
    }

    @Test
    public void finalIteratorTest(){
        Deque61B<String> arrayDeque = new ArrayDeque61B<>();
        Deque61B<String> linkedListDeque = new LinkedListDeque61B<>();
        String [] elements = {"hello", "world"};
        for (String element: elements){
            arrayDeque.addLast (element);
            linkedListDeque.addLast(element);
        }
        Iterator<String> iterator = arrayDeque.iterator();
        int count = 0;
        while(iterator.hasNext()){
            assertThat(iterator.next()).isEqualTo(elements[count]);
            count ++;
        }
        assertThat(count).isEqualTo(elements.length);
        Deque61B<String> differentContentDeque = new ArrayDeque61B<>();
        differentContentDeque.addLast("different");
        Deque61B<String> emptyDeque = new ArrayDeque61B<>();
        assertThat(arrayDeque).isNotEqualTo(differentContentDeque);
        assertThat(arrayDeque).isNotEqualTo(emptyDeque);
        assertThat(arrayDeque.removeFirst()).isEqualTo("hello");
        assertThat(arrayDeque.removeLast()).isEqualTo("world");
        assertThat(arrayDeque.isEmpty()).isTrue();
    }
}
