package com.company.Nodes;

import com.company.Scope;

import java.util.ArrayList;

import static com.company.Parser.buildTabs;

public class ParameterList extends Node {
    public ArrayList<Node> nodes = new ArrayList<>();


    public void addNode(Node statement) {
        nodes.add(statement);
    }

    public String toString(int offset) {
        String str = buildTabs(offset) + "<parameters-list> \n";
        for (int i = 0; i < nodes.size(); ++i) {
            str += buildTabs(offset + 1) + "<parameter> \n";
            str += nodes.get(i).toString(offset + 2);
            str += buildTabs(offset + 1) + "</parameter> \n";
        }
        str += buildTabs(offset) + "</parameters-list>\n";
        return str;
    }

    public void resolveNames(Scope scope) throws Exception {
        Scope FunctionParametersScope = new Scope(scope, "functionParameter");
        for (int i = 0; i < nodes.size(); i++ ){
            nodes.get(i).resolveNames(FunctionParametersScope);
        }
    }
}
