package com.company.nodes;

import com.company.ASTtree;

import java.util.ArrayList;

/**
 * Created by dovydas on 16.12.16.
 */
public class Expression extends ASTtree {

  public Expression(ASTtree node) {
    this.children = node.getChildren();
    this.lexem = node.getLexem();
    this.state = node.getState();
  }

  public Expression() {
    this.children = new ArrayList<>();
  }
}
