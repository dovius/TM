package com.company.Nodes;

import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class FunctionCall extends Node {
    public String name;

    public FunctionCall() {
    }

    public FunctionCall(String name) {
        this.name = name;
    }

    public FunctionCall(String name, Node parameters) {
        this.name = name;
        nodes.add(parameters);
    }

    @Override
    public String toString(int offset) {

        String str = buildTabs(offset) + "<FunctionCall> \n";
        str += buildTabs(offset + 1) + "Name: " + name + "\n";
        if (nodes != null) {
            str += buildTabs(offset + 1) + "<parameters> \n";
            if (nodes.size() > 0) {
                str += nodes.get(0).toString(offset + 2);
            }
            str += buildTabs(offset + 1) + "</parameters>\n";
        }
        str += buildTabs(offset) + "</FunctionCall> \n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        scope.lookup(name);
        for (int i = 0; i < nodes.size(); ++i) {
            nodes.get(i).resolveNames(scope);
        }
    }
}
