package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Performs consistency validations within a AVL Tree instance
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */
public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {
	private static int ONE = 1;
	private static int ZERO = 0;

	// TODO Do not forget: you must override the methods insert and remove
	// conveniently.
	@Override
	public void insert(T element) {
		if (element != null) {
			insert(super.getRoot(), element, new BSTNode<T>());
		}
	}

	private void insert(BSTNode<T> node, T element, BSTNode<T> parent) {
		if (node.isEmpty() || isNill(node)) {
			node.setData(element);
			node.setLeft(new BSTNode<T>());
			node.setRight(new BSTNode<T>());
			node.setParent(parent);
		} else {
			if (element.compareTo(node.getData()) < 0) {
				insert((BSTNode<T>) node.getLeft(), element, node);
			} else if (element.compareTo(node.getData()) > 0) {
				insert((BSTNode<T>) node.getRight(), element, node);
			}
		}
		rebalance(node);

	}

	@Override
	public void remove(T element) {
		if (element == null) {
			return;
		}
		BSTNode<T> aux = super.search(element);
		remove(aux);

	}

	private void remove(BSTNode<T> node) {
		if (node.isEmpty() || isNill(node)) {
			return;
		}
		// if node is leaf
		if (node.isLeaf()) {
			if (node.equals(getRoot())) {
				this.root = new BSTNode<>();
			} else {
				if (node.getParent().getLeft().equals(node)) {
					node.getParent().setLeft(new BSTNode<T>());
				} else if (node.getParent().getRight().equals(node)) {
					node.getParent().setRight(new BSTNode<T>());
				}
			}
			rebalanceUp(node);
		}
		// if node has one child on the right
		else if (!node.getRight().isEmpty() && node.getLeft().isEmpty()) {
			if (node.equals(getRoot())) {
				this.root = (BSTNode<T>) node.getRight();
			} else {
				if (node.getParent().getLeft().equals(node)) {
					node.getParent().setLeft(new BSTNode<T>());
				} else if (node.getParent().getRight().equals(node)) {
					node.getParent().setRight(new BSTNode<T>());
				}
				rebalanceUp(node);
			}
			// if node has one child on the left
		} else if (node.getRight().isEmpty() && !node.getLeft().isEmpty()) {
			if (node.equals(getRoot())) {
				this.root = (BSTNode<T>) node.getLeft();
			} else {
				if (node.getParent().getLeft().equals(node)) {
					node.getParent().setLeft(new BSTNode<T>());
				} else if (node.getParent().getRight().equals(node)) {
					node.getParent().setRight(new BSTNode<T>());
				}
				rebalanceUp(node);
			}

		} else {

			BSTNode<T> sucessor = sucessor(node.getData());
			if (isNill(sucessor)) {
				sucessor = predecessor(node.getData());
			}
			T data = node.getData();
			node.setData(sucessor.getData());
			sucessor.setData(data);
			remove(sucessor);
		}

	}

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		if (node.isEmpty() || isNill(node)) {
			return ZERO;
		}
		return super.height((BSTNode<T>) node.getLeft()) - super.height((BSTNode<T>) node.getRight());
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		if (node.isEmpty() || isNill(node)) {
			return;
		}

		int balance = calculateBalance(node);
		if (Math.abs(balance) <= ONE) {
			return;
		}

		if (balance > ZERO) {
			int auxBalance = calculateBalance((BSTNode<T>) node.getLeft());
			if (auxBalance < ZERO) {
				this.leftRotation((BSTNode<T>) node.getLeft());
			}
			this.rightRotation(node);
		} else {
			int auxBalance = calculateBalance((BSTNode<T>) node.getRight());
			if (auxBalance > ZERO) {
				this.rightRotation((BSTNode<T>) node.getRight());
			}
			this.leftRotation(node);
		}

	}

	private void leftRotation(BSTNode<T> node) {
		if (node.isEmpty() || isNill(node)) {
			return;
		}
		BSTNode<T> aux = Util.leftRotation(node);
		if (root.equals(node)) {
			root = aux;
		}

	}

	private void rightRotation(BSTNode<T> node) {
		if (node.isEmpty() || isNill(node)) {
			return;
		}
		BSTNode<T> aux = Util.rightRotation(node);
		if (root.equals(node)) {
			root = aux;
		}

	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		if (node.isEmpty() || isNill(node)) {
			return;
		}
		BSTNode<T> parent = (BSTNode<T>) node.getParent();
		while (!parent.isEmpty()) {
			rebalance(parent);
			parent = (BSTNode<T>) parent.getParent();
		}
	}

	private boolean isNill(BSTNode<T> node) {
		return node == null;
	}
}
