package com.company.nodes;
import com.company.ASTtree;

public class AddSubExpression extends Expression {

  public ASTtree operatorNode;
  public ASTtree leftNode;
  public ASTtree rightNode;

  public AddSubExpression(String operator, ASTtree left, ASTtree right) {
    this.state = "AddSubExpression";

    operatorNode = new ASTtree("operator", operator);
    leftNode = new ASTtree("left", left);
    rightNode = new ASTtree("right", right);

    this.children.add(operatorNode);
    this.children.add(leftNode);
    this.children.add(rightNode);
  }
}
