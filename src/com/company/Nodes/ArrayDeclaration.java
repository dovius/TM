package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class ArrayDeclaration extends Node {
    public ArrayList<Node> nodes = new ArrayList<>();
    public String typeSpecifier;
    public String size;
    public String identifier;

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public ArrayDeclaration() {

    }
    public ArrayDeclaration(String typeSpecifier, String size, String identifier) {
        this.typeSpecifier = typeSpecifier;
        this.size = size;
        this.identifier = identifier;
    }

    public String toString(int offset) {
        String str = new String();
        str +=  buildTabs(offset) + "Array Type: " + typeSpecifier + " Size: " + size + " Name: " +identifier + "\n";
        return str;
    }
}
