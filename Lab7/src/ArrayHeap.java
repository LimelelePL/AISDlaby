import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ArrayHeap <T extends Comparable<T>> extends TreeArrayBinaryHeap<T> {
    private final ArrayList<T> heap;

    public ArrayHeap () {
        heap = new ArrayList<>();
    }

    private boolean hasRightChild(int index) {
        return 2 * index + 2 < heap.size();
    }

    private boolean hasLeftChild(int index) {
        return 2 * index + 1 < heap.size();
    }

    public void add(T value) {
        heap.add(value);
        int index = heap.size() - 1;

        while (index > 0) {
            int parentIndex = getParentIndex(index);

            if (heap.get(parentIndex).compareTo(heap.get(index)) < 0) {
                T tmp = heap.get(index);
                heap.set(index, heap.get(parentIndex));
                heap.set(parentIndex, tmp);

                index = parentIndex;
            } else {
                break;
            }
        }
    }

    public T maximum() {
        if (heap.isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        if (heap.size() == 1) {
            return heap.removeFirst();
        }

        T max = heap.getFirst();
        heap.set(0, heap.removeLast());

        int index=0;

        while (hasLeftChild(index)) {

            int largest = index;
            int leftChildIndex = getLeftChildIndex(index);
            int rightChildIndex = getRightChildIndex(index);

            if(leftChildIndex< heap.size() &&
                    heap.get(leftChildIndex).compareTo(heap.get(largest)) > 0){

                largest = leftChildIndex;
            }
            if(rightChildIndex< heap.size() &&
                    heap.get(rightChildIndex).compareTo(heap.get(largest)) > 0){

                largest = rightChildIndex;
            }
            if (largest != index) {
                T temp = heap.get(index);
                heap.set(index, heap.get(largest));
                heap.set(largest, temp);
                index = largest;
            } else {
                break;
            }
        }
        return max;
    }

    public void clear() {
        heap.clear();
    }
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public ArrayList<T> getHeap() {
        return heap;
    }

    public static void main(String[] args) {
        ArrayHeap<Integer> heap = new ArrayHeap<>();
        heap.add(1);
        heap.add(2);
        heap.add(3);
        heap.add(4);
        heap.add(5);
        heap.add(6);
        heap.add(7);

        System.out.println(heap.maximum());
        System.out.println(heap.maximum());
        System.out.println(heap.maximum());
        System.out.println(heap.maximum());


    }
}
