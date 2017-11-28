package com.company.Nodes;

import com.company.*;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Program extends Node {

    public Program() {
        nodes = new ArrayList<Node>();
    }
    public Label mainLabel;


    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<program> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset + 1);
        }
        str += buildTabs(offset) + "</program>\n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        for (int i = 0; i < nodes.size(); ++i) {
            nodes.get(i).resolveNames(scope);
        }

        if (scope.variables.get("main") == null) {
            throw new Exception("program doesnt contain main function!");
        }
    }

    @Override
    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }
    }

    public void run( IntermediateRepresentation rep ) throws Exception {
        this.mainLabel = rep.newLabel();
        Instruction mainInstr = new Instruction();
        mainInstr.instructionNumber = Instructions.I_CALL;
        mainInstr.args.add( String.valueOf( 0 ) );
        mainInstr.label = this.mainLabel;
        rep.addInstr( mainInstr );

        Instruction exitInstr = new Instruction();
        exitInstr.instructionNumber = Instructions.I_EXIT;
        rep.addInstr( exitInstr );
        for( int i = 0; i < nodes.size(); i++ ) {
            //todo move to other place parent???
            nodes.get(i).parent = this;
            nodes.get( i ).run( rep );
        }
    }

    public void allocateSlots() {
        for( int i = 0; i < nodes.size(); i++ ) {
            nodes.get( i ).allocateSlots();
        }
    }
}
