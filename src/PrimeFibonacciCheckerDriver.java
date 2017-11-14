import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrimeFibonacciCheckerDriver {
 	
	public static void main(String args[]) {
		try {
			AtomicBoolean primeDone = new AtomicBoolean(false);
			AtomicBoolean fiboDone = new AtomicBoolean(false);
			
			
			Scanner in = new Scanner (System.in);
			System.out.println("Prime Fibonacci Checker: Please enter up to what integer "
					+ "you want to check.");
			int checkedInt = in.nextInt();
			
			System.out.println("What size do you want your buffer to be?");
			int size = in.nextInt();
			
			RingBuffer buffer = new RingBuffer(size);
	
			ExecutorService executorservice = Executors.newCachedThreadPool();
			
			Prime prime = new Prime(checkedInt, buffer, primeDone);
			Fibonacci fibo = new Fibonacci(checkedInt, buffer, fiboDone);
			Checker checker = new Checker(checkedInt, buffer, primeDone, fiboDone);
			
			executorservice.execute(prime);
			executorservice.execute(fibo);
			executorservice.execute(checker);
			
			in.close();
			executorservice.shutdown();
			executorservice.awaitTermination(1, TimeUnit.MINUTES);
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		catch(InputMismatchException m) {
			System.out.println("Error: Incorrect Input! Terminating...");
		}
	}
}
