import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TreeArrayBinaryHeap <T extends Comparable<T>>{
    private int maxHeight;

    public TreeArrayBinaryHeap(int maxHeight){
        this.maxHeight = maxHeight;
    }

    private class TreeNode{
        private TreeNode left;
        private TreeNode right;
        private TreeNode root;
        private T value;
        private int currentSize;
        private ArrayHeap subHeap;

        public TreeNode(T value) {
            this.value = value;
            currentSize=0;
        }

    }

    private class ArrayHeap{
        private ArrayList<T> heap;

        public ArrayHeap() {
            heap = new ArrayList<>();
        }

        private int getParentIndex(int index) {
            if(index < 0 || index >= heap.size()){
                throw new IndexOutOfBoundsException();
            }
            return (index - 1) / 2;
        }

        private int getLeftChildIndex(int index) {
            if (hasLeftChild(index)) {
                return (index * 2) + 1;
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        private int getRightChild(int index) {
           if(hasRightChild(index)) {
               return (index * 2) + 2;
           } else {
               throw new IndexOutOfBoundsException();
           }
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
                int rightChildIndex = getRightChild(index);

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

        public void clear(){
            heap.clear();
        }

    }
}
