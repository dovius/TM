package com.company.Nodes;

import com.company.*;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class SelectionStatement extends Node {

    public Node condition;
    public ArrayList<Node> ifNodes;
    public ArrayList<Node> elseNodes;


    public SelectionStatement() {
        nodes = new ArrayList<Node>();
    }

    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<conditionalStatement> \n";
        str += buildTabs(offset + 1) + "<condition>\n";
        str += condition.toString(offset + 2);
        str += buildTabs(offset + 1) + "</condition>\n";
        str += buildTabs(offset + 1) + "<if-block>\n";
        for (int i = 0; i < ifNodes.size(); ++i) {
            str += ifNodes.get(i).toString(offset + 2);
        }
        str += buildTabs(offset + 1) + "</if-block>\n";
        if (elseNodes != null) {
            str += buildTabs(offset + 1) + "<else-block>\n";
            for (int i = 0; i < elseNodes.size(); ++i) {
                str += elseNodes.get(i).toString(offset + 2);
            }
            str += buildTabs(offset + 1) + "</else-block>\n";
        }
        str += buildTabs(offset) + "</conditionalStatement>\n";
        return str;
    }

    public void allocateSlots() {
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).allocateSlots();
        }
        for (Node node : ifNodes) {
            node.allocateSlots();
        }
        if (elseNodes != null) {
            for (Node node : elseNodes) {
                node.allocateSlots();
            }
        }
    }

    public void resolveNames(Scope scope) throws Exception {
        Scope innerScope = new Scope(scope, "ifStatement" + scope.name);
        condition.resolveNames(scope);
        for (Node node : ifNodes) {
            node.resolveNames(innerScope);
        }
        if (elseNodes != null) {
            for (Node node : elseNodes) {
                node.resolveNames(innerScope);
            }
        }
    }

    public void checkTypes() throws Exception {
        condition.checkTypes();
        for (Node node : ifNodes) {
            node.checkTypes();
        }
        if (elseNodes != null) {
            for (Node node : elseNodes) {
                node.checkTypes();
            }
        }
        if (!condition.varType.equals("int")) {
            throw new Exception("Error: invalid condition type \"" + condition.varType + "\"");
        }
    }

    public void run(IntermediateRepresentation rep) throws Exception {
        boolean isElsePresent = false;
        Label label = rep.newLabel();
        Instruction instr = new Instruction();
        this.condition.run(rep);
        instr.instructionNumber = Instructions.I_JZ;
        instr.label = label;
        rep.addInstr(instr);

        Instruction instrJmpOut = new Instruction();
        for (Node node : ifNodes) {
            node.run(rep);
        }
        if (elseNodes != null && elseNodes.size() != 0) {
            instrJmpOut.instructionNumber = Instructions.I_JMP;
            instrJmpOut.label = rep.newLabel();
            rep.addInstr(instrJmpOut);
            rep.placeLabel(label);
            isElsePresent = true;

            for (Node node : elseNodes) {
                node.run(rep);
            }
        }
        if (!isElsePresent) {
            rep.placeLabel(label);
            instrJmpOut.instructionNumber = Instructions.I_JMP;
            instrJmpOut.label = rep.newLabel();
            rep.addInstr(instrJmpOut);
        }
        rep.placeLabel(instrJmpOut.label);
    }

}
