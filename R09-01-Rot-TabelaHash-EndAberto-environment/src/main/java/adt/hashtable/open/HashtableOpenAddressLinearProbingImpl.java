package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size, HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		if (isFull())
			throw new HashtableOverflowException();
		if (element != null) {
			int probe = 0;
		
			while (probe < capacity()) {
				int hash = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);
				if (table[hash] == null || table[hash].equals(deletedElement)) {
					table[hash] = element;
					elements++;
					break;
				} else {
					probe++;
					COLLISIONS++;
				}
			}
		}

	}

	@Override
	public void remove(T element) {
		if (element != null || !isEmpty()) {
			int probe = 0;

			while (probe < capacity()) {
				int hash = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);

				if (table[hash] == null)
					break;

				if (table[hash].equals(element)) {
					table[hash] = deletedElement;
					elements--;
					break;
				} else {
					probe++;
				}
			}

		}

	}

	@Override
	public T search(T element) {
		T result = null;

		if (element != null || !isEmpty()) {
			int probe = 0;
			while (probe < capacity()) {
				int hash = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);
				if (table[hash] == null)
					return result; 
				if (table[hash].equals(element)) {
					return element;
				} else
					probe++;
			}
		}
		return result;

	}

	@Override
	public int indexOf(T element) {
		if (element != null || !isEmpty()) {
			int probe = 0;
			while (probe < capacity()) {
				int hash = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, probe);
				if (table[hash] == null){
					break;
				}if (table[hash].equals(element)) {
					return hash;
				} else
					probe++;
			}
		}
		return -1;
	}

}
