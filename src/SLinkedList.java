package YaziciVarol;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author emreyazici
 */
public class SLinkedList {
    private SNode header;

    public SNode getHeader() {
        return header;
    }

    public SLinkedList() {
        this.header = null;
    }
    
    public void add(String spotType, int spotNum){
        SNode newNode=new SNode(spotType,spotNum);
        if (header==null) {
            header=newNode;
        }else{
            SNode temp=header;
            while (temp.getNext()!=null) {                
                temp=temp.getNext();
            }
            temp.setNext(newNode);
        }
    }
    
    public SNode nodePosition(int playerPosition){
        SNode temp=header;
        while (temp!=null) {            
            if (temp.getSpotNum()==playerPosition) {
                return temp;
            }
            temp=temp.getNext();
        }
        return null;
    }
}
