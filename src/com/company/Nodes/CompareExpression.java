package com.company.Nodes;

import com.company.*;

import static com.company.Parser.buildTabs;

public class CompareExpression extends Expression {
    public String operation;
    public Node left;
    public Node right;

    public CompareExpression(String operation, Node left, Node right) {
        this.operation = operation;
        this.left = left;
        this.right = right;
    }

    public String toString(int offset) {

        String str = buildTabs(offset) + "<CompareExpression> \n";
        str += buildTabs(offset + 1) + "Operator: '" + operation + "'\n";
        str += buildTabs(offset + 1) + "<left> \n";
        str += left.toString(offset + 2);
        str += buildTabs(offset + 1) + "</left>\n";
        str += buildTabs(offset + 1) + "<right> \n";
        str += right.toString(offset + 2);
        str += buildTabs(offset + 1) + "</right> \n";
        str += buildTabs(offset) + "</CompareExpression> \n";

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
        if (operation.equals(State.COMP_OP_MORE.toString())) {
            instr.instructionNumber = Instructions.I_GREATER;
        } else if (operation.equals(State.COMP_OP_MORE_EQ.toString())) {
            instr.instructionNumber = Instructions.I_GREATER_EQ;
        }
        else if (operation.equals(State.COMP_OP_LESS.toString())) {
            instr.instructionNumber = Instructions.I_LESS;
        }
        else if (operation.equals(State.COMP_OP_LESS_EQ.toString())) {
            instr.instructionNumber = Instructions.I_LESS_EQ;
        }
        else if (operation.equals(State.COMP_OP_EQ.toString())) {
            instr.instructionNumber = Instructions.I_EQ;
        }
        else if (operation.equals(State.COMP_OP_NOT_EQ.toString())) {
            instr.instructionNumber = Instructions.I_NOT_EQ;
        }
        instr.args.add(left.getValue());
        instr.args.add(right.getValue());
        rep.addInstr(instr);
    }
}
