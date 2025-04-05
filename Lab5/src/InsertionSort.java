import core.AbstractSwappingSortingAlgorithm;

import java.util.Comparator;
import java.util.List;

public class InsertionSort<T> extends AbstractSwappingSortingAlgorithm<T> {
    public InsertionSort(Comparator comparator) {
        super(comparator);
    }

    @Override
    public List<T> sort(List<T> list) {
        int size = list.size();

        for (int i = 1; i < size; i++) {
            T element = list.get(i);
            int insertIndex = binarySearch(list, element, 0, i - 1);
            for (int j = i; j > insertIndex; j--) {
                swap(list, j, j - 1);
            }
        }
        return list;
    }

    public int binarySearch(List<T> list, T element, int left, int right) {
        while (left <= right) {
            int mid = (left + right) / 2;
            int compare = compare(element, list.get(mid));

            if (compare < 0) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}

