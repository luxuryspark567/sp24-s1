import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    public void addFirstEmpty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        assert deque.size() == 1 && deque.get(0) == 1;
    }

    @Test
    public void addLastEmpty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        assert deque.size() == 1 && deque.get(0) == 1;
    }

    @Test
    public void addFirst() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(2);
        deque.addFirst(1);
        assert deque.size() == 2 && deque.get(0) == 1;
    }

    @Test
    public void addLast() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        assert deque.size() == 2 && deque.get(1) == 2;
    }

    @Test
    public void addFirstResize() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 8; i++) {
            deque.addFirst(i);
        }
        deque.addFirst(9);
        assert deque.size() == 9 && deque.get(0) == 9;
    }

    @Test
    public void addLastResize() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 8; i++) {
            deque.addLast(i);
        }
        deque.addLast(9);
        assert deque.size() == 9 && deque.get(8) == 9;
    }

    @Test
    public void removeFirstAddEmpty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.removeFirst();
        deque.addFirst(2);
        assert deque.size() == 1 && deque.get(0) == 2;
    }

    @Test
    public void removeLastAddEmpty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.removeLast();
        deque.addLast(2);
        assert deque.size() == 1 && deque.get(0) == 2;
    }

    @Test
    public void removeLast() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addLast(2);
        assert deque.removeLast() == 2;
    }

    @Test
    public void removeFirst() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.addLast(2);
        assert deque.removeFirst() == 1;
    }

    @Test
    public void removeFirsEmptyt() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(1);
        deque.removeFirst();
        assert deque.isEmpty();
    }

    @Test
    public void removeLastEmpty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(2);
        deque.removeLast();
        assert deque.isEmpty();
    }

    @Test
    public void removeFirstToOne() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.removeFirst();
        assert deque.size() == 1 && deque.get(0) == 2;
    }

    @Test
    public void removeLastToOne() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(2);
        deque.addFirst(1);
        deque.removeLast();
        assert deque.size() == 1 && deque.get(0) == 1;
    }

    @Test
    public void removeFirstResize() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 16; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 12; i++) {
            deque.removeFirst();
        }
        assert deque.size() == 4;
    }

    @Test
    public void removeLastResize() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 16; i++) {
            deque.addLast(i);
        }
        for (int i = 0; i < 12; i++) {
            deque.removeLast();
        }
        assert deque.size() == 4;
    }

    @Test
    public void getValid() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        assertThat(deque.get(1)).isEqualTo(2);
    }

    @Test
    public void getOobLarge() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        assertThat(deque.get(100)).isNull();
    }

    @Test
    public void getOobNeg() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        assertThat(deque.get(-1)).isNull();
    }

    @Test
    public void size() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.size()).isEqualTo(0);
        deque.addLast(1);
        assertThat(deque.size()).isEqualTo(1);
        deque.addFirst(0);
        assertThat(deque.size()).isEqualTo(2);
        deque.removeFirst();
        assertThat(deque.size()).isEqualTo(1);
        deque.addLast(2);
        deque.addLast(3);
        assertThat(deque.size()).isEqualTo(3);
        deque.removeLast();
        assertThat(deque.size()).isEqualTo(2);
        deque.removeFirst();
        deque.removeLast();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void sizeRemoveToEmpty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.removeLast();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void sizeRemoveFromEmpty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.removeFirst();
        assertThat(deque.size()).isEqualTo(0);
    }

    @Test
    public void emptyTrue() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.isEmpty()).isTrue();
    }

    @Test
    public void emptyFalse() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        assertThat(deque.isEmpty()).isFalse();
    }

    @Test
    public void toListEmpty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        assertThat(deque.toList()).isEmpty();
    }

    @Test
    public void toListNonEmpty() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        List<Integer> expected = Arrays.asList(1, 2, 3);
        assertThat(deque.toList()).isEqualTo(expected);
    }

    @Test
    public void resize() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        for (int i = 0; i < 9; i++) {
            deque.addLast(i);
        }
        assert deque.size() == 9;
        for (int i = 0; i < 7; i++) {
            deque.removeFirst();
        }
        assert deque.size() == 2;
    }
}
