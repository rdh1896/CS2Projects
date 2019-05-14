package rit.stu.act1;

import rit.cs.Node;
import rit.cs.Queue;

/**
 * A queue implementation that uses a Node to represent the structure.
 * @param <T> The type of data the queue will hold
 * @author Sean Strout @ RIT CS
 * @author Russell Harvey (rdh1896@rit.edu)
 */
public class QueueNode<T> implements Queue<T> {

    /** Size of the queue. */
    private int size;

    /** Front value of the queue. */
    private Node<T> front;

    /** Back value of the queue. */
    private Node<T> back;

    /**
     * Create an empty queue.
     */
    public QueueNode() {
        this.front = new Node<>(null, null);
        this.back = new Node<>(null, null);
        this.size = 0;
    }

    /**
     * Returns the back value of the queue.
     * @return back value.
     */
    @Override
    public T back() {
        assert !empty();
        return this.back.getData();
    }

    /**
     * Dequeue the front value of the queue.
     * @return front value.
     */
    @Override
    public T dequeue() {
        assert !empty();
        T tempData = this.front.getData();
        if (this.front.getNext() == null) {
            this.front.setData(null);
        } else {
            this.front = this.front.getNext();
        }
        if (empty()) {
            this.back.setData(null);
            this.back.setNext(null);
        }
        this.size--;
        return tempData;
    }

    /**
     * Checks if the queue is empty.
     * @return boolean.
     */
    @Override
    public boolean empty() {
        return this.front.getData() == null;
    }

    /**
     * Enqueues a new element to the back of the queue.
     * @param element The new data element
     */
    @Override
    public void enqueue(T element) {
        Node<T> newNode = new Node<>(element, null);
        if (empty()) {
            this.front = newNode;
        } else {
            this.back.setNext(newNode);
        }
        this.back = newNode;
        this.size++;
    }

    /**
     * Returns the front value of the queue.
     * @return front value.
     */
    @Override
    public T front() {
        assert !empty();
        return this.front.getData();
    }
}
