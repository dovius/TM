package com.company.Nodes;

import java.util.ArrayList;

public class ParameterDeclaration extends Node {
    public ArrayList<Node> nodes = new ArrayList<>();
    public String typeSpecifier;
    public String indentifier;

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = new String();
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset + 1);
        }
        return str;
    }
}
