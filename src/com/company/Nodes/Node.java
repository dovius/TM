package com.company.Nodes;

import com.company.State;

public class Node {

    public State state;
    public String lexem;
    public String string = "";

    public Node() {

    }

    public Node(State state, String lexem) {
        setState(state);
        setLexem(lexem);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getLexem() {
        return lexem;
    }

    public void setLexem(String lexem) {
        this.lexem = lexem;
    }


    String toString(int offset) {
        return string;
    }
}