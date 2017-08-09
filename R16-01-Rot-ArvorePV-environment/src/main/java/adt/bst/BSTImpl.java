package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.isEmpty();
	}

	@Override
	public int height() {
		   return height(this.root);
	   }

	   private int height(BSTNode<T> node) {
	      if (node.isEmpty()) {
	         return -1;
	      }
	      return Math.max(this.height((BSTNode<T>) node.getLeft()), this.height((BSTNode<T>) node.getRight())) + 1;

	   }

	@Override
	public BSTNode<T> search(T element) {
		 if (this.size() == 0) {
	         return new BSTNode<T>();
	      } else {
	         return search(this.root, element);
	      }
	   }

	   private BSTNode<T> search(BSTNode<T> node, T element) {
	      if (!node.isEmpty() && element != null) {
	         if (node.getData().equals(element)) {
	            return node;
	         } else {
	            if (node.getData().compareTo(element) < 0) {
	               return search((BSTNode<T>) node.getRight(), element);
	            } else {
	               return search((BSTNode<T>) node.getLeft(), element);
	            }
	         }
	      }
	      BSTNode<T> aux = new BSTNode<T>();
	      return aux;
	   }

	@Override
	public void insert(T element) {
		 if (isEmpty()) {
	         this.root.setData(element);
	         this.root.setLeft((BSTNode<T>) new BSTNode<>());
	         this.root.setRight((BSTNode<T>) new BSTNode<>());
	         this.root.setParent(null);

	      } else {
	         insert(this.root, element);
	      }

	   }

	   private void insert(BSTNode<T> node, T element) {

	      if (node.getData().compareTo(element) > 0) {
	         if (node.getLeft().isEmpty()) {
	            node.getLeft().setData(element);
	            node.getLeft().setLeft((BSTNode<T>) new BSTNode<T>());
	            node.getLeft().setRight((BSTNode<T>) new BSTNode<T>());
	            node.getLeft().setParent(node);
	         } else {
	            insert((BSTNode<T>) node.getLeft(), element);
	         }

	      } else {
	         if (node.getRight().isEmpty()) {
	            node.getRight().setData(element);
	            node.getRight().setLeft((BSTNode<T>) new BSTNode<T>());
	            node.getRight().setRight((BSTNode<T>) new BSTNode<T>());
	            node.getRight().setParent(node);
	         } else {
	            insert((BSTNode<T>) node.getRight(), element);
	         }

	      }}
	   

	@Override
	public BSTNode<T> maximum() {
		   return maximum(this.root);
	   }

	   protected BSTNode<T> maximum(BSTNode<T> node) {
	      if (node.isEmpty()) {
	         return null;
	      } else if (node.getRight().isEmpty()) {
	         return node;
	      } else {
	         return maximum((BSTNode<T>) node.getRight());
	      }
	   }

	@Override
	public BSTNode<T> minimum() {
		 return minimum(this.root);

	   }

	   protected BSTNode<T> minimum(BSTNode<T> node) {
	      if (node.isEmpty()) {
	         return null;
	      } else if (node.getLeft().isEmpty()) {
	         return node;
	      } else
	         return minimum((BSTNode<T>) node.getLeft());
	   }



	@Override
	public BSTNode<T> sucessor(T element) {
		 BSTNode<T> aux = this.search(element);
	      if (aux.isEmpty()) {
	         return null;
	      }
	      return sucessor(aux);
	   }

	   private BSTNode<T> sucessor(BSTNode<T> node) {
	      BSTNode<T> result = minimum((BSTNode<T>) node.getRight());
	      if (result != null) {
	         return result;
	      } else {
	         result = (BSTNode<T>) node.getParent();
	         while (result != null && result.getData().compareTo(node.getData()) < 0) {
	            result = (BSTNode<T>) result.getParent();
	         }
	         return result;
	      }
	   }
	@Override
	public BSTNode<T> predecessor(T element) {
		 BSTNode<T> aux = this.search(element);
	      if (aux.isEmpty())
	         return null;

	      return predecessor(aux);
	   }

	   private BSTNode<T> predecessor(BSTNode<T> node) {
	      BSTNode<T> result = maximum((BSTNode<T>) node.getLeft());

	      if (result != null) {
	         return result;
	      } else {
	         result = (BSTNode<T>) node.getParent();
	         while (result != null && result.getData().compareTo(node.getData()) > 0) {
	            result = (BSTNode<T>) result.getParent();
	         }
	         return result;
	      }
	   }


	@Override
	public void remove(T element) {
		  if (element == null) {
		         return;
		      }
		      BSTNode<T> aux = search(element);
		      if (!aux.isEmpty()) {
		         remove(aux);
		      }
		   }

		   private void remove(BSTNode<T> node) {
		      if (node.getLeft().isEmpty() && node.getRight().isEmpty()) {
		         node.setData(null);

		      } else if (node.getRight().isEmpty() || node.getLeft().isEmpty()) {
		         if (node.equals(getRoot())) {
		            if (!node.getRight().isEmpty()) {
		               root = (BSTNode<T>) node.getRight();
		            } else {
		               root = (BSTNode<T>) node.getLeft();
		            }

		         } else {
		            if (node.equals(node.getParent().getLeft())) {
		               if (!node.getRight().isEmpty()) {
		                  node.getParent().setLeft(node.getRight());
		                  node.getRight().setParent(node.getParent());
		               } else {
		                  node.getParent().setLeft(node.getLeft());
		                  node.getLeft().setParent(node.getParent());
		               }

		            } else {
		               if (!node.getRight().isEmpty()) {
		                  node.getParent().setRight(node.getRight());
		                  node.getRight().setParent(node.getParent());
		               } else {
		                  node.getParent().setRight(node.getLeft());
		                  node.getLeft().setParent(node.getParent());
		               }
		            }
		         }

		      } else {
		         BSTNode<T> sucessor = sucessor(node.getData());
		         T sucessorElement = sucessor.getData();
		         remove(sucessor);
		         node.setData(sucessorElement);
		      }
		   }

	@Override
	public T[] preOrder() {
		 T[] array;
	      if (size() == 0) {
	         array = (T[]) new Comparable[] {};
	         return array;
	      }
	      array = (T[]) new Comparable[size()];
	      int index = 0;
	      preOrder(array, index, root);
	      return array;

	   }

	   public int preOrder(T[] array, int index, BSTNode<T> node) {
	      if (!node.isEmpty()) {
	         array[index] = node.getData();
	         index++;
	         index = preOrder(array, index, (BSTNode<T>) node.getLeft());
	         index = preOrder(array, index, (BSTNode<T>) node.getRight());
	         return index;
	      } else {
	         return index;
	      }

	   }
	@Override
	public T[] order() {
		T[] array;
	      if (size() == 0) {

	         array = (T[]) new Comparable[] {};
	         return array;
	      }

	      array = (T[]) new Comparable[size()];

	      int index = 0;
	      order(array, index, root);
	      return array;
	   }

	   public int order(T[] array, int index, BSTNode<T> node) {
	      if (!node.isEmpty()) {
	         index = order(array, index, (BSTNode<T>) node.getLeft());
	         array[index] = node.getData();
	         index++;
	         index = order(array, index, (BSTNode<T>) node.getRight());
	         return index;
	      } else {
	         return index;
	      }

	   }


	@Override
	public T[] postOrder() {
		 T[] array;
	      if (size() == 0) {
	         array = (T[]) new Comparable[] {};
	         return array;
	      }
	      array = (T[]) new Comparable[size()];
	      int index = 0;
	      postOrder(array, index, root);
	      return array;
	   }

	   public int postOrder(T[] array, int index, BSTNode<T> node) {
	      if (!node.isEmpty()) {
	         index = postOrder(array, index, (BSTNode<T>) node.getLeft());
	         index = postOrder(array, index, (BSTNode<T>) node.getRight());
	         array[index] = node.getData();
	         index++;
	         return index;
	      } else {
	         return index;
	      }
	   }

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
