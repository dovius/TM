package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Program extends Node {
    public ArrayList<Node> nodes;

    public Program() {
        nodes = new ArrayList<Node>();
    }

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<program> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(1);
        }
        str += buildTabs(0) + "</program>";
        return str;
    }

}
