import java.util.ArrayList;

public class State {
    private boolean is_accepted_state;
    private ArrayList<ArrayList<State>> transition_destination;
    //records the destination states of each symbols in transition
    //uses the index of symbols in transition_symbols as index for itself
    private ArrayList<String> transition_symbols;
    //stores the symbols which the state can transition to
    //it also acts as keys in a dictionary data structure

    public State(boolean is_accepted_state){
        this.is_accepted_state = is_accepted_state;
        transition_destination = new ArrayList<>();
        transition_symbols = new ArrayList<>();
    }

    public void add_transition(State destination,String symbol){
        if (!transition_symbols.contains(symbol)){
        //symbol epsilon is represented as "" in this program
            transition_symbols.add(symbol);
        }
        int index = transition_symbols.indexOf(symbol);
        if (transition_destination.size()<=index){
            transition_destination.add(new ArrayList<>());
        }
        transition_destination.get(index).add(destination);
        //add the destination state to the list
    }

    public void set_is_accepted_state(boolean value){
        is_accepted_state = value;
    }

    private boolean can_transit(String ch){
    //indicates whether the state can transit to a
    //state using the given character from string
        return transition_symbols.contains(ch);
    }

    public ArrayList<State> reachable_states(String ch){
    //returns a list of all reachable states from this state using
    //the given character from string
        if (can_transit(ch)){
            int index = transition_symbols.indexOf(ch);
            return transition_destination.get(index);
        }
        return new ArrayList<>();
    }

    public boolean is_accepted(){
        return is_accepted_state;
    }
}
