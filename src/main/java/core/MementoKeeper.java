package core;

import java.util.EmptyStackException;
import java.util.Stack;

public class MementoKeeper {
    private final Stack<Memento> history = new Stack<>();

    public int historySize(){
        return history.size();
    }
    public void push(Memento memento) {
        history.add(memento);
        System.out.println("Saved memento #" + history.size());
    }

    public void undo() {
        try{
            Memento memento = history.pop();
            memento.restore();
        } catch (EmptyStackException ignored){
        }
    }
}