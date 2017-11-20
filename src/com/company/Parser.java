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
            do {
                functionDeclaration = parseFunctionDeclaration();
                if (functionDeclaration!= null) {
                    program.nodes.add(functionDeclaration);
                }
            } while (functionDeclaration != null);
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
//         | <arrayDeclaration>
//		   | <arrayDeclaration> "," <parameter_list>
    public ParameterList parseParameterList() {
        ParameterList parameterList = new ParameterList();

        ArrayDeclaration arrayDeclaration = parseArrayDeclaration();
        if (arrayDeclaration != null) {
            parameterList.addNode(arrayDeclaration);
        }

        ParameterDeclaration parameterDeclaration = parseParameterDeclaration();
        if (parameterDeclaration != null) {
            parameterList.addNode(parameterDeclaration);
        }

        if (arrayDeclaration != null || parameterDeclaration != null) {
            Node column;
            do {
                column = parseColumn();
                if (column != null) {
                    arrayDeclaration = parseArrayDeclaration();
                    if (arrayDeclaration != null) {
                        parameterList.addNode(arrayDeclaration);
                        continue;
                    }

                    parameterDeclaration = parseParameterDeclaration();
                    if (parameterDeclaration != null) {
                        parameterList.addNode(parameterDeclaration);
                    }
                    if (arrayDeclaration == null && parameterDeclaration == null) {
                        return (ParameterList) parameterList.backtrack();
                    }
                }
            } while (column != null && (arrayDeclaration != null || parameterDeclaration != null));
            return parameterList;
        }

        return (ParameterList) parameterList.backtrack();
    }

    //    <arrayDeclaration> ::= <type> <identifier> "[" <integer> "]" ";"
    public ArrayDeclaration parseArrayDeclaration() {
        ArrayDeclaration arrayDeclaration = new ArrayDeclaration();

        Node typeSpecifier = parseTypeSpecifier();
        Node identifier = parseIdentifier();
        Node lArrayBracket = getState(State.L_ARRAY_BRACKET);
        Node integer = getState(State.NUM_CONST);
        Node rArrayBracket = getState(State.R_ARRAY_BRACKET);
        if (checkNodes(typeSpecifier, identifier, lArrayBracket, identifier, rArrayBracket)) {
            return new ArrayDeclaration(typeSpecifier.lexem, identifier.lexem, integer.lexem);
        }
        return (ArrayDeclaration) arrayDeclaration.backtrack();
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

        Expression loopStatement = parseLoopStatement();
        if (loopStatement != null) {
            statement.nodes.add(loopStatement);
            return statement;
        }

        Node simpleStatement = parseSimpleStatement();
        if (simpleStatement != null) {
            statement.nodes.add(simpleStatement);
            return statement;
        }

        SelectionStatement selectionStatement = parseSelectionStatement();
        if (selectionStatement != null) {
            statement.nodes.add(selectionStatement);
            return statement;
        }

        Expression expressionStatement = parseExpressionStatement();
        if (expressionStatement != null) {
            statement.nodes.add(expressionStatement);
            return statement;
        }

        Return returnStatement = parseReturnStatement();
        if (returnStatement != null) {
            statement.nodes.add(returnStatement);
            return statement;
        }
        return (Statement) statement.backtrack();
    }

    //    <selection-statement> ::= if ( <expression> ) <block-statement>
//            | if ( <expression> ) <block-statement>else <block-statement>
    public SelectionStatement parseSelectionStatement() {
        SelectionStatement selectionStatement = new SelectionStatement();

        Node ifNode = parseState(State.IF);
        if (ifNode != null) {
            Node lBracker = parseLBracket();
            if (lBracker != null) {
                Expression expression = parseExpression();
                if (expression != null) {
                    Node rBracket = parseRBracket();
                    if (rBracket != null) {
                        Block ifBlock = parseBlock();
                        if (ifBlock != null) {
                            selectionStatement.condition = expression;
                            selectionStatement.ifNodes = ifBlock.nodes;
                        }
                        Node elseNode = parseState(State.ELSE);
                        if (elseNode != null) {
                            Block elseBlock = parseBlock();
                            if (elseBlock != null) {
                                selectionStatement.elseNodes = elseBlock.nodes;
                            }
                        }
                        return selectionStatement;
                    }
                }
            }
        }
        return (SelectionStatement) selectionStatement.backtrack();
    }

