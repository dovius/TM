package com.company.Nodes;

import com.company.IntermediateRepresentation;
import com.company.Scope;

import java.util.ArrayList;

public class Expression extends Node {

    public Expression() {
    }

    public String toString(int offset) {
        for (Node node : nodes) {
            string += node.toString(offset);
        }
        return string;
    }

    public void resolveNames(Scope scope) throws Exception {
        if (nodes == null ){
            System.out.println("sth strange...");
        }
        for (int i = 0; i < nodes.size(); ++i) {
            nodes.get(i).resolveNames(scope);
        }
    }

    @Override
    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }
        if (nodes!= null && nodes.size() != 0) {
            varType = nodes.get(0).varType;
        }



    }

    public void allocateSlots() {
        for (Node node : nodes) {
            node.allocateSlots();
        }
    }

    public void run(IntermediateRepresentation rep) throws Exception {
        for (Node node : nodes) {
            node.run(rep);
        }
    }

    public String getValue() {
        return nodes.get(0).getValue();
    }

}
