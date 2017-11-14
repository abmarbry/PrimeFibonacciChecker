import java.util.concurrent.atomic.AtomicBoolean;

public class Prime implements Runnable{
	private final int HALF_SECOND = 500;
	private int primeTo;
	private int currentIndex = 2;
	RingBuffer buffer;
	AtomicBoolean primeDone;
	
	public Prime(int primeTo, RingBuffer buffer, AtomicBoolean primeDone) {
		this.primeTo = primeTo;
		this.buffer = buffer;
		this.primeDone = primeDone;
	}
	
	public void run() {
		while(currentIndex <= primeTo) {
				try {
					Thread.sleep((long)(Math.random()*HALF_SECOND));
					if(isPrime(currentIndex)) {
						buffer.blockingPut(currentIndex);
					}
					currentIndex++;
				}
				catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				
			}
		primeDone.set(true);
		}

	public boolean isPrime(int num) {
		boolean isPrime = true;
		if(num == 2 || num == 3)  {
			isPrime = true;
		}
		else if(num % 2 == 0 || num % 3 == 0) {
			isPrime = false;
		}
		else {
			int i = 5;
			while((2*i) <= (num)) {
				if (num % i == 0) {
					isPrime = false;
				}
				i++;
			}
		}
		return isPrime;
	}
	
}
