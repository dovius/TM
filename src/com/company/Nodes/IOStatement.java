package com.company.Nodes;

import com.company.Instruction;
import com.company.Instructions;
import com.company.IntermediateRepresentation;
import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class IOStatement extends Node {
    public String identifier;
    public boolean isReadStatemnt;


    public IOStatement() {
        nodes = new ArrayList<Node>();
    }

    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str;
        if (isReadStatemnt) {
            str = buildTabs(offset) + "<read> \n";
            str += buildTabs(offset + 1) + "Name : " + identifier + "\n";
            str += buildTabs(offset) + "</read>\n";
        } else {
            str = buildTabs(offset) + "<write> \n";
            for (int i = 0; i < nodes.size(); ++i) {
                str += nodes.get(i).toString(offset + 1);
            }
            str += buildTabs(offset) + "</write>\n";
        }
        return str;
    }

    public void checkTypes() throws Exception {
        nodes.get(0).checkTypes();
    }

    public void run( IntermediateRepresentation rep  ) throws Exception {
        if (!isReadStatemnt) {
            if (nodes.get(0).nodes.get(0).target instanceof ArrayDeclaration && nodes.get(0).nodes.get(0).nodes.size() == 0) {
                Instruction instr = new Instruction();
                ArrayDeclaration arrayDeclaration = (ArrayDeclaration)  nodes.get(0).nodes.get(0).target;
                instr.args.add(String.valueOf(arrayDeclaration.localStartSlot));
                instr.args.add(String.valueOf(arrayDeclaration.size));
                instr.instructionNumber = Instructions.I_PRINT_ARRAY;
                rep.addInstr(instr);

                return;
            }
            nodes.get(0).run(rep);
            Instruction instr = new Instruction();
            instr.args.add(nodes.get(0).getValue());
            instr.instructionNumber = Instructions.I_PRINT;
            rep.addInstr(instr);
        }

    }

    public void resolveNames(Scope scope) throws Exception {
        if (!isReadStatemnt) {
            for (int i = 0; i < nodes.size(); ++i) {
                nodes.get(i).resolveNames(scope);
            }

        }
        else {
            scope.lookup(identifier);
        }
    }

}
