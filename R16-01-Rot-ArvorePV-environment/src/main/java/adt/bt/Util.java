package adt.bt;

import adt.bst.BSTNode;

public class Util {


	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		if (node.isEmpty() || node == null) {
			return null;
		}

		BSTNode<T> aux = (BSTNode<T>) node.getRight();
		node.setRight(aux.getLeft());
		node.getRight().setParent(node);
		aux.setParent(node.getParent());

		if (node.getParent().getLeft()== node) {
			node.getParent().setLeft(aux);
		} else {
			node.getParent().setRight(aux);
		}
		aux.setLeft(node);
		node.setParent(aux);
		return aux;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * @param node
	 * @return
	 */
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		if (node.isEmpty() || node == null) {
			return null;
		}
		
		BSTNode<T> aux = (BSTNode<T>) node.getLeft();
		node.setLeft(aux.getRight());
		node.getLeft().setParent(node);
		aux.setParent(node.getParent());
		
		if (node.getParent().getLeft() == node) {
			node.getParent().setLeft(aux);

		} else {
			node.getParent().setRight(aux);
		}
		
		aux.setRight(node);
		node.setParent(aux);
		return aux;
	}

	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}
