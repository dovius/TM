package com.company.Nodes;

import com.company.Instruction;
import com.company.Instructions;
import com.company.IntermediateRepresentation;
import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class VarAssigment extends Node {
    public String name;
    public String type;
    public String index;

    public VarAssigment() {
        nodes = new ArrayList<Node>();
    }

    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<VarAssigment> \n";
        str += buildTabs(offset + 1) + "Name: " + name + "\n";
        if (nodes.size() == 2) {
            str += buildTabs(offset + 1) + "<index>\n";
            str += nodes.get(1).toString(offset + 2);
            str += buildTabs(offset + 1) + "</index>\n";
        }
        if (nodes.size() > 0) {
            str += buildTabs(offset + 1) + "<value>\n";
            str += nodes.get(0).toString(offset + 2);

            str += buildTabs(offset + 1) + "</value>\n";
        }
        str += buildTabs(offset) + "</VarAssigment>\n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        target = scope.lookup(name);
        for (int i = 0; i < nodes.size(); ++i) {
            nodes.get(i).resolveNames(scope);
        }
    }

    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }
        if (!cmpTypes(target.varType, nodes)) {
            throw new Exception("bad types in var assignment");
        }

    }

    public void run(IntermediateRepresentation rep) throws Exception {
        Instruction instr = new Instruction();
        instr.instructionNumber = Instructions.I_SET;
        if (nodes.size() > 1) {
            nodes.get(1).run(rep);
        }
        nodes.get(0).run(rep);
        if (this.target instanceof VarDeclaration) {
            VarDeclaration declTarget = (VarDeclaration) target;
            instr.args.add(String.valueOf(declTarget.localSlot));
            instr.args.add("=");
            instr.args.add("( " + String.valueOf(declTarget.name + " )"));
            rep.addInstr(instr);
        } else if (this.target instanceof ParameterDeclaration) {
            ParameterDeclaration paramTarget = (ParameterDeclaration) target;
            instr.args.add(String.valueOf(paramTarget.localSlot));
            instr.args.add("=");
            instr.args.add("( " + String.valueOf(paramTarget.name + " )"));
            rep.addInstr(instr);
        } else if (this.target instanceof ArrayDeclaration) {
            instr.instructionNumber = Instructions.I_ARRAY_VALUE_SET;
            ArrayDeclaration arrayTarget = (ArrayDeclaration) target;
            instr.args.add(String.valueOf(arrayTarget.localStartSlot));
            instr.args.add("(" + String.valueOf(arrayTarget.identifier) +"[" + nodes.get(1).getValue() + "]"  + ")" );
            rep.addInstr(instr);
        } else if (this.target instanceof Parameter) {
            Parameter paramTarget = (Parameter) target;
            instr.args.add(String.valueOf(paramTarget.parent.localSlot));
            instr.args.add("=");
            instr.args.add("( " + String.valueOf(paramTarget.name.lexem + " )"));
            value = paramTarget.name.lexem;
            rep.addInstr(instr);
        } else {
            System.out.println("Unhandled error in assign statement ");
        }
    }
}
