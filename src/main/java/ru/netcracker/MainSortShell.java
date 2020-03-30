package ru.netcracker;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainSortShell {

    public static int[] setup() {
        System.out.print("Введите количество элементов массива : ");
        Scanner in = new Scanner(System.in);
        int count = in.nextInt();

        int[] arr = new int[count];

        for ( int i = 0 ; i < count ; i++ ) {
            arr[i] = (int) (Math.random() * 1000);
        }

        return arr;
    }


    public static int[] merge(int[] arr, Map<Integer, Integer> remainder, final int step) {

        int[] res = new int[arr.length];
        final Map<Integer, Integer> commit = Map.copyOf(remainder);
        for (int i = 0; i < arr.length; i++ ) {
            int min = Integer.MAX_VALUE;
            int section = -1;
            for ( Integer row : remainder.keySet() ) {
                if ( remainder.get(row) != 0 && arr[row * step + (commit.get(row) - remainder.get(row))] < min ) {

                    section = row;
                    min = arr[row * step + (commit.get(row) - remainder.get(row))];
                }
            }
            res[i] = arr[section * step + (commit.get(section) - remainder.get(section))];
            remainder.put(section, remainder.get(section) - 1);
        }

        return  res;
    }


    public static void main( String[] args ) throws Exception {

        //массив для сортировки
        int[] arr = setup();
        int[] arr2 = arr.clone();
        //количесво потоков
        int count = 4;


        Collection<Future> futures = new LinkedList<Future>();
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        int step = arr.length / count;
        long start = System.currentTimeMillis();
        Map<Integer, Integer> remainder = new HashMap<Integer, Integer>();
        for (int i = 0 ; i < count ; i++) {
            int lenght;
            if ( i == count - 1 )
                lenght = arr.length - ( step  * (count - 1) );
            else
                lenght = step;
            remainder.put(i, lenght);
            futures.add(executorService.submit(new MulSortShella(arr, i*step, lenght, i * 1000)));
        }

        for (Future f : futures) {
            while ( !f.isDone());
        }
        executorService.shutdown();
        int[] res = merge(arr, remainder ,step);

        long end = System.currentTimeMillis();

        System.out.println("Within concurrence : " + (end - start));

        start = System.currentTimeMillis();

        sort(arr2);

        end = System.currentTimeMillis();

        System.out.println("Without concurrence : " + (end - start));

    }


    public static void sort(int[] array) {
        int length = array.length;
        int semi = length / 2;
        while (semi > 0) {
            for (int i = 0; i < length - semi; i++) {
                int j = i;
                while ((j >= 0) && array[j] > array[j + semi]) {
                    int temp = array[j];
                    array[j] = array[j + semi];
                    array[j + semi] = temp;
                    j--;
                }
            }
            semi /= 2;
        }
    }

}
