package com.company.nodes;

import com.company.ASTtree;

/**
 * Created by dovydas on 16.12.16.
 */
public class SelectionStatement extends Statement {

  public ASTtree conditionNode;
  public ASTtree blockNode;

  public SelectionStatement(ASTtree condition, ASTtree block) {
    this.state = "SelectionStatement";

    conditionNode = new ASTtree("*condition*", condition);
    blockNode = new ASTtree("*block*", block);

    this.children.add(conditionNode);
    this.children.add(blockNode);
  }
}
