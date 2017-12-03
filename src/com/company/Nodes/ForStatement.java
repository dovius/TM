package com.company.Nodes;
import com.company.*;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class ForStatement extends Node {
    public Label jumpOutside;
    //    <for-loop> ::= "for" "(" <varDeclaration> ";" <expression> ";" <post-pre-fix>  ")" <block-statement>
    public ForStatement() {
        nodes = new ArrayList<>();
    }

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<for-loop> \n";
        str += nodes.get(0).toString(offset+1);
        str += buildTabs(offset+1) + "<condition>\n";
        str += nodes.get(1).toString(offset+2);
        str += buildTabs(offset+1) + "</condition>\n";
        str += buildTabs(offset+1) + "<expression>\n";
        str += nodes.get(2).toString(offset+2);
        str += buildTabs(offset+1) + "</expression>\n";
        str += nodes.get(3).toString(offset+1);
        str += buildTabs(offset) + "</for-loop>\n";
        return str;
    }

    public void allocateSlots() {
        nodes.get(0).allocateSlots();
        nodes.get(3).allocateSlots();
    }

    public void resolveNames(Scope scope) throws Exception {
        Scope innerScope = new Scope(scope, "forscope");
        for (Node node : nodes) {
            node.resolveNames(innerScope);
        }
    }

    public void checkTypes() throws Exception {
        nodes.get(0).checkTypes();
        nodes.get(1).checkTypes();
        nodes.get(2).checkTypes();
        //nodes.get(3).checkTypes();
        if (!nodes.get(0).varType.equals("int")) {
            throw new Exception("Error: invalid condition type " + nodes.get(0).varType);
        }
    }

    public void run(IntermediateRepresentation rep) throws Exception {
        Label retLabel = rep.newLabel();
        Instruction retInstr = new Instruction();
        retInstr.instructionNumber = Instructions.I_JMP;
        retInstr.label = retLabel;

        //rep.placeLabel(retLabel);

        this.jumpOutside = new Label();
        Label label = rep.newLabel();
        rep.placeLabel(retLabel);

        Instruction instr = new Instruction();
        instr.label = label;
        nodes.get(0).run(rep);
        nodes.get(1).run(rep);
        instr.instructionNumber = Instructions.I_JZ;
        rep.placeLabel(retLabel);

        nodes.get(2).run(rep);
        nodes.get(3).run(rep);


        rep.addInstr( instr );
        rep.addInstr(retInstr);
        rep.placeLabel(label);
       // rep.placeLabel(retLabel);

        rep.placeLabel(this.jumpOutside);
    }


    //todo implement for loop????
}
