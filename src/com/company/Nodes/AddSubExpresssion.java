package com.company.Nodes;

import com.company.Scope;

import static com.company.Parser.buildTabs;

public class AddSubExpresssion extends Expression {
    public String operation;
    public Node left;
    public Node right;

    public AddSubExpresssion(String operation, Node left, Node right) {
        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    public String toString(int offset) {

        String str = buildTabs(offset) + "<AddSubExpression> \n";
        str += buildTabs(offset + 1) + "Operator: " + operation + "\n";
        str += buildTabs(offset+1) + "<left> \n";
        str += left.toString(offset+2);
        str += buildTabs(offset+1) + "</left>\n";
        str += buildTabs(offset+1) + "<right> \n";
        str += right.toString(offset+2);
        str += buildTabs(offset+1) + "</right> \n";
        str += buildTabs(offset) + "</AddSubExpression> \n";

        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        left.resolveNames(scope);
        right.resolveNames(scope);
    }

}
