package com.web.bus.records;


import org.springframework.security.core.parameters.P;

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

    public void quickSort(BusList list, int left, int right) {
        if (left < right) {
            int sz = right - left + 1;
            int pivotpt = (left + right)/2;
            int i = left;
            int j = right - 1;
            int pivot = (int)list.getList()[pivotpt].getDistance();
            swap(list.getList()[pivotpt], list.getList()[right]);
            pivotpt = right;
            while(i<j) {
                while(i<right-1 && list.getList()[i].getDistance()<pivot) {
                    i++;
                }
                while(j > 0 && list.getList()[j].getDistance()>pivot) {
                    j--;
                }
                if(i<j) {
                    swap(list.getList()[i++], list.getList()[j--]);
                }
            }
            if(i == j && list.getList()[i].getDistance() < list.getList()[pivotpt].getDistance()) {
                i++;
            }
            swap(list.getList()[i], list.getList()[pivotpt]);
            quickSort(list, left, i-1);
            quickSort(list, i+1, right);
        }
    }

    public void swap(Bus bus1, Bus bus2) {
        Bus temp = bus1;
        bus1 = bus2;
        bus2 = temp;
    }
   /* public void quickSort(BusList list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSort(list, low, pivotIndex);
            quickSort(list, pivotIndex + 1, high);
        }
    }

    public int partition(BusList list, int low, int high) {
        int pivot = (int)list.getList()[high].getDistance();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if(list.getList()[j].getDistance() <= pivot) {
                i++;
                Bus temp = list.getList()[i];
                list.getList()[i] = list.getList()[j];
                list.getList()[j] = temp;
            }
        }
        Bus temp = list.getList()[i + 1];
        list.getList()[i + 1] = list.getList()[high];
        list.getList()[high] = temp;
        return i+1;
    }*/

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
        System.arraycopy(this.list, 0, newList, 0, this.list.length);
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

        String[] fileContents = new String[length];

        BusList list = new BusList();

        reader = new BufferedReader(new FileReader("allBuses.txt"));

        for (int i = 0; i < fileContents.length; i++) {
            fileContents[i] = reader.readLine();
            String[] words = fileContents[i].split("/");
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
