import java.util.ArrayList;
import java.util.NoSuchElementException;

public class TreeArrayBinaryHeap <T extends Comparable<T>>{
    private int maxHeight;
    private TreeNode root;
    private int size;

    public TreeArrayBinaryHeap(int maxHeight){
        this.maxHeight = maxHeight;
        size = 0;
    }
    public TreeArrayBinaryHeap(){}

    private class TreeNode{
        private TreeNode left;
        private TreeNode right;
        private T value;
        private ArrayHeap <T> subHeap;

        public TreeNode(T value) {
            this.value = value;
        }
    }

    public int calculateHeight(int size){
        return  (int) (Math.log(size + 1) / Math.log(2));
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getParentIndex(int index) {
        return (index - 1) / 2;
    }
    public int getLeftChildIndex(int index) {
        return (index * 2) + 1;
    }

    public int getRightChildIndex(int index) {
        return (index * 2) + 2;
    }

        //debug
        public void getLeft(){
            System.out.println(root.left.value);
        }
         public void getRight(){
        System.out.println(root.right.value);
        }
        public void getRoot(){
        System.out.println(root.value);
        }



    public static void main(String[] args) {
        TreeArrayBinaryHeap<Integer> treeArrayBinaryHeap = new TreeArrayBinaryHeap<>(1);
    }
}
