package com.company.Nodes;

import com.company.*;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class ArrayIndex extends Node {
    public String name;

    public ArrayIndex() {
        nodes = new ArrayList<Node>();
    }

    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public void resolveNames(Scope scope) throws Exception {
        if (nodes == null) {
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
        if (nodes != null && nodes.size() != 0) {
            varType = nodes.get(0).varType;
        }
    }


    public void run(IntermediateRepresentation rep) throws Exception {
        Instruction instr = new Instruction();
        nodes.get(0).run(rep);
        instr.instructionNumber = Instructions.I_GET;

        instr.args.add("( " + String.valueOf(name + " )"));
        rep.addInstr(instr);

    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<Array> \n";
        str += buildTabs(offset + 1) + "Name: " + name + "\n";
        str += buildTabs(offset + 1) + "<index>\n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset + 2);
        }
        str += buildTabs(offset + 1) + "</index>\n";
        str += buildTabs(offset) + "</Array>\n";
        return str;
    }

}
