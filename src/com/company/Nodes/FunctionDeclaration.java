package com.company.Nodes;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;


//<function-declaration> ::= <type-specifier> <identifier> "(" <parameter-list> ")" <block-statement>
public class FunctionDeclaration extends Node {
    public ArrayList<Node> nodes = new ArrayList<>();
    public String typeSpecifier;
    public String indentifier;

    public void addNode (Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<function-declaration> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += nodes.get(i).toString(offset + 1 );
        }
        str += buildTabs(offset) + "</function-declaration>\n";
        return str;
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
