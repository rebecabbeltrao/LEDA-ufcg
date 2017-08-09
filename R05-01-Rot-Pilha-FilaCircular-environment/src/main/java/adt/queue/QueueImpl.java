package adt.queue;

public class QueueImpl<T> implements Queue<T> {

	private T[] array;
	private int tail;
	private int capacity;

	@SuppressWarnings("unchecked")
	public QueueImpl(int size) {
		array = (T[]) new Object[size];
		tail = -1;
		capacity = size;
	}

	@Override
	public T head() {
		if(isEmpty()){
			return null;
		}
		return this.array[0];
	}

	@Override
	public boolean isEmpty() {
		return (this.tail == -1);

	}

	@Override
	public boolean isFull() {
		return (this.tail == capacity - 1);
	}

	private void shiftLeft() {
		for (int i = 0; i < this.tail; i++) {
			this.array[i] = this.array[i + 1];
		}
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (isFull()) {
			throw new QueueOverflowException();
		} 
			if (element != null) {
				tail++;
				array[tail] = element;

			}
		

	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (isEmpty()) {
			throw new QueueUnderflowException();
		} else {
			T aux = array[0];
			shiftLeft();
			this.tail--;
			return aux;
		}
	}

}
