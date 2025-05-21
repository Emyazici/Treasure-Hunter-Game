package YaziciVarol;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author emreyazici
 */
public class DLinkedList {
    private SNode header;
    private SNode tail;

    public SNode getHeader() {
        return header;
    }

    public void setHeader(SNode header) {
        this.header = header;
    }

    public SNode getTail() {
        return tail;
    }

    public void setTail(SNode tail) {
        this.tail = tail;
    }
    
    public void add(String spotType, int spotNum) {
        SNode newNode = new SNode(spotType, spotNum);
        if (header == null) {
            header = newNode;
            tail = newNode;
        } else {
            tail.setNext(newNode);
            newNode.setPrev(tail);
            tail = newNode;
        }
    }
}
