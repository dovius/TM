package com.company.Nodes;

import com.company.*;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class WhileStatement extends Node {
    public Label jumpOutside;

    public WhileStatement(Expression expression, Block block) {
        nodes = new ArrayList<Node>();
        nodes.add(expression);
        nodes.add(block);
    }

    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<while-loop> \n";
        str += buildTabs(offset + 1) + "<condition>\n";
        str += nodes.get(0).toString(offset + 2);
        str += buildTabs(offset + 1) + "</condition>\n";
        str += nodes.get(1).toString(offset + 2);
        str += buildTabs(offset) + "</while-loop>\n";
        return str;
    }

    public void allocateSlots() {
        nodes.get(1).allocateSlots();
    }

    public void resolveNames(Scope scope) throws Exception {
        Scope innerScope = new Scope(scope, "whileScope");
        nodes.get(0).resolveNames(scope);
        nodes.get(1).resolveNames(innerScope);
    }

    public void checkTypes() throws Exception {
        nodes.get(0).checkTypes();
        nodes.get(1).checkTypes();
        if (!nodes.get(0).varType.equals("int")) {
            throw new Exception("Error: invalid condition type " + nodes.get(0).varType);
        }
    }

    public void run(IntermediateRepresentation rep) throws Exception {
        Label retLabel = rep.newLabel();
        Instruction retInstr = new Instruction();
        retInstr.instructionNumber = Instructions.I_JMP;
        retInstr.label = retLabel;
        rep.placeLabel(retLabel);

        this.jumpOutside = new Label();
        Label label = rep.newLabel();
        Instruction instr = new Instruction();
        nodes.get(0).run(rep);
        instr.instructionNumber = Instructions.I_JZ;
        instr.label = label;
        rep.addInstr(instr);
        nodes.get(1).run(rep);
        rep.addInstr(retInstr);
        rep.placeLabel(label);
        rep.placeLabel(this.jumpOutside);
    }

}
