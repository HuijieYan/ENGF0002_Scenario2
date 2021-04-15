import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class ToEnglish extends JFrame {
    StringBuilder English;
    JPanel jtable;
    JTable enterthemeaning=new JTable();
    //JTable enterthemeaning;
    HashMap<String, ArrayList<Integer>> index;
    ArrayList<String> letter;
    //JPanel jtable;
    String logging;


        public ToEnglish(String log){

            logging=log;


             index = new HashMap<>();
             letter = new ArrayList<>();
            Set<String> nodup = new HashSet<>();

            final int[] replace = {0};
            for (int i = 0; i < log.length(); i++) {

                if (log.substring(i, i + 1).matches("[A-Z]")) {
                    if (nodup.add(log.substring(i, i + 1))) {
                        letter.add(log.substring(i, i + 1));
                        int finalI = i;
                        index.put(log.substring(i, i + 1), new ArrayList<Integer>() {{
                            add(finalI);
                        }});
                    } else {
                        index.get(log.substring(i, i + 1)).add(i);
                    }


                }






            }

        }


    public StringBuilder translate(String translate ) {
        StringBuilder sb = new StringBuilder(translate);

        for (int j = 0; j < 3; j++) {
            int start = 0;

            for (int i = 0; i < sb.length() - 3; i++) {
                if (j == 0) {
                    if (sb.substring(i, i + 1).equals("¬")) {
                        sb.replace(i, i + 1, " it is not true that ");
                    }
                }
                if (j == 1) {
                    if (sb.substring(i, i + 1).equals("∩")) {
                        sb.replace(i, i + 1, " and ");
                    }

                    if (sb.substring(i, i + 1).equals("∪")) {
                        sb.replace(i, i + 1, " or ");
                    }
                }
                if (j == 2) {

                    if (sb.substring(i, i + 4).equals("-><-")) {
                        sb.insert(start, " if ");
                        sb.replace(i+4, i + 8, ",only if ");
                        start = i + 13;
                    }
                    if (sb.substring(i, i + 2).equals("->")) {
                        sb.insert(start, " if ");
                        sb.replace(i+4, i + 6, ",then ");
                        start = i + 10;
                    }
                }


            }
        }
        return sb;
    }

public void getout() {
    Stack<String> stack = new Stack<>();
    Stack<Integer> index = new Stack<>();
    for (int i = 0; i < English.length(); i++) {
        if (English.substring(i, i + 1).equals("(")) {
            stack.push("(");
            index.push(i + 1);
        }
        if (English.substring(i, i + 1).equals(")")) {
            if (!stack.empty()) {
                English.replace(index.peek() - 1, i + 1, translate(English.substring(index.peek(), i)).toString());
                index.pop();
                stack.pop();
            }
            else {
                System.out.println("not balance");
            }
        }

    }
    if (!stack.empty()) {
        System.out.println("not balance");
    }
    else {
       English= translate(English.toString());
    }
    System.out.println(English);

}

}