package com.company;

import com.company.nodes.*;
import javafx.util.Pair;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Parser {
  public ArrayList<Token> tokens;
  public HashMap<String, Boolean> hasChildren = new HashMap<>();
  public String fatherName;
  public int id;

  public Parser(ArrayList<Token> tokens) {
    this.tokens = tokens;
  }

  public ASTtree parse() throws Exception {
    return parse_program();
  }

  //<program> ::= <function-declaration> <program> | <function-declaration>
  public ASTtree parse_program() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"function_declaration", "program", fatherName}, 1), fatherName, id);
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }

  //<function-declaration> ::= <type-specifier> <identifier> "(" <parameter-list> ")" <block-statement> | <type-specifier> <identifier> "(" ")" <block-statement>
  public ASTtree parse_function_declaration() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"type_specifier", "identifier", "L_BRACKET", "parameter_list", "R_BRACKET", "block_statement", fatherName}), fatherName, id);
    if (node1 != null) {
      node1 = new FunctionDeclaration(node1.getChild(0), node1.getChild(1), node1.getChild(3), node1.getChild(5));
    }
    ASTtree node2 = node1 != null ? null : createFatherNode(createChildNode(new String[]{"type_specifier", "identifier", "L_BRACKET", "R_BRACKET", "block_statement", fatherName}), fatherName, id);


    return notEmptyNode(Arrays.asList(node1, node2), fatherName, id);
  }

  //<parameter-list> ::= <parameter-declaration> "," <parameter-list> | <parameter-declaration>
  public ASTtree parse_parameter_list() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"parameter_declaration", "COLUMN", "parameter_list", fatherName}, 1), fatherName, id);
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }


  //<parameter-declaration> ::= <type-specifier> <identifier>
  public ASTtree parse_parameter_declaration() throws Exception {
    String fatherName =  getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"type_specifier", "identifier", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }

  // <statements> ::= <statement> <statements> | <statement>
  public ASTtree parse_statements() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"statement", "statements", fatherName}, 1), fatherName, id);
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }

  // <statement> ::= <expression-statement> | <selection-statement> | <simple-statement> | <return-statement>
  public ASTtree parse_statement() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"simple_statement", fatherName}), fatherName, id);
    ASTtree node2 = node1 != null ? null : createFatherNode(createChildNode(new String[]{"return_statement", fatherName}), fatherName, id);
    ASTtree node3 = node1 != null || node2 != null ? null : createFatherNode(createChildNode(new String[]{"selection_statement", fatherName}), fatherName, id);
    ASTtree node4 = node1 != null || node2 != null || node3 != null ? null : createFatherNode(createChildNode(new String[]{"expression_statement", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1, node2, node3, node4), fatherName, id);
  }

  // <simple-statement> ::= <varDeclaration | <assignmentStmt> | <ioStmt>
  public ASTtree parse_simple_statement() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"assignment_statement", fatherName}), fatherName, id);
    ASTtree node2 = node1 != null ? null : createFatherNode(createChildNode(new String[]{"var_declaration", fatherName}), fatherName, id);
    ASTtree node3 = node1 != null || node2 != null ? null : createFatherNode(createChildNode(new String[]{"io_statement", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1, node2, node3), fatherName, id);
  }

  //<ioStmt> ::= <input-statement> | <output-statement>
  public ASTtree parse_io_statement() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"input_statement", fatherName}), fatherName, id);
    ASTtree node2 = node1 != null ? null : createFatherNode(createChildNode(new String[]{"output_statement", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1, node2), fatherName, id);
  }

  //<inputStmt>  ::= 'read' <identifier> ';'
  public ASTtree parse_input_statement() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"READ", "identifier", "SEMI_CLN", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }

  //<outputStmt>  ::= 'out' <expression-statement>
  public ASTtree parse_output_statement() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"OUT", "expression_statement", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }

  // <var-declaration> ::= <type-specifier> <identifier> ';'
  public ASTtree parse_var_declaration() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"type_specifier", "identifier", "SEMI_CLN", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }

  // <type-specifier> ::= 'char' | 'int' | 'string'
  public ASTtree parse_type_specifier() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"TYPE_CHAR", fatherName}), fatherName, id);
    ASTtree node2 = node1 != null ? null : createFatherNode(createChildNode(new String[]{"TYPE_INT", fatherName}), fatherName, id);
    ASTtree node3 = node1 != null || node2 != null ? null : createFatherNode(createChildNode(new String[]{"TYPE_STRING", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1, node2, node3), fatherName, id);
  }

  //<assignmentStmt> ::= <identifier> "=" <expression-statement>
  public ASTtree parse_assignment_statement() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"identifier", "OP_EQ", "expression_statement", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }

  //<expression-statement> ::= <expression> ";"
  public ASTtree parse_expression_statement() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"expression", "SEMI_CLN", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }

  //<expression> ::= <expr-0>
  public ASTtree parse_expression() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"expr_0", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }

  // <expr-0> ::= <expr-1> "+" <expr-0> | <expr-1>
  public ASTtree parse_expr_0() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"expr_1", "PLUS", "expr_0", fatherName}, 1), fatherName, id);
    ASTtree node2 = node1 != null ? null : createFatherNode(createChildNode(new String[]{"expr_1", fatherName}), fatherName, id);

    if (node1 != null && node1.getChildren().size() == 3) {
      node1 = new AddSubExpression("+", node1.getChildren().get(0), node1.getChildren().get(2));
    }
    return notEmptyNode(Arrays.asList(node1, node2), fatherName, id);
  }

  // <expr-1> ::= <expr-2> "-" <expr-1> | <expr-2>
  public ASTtree parse_expr_1() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"expr_2", "MINUS", "expr_1", fatherName}, 1), fatherName, id);
    ASTtree node2 = node1 != null ? null : createFatherNode(createChildNode(new String[]{"expr_2", fatherName}), fatherName, id);

    if (node1 != null && node1.getChildren().size() == 3) {
      node1 = new AddSubExpression("-", node1.getChildren().get(0), node1.getChildren().get(2));
    }
    return notEmptyNode(Arrays.asList(node1, node2), fatherName, id);
  }

  //  <expr-2> ::= <expr-3> "/" <expr-2> | <expr-3>
  public ASTtree parse_expr_2() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"expr_3", "DIV", "expr_2", fatherName}, 1), fatherName, id);
    ASTtree node2 = node1 != null ? null : createFatherNode(createChildNode(new String[]{"expr_3", fatherName}), fatherName, id);

    if (node1 != null && node1.getChildren().size() == 3) {
      node1 = new ModDivExpression("/", node1.getChildren().get(0), node1.getChildren().get(2));
    }
    return notEmptyNode(Arrays.asList(node1, node2), fatherName, id);
  }

  //  <expr-3> ::= <expr-4> "*" <expr-3> | <expr-4>
  public ASTtree parse_expr_3() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"expr_4", "MULT", "expr_3", fatherName}, 1), fatherName, id);
    ASTtree node2 = node1 != null ? null : createFatherNode(createChildNode(new String[]{"expr_4", fatherName}), fatherName, id);

    if (node1 != null && node1.getChildren().size() == 3) {
      node1 = new ModDivExpression("*", node1.getChildren().get(0), node1.getChildren().get(2));
    }
    return notEmptyNode(Arrays.asList(node1, node2), fatherName, id);
  }

  // <expr-4> ::= <function-call> | <identifier> | <int> | "(" <expr-0> ")"
  public ASTtree parse_expr_4() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);
    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"function_call", fatherName}), fatherName, id);
    ASTtree node2 = node1 != null ? null : createFatherNode(createChildNode(new String[]{"NUM_CONST", fatherName}), fatherName, id);
    ASTtree node3 = node1 != null || node2 != null ? null : createFatherNode(createChildNode(new String[]{"identifier", fatherName}), fatherName, id);
    ASTtree node4 = node1 != null || node2 != null || node3 != null ? null : createFatherNode(createChildNode(new String[]{"L_BRACKET", "expr_0", "R_BRACKET", fatherName}), fatherName, id);

    ASTtree returnNode = notEmptyNode(Arrays.asList(node1, node2, node3, node4), fatherName, id);
