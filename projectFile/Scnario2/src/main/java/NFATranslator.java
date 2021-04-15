import java.util.ArrayList;

public class NFATranslator {
//Translation of regex is based on Thompson's construction
//from https://en.wikipedia.org/wiki/Thompson%27s_construction
    private ArrayList<NFA> nfa_stack = new ArrayList<>();
    //A stack of translated nfa's, at the end of the translation
    //the result is the top nfa on the stack. That NFA represents the
    //actual nfa which is going to be used for regex validation.
    private ArrayList<String> postfix_notation = new ArrayList<>();
    private ArrayList<String> operators = new ArrayList<>();

    public NFA translate_regex(String regex) throws Exception{
        if(regex.length() == 0){
            return construct_single_symbol("");
            //a NFA with epsilon transition
        }
        //the idea of using a postfix regex has referenced the
        //approach from https://swtch.com/~rsc/regexp/regexp1.html
        Parser parser = new Parser();
        convert_regex_to_postfix_notation(regex);
        parser.parse(postfix_notation);
        //checks whether the regex is syntactically correct
        for (int i=0;i<postfix_notation.size();i++){
            String token = postfix_notation.get(i);
            if (token.equals(".")){
                NFA left = nfa_stack.get(nfa_stack.size()-2);
                NFA right = nfa_stack.get(nfa_stack.size()-1);
                nfa_stack.remove(nfa_stack.size()-1);
                nfa_stack.remove(nfa_stack.size()-1);
                nfa_stack.add(concatenation(left,right));
            }
            else if(token.equals("|")){
                NFA left = nfa_stack.get(nfa_stack.size()-2);
                NFA right = nfa_stack.get(nfa_stack.size()-1);
                nfa_stack.remove(nfa_stack.size()-1);
                nfa_stack.remove(nfa_stack.size()-1);
                nfa_stack.add(union(left,right));
            }
            else if(token.equals("*")){
                NFA top = nfa_stack.get(nfa_stack.size()-1);
                nfa_stack.remove(nfa_stack.size()-1);
                nfa_stack.add(construct_star(top));
            }
            else if (token.equals("+")){
            //because a+ = a.a*
                NFA top = nfa_stack.get(nfa_stack.size()-1);
                nfa_stack.remove(nfa_stack.size()-1);
                nfa_stack.add(concatenation(top,construct_star(top)));
            }
            else if (token.equals("?")){
                NFA top = nfa_stack.get(nfa_stack.size()-1);
                nfa_stack.remove(nfa_stack.size()-1);
                nfa_stack.add(construct_zero_or_one(top));
            }
            else if(token.equals("_")){
                nfa_stack.add(construct_single_symbol("ANY"));
                //meaning that as long as the character is not empty, the nfa would
                //accept that character
            }
            else{
                if (token.length() == 2){
                    token = token.substring(1);
                    //only get the character after / symbol
                }
                nfa_stack.add(construct_single_symbol(token));
            }
        }
        return nfa_stack.get(nfa_stack.size()-1);
    }

    private void convert_regex_to_postfix_notation(String regex) throws Exception{
        regex = insert_concatenation_symbol(regex);
        for (int i=0;i<regex.length();i++){
            String current_char = get_char(regex,i);
            if (current_char.length() == 2){
                ///meaning the character extracted is a special character
                i++;
            }
            postfix_deal_with_char(current_char);
        }
        while (operators.size()>0){
            postfix_notation.add(operators.get(operators.size()-1));
            operators.remove(operators.size()-1);
            //add the rest of the operators back to notation when the scanning is over
        }
        String result = postfix_notation.toString();
    }

    private void postfix_deal_with_char(String ch) throws Exception{
    //This function converts the operators to postfix form
        Lexer lexer = new Lexer();
        if (lexer.is_character(ch)){
            postfix_notation.add(ch);
            //append if the character is an operand
        }
        if (lexer.is_operator(ch)||ch.equals(".")||ch.equals("|")){
            deal_with_operator(ch);
        }
        if (lexer.is_bracket(ch)){
            deal_with_bracket(ch);
        }
    }

    private void deal_with_operator(String ch){
        Lexer lexer = new Lexer();
        int precedence = lexer.get_precedence(ch);
        if (operators.size()>=1){
            String op = operators.get(operators.size()-1);
            if (precedence>lexer.get_precedence(op)){
                operators.add(ch);
                //append if the operator has higher precedence than the top of the stack
            }
            else{
                while (precedence <= lexer.get_precedence(op)){
                //add operators to the result until an operator with lower precedence than
                //scanned operator is found
                    postfix_notation.add(op);
                    operators.remove(operators.size()-1);
                    if (operators.size() == 0){
                        break;
                    }
                    op = operators.get(operators.size()-1);
                }
                operators.add(ch);
            }
        }
        else{
            operators.add(ch);
        }
    }

