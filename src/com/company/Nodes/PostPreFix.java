package com.company.Nodes;

import com.company.Instruction;
import com.company.Instructions;
import com.company.IntermediateRepresentation;
import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class PostPreFix extends Node {
    public String operator;
    public String identifier;
    public boolean isPrefix;

    public PostPreFix() {
        nodes = new ArrayList<Node>();
    }

    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = "";
        str = buildTabs(offset) + (isPrefix ? "<prefix>" : "<postfix>") + "\n";
        str += buildTabs(offset + 1) + "Operator: '" + operator + "' Name: " + identifier + "\n";
        str += buildTabs(offset) + (isPrefix ? "</prefix>" : "</postfix>") + "\n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        target = scope.lookup(identifier);
        value = identifier;
    }

    public void checkTypes() throws Exception {
        if (target instanceof VarDeclaration || target instanceof Parameter) {
            varType = "int";
        } else {
            throw new Exception("postfix/prefix bad types");
        }
    }

    public void run(IntermediateRepresentation rep) throws Exception {
        Instruction instr = new Instruction();
        if (isPrefix) {
            if (this.target instanceof VarDeclaration) {
                instr.instructionNumber = Instructions.I_GET;
                VarDeclaration declTarget = (VarDeclaration) target;
                instr.args.add(String.valueOf(declTarget.localSlot));
                instr.args.add("( " + String.valueOf(declTarget.name + " )"));
                value = declTarget.name;
                rep.addInstr(instr);

                instr = new Instruction();
                instr.instructionNumber = Instructions.I_PUSH_INT;
                instr.args.add(String.valueOf(1));
                rep.addInstr(instr);

                instr = new Instruction();
                instr.instructionNumber = operator.equals("+") ? Instructions.I_ADD : Instructions.I_SUB;
                instr.args.add(identifier);
                instr.args.add(String.valueOf(1));
                rep.addInstr(instr);
            }
        }
    }

    public Node getType() {
        return type;
    }

    public void setType(Node type) {
        this.type = type;
    }

}
