package com.company.Nodes;

import com.company.Main;
import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class ParameterList extends Node {
    public int localSlot;


    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<parameters-list> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += buildTabs(offset + 1) + "<parameter> \n";
            str += nodes.get(i).toString(offset + 2);
            str += buildTabs(offset + 1) + "</parameter> \n";
        }
        str += buildTabs(offset) + "</parameters-list>\n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        for (int i = 0; i < nodes.size(); i++ ){
            nodes.get(i).resolveNames(scope);
        }
    }

    public void checkTypes() {
    }

    public void allocateSlots() {
        for( int i = 0; i < nodes.size(); i++ ) {
            nodes.get( i ).allocateSlots();
        }
    }
}
