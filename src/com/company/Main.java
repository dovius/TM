package com.company;

import com.company.Nodes.Program;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {

        try {
            Lexer lex = new Lexer("inputSimple.txt");
            lex.execute();

            Parser parser = new Parser(lex.getTokens());

            System.out.println("\n");

            Program tree = parser.parseProgram();
            if (tree != null)
                System.out.println(tree.toString(0));

            System.out.println("\n");
            if (tree != null) {
//        displayTree(tree, 0);
                System.out.println("\nSyntax is correct\n");
            } else {
                System.out.println("Syntax is incorrect\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static String prefix(int level) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < level; i++) {
            s.append("  ");
        }

        return s.toString();
    }
}
