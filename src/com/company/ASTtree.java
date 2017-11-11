package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ASTtree {
  public List<ASTtree> children = null;
  public String state;
  public String lexem;

  public ASTtree() {
    this.children = new ArrayList<>();
    this.state = new String();
    this.lexem = new String();
  }

  public ASTtree(String state, String lexem) {
    this.children = new ArrayList<>();
    this.state = state;
    this.lexem = lexem;
  }

  public ASTtree(String state, ASTtree node) {
    this.children = new ArrayList<>();
    this.state = state;
    this.lexem = node.lexem;
    this.children = node.getChildren();
  }

  public ASTtree(Pair<String,String> pair) {
    this.children = new ArrayList<>();
    this.state = pair.getKey();
    this.lexem = pair.getValue();
  }

  public void addChild(ASTtree child) {
    children.add(child);
    lexem += child.lexem;
  }

  public String getLexem() {
    return lexem;
  }

  public void setLexem(String lexem) {
    this.lexem = lexem;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public List<ASTtree> getChildren() {
    return children;
  }

  public ASTtree getChild(int i) {
    return children.get(i);
  }
}
