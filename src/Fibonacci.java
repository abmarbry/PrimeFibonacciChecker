import java.util.concurrent.atomic.AtomicBoolean;

public class Fibonacci implements Runnable {
	private final int HALF_SECOND = 500;
	private int fiboTo;
	private int currentIndex = 1;
	RingBuffer buffer;
	AtomicBoolean fiboDone;
	
	public Fibonacci(int fiboTo, RingBuffer buffer, AtomicBoolean fiboDone) {
		this.fiboTo = fiboTo;
		this.buffer = buffer;
		this.fiboDone = fiboDone;
	}
	
	public void run() {
		while(currentIndex <= fiboTo) {
			try {
				Thread.sleep((long)(Math.random()*HALF_SECOND));
				if(isFibonacci(currentIndex)) {
					buffer.blockingPut(currentIndex);
				}
				currentIndex++;
			}
			catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			} 
		
		}
		fiboDone.set(true);
	}
	
	public boolean isFibonacci(int num) {
		/*"A number of Fibonacci iff one/both of (5(n^2) + 4) or/and (5(n^2)+4) is/are 
		 *a perfect square"*/
		boolean isFibonacci = false;
		int fibo = 5*(num*num) + 4;
		int fiboTwo = 5*(num*num) - 4;
		if(isPerfectSquare(fibo) || isPerfectSquare(fiboTwo)) {
			isFibonacci = true;
		}
		return isFibonacci;
		
	}
	
	private boolean isPerfectSquare(int num) {
		int squaredNum = (int) Math.sqrt((double)num);
		boolean perfSq = (squaredNum*squaredNum) == (double)num;
		return perfSq;
	}

}
