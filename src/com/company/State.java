package com.company;

public enum State {
  MAIN("main"),
  IDENTIFIER("identifier"),
  NUM_CONST("number"),
  IF("if"),
  ELSE("else"),
  BREAK("break"),
  RETURN("return"),
  READ("read"),
  WRITE("write"),
  OUT("out"),
  TYPE_STRING("string"),
  TYPE_INT("int"),
  TYPE_CHAR("char"),
  STRING("string object"),
  STRING_SPECIAL_SYMB("string special symbol"),
  L_BRACKET("("),
  R_BRACKET(")"),
  L_CURLY("{"),
  R_CURLY("}"),
  SEMI_CLN(";"),
  PLUS("+"),
  MINUS("-"),
  MULT("*"),
  DIV("/"),
  SLASH("/"),
  NEGATION("!"),
  COLUMN(","),

  SINGLE_L_COMMENT("//"),
  MULTI_L_COMMENT("/*..*/"),

  EQ_HANDLER("="),
  MORE_HANDLER(">"),
  LESS_HANDLER("<"),
  LOGIC_AND_HANDLER("&"),
  LOGIC_OR_HANDLER("|"),
  MULTI_L_COMMENT_HANDLER("MULTI LINE COMMENT HANDLER"),

  COMP_OP_MORE(">"),
  COMP_OP_LESS("<"),
  COMP_OP_MORE_EQ(">="),
  COMP_OP_LESS_EQ("<="),
  COMP_OP_EQ("=="),
  COMP_OP_NOT_EQ("!="),
  LOGIC_OP_OR("||"),
  LOGIC_OP_AND("&&"),
  ASSIGN_OP_EQ("="),
  ERROR_UNKNOWN_SYMB("error");

  private String name;

  private State(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public String toString() {
    return name();
  }
}
