package adt.rbtree;

import adt.bst.BSTImpl;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

   public RBTreeImpl() {
      this.root = new RBNode<T>();
   }

   protected int blackHeight() {
      return blackHeight((RBNode<T>) this.root);
   }

   private int blackHeight(RBNode<T> node) {
      if (node.isEmpty()) {
         return 1;
      }
      int height;
      if (node.getColour() == Colour.BLACK) {
         height = 1;
      } else {
         height = 0;
      }
      return height + Math.max(blackHeight((RBNode<T>) node.getLeft()), blackHeight((RBNode<T>) node.getRight()));
   }

   protected boolean verifyProperties() {
      boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
            && verifyBlackHeight();

      return resp;
   }

   /**
    * The colour of each node of a RB tree is black or red. This is guaranteed
    * by the type Colour.
    */
   private boolean verifyNodesColour() {
      return true; // already implemented
   }

   /**
    * The colour of the root must be black.
    */
   private boolean verifyRootColour() {
      return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
      // implemented
   }

   /**
    * This is guaranteed by the constructor.
    */
   private boolean verifyNILNodeColour() {
      return true; // already implemented
   }

   /**
    * Verifies the property for all RED nodes: the children of a red node must
    * be BLACK.
    */
   private boolean verifyChildrenOfRedNodes() {
      return verifyChildrenOfRedNodes((RBNode<T>) this.root);
   }

   private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
      if (!node.isEmpty()) {
         if (node.getColour().equals(Colour.RED)) {
            if (!((RBNode<T>) node.getLeft()).getColour().equals(Colour.BLACK)
                  || !((RBNode<T>) node.getRight()).getColour().equals(Colour.BLACK)) {
               return false;
            }
         }
         return verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
               && verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
      }
      return true;
   }

   /**
    * Verifies the black-height property from the root. The method blackHeight
    * returns an exception if the black heights are different.
    */
   private boolean verifyBlackHeight() {
      int leftHeight = verifyBlackHeight((RBNode<T>) this.root.getLeft(), 0);
      int rightHeight = verifyBlackHeight((RBNode<T>) this.root.getRight(), 0);
      if (leftHeight == rightHeight) {
         return true;
      }
      return false;
   }

   private int verifyBlackHeight(RBNode<T> node, int height) {
      if (node != null && !node.isEmpty()) {
         if (node.getColour().equals(Colour.BLACK)) {
            height += 1;
         }
         return Math.max(verifyBlackHeight((RBNode<T>) node.getLeft(), height),
               verifyBlackHeight((RBNode<T>) node.getRight(), height));
      }
      height += 1;
      return height;
   }

   @Override
   public void insert(T value) {
      if (value != null) {
         insert((RBNode<T>) this.root, value);
      }
   }

   private void insert(RBNode<T> node, T value) {
      RBNode<T> aux = node;
      if (node.isEmpty()) {
         node.setData(value);
         node.setLeft(new RBNode<>());
         node.setRight(new RBNode<>());
         node.getLeft().setParent(node);
         node.getRight().setParent(node);
         node.setColour(Colour.RED);
         fixUpCase1(node);
      } else if (value.compareTo(node.getData()) < 0) {
         insert((RBNode<T>) node.getLeft(), value);
      } else if (value.compareTo(node.getData()) > 0) {
         insert((RBNode<T>) node.getRight(), value);
      }
   }

   @Override
   public RBNode<T>[] rbPreOrder() {
      RBNode<T>[] array = new RBNode[size()];
      rbPreOrder(array, 0, (RBNode<T>) this.getRoot());
      return array;
   }

   private int rbPreOrder(RBNode<T>[] array, int index, RBNode<T> node) {
      if (!node.isEmpty()) {
         array[index++] = node;
         index = rbPreOrder(array, index, (RBNode<T>) node.getLeft());
         index = rbPreOrder(array, index, (RBNode<T>) node.getRight());
      }
      return index;
   }

   // FIXUP methods
   protected void fixUpCase1(RBNode<T> node) {
      if (node.equals(this.root)) {
         node.setColour(Colour.BLACK);
      } else {
         fixUpCase2(node);
      }
   }

   protected void fixUpCase2(RBNode<T> node) {
      if (!((RBNode<T>) node.getParent()).getColour().equals(Colour.BLACK)) {
         fixUpCase3(node);
      }
   }

   protected void fixUpCase3(RBNode<T> node) {
      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandParent = (RBNode<T>) parent.getParent();
      RBNode<T> uncle;
      //node.equals(node.getParent().getLeft())
      if (parent.equals(parent.getParent().getLeft())) {
         uncle = (RBNode<T>) grandParent.getRight();
      } else {
         uncle = (RBNode<T>) grandParent.getLeft();
      }
      if (!(uncle.getColour().equals(Colour.BLACK))) {
         parent.setColour(Colour.BLACK);
         uncle.setColour(Colour.BLACK);
         grandParent.setColour(Colour.RED);
         fixUpCase1(grandParent);

      } else {
         fixUpCase4(node);
      }

   }

   protected void fixUpCase4(RBNode<T> node) {
      RBNode<T> next = node;
      RBNode<T> parent = (RBNode<T>) node.getParent();
      if (!node.equals(node.getParent().getLeft()) && parent.equals(parent.getParent().getLeft())
            || !node.equals(node.getParent().getLeft()) && !parent.equals(parent.getParent().getLeft())) {
         if (node.equals(node.getParent().getLeft())) {
            Util.rightRotation(parent);
         } else {
            Util.leftRotation(parent);
         }
         fixUpCase5(parent);
      } else {
         fixUpCase5(next);
      }

   }

   protected void fixUpCase5(RBNode<T> node) {
      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandParent = (RBNode<T>) parent.getParent();

      parent.setColour(Colour.BLACK);
      grandParent.setColour(Colour.RED);

      if (node.equals(node.getParent().getLeft())) {
         Util.rightRotation(grandParent);
      } else {
         Util.leftRotation(grandParent);
      }
   }
}
