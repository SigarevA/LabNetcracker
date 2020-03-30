package ru.netcracker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MulSortShella implements Runnable {

    private int test;
    private int[] arr;
    private int start;
    private int lenght;


    public MulSortShella(int[] arr, int start, int lenght, int test) {
        this.arr = arr;
        this.start = start;
        this.lenght = lenght;
        this.test = test;
    }

    public void run() {
        sortShella2();
    }


    public void sortShella2() {
        int semi = this.lenght / 2;
        while (semi > 0) {
            for (int i = 0; i < this.lenght - semi; i++) {
                int j = i;
                while ((j >= 0) && arr[start + j] > arr[start +j + semi]) {
                    int temp = arr[start +j];
                    arr[start + j] = arr[start +j + semi];
                    arr[start + j + semi] = temp;
                    j--;
                }
            }
            semi /= 2;
        }
    }


    public void sortShella() throws InterruptedException {
        int d = this.lenght / 2;
        while( d >= 1 ) {
            for ( int i = 0; i < this.lenght - d; i++ ) {
                for (int j = i; j < this.lenght - d; j+=d)
                {
                    if ( arr[start + j] > arr[start + j + d] ) {
                        int temp = arr[start + j];
                        arr[ start +  j] = arr[start + j + d];
                        arr[start + j + d] = temp;
                    }
                }
            }
            d /= 2;
        }
    }

}
