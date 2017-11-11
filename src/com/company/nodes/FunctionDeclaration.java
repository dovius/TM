package com.company.nodes;
import com.company.ASTtree;
import java.util.ArrayList;

public class FunctionDeclaration extends ASTtree {

  public ASTtree typeNode;
  public ASTtree identifierNode;
  public ASTtree parametersNode;
  public ASTtree blockNode;

  public FunctionDeclaration(ASTtree type, ASTtree identifier, ASTtree parameters, ASTtree block) {
    this.children = new ArrayList<>();
    this.state = "FunctionExpression";

    typeNode = new ASTtree("typeNode", type);
    identifierNode = new ASTtree("identifierNode", identifier);
    parametersNode = new ASTtree("parametersNode", parameters);
    blockNode = new ASTtree("blockNode", block);

    this.children.add(typeNode);
    this.children.add(identifierNode);
    this.children.add(parametersNode);
    this.children.add(blockNode);

  }
}