//  <loop-statement> ::= <while-loop> | <for-loop>
//  <while-loop> ::= "while" "(" <expresion> ")" <block-statement>
//  <for-loop> ::= "for" "(" <varDeclaration> ";" <expression> ";" <post-pre-fix>  ")" <block-statement>
    public Expression parseLoopStatement() {
        Expression loopStatement = new Expression();

        Node whileNode = parseState(State.WHILE);
        Node lBracket = parseLBracket();
        if (whileNode != null && lBracket != null) {
            Expression expression = parseExpression();
            Node rBracket = parseRBracket();
            Block block = parseBlock();
            if (expression!=null && rBracket!=null && block!=null) {
                loopStatement.nodes.add(new WhileStatement(expression,block));
                return loopStatement;
            }
        }
        loopStatement.backtrack();

        Node forNode = parseState(State.FOR);
        if (forNode != null) {
            lBracket = parseLBracket();
            VarDeclaration varDeclaration = parseVarDeclaration();
            Expression expression = parseExpression();
            Node semicolumn = parseSemicolon();
            PostPreFix postPreFix = parsePostPrefix();
            Node rBracket = parseRBracket();
            Block block = parseBlock();
            if (lBracket!= null && varDeclaration != null && expression!= null && semicolumn!=null && postPreFix!= null
                    && rBracket !=null && block!=null) {
                ForStatement forStatement = new ForStatement();
                forStatement.nodes.add(varDeclaration);
                forStatement.nodes.add(expression);
                forStatement.nodes.add(postPreFix);
                forStatement.nodes.add(block);
                loopStatement.nodes.add(forStatement);
                return loopStatement;
            }
        }

        return (Expression) loopStatement.backtrack();
    }

    //    <expression-statement> ::= <expression> ";"
    public Expression parseExpressionStatement() {
        Expression expressionStatement = new Expression();
        Expression expression = parseExpression();
        Node semiColumn = parseSemicolon();
        if (expression != null && semiColumn != null) {
            expressionStatement.nodes.add(expression);
            return expressionStatement;
        }
        return (Expression) expressionStatement.backtrack();
    }


    //    <return-statement> ::= 'return' <expresion> ";" | 'return'
    public Return parseReturnStatement() {
        Return returnStatement = new Return();

        Node returnNode = parseState(State.RETURN);
        Expression expression = parseExpression();
        Node semicolumn = parseSemicolon();
        if (returnNode != null) {
            if (expression != null && semicolumn != null) {
                returnStatement.nodes.add(expression);
            }
            return returnStatement;
        }

        return (Return) returnStatement.backtrack();
    }

    //    <expresion> ::= <expr-1> <logicOp> <expresion>  | <expr-1>
    public Expression parseExpression() {
        Expression expression = new Expression();

        Expression expression1 = parseExpression1();
        if (expression1 != null) {
            Node logicNode = parseLogicOp();
            if (logicNode != null) {
                Expression recExpression = parseExpression();
                if (recExpression != null) {
                    expression.nodes.add(new LogicExpression(logicNode.lexem, expression1, recExpression));
                    return expression;
                }
            }
        }
        if (expression1 != null) {
            return expression1;
        }
        return (Expression) expression.backtrack();
    }

    //    <expr-1> ::= <expr-2> <compareOp> <expr-1> | <expr-2>
    public Expression parseExpression1() {
        Expression expression1 = new Expression();

        Expression expression2 = parseExpression2();
        if (expression2 != null) {
            Node compareNode = parseCompareOp();
            if (compareNode != null) {
                Expression recExpression1 = parseExpression1();
                if (recExpression1 != null) {
                    expression1.nodes.add(new CompareExpression(compareNode.lexem, expression2, recExpression1));
                    return expression1;
                }
            }
        }
        if (expression2 != null) {
            return expression2;
        }
        return (Expression) expression1.backtrack();
    }

    //    <expr-2> ::= <expr-3> <addSubOp> <expr-2>  | <expr-3>
    public Expression parseExpression2() {
        Expression expression2 = new Expression();

        Expression expression3 = parseExpression3();
        if (expression3 != null) {
            Node AddSubNode = parseAddSubOp();
            if (AddSubNode != null) {
                Expression recExpression2 = parseExpression2();
                if (recExpression2 != null) {
                    expression2.nodes.add(new AddSubExpresssion(AddSubNode.lexem, expression3, recExpression2));
                    return expression2;
                }
            }
        }
        if (expression3 != null) {
            return expression3;
        }
        return (Expression) expression2.backtrack();
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
            //potentional cursor error
        }
        if (expression4 != null) {
            return expression4;
        }
        return (Expression) expression3.backtrack();
    }

