package com.web.bus.records;


import java.io.*;

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
        this.maxSize = 0;
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

    public BusList searchCompany(String company, BusList list) {
        BusList companySorted = new BusList();
         for (int i = 0; i < list.getList().length; i++) {
             if (list.getList()[i].getBusID().equalsIgnoreCase(company)) {
                 companySorted.insert(list.getList()[i]);
            }
         }
        return companySorted;
    }

    public BusList searchByStartDestination(String startDestination, BusList list) {
        BusList matchingBusses = new BusList();
        for (int i = 0; i < list.getList().length; i++) {
            if (list.getList()[i].getStartDestination().equalsIgnoreCase(startDestination)) {
                matchingBusses.insert(list.getList()[i]);
            }
        }
        return matchingBusses;
    }

    public BusList searchByEndDestination(String endDestination, BusList list){
        BusList matchingBusses = new BusList();
        for (int i = 0; i < list.getList().length; i++) {
            if (list.getList()[i].getEndDestination().equalsIgnoreCase(endDestination)) {
                matchingBusses.insert(list.getList()[i]);
            }
        }
        return matchingBusses;
    }

    private void increaseSize() {
        this.setMaxSize(maxSize + 1);
        Bus[] newList = new Bus[this.getMaxSize()];
        for (int i = 0; i < this.list.length; i++) {
            newList[i] = this.list[i];
        }
        list = newList;
    }

    public Bus[] getList() {
        return list;
    }

    public void setList(Bus[] list) {
        this.list = list;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public BusList readFileMaster() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("allBuses.txt"));
        int length = 0;
        while(!reader.readLine().equalsIgnoreCase("EOF")) {
            length = length + 1;
        }
        reader.close();

        String fileContents[] = new String[length];

        BusList list = new BusList();

        reader = new BufferedReader(new FileReader("allBuses.txt"));

        for (int i = 0; i < fileContents.length; i++) {
            fileContents[i] = reader.readLine();
            String words [] = fileContents[i].split("/");
            Bus bus = new Bus(words[0], words[1], Integer.parseInt(words[2]), words[3]);
            list.increaseSize();
            list.insert(bus);
        }
        reader.close();

        return list;
    }
    public void writeFileMaster (BusList list) throws IOException {
        PrintWriter writerF = new PrintWriter(new FileWriter("allBuses.txt"));
        for (int i = 0; i < list.getList().length && list.getList()[i] != null; i++) {
            writerF.println(list.getList()[i].toStringFile());
        }
    writerF.println("EOF");
        writerF.close();
    }
}
