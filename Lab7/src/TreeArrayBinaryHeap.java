import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class TreeArrayBinaryHeap<T extends Comparable<T>> {
    private int maxHeight;
    private TreeNode root;
    private int size;

    public TreeArrayBinaryHeap(int maxHeight) {
        this.maxHeight = maxHeight;
        size = 0;
    }

    public TreeArrayBinaryHeap() {
    }

    public class TreeNode {
        private TreeNode left;
        private TreeNode right;
        private T value;
        private ArrayHeap<T> subHeapLeft, subHeapRight;

        public TreeNode(T value) {
            this.value = value;
        }
    }

    public void clearTree() {
        root = null;
        size = 0;
    }

    public T maxFromTree() {
        if (size == 0)
            throw new NoSuchElementException();

        T result = root.value;
        int lastIndex = size - 1;
        TreeNode lastNode = getNode(lastIndex);
        root.value = lastNode.value;

        // odłączamy ten ostatni węzeł od drzewa
        if (lastIndex != 0) {
            int parentIdx = getParentIndex(lastIndex);
            TreeNode parent = getNode(parentIdx);
            if (getLeftChildIndex(parentIdx) == lastIndex) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        }
        size--;

        //heapify down
        TreeNode current = root;
        while (current.left != null && current.right != null) {
            TreeNode left = current.left;
            TreeNode right = current.right;

            TreeNode biggerChild;
            if (left.value.compareTo(right.value) >= 0) {
                biggerChild = left;
            } else {
                biggerChild = right;
            }

            if (biggerChild.value.compareTo(current.value) > 0) {
                swapValues(current, biggerChild);
                current = biggerChild;
            } else {
                break;
            }
        }

        return result;
    }

    public void addToTree(T value) {
        TreeNode newNode = new TreeNode(value);
        int index = size++;

        if (index == 0) {
            root = newNode;
            return;
        }

        ArrayList<Integer> pathBits = calculateTrack(index);
        Collections.reverse(pathBits);

//idziemy do rodzica i zbieramy po drodze wezly bez ostateniago
        ArrayList<TreeNode> nodesOnPath = new ArrayList<>();
        TreeNode current = root;
        nodesOnPath.add(current);
        for (int i = 0; i < pathBits.size() - 1; i++) {
            if (pathBits.get(i) == 0) {
                current = current.left;
            } else {
                current = current.right;
            }
            nodesOnPath.add(current);
        }

        //dodajemy nowy wezel
        TreeNode lastParent = nodesOnPath.getLast();
        if (pathBits.getLast() == 0) {
            lastParent.left = newNode;
        } else {
            lastParent.right = newNode;
        }
        nodesOnPath.add(newNode);

        //idziemy wezlami do gory
        for (int i = nodesOnPath.size() - 1; i > 0; i--) {
            TreeNode child = nodesOnPath.get(i);
            TreeNode parent = nodesOnPath.get(i - 1);
            if (parent.value.compareTo(child.value) < 0) {
                swapValues(parent, child);
            } else {
                break;
            }
        }
    }

    //metody pomocnocze
    private int calculateHeight(int size) {
        return (int) (Math.log(size + 1) / Math.log(2));
    }


    private void swapValues(TreeNode a, TreeNode b) {
        T tmp = a.value;
        a.value = b.value;
        b.value = tmp;
    }

    private TreeNode getNode(int index) {
        if (size == 0) throw new NoSuchElementException();
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        if (index == 0) return root;

        ArrayList<Integer> path = calculateTrack(index);
        TreeNode node = root;

        for (int i = path.size() - 1; i >= 0; i--) {
            if (path.get(i) == 0) {
                node = node.left;
            } else {
                node = node.right;
            }
            if (node == null)
                throw new NoSuchElementException("Drzewo ma niepełną gałąź");
        }
        return node;
    }

    private ArrayList<Integer> calculateTrack(int index) {
        ArrayList<Integer> binaryRepresentation = new ArrayList<>();
        int k = index + 1;
        while (k > 1) {
            binaryRepresentation.add(k % 2);
            k /= 2;
        }
        return binaryRepresentation;
    }

    private int getMaxHeight() {
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
    public void getLeft(int i) {
        for (int x = 0; x < i; x++) {
            System.out.println(root.left.value);
        }
    }

    public void getRight(int i) {
        System.out.println(root.right.value);
    }

    public void getRoot() {
        System.out.println(root.value);
    }

    public T getAtIdx(int i) {
        System.out.println(getNode(i).value);
        return getNode(i).value;
    }


    public static void main(String[] args) {
        TreeArrayBinaryHeap<Integer> treeArrayBinaryHeap = new TreeArrayBinaryHeap<>(10);
//        ArrayList<Integer> binaryRepresentation = treeArrayBinaryHeap.calculateTrack(12);
//
//        for (int i = 0; i < binaryRepresentation.size(); i++) {
//            System.out.println(binaryRepresentation.get(i));
//        }

        treeArrayBinaryHeap.addToTree(1);
        treeArrayBinaryHeap.addToTree(2);
        treeArrayBinaryHeap.addToTree(3);
        treeArrayBinaryHeap.addToTree(4);
        treeArrayBinaryHeap.addToTree(43);
        treeArrayBinaryHeap.addToTree(5);

        treeArrayBinaryHeap.getAtIdx(0);
        treeArrayBinaryHeap.getAtIdx(1);
        treeArrayBinaryHeap.getAtIdx(2);
        treeArrayBinaryHeap.getAtIdx(3);
        treeArrayBinaryHeap.getAtIdx(4);
        treeArrayBinaryHeap.getAtIdx(5);

        treeArrayBinaryHeap.maxFromTree();
        System.out.println();


        treeArrayBinaryHeap.getAtIdx(0);
        treeArrayBinaryHeap.getAtIdx(1);
        treeArrayBinaryHeap.getAtIdx(2);
        treeArrayBinaryHeap.getAtIdx(3);
        treeArrayBinaryHeap.getAtIdx(4);

    }
}
