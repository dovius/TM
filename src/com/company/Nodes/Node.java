package com.company.Nodes;

import com.company.Parser;
import com.company.Scope;
import com.company.State;

import static com.company.Parser.buildTabs;

public class Node {

    public State state;
    public String lexem;
    public String string = "";
    public int savedCursor;
    public Node type;
    public Node name;

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

    public void resolveNames( Scope scope ) throws Exception {
        if (state == State.IDENTIFIER) {
            scope.lookup(this.lexem);
            return;
        }
        if (state == State.NUM_CONST) {
            return;
        }
        System.out.println( "Resolve names not implemented in: " + this.getClass() );
    }

    String toString(int offset) {
        return buildTabs(offset) + string ;
    }

    public Node getType() {
        return type;
    }

    public void setType(Node type) {
        this.type = type;
    }

    public Node getName() {
        return name;
    }

    public void setName(Node name) {
        this.name = name;
    }
}