package com.company.Nodes;

import com.company.*;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class ArrayDeclaration extends Node {
    public String typeSpecifier;
    public String size;
    public String identifier;
    public int localSlot;
    public int localStartSlot;

    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public ArrayDeclaration() {

    }

    public ArrayDeclaration(String typeSpecifier, String size, String identifier) {
        this.typeSpecifier = typeSpecifier;
        this.size = size;
        this.identifier = identifier;
        varType = typeSpecifier + "Array";
    }

    public ArrayDeclaration(String typeSpecifier, String identifier) {
        this.typeSpecifier = typeSpecifier;
        this.identifier = identifier;
        varType = typeSpecifier+ "Array";;
    }

    public String toString(int offset) {
        String str = new String();
        str += buildTabs(offset) + "Array Type: " + typeSpecifier + " Size: " + size + " Name: " + identifier + "\n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        scope.addVar(identifier, this);
        varType = typeSpecifier ;
        target = (ArrayDeclaration) scope.lookup(identifier);

    }

    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }
        if (!cmpTypes(varType, nodes)) {
            throw new Exception("bad types in array assignment");
        }
    }

    public void allocateSlots() {
        this.localSlot = Main.localSlot;
        localStartSlot = localSlot;
        if (size == null ) {
            size = String.valueOf(Interpreter.ARR_SIZE);
        }
        Main.localSlot += Integer.valueOf(size) ;
    }

    public void run(IntermediateRepresentation rep) throws Exception {
    }
}
