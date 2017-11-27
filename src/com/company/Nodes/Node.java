package com.company.Nodes;

import com.company.Parser;
import com.company.Scope;
import com.company.State;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Node {

    public State state;
    public String lexem;
    public String string = "";
    public int savedCursor;
    public Node type;
    public Node name;
    public Node target;
    public String varType;
    public ArrayList<Node> nodes = new ArrayList<>();

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
            target = scope.lookup(this.lexem);
            return;
        }
        if (state == State.NUM_CONST || state == State.STRING) {
            return;
        }
        System.out.println( "Resolve names not implemented in: " + this.getClass() );
    }

    public void checkTypes() throws Exception {
        if (state == State.STRING) {
            varType = "string";
            return;
        }
        if (state == State.NUM_CONST) {
            varType = "int";
            return;
        }
        if (state == State.IDENTIFIER) {
            varType = target.varType;
            return;
        }
        System.out.println( "Check types not implement in: " + this.getClass() );
    }

    public boolean cmpTypes(String type, ArrayList<Node> nodes) {
        boolean missmatch = false;

        for (Node node : nodes) {
            if (node.varType == null || !type.equals(node.varType)) {
                missmatch = true;
            }
            System.out.println(type + " -> " + node.varType);
        }

        return !missmatch;
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