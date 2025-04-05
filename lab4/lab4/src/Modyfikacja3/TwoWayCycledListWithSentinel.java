package Modyfikacja3;

import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class TwoWayCycledListWithSentinel<E> extends AbstractList<E> {
    private class Element {
        private E value;
        private Element next;
        private Element prev;

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }

        public Element getPrev() {
            return prev;
        }

        public void setPrev(Element prev) {
            this.prev = prev;
        }

        Element(E data) {
            this.value = data;
        }

        /**
         * elem będzie stawiony <b> za this </b>
         */
        public void insertAfter(Element elem) {
            elem.setNext(this.getNext());
            elem.setPrev(this);
            this.getNext().setPrev(elem);
            this.setNext(elem);
        }

        /**
         * elem będzie stawiany <b> przed this </b>
         */
        public void insertBefore(Element elem) {
            elem.setNext(this);
            elem.setPrev(this.getPrev());
            this.getPrev().setNext(elem);
            this.setPrev(elem);
        }

        /**
         * elem będzie usuwany z listy w której jest <p>
         * <b>Założenie:</b> element jest już umieszczony w liście i nie jest to sentinel
         */
        public void remove() {
            this.getNext().setPrev(this.getPrev());
            this.getPrev().setNext(this.getNext());
        }
    }

    Element sentinel = null;

    public TwoWayCycledListWithSentinel() {
        sentinel = new Element(null);
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    private Element getElement(int index) {
        if (index < 0) throw new IndexOutOfBoundsException();
        Element elem = sentinel.getNext();
        int counter = 0;
        while (elem != sentinel && counter < index) {
            counter++;
            elem = elem.getNext();
        }
        if (elem == sentinel)
            throw new IndexOutOfBoundsException();
        return elem;
    }

    private Element getElement(E value) {
        Element elem = sentinel.getNext();
        while (elem != sentinel && !value.equals(elem.getValue())) {
            elem = elem.getNext();
        }
        if (elem == sentinel)
            return null;
        return elem;
    }

    public boolean isEmpty() {
        return sentinel.getNext() == sentinel;
    }

    public void clear() {
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    public E get(int index) {
        Element elem = getElement(index);
        return elem.getValue();
    }

    public E set(int index, E value) {
        Element elem = getElement(index);
        E retValue = elem.getValue();
        elem.setValue(value);
        return retValue;
    }

    public boolean add(E value) {
        Element newElem = new Element(value);
        sentinel.insertBefore(newElem);
        return true;
    }

    public void add(int index, E value) {
        Element newElem = new Element(value);
        if (index == 0) sentinel.insertAfter(newElem);
        else {
            Element elem = getElement(index - 1);
            elem.insertAfter(newElem);
        }
    }

    public int indexOf(Object value) {
        Element elem = sentinel.getNext();
        int counter = 0;
        while (elem != sentinel && !elem.getValue().equals(value)) {
            counter++;
            elem = elem.getNext();
        }
        if (elem == sentinel)
            return -1;
        return counter;
    }

    public E remove(int index) {
        Element elem = getElement(index);
        elem.remove();
        return elem.getValue();
    }

    public boolean remove(Object value) {
        Element elem = getElement((E) value);
        if (elem == null) return false;
        elem.remove();
        return true;
    }

    public int size() {
        Element elem = sentinel.getNext();
        int counter = 0;
        while (elem != sentinel) {
            counter++;
            elem = elem.getNext();
        }
        return counter;
    }

    public Iterator<E> iterator() {
        return new TWCIterator();
    }

    private class TWCIterator implements Iterator<E> {
        Element _current = sentinel;

        public boolean hasNext() {
            return _current.getNext() != sentinel;
        }

        public E next() {
            _current = _current.getNext();
            return _current.getValue();
        }
    }

    public ListIterator<E> listIterator() {
        return new TWCListIterator();
    }

    private class TWCListIterator implements ListIterator<E> {
        boolean wasNext = false;
        boolean wasPrevious = false;
        /**
         * strażnik
         */
        Element _current = sentinel;

        public boolean hasNext() {
            return _current.getNext() != sentinel;
        }

        @Override
        public E next() {
            return null;
        }

        public boolean hasPrevious() {
            return _current != sentinel;
        }

        @Override
        public E previous() {
            return null;
        }

        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(E e) {

        }

        @Override
        public void add(E e) {

        }
    }



    public void printList(){
        Element elem=sentinel.getNext();

        while (elem!=sentinel){
            System.out.print(elem.getValue() + " ");
            elem=elem.getNext();
        }
    }

    public E middlew(){
        if(isEmpty()) throw  new NoSuchElementException();

        Element left = sentinel.getNext();
        Element right = sentinel.getPrev();

        while (left != right && left!=right.getPrev()) {
            left = left.next;
            right = right.prev;
        }
        return left.getValue();
     }
    }

