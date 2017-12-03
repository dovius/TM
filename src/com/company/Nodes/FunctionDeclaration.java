package com.company.Nodes;

import com.company.*;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;


//<function-declaration> ::= <type-specifier> <identifier> "(" <parameter-list> ")" <block-statement>
public class FunctionDeclaration extends Node {
    public String typeSpecifier;
    public String indentifier;
    public int globalSlot;
    public int numSlots;
    public int codeOffset;


    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<function-declaration> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset + 1);
        }
        str += buildTabs(offset) + "</function-declaration>\n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        //todo parent? not this
        scope.addVar(indentifier, this);
        Scope innerFunctionScope = new Scope(scope, indentifier + "FunctionScope");

        for (int i = 1; i < nodes.size(); i++) {
            nodes.get(i).resolveNames(innerFunctionScope);
        }
    }

    public String getValue() {
        return indentifier;
    }

    @Override
    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }

        Return returnStm = (Return) findReturn(nodes.get(nodes.size() - 1), ((Function) nodes.get(0)).type.lexem);
        if (returnStm == null) {
            throw new Exception("Error:  return not found");
        }
        Node target = findTarget(returnStm);

        //TODO fix numbers
        if (target != null && !target.varType.equals(((Function) nodes.get(0)).type.lexem)) {
            throw new Exception("Error:  bad return type");
        }

        if (((Function) nodes.get(0)).name.lexem != null && ((Function) nodes.get(0)).name.lexem.equals("main")) {
            if (nodes.size() == 3) {
                throw new Exception("Error:  invalid number of parameters in main function!");
            }
        }
    }

    public Node findTarget(Node start) {
        if (start.target != null) {
            return start.target;
        }

        if (nodes != null) {
            for (Node node : start.nodes) {
                Node targetNode = findTarget(node);
                if (targetNode != null) {
                    return targetNode;
                }
            }
        }

        return null;
    }

    public Node findReturn(Node start, String type) {
        if (start instanceof Return) {
            return start;
        }

        if (nodes != null) {
            for (Node node : start.nodes) {
                Node returnNode = findReturn(node, type);
                if (returnNode != null) {
                    return returnNode;
                }
            }
        }

        return null;
    }

    public void allocateSlots() {
        this.globalSlot = Main.globalSlot;
        Main.globalSlot += 1;
        Main.localSlot   = 0;

        for (Node node : nodes) {
            node.allocateSlots();
        }
        this.numSlots = Main.localSlot;
    }

    public void run(IntermediateRepresentation rep) throws Exception {
        if (indentifier.equals("main")) {
            rep.placeLabel(((Program) parent).mainLabel);
        }

        rep.addGlobal(this.globalSlot);
        this.codeOffset = rep.offset();
        Instruction instr = new Instruction();
        instr.instructionNumber = Instructions.I_LOCAL_ALLOC;
        instr.args.add(String.valueOf(numSlots));
        rep.addInstr(instr);
        nodes.get(nodes.size() - 1).run(rep);
    }

    public String getTypeSpecifier() {
        return typeSpecifier;
    }

    public void setTypeSpecifier(String typeSpecifier) {
        this.typeSpecifier = typeSpecifier;
    }

    public String getIndentifier() {
        return indentifier;
    }

    public void setIndentifier(String indentifier) {
        this.indentifier = indentifier;
    }
}
