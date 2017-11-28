package com.company.Nodes;

import com.company.Main;
import com.company.Scope;

import java.util.ArrayList;

public class ParameterDeclaration extends Node {
    public String typeSpecifier;
    public String indentifier;
    public int localSlot;

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = new String();
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset );
        }
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        scope.addVar(((Parameter)nodes.get(0)).name.lexem, nodes.get(0));
        for (int i = 1; i < nodes.size(); i++ ){
            nodes.get(i).resolveNames(scope);
        }
    }

    public void allocateSlots() {
        this.localSlot  = Main.localSlot;
        Main.localSlot += 1;
    }
}
