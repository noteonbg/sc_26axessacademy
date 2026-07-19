package nothorrible;

//Ramesh is owner
// we saw operations and content hence we  know Rectangle is a datatype

public class Rectangle {

    //content of the Rectangle data type
    private int length;
    private int breadth;

    public Rectangle(int length, int breadth) {
        this.length = length;
        this.breadth = breadth;
    }

    //below functions are operations on the REctangle Data type
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getBreadth() {
        return breadth;
    }

    public void setBreadth(int breadth) {
        this.breadth = breadth;
    }
}
