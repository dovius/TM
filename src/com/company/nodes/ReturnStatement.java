package com.company.nodes;

import com.company.ASTtree;

/**
 * Created by dovydas on 16.12.16.
 */
public class ReturnStatement extends Statement {
  public ASTtree expression;

  public ReturnStatement(ASTtree expression) {
    expression = new ASTtree("*expression*", expression);
    this.state = "ReturnStatement";
    this.children.add(expression);
  }

  public ReturnStatement() {
    this.state = "ReturnStatement";
  }
}
