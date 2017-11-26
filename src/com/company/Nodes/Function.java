package com.company.Nodes;

import com.company.Main;

public class Function extends Variable {
    public Function(Node type, Node name) {
        setName(name);
        setType(type);
    }

    @Override
    public void checkTypes() throws Exception {
        if (name.lexem != null && name.lexem.equals("main")) {
            if (type.lexem != null && !type.lexem.equals("int")) {
                Main.errorState = true;
                String errMsg = "Error: wrong type of function main. Expected type: int, got: \"" + type.lexem + "\"!" ;
                throw new Exception(errMsg);
            }
        }
    }
}
