package com.company.Nodes;

import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Return extends Node {

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

    public void resolveNames(Scope scope) throws Exception {
        if (nodes!=null) {
            for (int i = 0; i < nodes.size(); i++) {
                nodes.get(i).resolveNames(scope);
            }
        }
    }
}
