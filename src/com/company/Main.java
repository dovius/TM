package com.company;

import java.io.IOException;

public class Main {
  public static void main(String[] args) throws Exception {

    try {
      Lexer lex = new Lexer("inputSimple.txt");
      lex.execute();

      Parser parser = new Parser(lex.getTokens());

      System.out.println("\n");

      ASTtree tree = parser.parse();

      System.out.println("\n");
      if (tree != null) {
        displayTree(tree, 0);
        System.out.println("\nSyntaxeed is correct\n");
      }
      else {
        System.out.println("Syntaxee is incorrect\n");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static void displayTree(ASTtree root, int level) {
    if (!root.getState().contains("expr_")) {
      System.out.print(prefix(level));
      System.out.println("<" + root.getState() + ">");
    }
    else {
      level--;
    }

    for (ASTtree child : root.getChildren()) {
      displayTree(child, level + 1);
    }

    if (root.getChildren().size() == 0) {
      System.out.print(prefix(level+1));
      System.out.println(root.getLexem());
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
