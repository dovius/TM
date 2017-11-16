package com.company.Nodes;

import com.company.Parser;
import com.company.State;

import static com.company.Parser.buildTabs;

public class Node {

    public State state;
    public String lexem;
    public String string = "";
    public int savedCursor;

    public Node() {
        savedCursor = Parser.cursor;
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

    public Node backtrack() {
        Parser.cursor = savedCursor;
        return null;
    }

    String toString(int offset) {
        return buildTabs(offset) + string ;
    }
}