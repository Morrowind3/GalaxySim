package core;

import java.util.EmptyStackException;
import java.util.Stack;

public class MementoKeeper {
    private final Stack<Memento> history = new Stack<>();

    public void push(Memento memento) {
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