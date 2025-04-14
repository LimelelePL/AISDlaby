import core.AbstractSortingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSort<T> extends AbstractSortingAlgorithm<T> {

    public MergeSort(Comparator<? super T> comparator) {
        super(comparator);
    }

    @Override
    public List<T> sort(List<T> list) {
        if (list == null || list.size() <= 1) {
            return list;
        }
        mergeSort(list);
        return list;
    }

    private void mergeSort(List<T> list) {
        int n = list.size();
        if (n < 2) {
            return;
        }
        if (n < 3) {
           //dla małych fragmentów musimy zastosowac merge sort dla dwóch
            int mid = n / 2;
            List<T> left = new ArrayList<>(list.subList(0, mid));
            List<T> right = new ArrayList<>(list.subList(mid, n));
            mergeSort(left);
            mergeSort(right);
            list.clear();
            mergeTwoLists(list, left, right);
            return;
        }

        int firstSize = n / 3;
        int secondSize = n / 3;
        int thirdSize = n - firstSize - secondSize;

        List<T> first = new ArrayList<>(list.subList(0, firstSize));
        List<T> second = new ArrayList<>(list.subList(firstSize, firstSize + secondSize));
        List<T> third = new ArrayList<>(list.subList(firstSize + secondSize, n));

        mergeSort(first);
        mergeSort(second);
        mergeSort(third);
        list.clear();
        mergeThreeLists(list, first, second, third);
    }

    private void mergeTwoLists(List<T> output, List<T> list1, List<T> list2) {
        mergeTwoLists(output, list1, 0, list2, 0);
    }

    private void mergeTwoLists(List<T> output, List<T> list1, int start1, List<T> list2, int start2) {
        int i = start1, j = start2;
        int size1 = list1.size();
        int size2 = list2.size();

        while (i < size1 && j < size2) {
            if (compare(list1.get(i), list2.get(j)) <= 0) {
                output.add(list1.get(i));
                i++;
            } else {
                output.add(list2.get(j));
                j++;
            }
        }
        while (i < size1) {
            output.add(list1.get(i));
            i++;
        }
        while (j < size2) {
            output.add(list2.get(j));
            j++;
        }
    }

    private void mergeThreeLists(List<T> output, List<T> list1, List<T> list2, List<T> list3) {
        int i = 0, j = 0, k = 0;
        int size1 = list1.size();
        int size2 = list2.size();
        int size3 = list3.size();

        // jezeli wszystkie 3 listy maja elementy
        while (i < size1 && j < size2 && k < size3) {
            T val1 = list1.get(i);
            T val2 = list2.get(j);
            T val3 = list3.get(k);

            if (compare(val1, val2) <= 0 && compare(val1, val3) <= 0) {
                output.add(val1);
                i++;
            } else if (compare(val2, val1) <= 0 && compare(val2, val3) <= 0) {
                output.add(val2);
                j++;
            } else {
                output.add(val3);
                k++;
            }
        }

        // gdy jedna jest wyczerpana to merge dla dwóch list
        if (i == size1) {
            mergeTwoLists(output, list2, j, list3, k);
        } else if (j == size2) {
            mergeTwoLists(output, list1, i, list3, k);
        } else if (k == size3) {
            mergeTwoLists(output, list1, i, list2, j);
        }
    }
}

