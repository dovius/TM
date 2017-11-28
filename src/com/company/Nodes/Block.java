package com.company.Nodes;

import com.company.*;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Block extends Node {
    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<block> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset + 1);
        }
        str += buildTabs(offset) + "</block>\n";
        return str;
    }


    public void resolveNames(Scope scope) throws Exception {
        for (int i = 0; i < nodes.size(); ++i) {
            nodes.get(i).resolveNames(scope);
        }
    }

    public void allocateSlots() {
        for (Node node : nodes) {
            node.allocateSlots();
        }
    }

    @Override
    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }

    }

    public void run(IntermediateRepresentation rep) throws Exception {
        for (Node node : nodes) {
            node.run(rep);
        }
    }


}
