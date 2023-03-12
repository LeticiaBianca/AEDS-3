import java.io.IOException;


class StartHeap {
    // create the heapsort array
    HeapSort[] array;
    int heapSize;
    // initialyze the array
    StartHeap(HeapSort a[], int size) {
        heapSize = size;
        array = a;
        int i = (heapSize - 1) / 2;
        while (i >= 0) {
            startHeapify(i);
            i--;
            
        }
    }
    // define the left side of the array
    int left(int i) {
        return (2 * i + 1);
    }
    // define th e right side of the array
    int right(int i) {
        return (2 * i + 2);
    }
 
    HeapSort getStart() {
        return array[0];
    }
    
    void replaceStart(HeapSort x) {
        array[0] = x;
        startHeapify(0);
    }
 
    void startHeapify(int i) {
        int left = left(i);
        int right = right(i);
        int smallest = i;
        // seeks along the array to define the sort
        if (left < heapSize && array[left].pos < array[i].pos)
            smallest = left;
            
            if (right < heapSize && array[right].pos < array[smallest].pos)
            smallest = right;
            
            if (smallest != i) {
                swap(array, i, smallest);
                startHeapify(smallest);
            }
        }
        
        // to swap two start heap nodes
        void swap(HeapSort array[], int lastPos, int nextPos) {
            HeapSort temp = array[lastPos];
            array[lastPos] = array[nextPos];
            array[nextPos] = temp;
        }
    }
    class HeapSort {
        int pos;
        int i;
        public HeapSort() {
    
        }
        public void sort() throws IOException{
            MergeSort sort = new MergeSort();
            sort.sort();
        }
    }
    // method used to get the heapsort end translate to bytes
class ExternMergeSort {
    void merge(int array[], int left, int mid, int right) {
        int i, j, k;
        int n1 = mid - left + 1;
        int n2 = right - mid;
 
        int L[] = new int[n1];
        int R[] = new int[n2];
 
        for (i = 0; i < n1; i++)
            L[i] = array[left + i];
        for (j = 0; j < n2; j++)
            R[j] = array[mid + 1 + j];
 
        i = 0;
        j = 0;
        k = left;
 
        while (i < n1 && j < n2) {
            if (L[i] <= R[j])
                array[k++] = L[i++];
            else
                array[k++] = R[j++];
        }
 
        while (i < n1)
            array[k++] = L[i++];
 
        while (j < n2)
            array[k++] = R[j++];
    }
    // recursive method to walk on teh array
    void mergeSort(int array[], int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }
}