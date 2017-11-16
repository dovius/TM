package com.company.Nodes;

import static com.company.Parser.buildTabs;

public class Operator extends Node {
    String operator;

    public Operator(String operator) {
        this.operator = operator;
    }


    public String toString(int offset) {
        string = buildTabs(offset) + "Operator: " + operator + "\n";
        return string;
    }

}
