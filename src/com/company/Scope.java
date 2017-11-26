package com.company;

import java.util.ArrayList;
import java.util.Hashtable;
import com.company.Nodes.Node;

public class Scope {

    public Hashtable<String, Node> variables;
    public ArrayList<Scope> child = new ArrayList<>();
    public Scope parent;
    public String name;

    public Scope( Scope parent, String name ) {
        this.parent = parent;
        this.variables = new Hashtable<String, Node>();
        this.name = name;
        if (parent != null) {
            parent.child.add(this);
        }
    }

    public void addVar( String name, Node node ) throws Exception {
        if( variables.get( name ) != null ) {
            throw new Exception( "Error: duplicate variable " + name );
        } else {
            variables.put( name, node );
            System.out.println("<" + this.name + ">   <-- " + name);
        }
    }

    public Node lookup( String name ) throws Exception {
        System.out.println("<" + this.name + ">   --> " + name);
        variables.get(name);
        if( variables.get( name ) != null ) {
            return variables.get( name );
        } else if( parent != null ) {
            return parent.lookup( name );
        } else {
            throw new Exception( "Error: variable " + name + " not found!" );
        }
    }

    public String toString() {
        return variables.toString();
    }

}
