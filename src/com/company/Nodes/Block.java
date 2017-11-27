package com.company.Nodes;

import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Block extends Node {
    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<block> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset + 1);
        }
        str += buildTabs(offset) + "</block>\n";
        return str;
    }


    public void resolveNames(Scope scope) throws Exception {
        Scope innerFunctionScope = new Scope(scope, "innerFunctionScope");
        for (int i = 0; i < nodes.size(); ++i) {
            nodes.get(i).resolveNames(innerFunctionScope);
        }
    }

    @Override
    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }

    }

}
