package com.web.bus.records;


import java.io.*;

/**
 * @author: Daksh & Ashwin
 * Date: Jan. 2023
 * Description: Class to represent bus list object
 * Method List:
 * public BusList()
 * public void insert(Bus bus)
 * public void delete(Bus bus)
 * public void replace(int index, Bus bus)
 * public void quickSort()
 * public void quickSort(int left, int right)
 * public void swap(int i, int j)
 * public int binarySearch(double distance)
 * public BusList searchCompany(String company, BusList list)
 * public BusList searchByStartDestination(String startDestination, BusList list)
 * public BusList searchByEndDestination(String endDestination, BusList list)
 * public void increaseSize()
 * getters and setters
 * public BusList readFileMaster() throws IOException
 * public void writeFileMaster(BusList list) throws IOException
 * public String[] readFilePurchases() throws IOException
 * public void writeFilePurchases(String[] usernames, BusList list) throws IOException
 * public void printList() throws IOException
 * public static void main (String [] args) throws IOException
 **/
public class BusList {
    /*
    Private Instace Data
     */

    private Bus[] list;
    private int maxSize;
    private int currentSize;

    /*
    Default Constructor to create bus list object
     */
    public BusList() {
        this.maxSize = 0;
        this.currentSize = 0;
        this.list = new Bus[maxSize];
    }

    /**
     * Insert Method
     *
     * @param bus the bus to be inserted
     */
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

    /**
     * Delete Method
     *
     * @param bus the bus to be deleted
     */
    public void delete(Bus bus) {
        for (int i = 0; i < currentSize; i++) {
            if (list[i].equals(bus)) {
                for (int j = i; j < currentSize - 1; j++) {
                    list[j] = list[j + 1];
                }
                currentSize--;
                break;
            }
        }
    }

    /**
     * Replace bus at index with bus
     *
     * @param index the index of the bus to be replaced
     * @param bus   the bus to replace the bus at index
     */
    public void replace(int index, Bus bus) {
        if (index >= 0 && index < currentSize) {
            list[index] = bus;
        }
    }

    /**
     *Quicksort list
     */
    public void quickSort() {
        quickSort( 0, currentSize-1);
    }

    /**
     * Quick sort helper method
     *
     * @param left  the left index
     * @param right the right index
     */
    public void quickSort(int left, int right) {
        if (left < right) {
            int sz = right - left + 1;
            int pivotpt = (left + right)/2;
            int i = left;
            int j = right - 1;
            int pivot = (int)list[pivotpt].getDistance();
            swap(pivotpt, right);
            pivotpt = right;
            while(i<j) {
                while(i<right-1 && list[i].getDistance()<pivot) {
                    i++;
                }
                while(j > 0 && list[j].getDistance()>pivot) {
                    j--;
                }
                if(i<j) {
                    swap(i++, j--);
                }
            }
            if(i == j && list[i].getDistance() < list[pivotpt].getDistance()) {
                i++;
            }
            swap(i, pivotpt);
            quickSort(left, i-1);
            quickSort(i+1, right);
        }
    }

    /**
     * Swap two elements in the list
     *
     * @param i the index of the first element
     * @param j the index of the second element
     */
    public void swap(int i, int j) {
        Bus temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }


    /**
     * Binary Search
     *
     * @param distance the distance to be searched for
     * @return the index of the distance
     */
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

    /**
     * Search by company
     * @param company the company to be searched for
     * @param list the list to be searched
     * @return the list of busses with the company
     */
    public BusList searchCompany(String company, BusList list) {
        BusList companySorted = new BusList();
        for (int i = 0; i < list.getList().length; i++) {
            if (list.getList()[i].getBusID().equalsIgnoreCase(company)) {
                companySorted.insert(list.getList()[i]);
            }
        }
        return companySorted;
    }

    /**
     * Search by start destination
     * @param startDestination the start destination to be searched for
     * @param list the list to be searched
     * @return the list of busses with the start destination
     */
    public BusList searchByStartDestination(String startDestination, BusList list) {
        BusList matchingBusses = new BusList();
        for (int i = 0; i < list.getList().length; i++) {
            if (list.getList()[i].getStartDestination().equalsIgnoreCase(startDestination)) {
                matchingBusses.insert(list.getList()[i]);
            }
        }
        return matchingBusses;
    }