//    return new Expression(returnNode);
    return returnNode;
  }

  // <return-statement> ::= 'return' <expr-0> ';' | 'return' ';'
  public ASTtree parse_return_statement() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"RETURN", "expr_0", "SEMI_CLN", fatherName}), fatherName, id);
    if (node1 != null) {
      node1 = new ReturnStatement(node1.getChild(1));
    }
    ASTtree node2 = node1 != null ? null : createFatherNode(createChildNode(new String[]{"RETURN", "SEMI_CLN", fatherName}), fatherName, id);
    if (node2 != null) {
      node2 = new ReturnStatement();
    }
    return notEmptyNode(Arrays.asList(node1, node2), fatherName, id);
  }

  // <selection-statement> ::= if ( <expression> ) <block-statement>
  //                         | if ( <expression> ) <block-statement> else <block-statement>
  public ASTtree parse_selection_statement() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"IF", "L_BRACKET", "expression", "R_BRACKET",
            "block_statement", "ELSE", "block_statement", fatherName}, 5), fatherName, id);
    if (node1 != null ) {
      node1 = new SelectionStatement(node1.getChild(2), node1.getChild(4));
    }
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }


  //<block-statement> ::= "{" <statements> "}" | "{" "}"
  public ASTtree parse_block_statement() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"L_CURLY", "statements", "R_CURLY", fatherName}), fatherName, id);
    ASTtree node2 = createFatherNode(createChildNode(new String[]{"L_CURLY", "R_CURLY", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1, node2), fatherName, id);
  }

  //<function-call> ::= <identifier> "(" <call-param-list> ")" | <identifier> "(" ")"
  public ASTtree parse_function_call() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"identifier", "L_BRACKET", "call_param_list", "R_BRACKET", fatherName}), fatherName, id);
    ASTtree node2 = createFatherNode(createChildNode(new String[]{"identifier", "L_BRACKET", "R_BRACKET", fatherName}), fatherName, id);
    return notEmptyNode(Arrays.asList(node1, node2), fatherName, id);
  }

  // <call-param-list-item> ::= <expr-0> ',' <call-param-list> | <expr-0>
  public ASTtree parse_call_param_list() throws Exception {
    String fatherName = getFatherName();
    System.out.println(fatherName);

    int id = getNextId();
    ASTtree node1 = createFatherNode(createChildNode(new String[]{"expr_0", "COLUMN", "call_param_list", fatherName}, 1), fatherName, id);
    return notEmptyNode(Arrays.asList(node1), fatherName, id);
  }

  public ASTtree parse_READ() throws IOException {
    if (equalStates(State.READ)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_OUT() throws IOException {
    if (equalStates(State.WRITE)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_TYPE_INT() throws IOException {
    if (equalStates(State.TYPE_INT)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_TYPE_CHAR() throws IOException {
    if (equalStates(State.TYPE_CHAR)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_TYPE_STRING() throws IOException {
    if (equalStates(State.TYPE_STRING)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_COLUMN() throws IOException {
    if (equalStates(State.COLUMN)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_RETURN() throws IOException {
    if (equalStates(State.RETURN)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_IF() throws IOException {
    if (equalStates(State.IF)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_R_CURLY() throws IOException {
    if (equalStates(State.R_CURLY)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_L_CURLY() throws IOException {
    if (equalStates(State.L_CURLY)) {
      return new ASTtree(createNode());
    }
    return null;
  }


  public ASTtree parse_ELSE() throws IOException {
    if (equalStates(State.ELSE)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_L_BRACKET() throws IOException {
    if (equalStates(State.L_BRACKET)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_R_BRACKET() throws IOException {
    if (equalStates(State.R_BRACKET)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_SEMI_CLN() throws IOException {
    if (equalStates(State.SEMI_CLN)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_OP_EQ() throws IOException {
    if (equalStates(State.ASSIGN_OP_EQ)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_LOGIC_OP_AND() throws IOException {
    if (equalStates(State.LOGIC_OP_AND)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_identifier() throws IOException {
    if (equalStates(State.IDENTIFIER)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_COMP_OP_EQ() throws IOException {
    if (equalStates(State.COMP_OP_EQ)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_PLUS() throws IOException {
    if (equalStates(State.PLUS)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_DIV() throws IOException {
    if (equalStates(State.DIV)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_MINUS() throws IOException {
    if (equalStates(State.MINUS)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_MULT() throws IOException {
    if (equalStates(State.MULT)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public ASTtree parse_NUM_CONST() throws IOException {
    if (equalStates(State.NUM_CONST)) {
      return new ASTtree(createNode());
    }
    return null;
  }

  public boolean equalStates(State state) {
    Token notReserved = getNotReservedToken();
    if (notReserved.getState().equals(state.name())) {
      notReserved.reservedFor = fatherName;
      return true;
    }
    return false;
  }

  public Pair<String, String> createNode() {
    Token lastReserved = null;
    for (Token token : tokens) {
      if (token.reservedFor != null) {
        lastReserved = token;
      } else {
        break;
      }
    }
    Pair<String, String> pair = new Pair<>(lastReserved.getState(), lastReserved.getLexem());
    return pair;
  }

  public ASTtree createFatherNode(List<ASTtree> nodes, String nodeName, int id) {
    if (hasChildren.get(nodeName + id) != null && hasChildren.get(nodeName + id).equals(true)) {
      return null;
    }
    if (nodes != null) {
      deleteReservedTokens();
      ASTtree node;
      node = new ASTtree(nodeName, "");
      for (ASTtree child : nodes) {
        node.addChild(child);
      }
      hasChildren.put(nodeName + id, true);
      return node;
    }
    return null;
  }

  // orNodesCount - node duplicate because of 'or' count
  public ArrayList<ASTtree> createChildNode(String[] nodeNames, int orNodesCount) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
    Method method;
    ASTtree child;
    String fatherName = nodeNames[nodeNames.length - 1];
    ArrayList<ASTtree> childs = new ArrayList<>();
    ArrayList<String> nodesToFree = new ArrayList<>();
    Object obj = this;
    int nodeCount = 0;

    String grandFather = getGrandFatherName();
    if (grandFather.equals("ChildNode")) {
      grandFather = getGrandGrandFatherName();
    }
    if (hasChildren.get(grandFather + id) != null && hasChildren.get(grandFather + id).equals(true)) {
      return null;
    }

    for (String nodeName : nodeNames) {
      if (nodeName == nodeNames[nodeNames.length - 1]) {
        break;
      }
      method = Parser.class.getDeclaredMethod("parse_" + nodeName);
      this.fatherName = fatherName;
      try {
        child = (ASTtree) method.invoke(obj);
        if (child == null) {
          if (orNodesCount != 0 && nodeCount >= orNodesCount) {
            return childs;
          }
          clearTokensResevation(fatherName, nodeCount);
          for (String node : nodesToFree) {
            clearTokensResevation(node, nodeCount);
          }
          return null;
        } else {
          nodeCount++;
          nodesToFree.add(nodeName);
          childs.add(child);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return childs;
  }

  public ArrayList<ASTtree> createChildNode(String[] nodeNames) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
    return createChildNode(nodeNames, 0);
  }


  public ASTtree notEmptyNode(List<ASTtree> nodes, String fatherName, int id) {
    hasChildren.put(fatherName + id, Boolean.FALSE);
    ASTtree notEmptyNode = null;
    ASTtree nodeIter = null;
    for (ASTtree node : nodes) {
      if (node != null) {
        notEmptyNode = node;
        nodeIter = node;
        break;
      }
    }
    return notEmptyNode != null ? notEmptyNode : null;
  }

  public Token getNotReservedToken() {
    Token notReserved = new Token("init", "init");
    for (Token token : tokens) {
      if ((token.reservedFor == null || token.reservedFor.isEmpty()) && !token.getState().equals("SINGLE_L_COMMENT") && !token.getState().equals("MULTI_L_COMMENT")) {
        notReserved = token;
        break;
      }
    }
    return notReserved;
  }

  public void deleteReservedTokens() {
    int reservedCount = 0;
    for (Token token : tokens) {
      if (token.isReserved()) {
        reservedCount++;
      }
    }
    for (int i = 0; i < reservedCount; i++) {
      tokens.remove(0);
    }
  }

  public void clearTokensResevation(String fatherName, int nodeCount) {
    int lastReserved = -1;
    for (Token token : tokens) {
      if (token.reservedFor == null)
        break;
      lastReserved++;
    }
    if (lastReserved == -1 || nodeCount == 0) {
      return;
    }
    for (int i = lastReserved - nodeCount + 1; i < lastReserved + nodeCount; i++) {
      if (i >= 0 && i < tokens.size())
        if (i < tokens.size() && fatherName.equals(tokens.get(i).reservedFor)) {
          tokens.get(i).reservedFor = null;
        }
    }
  }

  public String getFatherName() {
    StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    return trace[2].getMethodName().substring(6);
  }

  public String getGrandFatherName() {
    StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    return trace[3].getMethodName().substring(6);
  }

  public String getGrandGrandFatherName() {
    StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    return trace[4].getMethodName().substring(6);
  }

  public int getNextId() {
    return id++;
  }
}
