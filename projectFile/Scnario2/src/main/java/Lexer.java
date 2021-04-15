import java.util.ArrayList;

public class Lexer {
    private ArrayList<String> accepted_special_characters = new ArrayList<>();
    private ArrayList<Character> accepted_operators = new ArrayList<>();
    private ArrayList<Character> accepted_brackets = new ArrayList<>();

    public Lexer(){
        accepted_special_characters.add("/*");
        accepted_special_characters.add("/_");
        accepted_special_characters.add("/?");
        accepted_special_characters.add("/+");
        accepted_special_characters.add("/|");
        accepted_special_characters.add("/[");
        accepted_special_characters.add("/]");
        accepted_special_characters.add("/(");
        accepted_special_characters.add("/)");
        accepted_special_characters.add("/^");
        accepted_special_characters.add("/\"");
        //the special symbols which will be considered as characters

        accepted_operators.add('?');
        accepted_operators.add('*');
        accepted_operators.add('+');
        accepted_operators.add('^');

        accepted_brackets.add('(');
        accepted_brackets.add(')');
        accepted_brackets.add('[');
        accepted_brackets.add(']');
    }

    private boolean char_is_character(char character){
    //this function find out whether the given character is accepted as
    //a character
        if (accepted_operators.contains(character)||accepted_brackets.contains(character)||character == '.'||character == '|'){
        //if the given character is an operator or brackets or a concatenation symbol
            return false;
        }
        return true;
        //otherwise it is an accepted character
    }

    public boolean is_character(String ch){
        if (ch.length() == 1){
            return char_is_character(ch.charAt(0));
        }
        return accepted_special_characters.contains(ch);
        //for special characters as ch would be "/+" etc.
        //Such string has length of 2
    }

    public boolean is_operator(String character){
        if (character.length() == 1){
            return accepted_operators.contains(character.charAt(0));
        }
        return false;
    }

    public boolean is_bracket(String character){
        if (character.length() == 1){
            return accepted_brackets.contains(character.charAt(0));
        }
        return false;
    }

    public int get_precedence(String op){
    /*the precedence of this regex reader would be: (from high to low)
        1.+,*,?,^
        2. .(concatenation symbol)
        3. |
     */
        if(op.equals(".")){
            return 2;
        }
        if (op.equals("|")){
            return 1;
        }
        if(accepted_operators.contains(op.charAt(0))){
            return 3;
        }
        return -1;
    }

}
