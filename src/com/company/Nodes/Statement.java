package com.company.Nodes;

import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Statement extends Node {
    public Statement() {
    }


    public String toString(int offset) {
        String str = buildTabs(offset) + "<statement> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset + 1);
        }
        str += buildTabs(offset) + "</statement>\n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        for (int i = 0; i < nodes.size(); ++i) {
            nodes.get(i).resolveNames(scope);
        }
    }

    @Override
    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }
    }
}
