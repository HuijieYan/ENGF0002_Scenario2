import java.util.ArrayList;

public class Parser {
    public boolean parse(ArrayList<String> postfix_notation) throws Exception{
    /*
        1. For +,*,? symbol, there must be a regex before it
        2. For | and ., there must be two regexes in the postfix_notation array
        3. regex -> regex_stmt regex_stmt (.||)|regex_stmt (+|*|?)
        4. regex_stmt -> character|regex
     */
        String err_message = "False. An error found in regex.";
        int regex_count = 0;
        Lexer lexer = new Lexer();
        for (int i=0;i<postfix_notation.size();i++){
            String character = postfix_notation.get(i);
            if (lexer.is_character(character)){
                regex_count += 1;
            }
            if (lexer.is_operator(character)){
                if (regex_count <= 0){
                    throw new Exception(err_message);
                //no regex found before the operator
                }
            }
            if(character.equals("|")||character.equals(".")){
                if (regex_count <= 1){
                    throw new Exception(err_message);
                    //not enough regexes before | or . symbol
                }
                else{
                    regex_count -= 1;
                    //(a) (b) | to (a|b) where things inside () represents a regex
                }
            }
        }
        return true;
    }
}
