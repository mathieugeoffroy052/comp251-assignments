package A1.Q3;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {

        String in =
                "user1 doubledutch double double dutch\n" +
                "user2 dutch doubledutch doubledutch double\n" +
                "user3 not double dutch doubledutch\n";

        String lines[] = in.split("\\r?\\n");

        //String[] lines = {"Z no","D no","Z no","D no","Z no","D no","Z no","D no",};

        //String[] test = {"David no no no no nobody never", "Jennifer why ever not", "Parham no not never nobody"};
        ArrayList<String> ret = A1_Q3.Discussion_Board(lines);
        for(String i:ret){
            System.out.println(i);
        }
    }
}