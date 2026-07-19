package nothorrible;

// i am not seeing any operations or content.. hence
//GreatMain is just a class used to group functions..

public class GreatMain {

    public static void main(String[] args) {

        int i ; // REctangle r =new Rectanle();
        i =3;// content of the rectangle


        Rectangle r =new Rectangle(2,3);// creating the variable of that data type
        r.setLength(3);  //operations
        r.setBreadth(4); //operations.

    }

    public static void main1(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 11; i <= 16; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }
}