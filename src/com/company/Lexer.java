package com.company;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Lexer {
    private State currentState;
    private String lexem;
    private int lineNumb = 0;
    private String fileName;
    ArrayList<Token> tokens = new ArrayList<>();
    String fullFile = "";

    public Lexer(String fileName) throws IOException {
        this.fileName = fileName;
    }

    public void execute() throws IOException {
        currentState = State.MAIN;
        lexem = "";
        String line;
        boolean scanning = true;

        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            while (scanner.hasNext()) {
                line = scanner.nextLine() + '\n';
                fullFile += line;
                lineNumb += 1;
                for (int i = 0; i < line.length(); ++i) {
                    analyse(line.charAt(i));
                }
            }
            //analyse(' ');
            analyseEnd();

        }

        tokens.add(new Token(State.END, "END"));

    }

    public void analyseEnd() {
        if (currentState == State.STRING) {
            currentState = State.ERROR_NOT_CLOSED_STRING;
            reset();
        } else if (currentState == State.MULTI_L_COMMENT_HANDLER || currentState == State.MULTI_L_COMMENT) {
            currentState = State.ERROR_NOT_CLOSED_COMMENT;
            reset();
        } else if (currentState != State.MAIN) {
            currentState = State.ERROR_UNKNOWN_SYMB;
            reset();
        }
    }

    public void analyse(char c) {
        switch (currentState) {
            case MAIN:
                if (isLetter(c)) {
                    currentState = State.IDENTIFIER;
                    lexem += c;
                } else if (isDigit(c)) {
                    currentState = State.NUM_CONST;
                    lexem += c;
                } else if (c == '(') {
                    currentState = State.L_BRACKET;
                    lexem += c;
                    reset();
                } else if (c == ')') {
                    currentState = State.R_BRACKET;
                    lexem += c;
                    reset();
                } else if (c == '{') {
                    currentState = State.L_CURLY;
                    lexem += c;
                    reset();
                } else if (c == '}') {
                    currentState = State.R_CURLY;
                    lexem += c;
                    reset();
                } else if (c == '[') {
                    currentState = State.L_ARRAY_BRACKET;
                    lexem += c;
                    reset();
                } else if (c == ']') {
                    currentState = State.R_ARRAY_BRACKET;
                    lexem += c;
                    reset();
                } else if (c == ';') {
                    currentState = State.SEMI_CLN;
                    lexem += c;
                    reset();
                } else if (c == '+') {
                    currentState = State.PLUS;
                    lexem += c;
                    reset();
                } else if (c == '-') {
                    currentState = State.MINUS;
                    lexem += c;
                    reset();
                } else if (c == '*') {
                    currentState = State.MULT;
                    lexem += c;
                    reset();
                } else if (c == ',') {
                    currentState = State.COLUMN;
                    lexem += c;
                    reset();
                } else if (c == '/') {
                    currentState = State.SLASH;
                    lexem += c;
                } else if (c == '=') {
                    currentState = State.EQ_HANDLER;
                    lexem += c;
                } else if (c == '>') {
                    currentState = State.MORE_HANDLER;
                    lexem += c;
                } else if (c == '<') {
                    currentState = State.LESS_HANDLER;
                    lexem += c;
                } else if (c == '|') {
                    currentState = State.LOGIC_OR_HANDLER;
                    lexem += c;
                } else if (c == '&') {
                    currentState = State.LOGIC_AND_HANDLER;
                    lexem += c;
                } else if (c == '!') {
                    currentState = State.NEGATION;
                    lexem += c;
                } else if (c == '\'') {
                    currentState = State.STRING;
                    lexem += c;
                } else if (c == '\t' || c == '\n' || c == '\r' || c == ' ') {
                } else {
                    currentState = State.ERROR_UNKNOWN_SYMB;
                    lexem += "FILE : " + fileName + "    " + c;
                    reset();
                }
                break;

            case IDENTIFIER:
                if ((isLetter(c) || isDigit(c))) {
                    lexem += c;
                    break;
                } else {
                    switch (lexem) {
                        case "int":
                            currentState = State.TYPE_INT;
                            break;
                        case "string":
                            currentState = State.TYPE_STRING;
                            break;
                        case "break":
                            currentState = State.BREAK;
                            break;
                        case "return":
                            currentState = State.RETURN;
                            break;
                        case "if":
                            currentState = State.IF;
                            break;
                        case "else":
                            currentState = State.ELSE;
                            break;
                        case "read":
                            currentState = State.READ;
                            break;
                        case "write":
                            currentState = State.WRITE;
                            break;
                        default:
                            currentState = State.IDENTIFIER;
                            break;
                    }
                    reset();
                    analyse(c);
                    break;
                }

            case NUM_CONST:
                if (isDigit(c)) {
                    lexem += c;
                    break;
                } else {
                    reset();
                    analyse(c);
                    break;
                }

            case STRING:
                if (c == '\'') {
                    lexem += c;
                    reset();
                    break;
                } else {
                    lexem += c;
                    break;
                }

            case STRING_SPECIAL_SYMB:
                if (c == '\'') {
                    lexem += '\'';
                    currentState = State.STRING;
                    break;
                } else if (c == '\\') {
                    lexem += '\\';
                    currentState = State.STRING;
                    break;
                } else {
                    lexem += '\\' + c;
                    currentState = State.STRING;
                    break;
                }

            case SLASH:
                if (c == '/') {
                    currentState = State.SINGLE_L_COMMENT;
                    lexem += c;
                } else if (c == '*') {
                    currentState = State.MULTI_L_COMMENT;
                    lexem += c;
                } else {
                    currentState = State.DIV;
                    reset();
                    analyse(c);
                }
                break;

            case SINGLE_L_COMMENT:
                if (c == '\n') {
                    reset();
                } else {
                    lexem += c;
                    currentState = State.SINGLE_L_COMMENT;
                }
                break;

            case MULTI_L_COMMENT:
                if (c == '*') {
                    currentState = State.MULTI_L_COMMENT_HANDLER;
                    lexem += c;
                } else {
                    lexem += c;
                }
                break;

            case MULTI_L_COMMENT_HANDLER:
                if (c == '/') {
                    currentState = State.MULTI_L_COMMENT;
                    lexem += c;
                    reset();
                } else if (c == '*') {
                    lexem += c;
                } else {
                    // prideta eilute
                    currentState = State.MULTI_L_COMMENT;
                    lexem += c;
                }
                break;

            case EQ_HANDLER:
                if (c == '=') {
                    lexem += '=';
                    currentState = State.COMP_OP_EQ;
                    reset();
                } else {
                    currentState = State.ASSIGN_OP_EQ;
                    reset();
                    analyse(c);
                }
                break;

            case MORE_HANDLER:
                if (c == '=') {
                    lexem += '=';
                    currentState = State.COMP_OP_MORE;
                    reset();
                } else {
                    currentState = State.COMP_OP_MORE_EQ;
                    reset();
                    analyse(c);
                }
                break;

            case LESS_HANDLER:
                if (c == '=') {
                    lexem += '=';
                    currentState = State.COMP_OP_LESS;
                    reset();
                } else {
                    currentState = State.COMP_OP_LESS_EQ;
                    reset();
                    analyse(c);
                }
                break;

            case NEGATION:
                if (c == '=') {
                    lexem += '=';
                    currentState = State.COMP_OP_NOT_EQ;
                    reset();
                } else {
                    reset();
                    analyse(c);
                }
                break;

            case LOGIC_AND_HANDLER:
                if (c == '&') {
                    lexem += "&";
                    currentState = State.LOGIC_OP_AND;
                    reset();
                } else {
                    currentState = State.ERROR_UNKNOWN_SYMB;
                    reset();
                    analyse(c);
                }
                break;

            case LOGIC_OR_HANDLER:
                if (c == '|') {
                    lexem += '|';
                    currentState = State.LOGIC_OP_OR;
                    reset();
                } else {
                    currentState = State.ERROR_UNKNOWN_SYMB;
                    reset();
                    analyse(c);
                }
                break;

            case ERROR_UNKNOWN_SYMB:
                lexem += c;
                reset();
                break;
        }
    }

    public void print() {
        System.out.println("LINE_" + lineNumb + "  " + String.format("%-20s", currentState) + lexem);
    }

    public void reset() {
        print();
        tokens.add(new Token(currentState, lexem));
        lexem = "";
        currentState = State.MAIN;
    }

    public ArrayList<Token> getTokens() {
        Iterator<Token> i = tokens.iterator();
        while (i.hasNext()) {
            Token t = i.next();


            if (t.getState().equals(State.SINGLE_L_COMMENT) || t.getState().equals(State.MULTI_L_COMMENT)) {
                i.remove();
            }
        }


        return tokens;
    }
}




