package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Block extends Node {
    public ArrayList<Node> nodes = new ArrayList<>();

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<block> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset + 1 );
        }
        str += buildTabs(offset) + "</block>\n";
        return str;
    }

}
