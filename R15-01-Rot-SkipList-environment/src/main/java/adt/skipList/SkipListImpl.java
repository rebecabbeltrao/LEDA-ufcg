package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {

		this.maxHeight = maxHeight;
		root = new SkipListNode(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	private int randomLevel() {
		int randomLevel = 1;
		double random = Math.random();
		while (Math.random() <= PROBABILITY && randomLevel < maxHeight) {
			randomLevel = randomLevel + 1;
		}
		return randomLevel;
	}

	@Override
	public void insert(int key, T newValue, int height) {
		if (height <= maxHeight) {
			@SuppressWarnings("unchecked")
			SkipListNode<T>[] update = new SkipListNode[height];
			SkipListNode<T> aux = this.root;

			// pesquisa local
			for (int i = height - 1; i >= 0; i--) {
				while (aux.getForward(i) != null && aux.getForward(i).key < key){
					aux = aux.getForward(i);
				}
				
				update[i] = aux; // guarda caminho
			}
			aux = aux.forward[0];
			
			if (aux.getKey() == key) {
				aux.setValue(newValue);
			} else {
				ajustHeight(height, update);
				aux = new SkipListNode<T>(key, height, newValue);

				// altera ponteiros
				for (int i = 0; i < height; i++) {
					aux.forward[i] = update[i].forward[i];
					update[i].forward[i] = aux;
				}
			}
		}
	}

	private void ajustHeight(int height, SkipListNode<T>[] update) {
		if (height > this.maxHeight) {
			for (int i = this.maxHeight; i <height; i++) {
				root.getForward()[i] = NIL;
			}
			this.maxHeight = height;
		}
	}

	@Override
	public void remove(int key) {
		@SuppressWarnings("unchecked")
		SkipListNode<T>[] update = new SkipListNode[this.maxHeight];
		SkipListNode<T> aux = this.root;

		for (int i = maxHeight - 1; i >= 0; i--) {
			if (aux.forward[i] != NIL) {
				while (aux.forward[i].value != null && aux.forward[i].key < key)
					aux = aux.forward[i];
			}
			update[i] = aux;
		}
		aux = aux.getForward()[0];

		if (aux.key == key) {
			for (int i = 0; i < maxHeight; i++) {
				if (update[i].getForward()[i] != aux){
					break;
				}
				update[i].getForward()[i] = aux.getForward()[i];
			}
		}

	}

	@Override
	public int height() {
		int height = this.maxHeight - 1;
		while (height >= 0 && root.getForward(height).equals(NIL)) {
			if (height ==0) {
				height-=1;
				break;
			} else {
				height-=1;
			}
		}
		return height +1;
	}

	@Override
	public SkipListNode<T> search(int key) {
		if (key == root.getKey()) {
			return root;
		}else if (key == NIL.getKey()) {
			return NIL;
		}
		SkipListNode<T> aux = this.root;
		for (int i = maxHeight - 1; i >= 0; i--) {

			while (aux.getForward(i) != null && aux.getForward(i).key < key) {
				aux = aux.getForward(i);
			}
		}
		if (aux.getForward(0) != null && aux.getForward(0).getKey() == key) {
			return aux.getForward(0);
		}

		return null;
	}

	@Override
	public int size() {
		SkipListNode<T> aux = this.root.getForward(0);
		int count = 0;
		while (aux != NIL) {
			count++;
			aux = aux.getForward(0);
		}
		return count;

	}

	@Override
	public SkipListNode<T>[] toArray() {
		int size = size() + 2;
		@SuppressWarnings("unchecked")
		SkipListNode<T>[] array = new SkipListNode[size];
		SkipListNode<T> aux = this.root;

		for (int i = 0; i < size; i++) {
			array[i] = aux;
			aux = aux.getForward(0);
		}
		return array;
	}

}
