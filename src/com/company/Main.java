package com.company;

import com.company.Nodes.Program;

import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static boolean errorState;

    public static void main(String[] args) throws Exception {

        try {
            Lexer lex = new Lexer("inputSimple.c");
            lex.execute();

            Parser parser = new Parser(lex.getTokens());

            System.out.println("\n");

            Program program = parser.parseProgram();
            PrintWriter writer = new PrintWriter("out.xml", "UTF-8");
            if (program != null) {
                String file = program.toString(0);

                //4.1 checking names
                Scope scope = new Scope(null , "global");
                program.resolveNames(scope);

                //4.2 checking types
                program.checkTypes();


                writer.print(file);
                writer.close();
                System.out.println("\n");
            }


            if (program != null) {
                System.out.println("\nSyntax is correct\n");
            } else {
                System.out.println("Syntax is incorrect\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
