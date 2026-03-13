package code.model.collections;

import java.util.LinkedList;
import java.util.List;

public class MyTree<T>
    {
        private Node<T> root;

        public MyTree()
            {
            }

        public MyTree(Node<T> root)
            {
                this.root = root;
            }

        public Node<T> getRoot()
            {
                return root;
            }

        public void setRoot(Node<T> root)
            {
                this.root = root;
            }

        /*
        * List-> empty list to which we'll return the inorder traversal
        * */
        public void inorderTraversal(List<T> l, Node<T> node)
            {
                if (node==null) return;
                inorderTraversal(l,node.getLeft());
                l.add(node.getValue());
                inorderTraversal(l,node.getRight());
            }

        public boolean isEmpty()
            {
                return root == null;
            }

        @Override
        public String toString()
            {
                List<T> l=new LinkedList<T>();
                inorderTraversal(l,root);
                return l.toString();
            }

    }
