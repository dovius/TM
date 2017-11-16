package com.company;

import com.company.Nodes.*;

import java.util.*;

public class Parser {
    public ArrayList<Token> tokens;
    public int cursor = 0;
    public int savedCursor = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    //  <program> ::= <function-declaration> <program>
//            | <function-declaration>
    public Program parseProgram() throws Exception {
        Program program = new Program();

        FunctionDeclaration functionDeclaration = parseFunctionDeclaration();
        if (functionDeclaration != null) {
            program.addNode(functionDeclaration);
            return program;
        }
        return null;
    }

    //<function-declaration> ::= <type-specifier> <identifier> "(" <parameter-list> ")" <block-statement>
    public FunctionDeclaration parseFunctionDeclaration() {
        FunctionDeclaration functionDeclaration = new FunctionDeclaration();

        Node typeSpeficier = parseTypeSpecifier();
        if (typeSpeficier != null) {
            Node identifier = parseIdentifier();
            if (identifier != null) {
                Node LBracket = parseLBracket();
                if (LBracket != null) {
                    ParameterList parameterList = parseParameterList();
                    if (parameterList != null) {
                        Node RBracket = parseRBracket();
                        if (RBracket != null) {
//              functionDeclaration.setTypeSpecifier(typeSpeficier.lexem);
//              functionDeclaration.setIndentifier(identifier.lexem);
                            functionDeclaration.addNode(new Function(typeSpeficier, identifier));
                            functionDeclaration.addNode(parameterList);
                            return functionDeclaration;
                        }
                    }
                }

            }

        }

        return null;
    }

    //<type-specifier> ::= 'char'
//               | 'int'
//               | 'string'
    public Node parseTypeSpecifier() {
        return getState(State.TYPE_CHAR, State.TYPE_INT, State.TYPE_STRING);
    }

    //<parameter-list>
// ::= <parameter-declaration>
//         | <arrayDeclaration>
//		   | <arrayDeclaration> "," <parameter_list>
//	T	   | <parameter-list> "," <parameter-declaration>
    public ParameterList parseParameterList() {
        ParameterList parameterList = new ParameterList();

        ParameterDeclaration parameterDeclaration = parseParameterDeclaration();
        if (parameterDeclaration != null) {
            parameterList.nodes.add(parameterDeclaration);
            return parameterList;
        }

        return null;
    }

    //  <parameter-declaration> ::= <type-specifier> <identifier>
    public ParameterDeclaration parseParameterDeclaration() {
        ParameterDeclaration parameterDeclaration = new ParameterDeclaration();

        Node typeSpeficier = parseTypeSpecifier();
        if (typeSpeficier != null) {
            Node identifier = parseIdentifier();
            parameterDeclaration.nodes.add(new Parameter(typeSpeficier, identifier));
            return parameterDeclaration;
        }
        return null;
    }


    //  <identifier> ::= [a-zA-Z_][a-zA-Z0-9_]*
    public Node parseIdentifier() {
        return getState(State.IDENTIFIER);
    }

    public Node parseLBracket() {
        return getState(State.L_BRACKET);
    }

    public Node parseRBracket() {
        return getState(State.R_BRACKET);
    }

    public void saveCursor() {
        savedCursor = cursor;
    }

    public void backtrack() {
        cursor = savedCursor;
    }

    public Token term(String state) {
        Token token = getNextToken();
        if (token.getState().equals(state)) {
            return token;
        }
        return null;
    }

    public Token getNextToken() {
        return tokens.get(cursor++);
    }

    public static String buildTabs(int number) {
        String offset = "";
        for (int i = 0; i < number * 2; ++i) {
            offset += "  ";
        }
        return offset;
    }

    public Node getState(State... types) {
        Token token;
        State state = tokens.get(cursor).getState();
        for (State type : types)
            if (state == type) {
                token = getNextToken();
                return new Node(token.getState(), token.getLexem());
            }
        return null;
    }
}
