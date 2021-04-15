public class NFA {
//represents a NFA, uses arraylist
    private State start_state;
    private State end_state;

    public NFA(State start_state,State end_state){
        this.start_state = start_state;
        this.end_state = end_state;
    }

    public State get_start_state(){return start_state;}

    public State get_end_state(){return end_state;}

    public void definalise_end_state(){
        end_state.set_is_accepted_state(false);
    }

}
