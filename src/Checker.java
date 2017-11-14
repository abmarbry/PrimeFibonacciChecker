import java.util.concurrent.atomic.AtomicBoolean;

public class Checker implements Runnable {
	private final int HALF_SECOND = 500;
	private int doubleIndex;
	private int inputIndex;
	private boolean[] input;
	RingBuffer buffer;
	AtomicBoolean primeDone;
	AtomicBoolean fiboDone;
	
	public Checker(int checkedInt, RingBuffer buffer, AtomicBoolean primeDone, AtomicBoolean fiboDone) {
		input = new boolean[checkedInt];
		doubleIndex = 0; 
		inputIndex = 0;
		this.buffer = buffer;
		this.primeDone = primeDone;
		this.fiboDone = fiboDone;
	}
	
	public void run() {
		System.out.printf("Fibonacci and Prime Numbers: ");
		while((!primeDone.get() && !fiboDone.get()) || buffer.hasCellsLeft()) {
			try {
				Thread.sleep((long)(Math.random()*HALF_SECOND));
				if(buffer.hasCellsLeft()) {
					inputIndex = buffer.blockingGet();
					if(input[inputIndex-1]) {
						System.out.printf(" " + inputIndex);
						doubleIndex++;
					}
					else {
						input[inputIndex-1] = true;
					}
				}
			}
			catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		System.out.println("\nThere were " + doubleIndex + " Fibonacci and Prime numbers.");
	}
	
}
