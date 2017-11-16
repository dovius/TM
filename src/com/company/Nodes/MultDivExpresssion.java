package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class MultDivExpresssion extends Expression {
    public String operation;
    public Node left;
    public Node right;

    public MultDivExpresssion(String operation, Node left, Node right) {
        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    public String toString(int offset) {

        String str = buildTabs(offset) + "<MultDivExpression> \n";
        str += buildTabs(offset + 1) + "Operator: " + operation + "\n";
        str += buildTabs(offset+1) + "<left> \n";
        str += left.toString(offset+2);
        str += buildTabs(offset+1) + "</left>\n";
        str += buildTabs(offset+1) + "<right> \n";
        str += right.toString(offset+2);
        str += buildTabs(offset+1) + "</right> \n";
        str += buildTabs(offset) + "</MultDivExpression> \n";

        return str;
    }

}
