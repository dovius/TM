package com.company.Nodes;

import com.company.Instruction;
import com.company.Instructions;
import com.company.IntermediateRepresentation;
import com.company.Scope;

import static com.company.Parser.buildTabs;

public class AddSubExpresssion extends Expression {
    public String operation;
    public Node left;
    public Node right;

    public AddSubExpresssion(String operation, Node left, Node right) {
        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    public String toString(int offset) {

        String str = buildTabs(offset) + "<AddSubExpression> \n";
        str += buildTabs(offset + 1) + "Operator: " + operation + "\n";
        str += buildTabs(offset+1) + "<left> \n";
        str += left.toString(offset+2);
        str += buildTabs(offset+1) + "</left>\n";
        str += buildTabs(offset+1) + "<right> \n";
        str += right.toString(offset+2);
        str += buildTabs(offset+1) + "</right> \n";
        str += buildTabs(offset) + "</AddSubExpression> \n";

        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        left.resolveNames(scope);
        right.resolveNames(scope);
    }

    @Override
    public void checkTypes() throws Exception {
        //if same
        left.checkTypes();
        right.checkTypes();
        if (left.varType.equals(right.varType)) {
            varType = left.varType;
        }
        else {
            throw new Exception("bad types in AddSub sentence");
        }
    }

    public String getValue() {
        return left.getValue() + " " + right.getValue();
    }

    public void run(IntermediateRepresentation rep) throws Exception {
        right.run(rep);
        left.run(rep);
        Instruction instr = new Instruction();
        if (operation.equals("+")) {
            instr.instructionNumber = Instructions.I_ADD;
        } else {
            instr.instructionNumber = Instructions.I_SUB;
        }
        instr.args.add(left.getValue());
        instr.args.add(right.getValue());
        rep.addInstr(instr);
    }

}
