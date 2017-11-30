package com.company.Nodes;

import com.company.*;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class FunctionCall extends Node {
    public String name;
    public boolean isStetement = false;

    public FunctionCall() {
    }

    public FunctionCall(String name) {
        this.name = name;
    }

    public FunctionCall(String name, Node parameters) {
        this.name = name;
        nodes.add(parameters);
    }

    @Override
    public String toString(int offset) {
        String nodeName = "functionCall";
        if (isStetement) {
            nodeName += "statement";
        }

        String str = buildTabs(offset) + "<"+ nodeName +"> \n";
        str += buildTabs(offset + 1) + "Name: " + name + "\n";
        if (nodes != null) {
            str += buildTabs(offset + 1) + "<parameters> \n";
            if (nodes.size() > 0) {
                str += nodes.get(0).toString(offset + 2);
            }
            str += buildTabs(offset + 1) + "</parameters>\n";
        }
        str += buildTabs(offset) + "</"+ nodeName +"> \n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        target = scope.lookup(name);
        for (int i = 0; i < nodes.size(); ++i) {
            nodes.get(i).resolveNames(scope);
        }
    }

    @Override
    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }

        varType = target.varType;
        ArrayList<String> argsTypes = new ArrayList<>();


        if (nodes != null && nodes.size() > 0) {
            for (Node node : nodes.get(0).nodes) {
                if (node.varType != null) {
                    argsTypes.add(node.varType);
                } else {
                    System.out.println("Error in checking function call parameters types");
                }
            }
            if (target.nodes.size() == 2) {
                throw new Exception(name + " function must have no arguments");
            }
            int i = 0;
            for (Node paramNode : target.nodes.get(1).nodes) {
                if (!paramNode.varType.equals(argsTypes.get(i))) {
                    throw new Exception(name + " function expected " + paramNode.varType + " type, but got " + argsTypes.get(i) + " instead");
                }
                i++;
            }
        }
    }

    public String getValue() {
        return target.getValue();
    }


    public void run(IntermediateRepresentation rep) throws Exception {
        for (Node node : nodes) {
            node.run(rep);
        }

        if (isStetement) {
            Instruction instruction = new Instruction();
            instruction.instructionNumber = Instructions.I_POP;
            rep.addInstr(instruction);
            return;
        }
        Instruction instr = new Instruction();
        instr.instructionNumber = Instructions.I_CALL;
        instr.label = new Label();
        instr.label.position = ((FunctionDeclaration) this.target).codeOffset;
        instr.args.add(String.valueOf(this.nodes.get(0).nodes.size()));
        rep.addInstr(instr);
    }
}
