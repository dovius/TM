package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class PostPreFix extends Node {
    public String operator;
    public String identifier;
    public boolean isPrefix;

    public PostPreFix() {
        nodes = new ArrayList<Node>();
    }

    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = "";
        str = buildTabs(offset) + (isPrefix ? "<prefix>" : "<postfix>") + "\n";
        str += buildTabs(offset + 1) + "Operator: '" + operator + "' Name: " + identifier + "\n";
        str += buildTabs(offset) + (isPrefix ? "</prefix>" : "</postfix>") + "\n";
        return str;
    }
}
