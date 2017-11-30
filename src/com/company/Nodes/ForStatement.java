package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class ForStatement extends Node {

//    <for-loop> ::= "for" "(" <varDeclaration> ";" <expression> ";" <post-pre-fix>  ")" <block-statement>
    public ForStatement() {
        nodes = new ArrayList<>();
    }

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<for-loop> \n";
        str += nodes.get(0).toString(offset+1);
        str += buildTabs(offset+1) + "<condition>\n";
        str += nodes.get(1).toString(offset+2);
        str += buildTabs(offset+1) + "</condition>\n";
        str += buildTabs(offset+1) + "<expression>\n";
        str += nodes.get(2).toString(offset+2);
        str += buildTabs(offset+1) + "</expression>\n";
        str += nodes.get(3).toString(offset+1);
        str += buildTabs(offset) + "</for-loop>\n";
        return str;
    }

    //todo implement for loop????
}
