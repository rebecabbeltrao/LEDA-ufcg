package adt.queue;

public class CircularQueue<T> implements Queue<T> {

	private T[] array;
	private int tail;
	private int head;
	private int elements;
	private int capacity;

	public CircularQueue(int size) {
		array = (T[]) new Object[size];
		head = -1;
		tail = -1;
		elements = 0;
		capacity = size;
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull()) {
			throw new QueueOverflowException();
		} if(element != null){
			elements ++;
			tail++;
			tail = tail % array.length;
			array[tail]= element;
		}

	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
	       throw new QueueUnderflowException();
	    }
		
		head ++;
		head = head % array.length;
		elements --;
		return array[head];
	
	}

	@Override
	public T head() {
		/* 
		 * a head começa com -1, por isso temos que retornar head + 1;
		 */
		return array[head + 1];
	}

	@Override
	public boolean isEmpty() {
		return elements == 0;
	}

	@Override
	public boolean isFull() {
		return elements == capacity;
	}

}
