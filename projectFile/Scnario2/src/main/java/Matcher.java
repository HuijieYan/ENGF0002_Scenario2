import java.util.ArrayList;

public class Matcher {
    private String regex_stored;
    private NFA compiled_nfa;
    private ArrayList<State> current_states = new ArrayList<>();
    //a list of states which the matcher can be in
    private ArrayList<State> next_states = new ArrayList<>();
    //a list of states which the matcher can be in after an input

    public Matcher(){
        regex_stored = "";
        State start_state = new State(false);
        State end_state = new State(true);
        start_state.add_transition(end_state,"");
        //construct an empty nfa with epsilon transition only
        compiled_nfa = new NFA(start_state,end_state);
        //initialisation
    }

    public void set_nfa(String regex) throws Exception{
        if (regex_stored.equals(regex)){
            return;
        }
        NFATranslator tr = new NFATranslator();
        compiled_nfa = tr.translate_regex(regex);
        regex_stored = regex;
    }

    public boolean match(String regex,String input) throws Exception{
        set_nfa(regex);
        initialise_start_state();

        for (int i=0;i<input.length();i++){
            match_char(input.substring(i,i+1));
            current_states = new ArrayList<>(next_states);
        }
        for (State state:next_states){
            if (state.is_accepted()){
                return true;//return true if the states we got has accepted state
            }
        }
        return false;
    }

    private void initialise_start_state(){
    //finds all states at the end of the epsilon closure
        current_states.clear();
        next_states.clear();
        match_epsilon_state(compiled_nfa.get_start_state());
        current_states = new ArrayList<>(next_states);
    }


    private void match_char(String ch){
        next_states.clear();
        for (State state:current_states){
            ArrayList<State> reachable_state = state.reachable_states(ch);
            reachable_state.addAll(state.reachable_states("ANY"));
            //all states which this state can go to with the character given
            for (State reached_state:reachable_state){
                match_epsilon_state(reached_state);
            }
        }
    }

    private void match_epsilon_state(State state){
    //find the states which are the end of the epsilon closure, which means they would
    //not have any epsilon transitions
    //because a state can only have epsilon transition or character transition, we add states
    //which has character transition only
        ArrayList<State> reachable_state = state.reachable_states("");
        if (reachable_state.size()==0){
            next_states.add(state);
        }
        for (State reached_state:reachable_state){
            match_epsilon_state(reached_state);
        }
    }
}
