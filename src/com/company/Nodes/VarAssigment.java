package com.company.Nodes;

import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class VarAssigment extends Node {
    public String name;
    public String type;
    public String index;

    public VarAssigment() {
        nodes = new ArrayList<Node>();
    }

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<VarAssigment> \n";
        str += buildTabs(offset+1) + "Name: " + name + "\n";
        if (nodes.size() == 2) {
            str += buildTabs(offset + 1) + "<index>\n";
                str += nodes.get(1).toString(offset + 2);
            str += buildTabs(offset + 1) + "</index>\n";
        }
        if (nodes.size() > 0) {
            str += buildTabs(offset + 1) + "<value>\n";
                str += nodes.get(0).toString(offset + 2);

            str += buildTabs(offset + 1) + "</value>\n";
        }
        str += buildTabs(offset) + "</VarAssigment>\n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        target = scope.lookup(name);
        for (int i = 0; i < nodes.size(); ++i) {
            nodes.get(i).resolveNames(scope);
        }
    }

    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }
        if (!cmpTypes(target.varType, nodes)) {
            throw new Exception("bad types in var assignment");
        }

    }

}
