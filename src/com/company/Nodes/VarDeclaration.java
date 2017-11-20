package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class VarDeclaration extends Node {
    public ArrayList<Node> nodes;
    public String name;
    public String type;

    public VarDeclaration() {
        nodes = new ArrayList<Node>();
    }

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<VarDeclaration> \n";
        str += buildTabs(offset+1) + "Type: " + type + " Name: " + name + "\n";
        if (nodes.size() > 0) {
            str += buildTabs(offset + 1) + "<value>\n";
            for (int i = 0; i < nodes.size(); ++i) {
                str += nodes.get(i).toString(offset + 2);
            }
            str += buildTabs(offset + 1) + "</value>\n";
        }
        str += buildTabs(offset) + "</VarDeclaration>\n";
        return str;
    }

}
