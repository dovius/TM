package com.company.Nodes;

import com.company.Instruction;
import com.company.Instructions;
import com.company.IntermediateRepresentation;
import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Return extends Node {

    public Return() {
        nodes = new ArrayList<Node>();
    }

    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<return> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset + 1);
        }
        str += buildTabs(offset) + "</return> \n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        if (nodes != null) {
            for (int i = 0; i < nodes.size(); i++) {
                nodes.get(i).resolveNames(scope);
            }
        }
    }

    @Override
    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }
    }

    public void run(IntermediateRepresentation rep) throws Exception {
        if (nodes != null && nodes.size() != 0) {
            nodes.get(0).run(rep);
            Instruction instr = new Instruction();
            instr.instructionNumber = Instructions.I_RET_VALUE;
            instr.args.add(nodes.get(0).varType);
            instr.args.add(nodes.get(0).getValue());
            rep.addInstr(instr);
        }
    }
}