//    <expr-4> ::= <identifier>
//            | <pre-post-fix>
//            | <int>
//            | "(" <expresion> ")"
//            | <arrayIndexStatement>
//            | <function-call>
//            | <string>
    public Expression parseExpression4() {
        Expression expression4 = new Expression();

        PostPreFix postPreFix = parsePostPrefix();
        if (postPreFix != null) {
            expression4.nodes.add(postPreFix);
            return expression4;
        }

        ArrayIndex arrayIndex = parseIndexStatement();
        if (arrayIndex != null) {
            expression4.nodes.add(arrayIndex);
            return expression4;
        }

        FunctionCall functionCall = parseFunctionCall();
        if (functionCall != null) {
            expression4.nodes.add(functionCall);
            return expression4;
        }
        expression4.backtrack();

        Node identifier = parseIdentifier();
        if (identifier != null) {
            identifier.string = "Variable: " + identifier.lexem + "\n";
            expression4.nodes.add(identifier);
            return expression4;
        }

        Node number = parseState(State.NUM_CONST);
        if (number != null) {
            number.string = "Number: " + number.lexem + "\n";
            expression4.nodes.add(number);
            return expression4;
        }

        Node LBracket = parseLBracket();
        if (LBracket != null) {
            Expression expression = parseExpression();
            if (expression != null) {
                Node RBracket = parseRBracket();
                if (RBracket != null) {
                    expression4.nodes.add(expression);
                    return expression4;
                }
            }
        }
        expression4.backtrack();

        Node string = parseState(State.STRING);
        if (string != null) {
            string.string = "String: " + string.lexem + "\n";
            expression4.nodes.add(string);
            return expression4;
        }

        return (Expression) expression4.backtrack();
    }


    //    <post-pre-fix> ::= <identifier> <incrementDecrementOp>
//		| <incrementDecrementOp> <identifier>
    public PostPreFix parsePostPrefix() {
        PostPreFix postPreFix = new PostPreFix();

        Node identifier = parseIdentifier();
        if (identifier != null) {
            Node first = getState(State.PLUS, State.MINUS);
            if (first != null) {
                Node second = getState(State.PLUS, State.MINUS);
                if (second!=null) {
                    if (first.lexem.equals(second.lexem)) {
                        postPreFix.isPrefix = false;
                        postPreFix.identifier = identifier.lexem;
                        postPreFix.operator = second.lexem;
                        return postPreFix;
                    }
                }
            }
        }
        Node first = getState(State.PLUS, State.MINUS);
        if (first != null) {
            Node second = getState(State.PLUS, State.MINUS);
            if (first.lexem.equals(second.lexem)) {
                identifier = parseIdentifier();
                if (identifier != null) {
                    postPreFix.isPrefix = true;
                    postPreFix.identifier = identifier.lexem;
                    postPreFix.operator = second.lexem;
                    return postPreFix;
                }
            }
        }

        return (PostPreFix) postPreFix.backtrack();
    }

    //    <simple-statement> ::= <varDeclaration>
