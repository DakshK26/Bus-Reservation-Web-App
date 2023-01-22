package com.web.bus.records;


public class BusList{
    /*
    Private Instace Data
     */

    private Bus[] list;
    private int maxSize;
    private int currentSize;
    /*
    Default Constructor
     */
    public BusList() {
        this.maxSize = 10;
        this.currentSize = 0;
        this.list = new Bus[maxSize];
    }

    public void insert(Bus bus) {
        if (currentSize < maxSize) {
            list[currentSize] = bus;
            currentSize++;
        } else {
            increaseSize();
            list[currentSize] = bus;
            currentSize++;
        }
    }

    public void delete(int index) {
        if (index >= 0 && index < currentSize) {
            for (int i = index; i < currentSize - 1; i++) {
                list[i] = list[i + 1];
            }
            currentSize--;
        }
    }

    public void replace(int index, Bus bus) {
        if (index >= 0 && index < currentSize) {
            list[index] = bus;
        }
    }

    public void quicksort(int low, int high) {
        if (low < high) {
            int pivot = partition(low, high);
            quicksort(low, pivot);
            quicksort(pivot + 1, high);
        }
    }

    private int partition(int low, int high) {
        Bus pivot = list[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if (list[j].getDistance() < pivot.getDistance()) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void swap(int i, int j) {
        Bus temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    public int binarySearch(double distance) {
        int low = 0;
        int high = currentSize - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (list[mid].getDistance() == distance) {
                return mid;
            } else if (list[mid].getDistance() < distance) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    private void increaseSize() {
        maxSize = maxSize * 2;
        Bus[] newList = new Bus[maxSize];
        if (currentSize >= 0) System.arraycopy(list, 0, newList, 0, currentSize);
        list = newList;
    }
}