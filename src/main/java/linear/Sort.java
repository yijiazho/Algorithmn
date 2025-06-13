package linear;

public class Sort {
    

    public void sort(int[] input, SortMethod sortMethod) {
        if (input == null || input.length == 0) {
            throw new IllegalArgumentException("Input Array should contain at least one element");
        }

        switch (sortMethod) {
            case QUICKSORT:
                quickSort(input, 0, input.length - 1);
                break;
            case MERGESORT:
                mergeSort(input, 0, input.length - 1);
                break;
            default:
                throw new RuntimeException("Sorting method not supported");
        }
    }



    private void quickSort(int[] input, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivotIndex = partition(input, start, end);
        quickSort(input, start, pivotIndex - 1);
        quickSort(input, pivotIndex + 1, end);
    }

    private int partition(int[] input, int start, int end) {
        int pivot = input[end];
        int firstLargerThanPivotIndex = start;

        for (int index = start; index < end; index++) {
            if (input[index] < pivot) {
                swap(input, index, firstLargerThanPivotIndex);
                firstLargerThanPivotIndex++;
            }
        }

        swap(input, end, firstLargerThanPivotIndex);
        return firstLargerThanPivotIndex;
    }

    private void swap(int[] input, int i, int j) {
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }

    private void mergeSort(int[] input, int start, int end) {
        if (start >= end) {
            return;
        }
 
        int mid = start + (end - start) / 2;
        mergeSort(input, start, mid);
        mergeSort(input, mid + 1, end);
        merge(input, start, mid, end);
    }

    private void merge(int[] input, int start, int mid, int end) {
        int n1 = mid - start + 1;
        int n2 = end - (mid + 1) + 1;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = input[i + start];
        }
        for (int i = 0; i < n2; i++) {
            rightArray[i] = input[mid + 1 + i];
        }

        int i = 0;
        int j = 0;
        int index = start;
        while (i < n1 && j  < n2) {
            if (leftArray[i] <= rightArray[j]) {
                input[index] = leftArray[i];
                i++;
            } else {
                input[index] = rightArray[j];
                j++;
            }
            index++;
        }

        while (i < n1) {
            input[index] = leftArray[i];
            i++;
            index++;
        }

        while (j < n2) {
            input[index] = rightArray[j];
            j++;
            index++;
        }
    }
}

enum SortMethod {
    QUICKSORT,
    MERGESORT
}