//		      | <assignmentStmt>
//              | <ioStmt>
    public Expression parseSimpleStatement() {
        Expression simpleStatement = new Expression();

        VarDeclaration varDeclaration = parseVarDeclaration();
        if (varDeclaration != null) {
            simpleStatement.nodes.add(varDeclaration);
            return simpleStatement;
        }

        VarAssigment varAssigment = parseAssigmentStatement();
        if (varAssigment != null) {
            simpleStatement.nodes.add(varAssigment);
            return simpleStatement;
        }

        Expression iOStatement = parseIoStatement();
        if (iOStatement != null) {
            simpleStatement.nodes.add(iOStatement);
            return simpleStatement;
        }

        return (Expression) simpleStatement.backtrack();
    }

    //    <ioStmt> ::= <inputStmt>
//               | <outputStmt>
    public Expression parseIoStatement() {
        Expression ioStatement = new Expression();

        IOStatement io = parseInputStatement();
        if (io != null) {
            ioStatement.nodes.add(io);
            return ioStatement;
        }

        io = parseOutputStatement();
        if (io != null) {
            ioStatement.nodes.add(io);
            return ioStatement;
        }

        return (Expression) ioStatement.backtrack();
    }

    //    <inputStmt>  ::= 'read' <identifier> ";"
    public IOStatement parseInputStatement() {
        IOStatement ioStatement = new IOStatement();

        Node read = parseState(State.READ);
        if (read != null) {
            Node identifier = parseIdentifier();
            if (identifier != null) {
                Node semicolumn = parseSemicolon();
                if (semicolumn != null) {
                    ioStatement.isReadStatemnt = true;
                    ioStatement.identifier = identifier.lexem;
                    return ioStatement;
                }
            }
        }
        return (IOStatement) ioStatement.backtrack();
    }

    //    <outputStmt> ::= 'write' <expression> ";"
    public IOStatement parseOutputStatement() {
        IOStatement ioStatement = new IOStatement();
        Node write = parseState(State.WRITE);
        if (write != null) {
            Expression expression = parseExpression();
            if (expression != null) {
                Node semicolumn = parseSemicolon();
                if (semicolumn != null) {
                    ioStatement.isReadStatemnt = false;
                    ioStatement.addNode(expression);
                    return ioStatement;
                }
            }
        }
        return (IOStatement) ioStatement.backtrack();
    }

    //    <assignmentStmt> ::= <identifier> <assigmentOp> <expression-statement>
//            | <arrayIndexStatement> <assigmentOp> <expression-statement>
    public VarAssigment parseAssigmentStatement() {
        VarAssigment varAssigment = new VarAssigment();

        Node identifier = parseIdentifier();
        if (identifier != null) {
            Node assigmentOp = parseState(State.ASSIGN_OP_EQ);
            if (assigmentOp != null) {
                Expression expressionStatement = parseExpressionStatement();
                if (expressionStatement != null) {
                    varAssigment.name = identifier.lexem;
                    varAssigment.nodes.add(expressionStatement);
                    return varAssigment;
                }
            }
            cursor--;
        }

        ArrayIndex arrayIndex = parseIndexStatement();
        if (arrayIndex != null) {
            Node assigmentOp = parseState(State.ASSIGN_OP_EQ);
            if (assigmentOp != null) {
                Expression expressionStatement = parseExpressionStatement();
                if (expressionStatement != null) {
                    varAssigment.name = arrayIndex.name;
                    varAssigment.nodes.add(expressionStatement.nodes.get(0));
                    varAssigment.nodes.add(arrayIndex.nodes.get(0));
                    return varAssigment;
                }
            }
        }
        return (VarAssigment) varAssigment.backtrack();
    }

    //    <varDeclaration> ::= <type-specifier> <identifier>;
