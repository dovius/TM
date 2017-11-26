package com.company.Nodes;

import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class IOStatement extends Node {
    public String identifier;
    public boolean isReadStatemnt;

    public IOStatement() {
        nodes = new ArrayList<Node>();
    }

    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str;
        if (isReadStatemnt) {
            str = buildTabs(offset) + "<read> \n";
            str += buildTabs(offset + 1) + "Name : " + identifier + "\n";
            str += buildTabs(offset) + "</read>\n";
        } else {
            str = buildTabs(offset) + "<write> \n";
            for (int i = 0; i < nodes.size(); ++i) {
                str += nodes.get(i).toString(offset + 1);
            }
            str += buildTabs(offset) + "</write>\n";
        }
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        if (!isReadStatemnt) {
            for (int i = 0; i < nodes.size(); ++i) {
                nodes.get(i).resolveNames(scope);
            }
        }
        else {
            scope.lookup(identifier);
        }
    }

}
