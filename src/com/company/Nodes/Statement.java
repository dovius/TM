package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Statement extends Node {
    public Statement() {
    }
    public ArrayList<Node> nodes = new ArrayList<>();


    public String toString(int offset) {
        String str = buildTabs(offset) + "<statement> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset+1);
        }
        str += buildTabs(offset) + "</statement>\n";
        return str;
    }
}