//                  | <type-specifier> <identifier> <assigmentOp> <expression>;
    public VarDeclaration parseVarDeclaration() {
        VarDeclaration varDeclaration = new VarDeclaration();

        Node typeSpecifier = parseTypeSpecifier();
        Node identifier = parseIdentifier();
        if (typeSpecifier != null && identifier != null) {

            Node assigmentOp = getState(State.ASSIGN_OP_EQ);
            if (assigmentOp != null) {
                Expression expression = parseExpression();
                if (expression != null) {
                    Node semicolumn = parseSemicolon();
                    if (semicolumn != null) {
                        varDeclaration.type = typeSpecifier.lexem;
                        varDeclaration.name = identifier.lexem;
                        varDeclaration.nodes.add(expression);
                        return varDeclaration;
                    }
                }
            } else {
                Node semicolumn = parseSemicolon();
                if (semicolumn != null) {
                    varDeclaration.type = typeSpecifier.lexem;
                    varDeclaration.name = identifier.lexem;
                    return varDeclaration;
                }
            }

        }
        return (VarDeclaration) varDeclaration.backtrack();
    }

    //    <arrayIndexStatement> ::= <identifier> "["<expression>"]"
    public ArrayIndex parseIndexStatement() {
        ArrayIndex arrayIndex = new ArrayIndex();

        Node identifier = parseIdentifier();
        Node lArrayBracket = parseState(State.L_ARRAY_BRACKET);
        if (identifier == null || lArrayBracket == null) {
            return (ArrayIndex) arrayIndex.backtrack();
        }
        Expression expression = parseExpression();
        Node rArrayBracket = parseState(State.R_ARRAY_BRACKET);

        if (identifier != null && lArrayBracket != null && expression != null && rArrayBracket != null) {
            arrayIndex.name = identifier.lexem;
            arrayIndex.nodes.add(expression);
            return arrayIndex;
        }

        return (ArrayIndex) arrayIndex.backtrack();
    }

    //    <function-call> ::= <identifier> "(" <call-param-list> ")"
//            | <identifier> "(" ")"
    public FunctionCall parseFunctionCall() {
        FunctionCall functionCall = new FunctionCall();

        Node identifier = parseIdentifier();
        if (identifier != null) {
            Node lBracket = parseLBracket();
            if (lBracket != null) {
                Expression callParamList = parseCallParamList();
                if (callParamList != null) {
                    Node rBracket = parseRBracket();
                    if (rBracket != null) {
                        functionCall.name = identifier.lexem;
                        functionCall.nodes.add(callParamList);
                        return functionCall;
                    }
                } else {
                    Node rBracket = parseRBracket();
                    if (rBracket != null) {
                        functionCall.name = identifier.lexem;
                        return functionCall;
                    }
                }
            }
        }
        return (FunctionCall) functionCall.backtrack();
    }

    //        <call-param-list-item>    ::= <expresion>
//                | <expresion> , <call-param-list>
    public Expression parseCallParamList() {
        Expression callParamList = new Expression();

        Expression expression = parseExpression();
        if (expression != null) {
            callParamList.nodes.add(expression);
            Node column;
            do {
                column = parseColumn();
                if (column != null) {
                    expression = parseExpression();
                    if (expression != null) {
                        callParamList.nodes.add(expression);
                    } else {
                        cursor--;
                    }
                }
            } while (expression != null && column != null);
            return callParamList;
        }
        return (Expression) callParamList.backtrack();
    }

    public Node parseState(State state) {
        return getState(state);
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

    public boolean checkNodes(Node... nodes) {
        for (Node node : nodes) {
            if (node == null) {
                return false;
            }
        }
        return true;
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
