import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.TableView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Turthtable extends JFrame {

    String formular;
    HashMap<String, Integer[]> single = new HashMap<>();
    String[] display;
    String[][] correct;
    HashMap<String, Integer> matchedcool = new HashMap<>();
    int formularsize;
    int lettersize;
    ArrayList<String> singleformular;
    JTable permu;
    Boolean wrongin=false;
    int wrongrow;
    int wrongcol;

    public  Turthtable(String formular){
        this.formular=formular;
         singleformular = new ArrayList<>();
        Set<String> nodu = new HashSet<>();
        formularsize = 0;
        for (int i = 0; i < formular.length(); i++) {

            if (formular.substring(i, i + 1).matches("[A-Z]")) {
                formularsize++;
                if (nodu.add(formular.substring(i, i + 1))) {
                    singleformular.add(formular.substring(i, i + 1));
                }
            }
        }

        lettersize = singleformular.size();
        if (formularsize>0) {
            display = new String[formularsize - 1];
            correct = new String[(int) Math.pow(2, lettersize)][formularsize - 1];
        }


    }


    public void produceturth() {


        for (int h = 0; h < Math.pow(2, lettersize); h++) {
            for (int w = 0; w < lettersize; w++) {
                   if (!(((String) permu.getValueAt(h,w)).equals("1") || ((String)permu.getValueAt(h,w)).equals("0"))){
                       wrongin=true;
                       wrongrow=h;
                       wrongcol=w;
                       return;
                   }

                matchedcool.put(permu.getColumnName(w),  Integer.parseInt((String) permu.getValueAt(h,w)));
            }
            int g = 0;
            for (int j = 0; j < 3; j++) {
                for (int i = 0; i < formular.length(); i++) {
                    if (j == 0) {
                        if (formular.substring(i, i + 1).equals("¬")) {
                            String neg = formular.substring(i, i + 2);
                            Integer bool = neg(matchedcool.get(formular.substring(i + 1, i + 2)));
                            single.put(neg, new Integer[]{i, i + 1, bool});
                        }
                    }
                    if (j == 1) {
                        if (formular.substring(i, i + 1).equals("∩") || formular.substring(i, i + 1).equals("∪")) {
                            Boolean connect = false;
                            boolean bothside = false;
                            for (String Key : single.keySet()) {


                                if (single.get(Key)[0] == i + 1) {


                                    for (String key : single.keySet()) {
                                        if (single.get(key)[1] == i - 1) {
                                            display[g] = key + formular.substring(i, i + 1) + Key;
                                            Integer bool = check(single.get(key)[2], formular.substring(i, i + 1), single.get(Key)[2]);
                                            single.put(display[g], new Integer[]{single.get(key)[0], single.get(Key)[1], bool});
                                            correct[h][g] = bool.toString();
                                            single.remove(key);
                                            single.remove(Key);
                                            connect = true;
                                            bothside = true;
                                            g++;
                                            break;
                                        }


                                    }
                                    if (!bothside) {
                                        display[g] = formular.substring(i - 1, i + 1) + Key;
                                        Integer bool = check(matchedcool.get(formular.substring(i - 1, i)), formular.substring(i, i + 1), single.get(Key)[2]);
                                        single.put(display[g], new Integer[]{i - 1, single.get(Key)[1], bool});
                                        correct[h][g] = bool.toString();
                                        single.remove(Key);
                                        connect = true;
                                        g++;
                                        break;
                                    }

                                }
                                if (single.get(Key) != null && single.get(Key)[1] == i - 1) {
                                    display[g] = Key + formular.substring(i, i + 2);
                                    Integer bool = check(single.get(Key)[2], formular.substring(i, i + 1), matchedcool.get(formular.substring(i + 1, i + 2)));
                                    single.put(display[g], new Integer[]{single.get(Key)[0], i + 1, bool});
                                    correct[h][g] = bool.toString();
                                    single.remove(Key);
                                    g++;
                                    connect = true;
                                    break;
                                }

                            }
                            if (!connect) {
                                display[g] = formular.substring(i - 1, i + 2);
                                Integer bool = check(matchedcool.get(formular.substring(i - 1, i)), formular.substring(i, i + 1), matchedcool.get(formular.substring(i + 1, i + 2)));
                                single.put(display[g], new Integer[]{i - 1, i + 1, bool});
                                correct[h][g] = bool.toString();
                                g++;
                            }
                        }
                    }
                    if (j == 2) {
                        if (i < formular.length() - 4 && formular.substring(i, i + 4).equals("-><-")) {
                            Boolean connect = false;
                            boolean bothside = false;
                            for (String Key : single.keySet()) {


                                if (single.get(Key)[0] == i + 4) {


                                    for (String key : single.keySet()) {
                                        if (single.get(key)[1] == i - 1) {
                                            display[g] = key + formular.substring(i, i + 4) + Key;
                                            Integer bool = check(single.get(key)[2], formular.substring(i, i + 4), single.get(Key)[2]);
                                            single.put(display[g], new Integer[]{single.get(key)[0], single.get(Key)[1], bool});
                                            correct[h][g] = bool.toString();
                                            single.remove(key);
                                            single.remove(Key);
                                            connect = true;
                                            bothside = true;
                                            g++;
                                            break;
                                        }


                                    }
                                    if (!bothside) {
                                        display[g] = formular.substring(i - 1, i + 4) + Key;
                                        Integer bool = check(matchedcool.get(formular.substring(i - 1, i)), formular.substring(i, i + 4), single.get(Key)[2]);
                                        single.put(display[g], new Integer[]{i - 1, single.get(Key)[1], bool});
                                        correct[h][g] = bool.toString();
                                        single.remove(Key);
                                        connect = true;
                                        g++;
                                        break;
                                    }

                                }
                                if (single.get(Key) != null && single.get(Key)[1] == i - 1) {
                                    display[g] = Key + formular.substring(i, i + 5);
                                    Integer bool = check(single.get(Key)[2], formular.substring(i, i + 4), matchedcool.get(formular.substring(i + 4, i + 5)));
                                    single.put(display[g], new Integer[]{single.get(Key)[0], i + 4, bool});
                                    correct[h][g] = bool.toString();
                                    single.remove(Key);
                                    g++;
                                    connect = true;
                                    break;
                                }

                            }
                            if (!connect) {
                                display[g] = formular.substring(i - 1, i + 5);
                                Integer bool = check(matchedcool.get(formular.substring(i - 1, i)), formular.substring(i, i + 4), matchedcool.get(formular.substring(i + 4, i + 5)));
                                single.put(display[g], new Integer[]{i - 1, i + 4, bool});
                                correct[h][g] = bool.toString();
                                g++;
                            }
                        }
                        if (i < formular.length() - 2 && formular.substring(i, i + 2).equals("->")) {
                            Boolean connect = false;
                            boolean bothside = false;
                            for (String Key : single.keySet()) {


                                if (single.get(Key)[0] == i + 2) {


                                    for (String key : single.keySet()) {
                                        if (single.get(key)[1] == i - 1) {
                                            display[g] = key + formular.substring(i, i + 2) + Key;
                                            Integer bool = check(single.get(key)[2], formular.substring(i, i + 2), single.get(Key)[2]);
                                            single.put(display[g], new Integer[]{single.get(key)[0], single.get(Key)[1], bool});
                                            correct[h][g] = bool.toString();
                                            single.remove(key);
                                            single.remove(Key);
                                            connect = true;
                                            bothside = true;
                                            g++;
                                            break;
                                        }


                                    }
                                    if (!bothside) {
                                        display[g] = formular.substring(i - 1, i + 2) + Key;
                                        Integer bool = check(matchedcool.get(formular.substring(i - 1, i)), formular.substring(i, i + 2), single.get(Key)[2]);
                                        single.put(display[g], new Integer[]{i - 1, single.get(Key)[1], bool});
                                        correct[h][g] = bool.toString();
                                        single.remove(Key);
                                        connect = true;
                                        g++;
                                        break;
                                    }

                                }
                                if (single.get(Key) != null && single.get(Key)[1] == i - 1) {
                                    display[g] = Key + formular.substring(i, i + 3);
                                    Integer bool = check(single.get(Key)[2], formular.substring(i, i + 2), matchedcool.get(formular.substring(i + 2, i + 3)));
                                    single.put(display[g], new Integer[]{single.get(Key)[0], i + 2, bool});
                                    correct[h][g] = bool.toString();
                                    single.remove(Key);
                                    g++;
                                    connect = true;
                                    break;
                                }

                            }
                            if (!connect) {
                                display[g] = formular.substring(i - 1, i + 3);
                                Integer bool = check(matchedcool.get(formular.substring(i - 1, i)), formular.substring(i, i + 2), matchedcool.get(formular.substring(i + 2, i + 3)));
                                single.put(display[g], new Integer[]{i - 1, i + 2, bool});
                                correct[h][g] = bool.toString();
                                g++;
                            }

                        }

                    }

                }
            }

        }

    }



public Integer neg(Integer A){
        if (A==1){
            return 0;
        }
        if (A==0){
            return 1;
        }
        return null;
}
public  Integer  check(Integer A ,String sym,Integer B ){
if(sym.equals("∪")){
    Boolean C=transfertobool(A) || transfertobool(B);
    return fromebool(C);
}
if (sym.equals("∩")){
    Boolean C=transfertobool(A) && transfertobool(B);
    return fromebool(C);
}
if (sym.equals("->")){
    Boolean C=!(transfertobool(A)) || transfertobool(B);
    return fromebool(C);
}
if (sym.equals("-><-")){
    Boolean C=transfertobool(check(A,"->",B)) && transfertobool(check(B,"->",A));
    return fromebool(C);
}
return 0;
}
public Boolean transfertobool(Integer bool){
        if (bool==1){
            return true;
        }
        if (bool==0){
            return false;
        }
        return null;
}
public  Integer fromebool(Boolean bool){
    if (bool==true){
        return 1;
    }
    if (bool==false){
        return 0;
    }
    return null;

}



}
