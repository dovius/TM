package com.company;

import com.company.Nodes.Program;

import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {

        try {
            Lexer lex = new Lexer("inputSimple.txt");
            lex.execute();

            Parser parser = new Parser(lex.getTokens());

            System.out.println("\n");

            Program tree = parser.parseProgram();
            PrintWriter writer = new PrintWriter("out.xml", "UTF-8");
            if (tree != null) {
                String file = tree.toString(0);
                writer.print(file);
            }
            writer.close();
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
