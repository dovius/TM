package com.company;

import com.company.Nodes.*;

import java.util.*;

public class Parser {
    public ArrayList<Token> tokens;
    public static Integer cursor = 0;


    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    //  <program> ::= <function-declaration> <program>
//  TODO          | <function-declaration>
    public Program parseProgram() throws Exception {
        Program program = new Program();
        FunctionDeclaration functionDeclaration = parseFunctionDeclaration();
        if (functionDeclaration != null) {
            program.addNode(functionDeclaration);
            return program;
        }
        return (Program) program.backtrack();
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
                            Node block = parseBlock();
                            if (block != null) {
                                functionDeclaration.addNode(new Function(typeSpeficier, identifier));
                                functionDeclaration.addNode(parameterList);
                                functionDeclaration.addNode(block);
                                return functionDeclaration;
                            }
                        }
                    }
                }
            }
        }
        return (FunctionDeclaration) functionDeclaration.backtrack();
    }

    // ::= <parameter-declaration>
//		   | <parameter-list> "," <parameter-declaration>
//  TODO       | <arrayDeclaration>
//	TODO	   | <arrayDeclaration> "," <parameter_list>
    public ParameterList parseParameterList() {
        ParameterList parameterList = new ParameterList();

        ParameterDeclaration parameterDeclaration = parseParameterDeclaration();
        if (parameterDeclaration != null) {
            parameterList.nodes.add(parameterDeclaration);
            Node column;
            do {
                column = parseColumn();
                if (column != null) {
                    parameterDeclaration = parseParameterDeclaration();
                    if (parameterDeclaration != null) {
                        parameterList.addNode(parameterDeclaration);
                    } else {
                        cursor--;
                    }
                }
            } while (column != null && parameterDeclaration != null);
            return parameterList;
        }

        return (ParameterList) parameterList.backtrack();
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
        return (ParameterDeclaration) parameterDeclaration.backtrack();
    }

    //    <block-statement> ::= "{" <statements> "}"
//            | "{" "}"
    public Block parseBlock() {
        Block block = new Block();

        Node LCurly = parseLCurlyBracket();
        Node RCurly = parseRCurlyBracket();
        if (LCurly != null && RCurly != null) {
            return block;
        } else {
            block.backtrack();
            LCurly = parseLCurlyBracket();
            Statement statement = parseStatement();
            if (LCurly != null && statement != null) {
                block.addNode(statement);
                do {
                    statement = parseStatement();
                    if (statement != null) {
                        block.addNode(statement);
                    }
                } while (statement != null);
                RCurly = parseRCurlyBracket();
                if (RCurly != null) {
                    return block;
                }
            }
        }
        return (Block) block.backtrack();
    }

    //    <statement> ::= <expression-statement>
//            | <selection-statement>
//            | <simple-statement>
//            | <return-statement>
//            | <loop-statement>
    public Statement parseStatement() {
        Statement statement = new Statement();

        Expression expressionStatement = parseExpressionStatement();
        if (expressionStatement != null) {
            statement.nodes.add(expressionStatement);
            return statement;
        }

        return (Statement) statement.backtrack();
    }

    //    <expression-statement> ::= <expression> ";"
    public Expression parseExpressionStatement() {
        Expression expressionStatement = new Expression();

        Expression expression = parseExpression();
        Node semiColumn = parseSemicolon();
        if (expression != null && semiColumn != null) {
            expression.nodes.add(expression);
            return expression;
        }

        return (Expression) expressionStatement.backtrack();
    }

    public Expression parseExpression() {
        Expression expression = new Expression();

        Expression expression3 = parseExpression3();
        if (expression3 != null) {
            expression.nodes.add(expression3);
            return expression;
        }
        return (Expression) expression.backtrack();
    }

    //    <expr-3> ::= <expr-4> <multDivOp> <expr-3>  | <expr-4>
    public Expression parseExpression3() {
        Expression expression3 = new Expression();

        Expression expression4 = parseExpression4();
        if (expression4 != null) {
            Node multDivNode = parseMultDicOp();
            if (multDivNode != null) {
                Expression recExpression3 = parseExpression3();
                if (recExpression3 != null) {
                    expression3.nodes.add(new MultDivExpresssion(multDivNode.lexem, expression4, recExpression3));
                    return expression3;
                }
            }
        }
        if (expression4 != null) {
            expression4.backtrack();
        }
        expression4 = parseExpression4();
        if (expression4 != null) {
            return expression4;
        }
        return (Expression) expression3.backtrack();
    }


    //    <expr-4> ::= <identifier>
//           | <pre-post-fix>
//            | <int>
//            | "(" <expresion> ")"
//            | <arrayIndexStatement>
//           | <function-call>
//            | <string>
    public Expression parseExpression4() {
        Expression expression4 = new Expression();

        Node identifier = parseIdentifier();
        if (identifier != null) {
            identifier.string = "Variable: " + identifier.lexem + "\n";
            expression4.nodes.add(identifier);
            return expression4;
        }
        return (Expression) expression4.backtrack();
    }

    public Node parseSemicolon() {
        return getState(State.SEMI_CLN);
    }

    public Node parseRCurlyBracket() {
        return getState(State.R_CURLY);
    }

    public Node parseLCurlyBracket() {
        return getState(State.L_CURLY);
    }

    //<type-specifier> ::= 'char'
//               | 'int'
//               | 'string'
    public Node parseTypeSpecifier() {
        return getState(State.TYPE_CHAR, State.TYPE_INT, State.TYPE_STRING);
    }

    public Node parseLogicOp() {
        return getState(State.LOGIC_OP_AND, State.LOGIC_OP_OR);
    }

    public Node parseCompareOp() {
        return getState(State.COMP_OP_EQ, State.COMP_OP_LESS, State.COMP_OP_LESS_EQ, State.COMP_OP_MORE,
                State.COMP_OP_MORE_EQ, State.COMP_OP_NOT_EQ);
    }

    public Node parseAddSubOp() {
        return getState(State.PLUS, State.MINUS);
    }

    public Node parseMultDicOp() {
        return getState(State.MULT, State.DIV);
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

    public Node parseColumn() {
        return getState(State.COLUMN);
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
