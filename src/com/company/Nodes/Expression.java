package com.company.Nodes;

import java.util.ArrayList;

public class Expression extends Node {
    public ArrayList<Node> nodes = new ArrayList<>();

    public Expression() {
    }

    public String toString(int offset) {
        for (Node node : nodes) {
            string += node.toString(offset);
        }
        return string;

    }
}
