package com.company;

public class Token {
  private State state;
  private String lexem;
  private boolean reserved;
  public String reservedFor;

  public Token(State state, String lexem) {
    this.state = state;
    this.lexem = lexem;
  }

  public State getState() {
    return state;
  }

  public String getLexem() {
    return lexem;
  }

  public boolean isReserved() {
    return reserved;
  }

  public void setReserved(boolean reserved) {
    this.reserved = reserved;
  }

  public void setLexem(String lexem) {
    this.lexem = lexem;
  }
}
