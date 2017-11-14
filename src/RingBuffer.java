public class RingBuffer {
	//Referenced from the Circular Buffer in Chapter 23 of Java(tm) How To Program by Paul and Harvey Deitel 
	
	private int[] buffer;
	
	private int occupiedCells;
	private int writeIndex;
	private int readIndex;
	
	public RingBuffer(int size) {
		buffer = new int[size];
		occupiedCells = 0;
		writeIndex = 0;
		readIndex = 0;
	}
	
	public synchronized void blockingPut(int checkedFiboPrime) throws InterruptedException {
		while(occupiedCells == buffer.length) {
			wait();
		}
		buffer[writeIndex] = checkedFiboPrime;
		writeIndex = (writeIndex + 1) % buffer.length;
		occupiedCells++;
		notifyAll();
	}
	
	public synchronized int blockingGet() throws InterruptedException {
		while(occupiedCells == 0) {
			wait();
		}
		int readValue = buffer[readIndex];
		readIndex = (readIndex + 1) % buffer.length;
		--occupiedCells;
		notifyAll();
		
		return readValue;
	}
	
	public boolean hasCellsLeft() {
		return (occupiedCells != 0);
	}
}
