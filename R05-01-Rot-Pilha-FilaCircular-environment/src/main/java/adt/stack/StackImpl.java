package adt.stack;

public class StackImpl<T> implements Stack<T> {

	private T[] array;
	private int top;
	private int capacity;
	@SuppressWarnings("unchecked")
	public StackImpl(int size) {
		array = (T[]) new Object[size];
		top = -1;
		capacity = size;
	}

	@Override
	public T top() {
		if(isEmpty()){
			return null;
		}
		return array[top];
	}

	@Override
	public boolean isEmpty() {
		return (this.top == -1);
	}

	@Override
	public boolean isFull() {
		return (this.top == capacity -1);
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if(isFull()){
			throw new StackOverflowException();
		}else{
			if(element != null){
				this.top++;
				array[top] = element;
			}
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if(isEmpty()){
			throw new StackUnderflowException();
		}else{
			T aux = this.array[top];
			top--;
			return aux;
		}
	}

}
