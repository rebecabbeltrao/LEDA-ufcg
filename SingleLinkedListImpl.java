

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		return this.head.isNIL();
		
	}

	@Override
	public int size() {
		int count = 0;
		SingleLinkedListNode<T> aux = this.getHead();
		
		while(!aux.isNIL()){
			aux = aux.next;
			count += 1;
		}
		return count;
	}

	@Override
	public T search(T element) {
		SingleLinkedListNode<T> aux = this.getHead();
		T result = null;
		if(this.head.getData().equals(element)){
			result = this.head.getData();
			return result;
		}else{
			while(aux.isNIL()){
				if(aux.getData().equals(element)){
					result = aux.getData();
					return result;
				}
				aux = aux.getNext();
			}
		}
		return result;
		
		
	}

	@Override
	public void insert(T element) {
		SingleLinkedListNode<T> aux = this.getHead();
		SingleLinkedListNode<T> node = new SingleLinkedListNode<T>(element, null);
		if(isEmpty()){
			setHead(node);
		}
		while(!aux.isNIL()){
			aux = aux.getNext();
			
		}
		aux.setData(element);
		aux.setNext(new SingleLinkedListNode<>());
	}

	@Override
	public void remove(T element) {
		SingleLinkedListNode<T> aux = this.getHead();
		SingleLinkedListNode<T> previous = this.getHead();
		if(this.head.equals(element)){
			this.head = head.getNext();
		}
		while(!aux.isNIL() && !aux.getData().equals(element)){
			 previous = aux;
			aux = aux.getNext();
		}
		while(!aux.isNIL()){
			previous.setNext(aux.getNext());
		}
	}

	@Override
	public T[] toArray() {
		T[] array = (T[]) new Object[size()];
		int index = 0;
		SingleLinkedListNode<T> aux = this.getHead();
		while(!aux.isNIL()){
			array[index] = aux.getData();
			aux = aux.getNext();
			index++;
		}
		return array;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}
	public int indexOf(T element){
		return indexOf(this.head, element, 0);
	
}

	private int indexOf(SingleLinkedListNode<T> node, T element, int index) {
		if(isEmpty()){
			return -1;
		}else{
			if(node.getData().equals(element)){
				return index;
			}else{
				return indexOf(node.next, element, index+1);
			}
		}
	}
	public void remove(int position) {
		if(!isEmpty()){
			SingleLinkedListNode<T> previous = this.getHead();
			SingleLinkedListNode<T> aux = this.head;
			
			int index = 0;
			
			while(!aux.isNIL() || index != position){
				previous = aux;
				aux = aux.next;
			}
			while(aux!= null){
				previous.setNext(aux.getNext());
			}
		}
		
	}
}
