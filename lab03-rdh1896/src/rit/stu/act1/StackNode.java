package rit.stu.act1;

import rit.cs.Stack;
import rit.cs.Node;

/**
 * A stack implementation that uses a Node to represent the structure.
 * @param <T> The type of data the stack will hold
 * @author Sean Strout @ RIT CS
 * @author Russell Harvey (rdh1896@rit.edu)
 */
public class StackNode<T> implements Stack<T> {

    /** Holds the stack. */
    private Node<T> stack;

    /**
     * Create an empty stack.
     */
    public StackNode() {
        this.stack = new Node<>(null, null);
    }

    /**
     * Checks if the stack is empty.
     * @return boolean.
     */
    @Override
    public boolean empty() {
        return this.stack.getData() == null;
    }

    /**
     * Removes the top value and returns it.
     * @return top value.
     */
    @Override
    public T pop() {
        assert !empty();
        T element = this.stack.getData();
        if (this.stack.getNext() == null) {
            this.stack.setData(null);
        } else {
            this.stack = this.stack.getNext();
        }
        return element;
    }

    /**
     * Pushes a new node onto the top of the stack.
     * @param element The new data element
     */
    @Override
    public void push(T element) {
        if (this.stack.getData() == null){
            this.stack.setData(element);
        } else {
            Node<T> temp = this.stack;
            Node<T> current = new Node<>(element, temp);
            this.stack = current;
        }
    }

    /**
     * Gets the top value of the stack and returns it.
     * @return top value.
     */
    @Override
    public T top() {
        assert !empty();
        return this.stack.getData();
    }
}
