package com.company.nodes;

import com.company.ASTtree;

import java.util.ArrayList;

public class ModDivExpression extends Expression {

  public ASTtree operatorNode;
  public ASTtree leftNode;
  public ASTtree rightNode;

  public ModDivExpression(String operator, ASTtree left, ASTtree right) {
    this.children = new ArrayList<>();
    this.state = "ModDivExpression";

    operatorNode = new ASTtree("operator", operator);
    leftNode = new ASTtree("left", left);
    rightNode = new ASTtree("right", right);

    this.children.add(operatorNode);
    this.children.add(leftNode);
    this.children.add(rightNode);

  }

}
