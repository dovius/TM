package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class ArrayIndex extends Node {
    public ArrayList<Node> nodes;
    public String name;

    public ArrayIndex() {
        nodes = new ArrayList<Node>();
    }

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<Array> \n";
        str += buildTabs(offset+1) + "Name: " + name + "\n";
        str += buildTabs(offset+1) + "<index>\n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset+2);
        }
        str += buildTabs(offset+1) + "</index>\n";
        str += buildTabs(offset) + "</Array>\n";
        return str;
    }

}
