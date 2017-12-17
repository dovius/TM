package com.company.Nodes;

import com.company.*;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class Node {

    public State state;
    public String lexem;
    public String string = "";
    public int savedCursor;
    public Node type;
    public Node name;
    public Node target;
    public Node parent;

    public String varType;
    public String value;
    public ArrayList<Node> nodes = new ArrayList<>();

    public Node() {
        savedCursor = Parser.cursor;
    }

    public Node(State state, String lexem) {
        setState(state);
        setLexem(lexem);
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getLexem() {
        return lexem;
    }

    public void setLexem(String lexem) {
        this.lexem = lexem;
    }

    public Node backtrack() {
        Parser.cursor = savedCursor;
        return null;
    }

    public void resolveNames( Scope scope ) throws Exception {
        if (state == State.IDENTIFIER) {
            target = scope.lookup(this.lexem);
            return;
        }
        if (state == State.NUM_CONST || state == State.STRING) {
            return;
        }
        System.out.println( "Resolve names not implemented in: " + this.getClass() );
    }

    public void checkTypes() throws Exception {
        if (state == State.STRING) {
            varType = "string";
            return;
        }
        if (state == State.NUM_CONST) {
            varType = "int";
            return;
        }
        if (state == State.IDENTIFIER) {
            varType = target.varType;
            if (this.varType.contains("Array")) {
                return;
            }
            return;
        }
        System.out.println( "Check types not implement in: " + this.getClass() );
    }

    public void run( IntermediateRepresentation rep ) throws Exception {
        Instruction instr = new Instruction();
        if( state == State.STRING ) {
            instr.instructionNumber = Instructions.I_PUSH_STRING;
            value = lexem;
            instr.args.add( lexem );
            rep.addInstr( instr );
        } else if( state == State.NUM_CONST) {
            instr.instructionNumber = Instructions.I_PUSH_INT;
            value = lexem;
            instr.args.add( lexem );
            rep.addInstr( instr );
        } else if (state == State.IDENTIFIER){
            instr.instructionNumber = Instructions.I_GET;
            if( this.target instanceof VarDeclaration ) {
                VarDeclaration declTarget = ( VarDeclaration ) target;
                instr.args.add( String.valueOf( declTarget.localSlot ) );
                instr.args.add( "( " + String.valueOf( declTarget.name + " )" ) );
                value = declTarget.name;
                rep.addInstr( instr );
            } else if( this.target instanceof Parameter ) {
                Parameter paramTarget = (Parameter) target;
                instr.args.add(String.valueOf(paramTarget.parent.localSlot));
                instr.args.add("( " + String.valueOf(paramTarget.name.lexem + " )"));
                value = paramTarget.name.lexem;
                rep.addInstr(instr);
            } else if (this.target instanceof ArrayDeclaration) {
                System.out.println("ARRAY GET ALL");
                ArrayDeclaration arrayDeclarationTarget = (ArrayDeclaration) target;
                instr.instructionNumber = Instructions.I_ARRAY_GET;
                instr.args.add(String.valueOf(arrayDeclarationTarget.localStartSlot));
                instr.args.add(String.valueOf(((ArrayDeclaration) this.target).size));
                instr.args.add("(" + arrayDeclarationTarget.identifier + "["+ arrayDeclarationTarget.size+"]" + ")");
                rep.addInstr(instr);
            } else {
                System.out.println( "Unhandled error in varExp run " );
                throw new Exception("Unexpected variable " + this.lexem);
            }

        }
        else {

            System.out.println( "Run not implement in: " + this.getClass() );
        }
    }

    public String getValue() {
        if (value == null && state == State.IDENTIFIER) {
            return lexem;
        }
        return value;
    }


    public void allocateSlots() {
//        System.out.println( "Allocate slots not implement in: " + this.getClass() );

    }

    public boolean cmpTypes(String type, ArrayList<Node> nodes) {
        boolean missmatch = false;

        for (Node node : nodes) {
            if (node.varType == null || !type.equals(node.varType)) {
                missmatch = true;
            }
            System.out.println(type + " -> " + node.varType);
        }

        return !missmatch;
    }

    String toString(int offset) {
        return buildTabs(offset) + string ;
    }

    public Node getType() {
        return type;
    }

    public void setType(Node type) {
        this.type = type;
    }

    public Node getName() {
        return name;
    }

    public void setName(Node name) {
        this.name = name;
    }
}