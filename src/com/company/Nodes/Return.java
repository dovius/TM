package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Return extends Node {
    public ArrayList<Node> nodes;

    public Return() {
        nodes = new ArrayList<Node>();
    }

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<return> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset+1);
        }
        str += buildTabs(offset) + "</return> \n";
        return str;
    }
}