    /**
     * Search by end destination
     * @param endDestination the end destination to be searched for
     * @param list the list to be searched
     * @return the list of busses with the end destination
     */
    public BusList searchByEndDestination(String endDestination, BusList list) {
        BusList matchingBusses = new BusList();
        for (int i = 0; i < list.getList().length; i++) {
            if (list.getList()[i].getEndDestination().equalsIgnoreCase(endDestination)) {
                matchingBusses.insert(list.getList()[i]);
            }
        }
        return matchingBusses;
    }

    /**
     * Increase the size of the list
     */
    public void increaseSize() {
        this.setMaxSize(maxSize + 1);
        Bus[] newList = new Bus[this.getMaxSize()];
        System.arraycopy(this.list, 0, newList, 0, this.list.length);
        list = newList;
    }

    /*
    Getters and Setters
     */
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

    /**
     * Read the file and create a list of busses
     * @return the list of busses
     * @throws IOException
     */
    public BusList readFileMaster() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("allBuses.txt"));
        int length = 0;
        while (!reader.readLine().equalsIgnoreCase("EOF")) {
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

    /**
     * Write the list of busses to a file
     * @param list the list of busses
     * @throws IOException
     */
    public void writeFileMaster(BusList list) throws IOException {
        PrintWriter writerF = new PrintWriter(new FileWriter("allBuses.txt"));
        for (int i = 0; i < list.getList().length && list.getList()[i] != null; i++) {
            writerF.println(list.getList()[i].toStringFile());
        }
        writerF.println("EOF");
        writerF.close();
    }

    /**
     * Read the file and create a list of usernames
     * @return the list of usernames
     * @throws IOException
     */
    public String[] readFilePurchases() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("allPurchases.txt"));
        int length = 0;
        while (!reader.readLine().equalsIgnoreCase("EOF")) {
            length = length + 1;
        }
        reader.close();

        String[] fileContents = new String[length];

        reader = new BufferedReader(new FileReader("allPurchases.txt"));

        for (int i = 0; i < fileContents.length; i++) {
            fileContents[i] = reader.readLine();
        }
        reader.close();

        return fileContents;
    }

    /**
     * Write the list of usernames to a file
     * @param usernames the list of usernames
     * @param list the list of busses
     * @throws IOException
     */
    public void writeFilePurchases(String[] usernames, BusList list) throws IOException {
        PrintWriter writerF = new PrintWriter(new FileWriter("allPurchases.txt"));
        for (int i = 0; i < list.getList().length && list.getList()[i] != null; i++) {
            writerF.println(usernames[i] + "/" + list.getList()[i].toStringFile());
        }
        writerF.println("EOF");
        writerF.close();
    }

    /**
     * Print list method
     */
    public void printList() throws IOException {
        for (Bus bus : list) {
            if (bus != null) {
                System.out.println(bus.toString());
            }
        }
    }

    /**
     *  Self Testing Main
     * @param args command line arguments
     * @throws IOException if file not found
     */
    public static void main(String[] args) throws IOException {
        BusList busList = new BusList();
        Bus bus1 = new Bus("New York City", "Los Angeles", 50, "NYC-LA-01");
        Bus bus2 = new Bus("Chicago", "Houston", 40, "CHI-HOU-01");
        Bus bus3 = new Bus("Boston", "San Francisco", 45, "BOS-SF-01");

        // Test Insert method
        busList.insert(bus1);
        busList.insert(bus2);
        busList.insert(bus3);

        System.out.println("Test Insert Method:");
        busList.printList();

        // Test Delete method
        busList.delete(bus2);

        System.out.println("Test Delete Method:");
        busList.printList();

        // Test Replace method
        Bus bus4 = new Bus("Houston", "New York City", 40, "HOU-NYC-01");
        busList.replace(0, bus4);

        System.out.println("Test Replace Method:");
        busList.printList();

        // Test quickSort method
        busList.quickSort();

        System.out.println("Test quickSort Method:");
        busList.printList();

        // Test binarySearch method
        System.out.println("Test binarySearch Method:");
        System.out.println(busList.binarySearch(4200));

        // Test searchCompany method
        System.out.println("Test searchCompany Method:");
        BusList companyList = busList.searchCompany("Greyhound", busList);
        companyList.printList();

        // Test searchByStartDestination method
        System.out.println("Test searchByStartDestination Method:");
        BusList startDestinationList = busList.searchByStartDestination("Houston", busList);
        startDestinationList.printList();

        // Test searchByEndDestination method
        System.out.println("Test searchByEndDestination Method:");
        BusList endDestinationList = busList.searchByEndDestination("New York City", busList);
        endDestinationList.printList();
    }
}
