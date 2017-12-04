package com.company.Nodes;

import com.company.*;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class VarDeclaration extends Node {
    public String name;
    public int localSlot;

    public VarDeclaration() {
        nodes = new ArrayList<Node>();
    }

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<VarDeclaration> \n";
        str += buildTabs(offset+1) + "Type: " + varType + " Name: " + name + "\n";
        if (nodes.size() > 0) {
            str += buildTabs(offset + 1) + "<value>\n";
            for (int i = 0; i < nodes.size(); ++i) {
                str += nodes.get(i).toString(offset + 2);
            }
            str += buildTabs(offset + 1) + "</value>\n";
        }
        str += buildTabs(offset) + "</VarDeclaration>\n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        scope.addVar(name, this);
        for (int i = 0; i < nodes.size(); ++i) {
            nodes.get(i).resolveNames(scope);
        }
    }

    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }
        if (!cmpTypes(varType, nodes)) {
            throw new Exception("bad types in var assignment");
        }
    }

    public void allocateSlots() {
        this.localSlot  = Main.localSlot;
        Main.localSlot += 1;
    }

    public void run( IntermediateRepresentation rep  ) throws Exception {
        if (nodes.size() == 0) {
            return;
        }
        nodes.get(0).run( rep );
        Instruction instr = new Instruction();
        instr.instructionNumber = Instructions.I_SET;
        instr.args.add( String.valueOf( localSlot ) );
        instr.args.add( "=" );
        instr.args.add( "( " + name + " )" );
        rep.addInstr( instr );
    }
}
