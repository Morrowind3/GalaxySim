package core;

import java.util.EmptyStackException;
import java.util.Stack;

public class MementoKeeper {
    private final Stack<Memento> history = new Stack<>();

    public int historySize(){
        return history.size();
    }
    public void push(Memento memento) {

        System.out.println("Saved memento #" + history.size());
        history.add(memento);
    }

    public void undo() {
        try{
            Memento menento = history.pop();
            menento.restore();
        } catch (EmptyStackException ignored){
        }
    }

}