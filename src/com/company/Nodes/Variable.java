package com.company.Nodes;

import static com.company.Parser.buildTabs;

public class Variable extends Node{
    public Node type;
    public Node name;
    public String string;


    public Variable(Node type, Node name) {
        setName(name);
        setType(type);
    }

    public Variable() {

    }


    public String toString(int offset) {
        string = buildTabs(offset) + "Type: " + type.getLexem() + " Name: " + name.getLexem() + "\n";
        return string;
    }

    public Node getType() {
        return type;
    }

    public void setType(Node type) {
        this.type = type;
    }

    public Node getName() {
        return name;
    }

    public void setName(Node name) {
        this.name = name;
    }
}
