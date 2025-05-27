import java.util.ArrayList;
import java.util.List;

public class TrieDictionary <V> implements TrieOperations<V> {
    private TrieNode<V> root;

    private static class TrieNode<V>{
        private V value;
        private char key;
        private TrieNode<V> firstChild;
        private TrieNode<V> nextSibling;

        public TrieNode(char key){
            this.key = key;
            this.value=null;
            this.firstChild = null;
            this.nextSibling = null;
        }
    }

    public TrieDictionary(){
        root = new TrieNode<>('\0');
    }


    @Override
    public V insert(String key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Klucz nie może być null.");
        }
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Klucz nie może być pusty.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Wartość nie może być null.");
        }

        TrieNode<V> currentNode = root;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            TrieNode<V> child = findChildNode(currentNode, ch);
            if (child == null) {
                child = addChildNode(currentNode, ch);
            }
            currentNode = child;
        }

        V oldValue = currentNode.value;
        currentNode.value = value;
        return oldValue;
    }

    private TrieNode<V> addChildNode(TrieNode<V> parentNode, char ch) {
        TrieNode<V> newChild = new TrieNode<>(ch);
        TrieNode<V> prev = null;
        TrieNode<V> current = parentNode.firstChild;

        // miejsce do wstawienia węzła w porządku leksykograficznym
        while (current != null && current.key < ch) {
            prev = current;
            current = current.nextSibling;
        }

        if (prev == null) {
            // Wstaw na początek listy dzieci
            newChild.nextSibling = parentNode.firstChild;
            parentNode.firstChild = newChild;
        } else {
            // Wstaw pomiędzy prev a current
            newChild.nextSibling = current;
            prev.nextSibling = newChild;
        }
        return newChild;
    }

    @Override
    public V search(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Klucz nie może być null.");
        }
        if (key.isEmpty()) {
            return null;
        }

        TrieNode<V> currentNode = root;
        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);

            TrieNode<V> child = findChildNode(currentNode, ch);
            if (child == null) {
                return null;
            }
            currentNode = child;
        }
        return currentNode.value;
    }

    @Override
    public V delete(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Klucz nie może być null.");
        }
        if (key.isEmpty()) {
            return null;
        }

        // Ścieżka węzłów od korzenia do węzła docelowego
        List<TrieNode<V>> path = new ArrayList<>();
        TrieNode<V> currentNode = root;
        path.add(currentNode);

        for (int i = 0; i < key.length(); i++) {
            char ch = key.charAt(i);
            TrieNode<V> child = findChildNode(currentNode, ch);
            if (child == null) {
                return null;
            }
            currentNode = child;
            path.add(currentNode);
        }

        // currentNode to teraz węzeł dla ostatniego znaku klucza
        if (currentNode.value == null) {
            return null;
        }

        V valueToRemove = currentNode.value;
        currentNode.value = null; // "nie-klucz"

        // Węzeł jest zbędny, jeśli .value == null oraz .firstChild == null
        for (int i = path.size() - 1; i > 0; i--) { // i > 0, bo path.get(0) to korzeń pomocniczy
            TrieNode<V> nodeToPotentiallyRemove = path.get(i);
            TrieNode<V> parentOfNode = path.get(i - 1);

            if (nodeToPotentiallyRemove.value == null && nodeToPotentiallyRemove.firstChild == null) {
                // Usuń nodeToPotentiallyRemove z listy dzieci parentOfNode
                removeChildNode(parentOfNode, nodeToPotentiallyRemove.key);
            } else {
                break;
            }
        }
        return valueToRemove;
    }
    private TrieNode<V> findChildNode(TrieNode<V> parentNode, char ch) {
        TrieNode<V> currentChild = parentNode.firstChild;
        while (currentChild != null) {
            if (currentChild.key == ch) {
                return currentChild;
            }
            currentChild = currentChild.nextSibling;
        }
        return null;
    }

    private void removeChildNode(TrieNode<V> parentNode, char ch) {
        TrieNode<V> currentChild = parentNode.firstChild;
        TrieNode<V> prevChild = null;

        while (currentChild != null) {
            if (currentChild.key == ch) {
                // Znaleziono dziecko do usunięcia
                if (prevChild == null) { // Jest to pierwsze dziecko na liście rodzeństwa
                    parentNode.firstChild = currentChild.nextSibling;
                } else {
                    prevChild.nextSibling = currentChild.nextSibling;
                }
                return;
            }
            prevChild = currentChild;
            currentChild = currentChild.nextSibling;
        }
    }



    public void printAllKeysDfs() {
        System.out.println("Klucze w słowniku (DFS):");
        dfsPrintRecursive(root.firstChild, new StringBuilder());
    }

    private void dfsPrintRecursive(TrieNode<V> currentNode, StringBuilder currentPath) {
        if (currentNode == null) {
            return;
        }
        currentPath.append(currentNode.key);
        if (currentNode.value != null) {
            System.out.println(currentPath.toString() + ": " + currentNode.value);
        }
        dfsPrintRecursive(currentNode.firstChild, currentPath);
        currentPath.setLength(currentPath.length() - 1);
        dfsPrintRecursive(currentNode.nextSibling, currentPath);
    }
}