    private void deal_with_bracket(String ch) throws Exception{
    //checking whether the number of brackets matches
        String err_message = "False. Unmatched bracket in the regex given.";
        if (ch.equals("(")||ch.equals("[")){
            operators.add(ch);
        }
        if (ch.equals(")")){
            while (operators.size()>0){
                String top = operators.get(operators.size()-1);
                if (top.equals("(")){
                    operators.remove(operators.size()-1);
                    return;
                }
                //what if ([_^a]a)+ found?
                else if(top.equals("[")){
                    throw new Exception(err_message);
                    //error:unmatched bracket
                }
                else{
                    postfix_notation.add(top);
                    operators.remove(operators.size()-1);
                }
            }
            throw new Exception(err_message);
            //error:unmatched bracket
        }
        if (ch.equals("]")){
            while (operators.size()>0){
                String top = operators.get(operators.size()-1);
                if (top.equals("[")){
                    operators.remove(operators.size()-1);
                    return;
                }
                else if(top.equals("(")){
                    throw new Exception(err_message);
                    //error:unmatched bracket
                }
                else{
                    postfix_notation.add(top);
                    operators.remove(operators.size()-1);
                }
            }

            throw new Exception(err_message);
            //error:unmatched bracket
        }
    }

    private String insert_concatenation_symbol(String regex){
    //Need to insert concatenation symbol to recognise concatenations
    //in regex, we use . as the symbol in this program.
        if (regex.length()<=1){
            return regex;//no need insert anything
        }
        StringBuilder processed_regex = new StringBuilder();
        Lexer lexer = new Lexer();
        //the number of . symbol appended
        for (int i = 0;i<regex.length()-1;i++){
            String current_char = get_char(regex,i);
            if (current_char.length() == 2){
            ///meaning the character extracted is a special character
                i++;
            }
            String next_char = get_char(regex,i+1);
            processed_regex.append(current_char);

            if (lexer.is_character(current_char)){
                if (lexer.is_character(next_char)||next_char.equals("(")||next_char.equals("[")){
                    processed_regex.append(".");
                    //ab = a.b
                    //a(abc) = a.(a.b.c)
                }
                // ca|b = c.a|b
                //no concatenation symbol is inserted before or after |
            }
            if (current_char.equals(")")||current_char.equals("]")){
                if (lexer.is_character(next_char)||current_char.equals("]")){
                    processed_regex.append(".");
                    //(ab)c = (a.b).c
                }
            }
            if (lexer.is_operator(current_char)&&!current_char.equals("^")){
                if (next_char.equals("(")||next_char.equals("[")|| lexer.is_character(next_char)){
                    processed_regex.append(".");
                    //a*(ab) = a*.(a.b)
                    //a+b = a+.b
                }
            }
            if (i == regex.length()-2){
                processed_regex.append(next_char);
            }
        }
        return processed_regex.toString();
    }

    private String get_char(String regex,int index){
    //returning a string because a character can be "\*" etc.
        if (index >= regex.length()){
            return "";//as an invalid character
        }
        if (regex.charAt(index) == '/'){
            return regex.substring(index,index+2);
            //return the special character
        }
        else{
            return regex.substring(index,index+1);
            //return normal character
        }
    }

    private NFA construct_single_symbol(String symbol){
    //This constructs a basic nfa for an regex expression of
    //the given symbol only.
        State start_state = new State(false);
        State end_state = new State(true);
        start_state.add_transition(end_state,symbol);
        NFA nfa = new NFA(start_state,end_state);
        return nfa;
    }

    private NFA union(NFA left_side,NFA right_side){
    //to construct NFA when translating "|" in regex
        State start_state = new State(false);
        State end_state = new State(true);

        left_side.definalise_end_state();
        right_side.definalise_end_state();
        start_state.add_transition(left_side.get_start_state(),"");
        start_state.add_transition(right_side.get_start_state(),"");

        State left_final_state = left_side.get_end_state();
        State right_final_state = right_side.get_end_state();
        left_final_state.add_transition(end_state,"");
        right_final_state.add_transition(end_state,"");
        //Following Thompson's construction, definalise left and right nfa
        //and create a new accepting state and start state with epsilon transition
        //link to them.

        return new NFA(start_state,end_state);
    }

    private NFA concatenation(NFA left,NFA right){
    //To construct NFA when two NFAs are concatenated
    //eg. regex = ab, actual representation is NFA(a)
    //concatenate with NFA(b)
        left.definalise_end_state();
        left.get_end_state().add_transition(right.get_start_state(), "");
        //Basically just add an epsilon transition from end state at left to start
        //state at right.
        //Did not create a new state replacing the end state of left because this state
        //is being pointed by the states before it, by removing this state those pointers
        //would point to nothing instead.
        return new NFA(left.get_start_state(),right.get_end_state());
    }

    private NFA construct_star(NFA nfa){
    //To construct NFA when a Kleene star is found
        State start_state = new State(false);
        State end_state = new State(true);
        start_state.add_transition(nfa.get_start_state(), "");
        start_state.add_transition(end_state,"");
        nfa.definalise_end_state();

        State nfa_end_state = nfa.get_end_state();
        nfa_end_state.add_transition(end_state,"");
        nfa_end_state.add_transition(nfa.get_start_state(),"");
        return new NFA(start_state,end_state);
    }

    private NFA construct_zero_or_one(NFA nfa){
        State start_state = new State(false);
        State end_state = new State(true);
        nfa.definalise_end_state();
        start_state.add_transition(nfa.get_start_state(),"");
        start_state.add_transition(end_state,"");
        nfa.get_end_state().add_transition(end_state,"");
        //? is a * without the end state of the nfa back to the start
        return new NFA(start_state,end_state);
    }
}
