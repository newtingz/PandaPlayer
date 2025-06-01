package application;

import java.util.concurrent.ArrayBlockingQueue;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ImageQeue {

	 ThreadPoolExecutor pool = new ThreadPoolExecutor(
			    2,                                     // keep at least two thread ready,
			                                           // even if no Runnables are executed
			    4,                                     // at most five Runnables/Threads
			                                           // executed in parallel
			    5, TimeUnit.MINUTES,                   // idle Threads terminated after one
			                                           // minute, when min Pool size exceeded
			    new ArrayBlockingQueue<Runnable>(2),r ->{
					Thread t=new Thread(r);
					//r.
				//	t.setName("ImageLoader");
				//	t.setDaemon(true);
					return t;

				},new ThreadPoolExecutor.DiscardOldestPolicy());
    final Lock lock = new ReentrantLock();

    // Conditions
    final Condition produceCond  = lock.newCondition();
    final Condition consumeCond = lock.newCondition();

    // Array to store element for CustomBlockingQueue
    final Runnable[] array = new Runnable[20];
    int putIndex, takeIndex;
    int count;

    public void put(Runnable x) throws InterruptedException {

       // lock.lock();
        try {
            while (count == array.length){
                // Queue is full, producers need to wait
            	//array.
             //   produceCond.await();
            }

            array[putIndex] = x;
            System.out.println("Producing - " + x);
            putIndex++;
            if (putIndex == array.length){
                putIndex = 0;
            }
            // Increment the count for the array
            ++count;
         //   consumeCond.signal();
        } finally {
        //    lock.unlock();
        }
    }

    public Runnable take() throws InterruptedException {
      //  lock.lock();
        try {
            while (count == 0){
                // Queue is empty, consumers need to wait
               // consumeCond.await();
            }
            Runnable x = array[takeIndex];
           // Platform.runLater(() -> {
            pool.execute(x);
	//	});
            System.out.println("Purring Images - " + x);
            takeIndex++;
            if (takeIndex == array.length){
                takeIndex = 0;
            }
            // reduce the count for the array
            --count;
            // send signal producer
          //  produceCond.signal();
            return x;
        } finally {
      //      lock.unlock();
        }
    }

    public boolean isEmpty() {
    	boolean result=false;
    	if(array.length==0) {
    		result=true;

    	}else {

    		result=false;
    	}



    	return result;

    }
}
