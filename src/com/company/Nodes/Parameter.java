package com.company.Nodes;

public class Parameter extends Variable {
    ParameterDeclaration parent;

    public Parameter(Node type, Node name, ParameterDeclaration parent) {
        setName(name);
        setType(type);
        varType = type.lexem;
        this.parent = parent;
    }

}
