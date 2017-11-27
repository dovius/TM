package com.company.Nodes;

import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;


//<function-declaration> ::= <type-specifier> <identifier> "(" <parameter-list> ")" <block-statement>
public class FunctionDeclaration extends Node {
    public String typeSpecifier;
    public String indentifier;

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
        scope.addVar(((Function) nodes.get(0)).name.lexem, nodes.get(0));
        for (int i = 1; i < nodes.size(); i++ ){
            nodes.get(i).resolveNames(scope);
        }
    }

    @Override
    public void checkTypes() throws Exception {
        for (Node node : nodes) {
            node.checkTypes();
        }

        Return returnStm = (Return) findReturn(nodes.get(nodes.size() - 1), ((Function) nodes.get(0)).type.lexem);
        Node target = findTarget(returnStm);

        if (returnStm == null) {
            throw new Exception("Error:  return not found");
        }
        //TODO fix numbers
//        else if (target != null && !target.varType.equals(((Function) nodes.get(0)).type.lexem)) {
//            throw new Exception("Error:  bad return type");
//        }

        if (((Function) nodes.get(0)).name.lexem != null && ((Function) nodes.get(0)).name.lexem.equals("main")){
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
                if (targetNode  != null) {
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
                if (returnNode  != null) {
                    return returnNode;
                }
            }
        }

        return null;
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
