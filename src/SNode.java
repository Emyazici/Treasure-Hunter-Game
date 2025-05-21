package YaziciVarol;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author emreyazici
 */
public class SNode {
    private String spotType;
    private int spotNum;
    private SNode next;
    private SNode prev;

    public SNode(String spotType, int spotNum) {
        this.spotType = spotType;
        this.spotNum = spotNum;
        this.next=null;
        this.prev=null;
    }

    public SNode getPrev() {
        return prev;
    }

    public void setPrev(SNode prev) {
        this.prev = prev;
    }
    
    public String getSpotType() {
        return spotType;
    }

    public void setSpotType(String spotType) {
        this.spotType = spotType;
    }

    public int getSpotNum() {
        return spotNum;
    }

    public void setSpotNum(int spotNum) {
        this.spotNum = spotNum;
    }

    public SNode getNext() {
        return next;
    }

    public void setNext(SNode next) {
        this.next = next;
    }
    
}
