package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class WhileStatement extends Node {
    public ArrayList<Node> nodes;

    public WhileStatement(Expression expression, Block block) {
        nodes = new ArrayList<Node>();
        nodes.add(expression);
        nodes.add(block);
    }

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<while-loop> \n";
        str += buildTabs(offset+1) + "<condition>\n";
        str += nodes.get(0).toString(offset+2);
        str += buildTabs(offset+1) + "</condition>\n";
        str += nodes.get(1).toString(offset+2);
        str += buildTabs(offset) + "</while-loop>\n";
        return str;
    }
}
