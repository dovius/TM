package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class SelectionStatement extends Node {

    public Node condition;
    public ArrayList<Node> ifNodes;
    public ArrayList<Node> elseNodes;


    public SelectionStatement() {
        nodes = new ArrayList<Node>();
    }

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<conditionalStatement> \n";
        str += buildTabs(offset+1) + "<condition>\n";
        str += condition.toString(offset+2);
        str += buildTabs(offset+1) + "</condition>\n";
        str += buildTabs(offset+1) + "<if-block>\n";
        for (int i = 0; i < ifNodes.size(); ++i) {
            str += ifNodes.get(i).toString(offset+2);
        }
        str += buildTabs(offset+1) + "</if-block>\n";
        if (elseNodes!=null) {
            str += buildTabs(offset+1) + "<else-block>\n";
            for (int i = 0; i < elseNodes.size(); ++i) {
                str += elseNodes.get(i).toString(offset+2);
            }
            str += buildTabs(offset+1) + "</else-block>\n";
        }
        str += buildTabs(offset) + "</conditionalStatement>\n";
        return str;
    }
}
