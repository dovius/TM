package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Interpreter {

    public ArrayList<Instruction> code;
    public int ip;    //instruction pointer
    public int sp;    //stack pointer
    public int bp;    //base pointer
    public Object[] stack;
    public boolean running;
    public Scanner reader;
    public static int ARR_SIZE = 128;

    public Interpreter(ArrayList<Instruction> ir) {
        this.code = ir;
        this.ip = 0;
        this.sp = 0;
        this.bp = 0;
        this.stack = new Object[512*4];
        this.running = true;
        this.reader = new Scanner(System.in);
    }

    public void execute(int ip) {
        this.ip = ip;
        while (running == true) {
            executeInstruction();
        }
        System.out.println("\nProgram returned: " + stack[0]);
        reader.close();
    }

    public void executeInstruction() {
        int oldIp = this.ip;
        int oldSp;
        int oldBp;
        Object value;
        Object value2;
        int slotId;
        Object left;
        Object right;
        int target;
        int index;
        Object condition;
        Instruction instr = readCode();
        switch (instr.instructionNumber) {
            case Instructions.I_LOCAL_ALLOC:
                int numSlots = Integer.valueOf(instr.args.get(0));
                sp += numSlots;
                break;

            case Instructions.I_GET:
                slotId = Integer.valueOf(instr.args.get(0));
                push(stack[bp + slotId]);
                break;

            case Instructions.I_ARRAY_VALUE_GET:
                index = (int) pop();
                slotId = Integer.valueOf(instr.args.get(0));
                push(stack[bp+slotId+index]);
                break;

            case Instructions.I_ARRAY_VALUE_SET:
                value = pop();
                index = (int) pop();
                slotId = Integer.valueOf(instr.args.get(0));
                stack[bp + slotId + index] = value;
                break;

            case Instructions.I_ARRAY_GET:
                slotId = Integer.valueOf(instr.args.get(0));
                index = Integer.valueOf(instr.args.get(1));
                int test=0;
                for (int i = 0; i<index && i<ARR_SIZE; i++) {
                    push(stack[bp+slotId+i]);
                    test++;
                }
                for (int i = index; i<Interpreter.ARR_SIZE;i++) {
                    push("***");
                    test++;
                }
//                System.out.println(test);
                break;
            case Instructions.I_SET:
                slotId = Integer.valueOf(instr.args.get(0));
                value = pop();
                String operator = instr.args.get(1);
                switch (operator) {
                    case "=":
                        stack[bp + slotId] = value;
                        break;
                    case "+=":
                        stack[bp + slotId] = (int) stack[bp + slotId] + (int) value;
                        break;
                    case "-=":
                        stack[bp + slotId] = (int) stack[bp + slotId] - (int) value;
                        break;
                    case "/=":
                        stack[bp + slotId] = (int) stack[bp + slotId] / (int) value;
                        break;
                    case "*=":
                        stack[bp + slotId] = (int) stack[bp + slotId] * (int) value;
                        break;
                }
                break;

            case Instructions.I_POP:
                pop();
                break;

            case Instructions.I_PUSH_INT:
                value = Integer.valueOf(instr.args.get(0));
                push(value);
                break;

            case Instructions.I_PUSH_STRING:
                push(instr.args.get(0));
                break;

            case Instructions.I_PUSH_BOOL:
                push(Boolean.valueOf(instr.args.get(0)));
                break;

            case Instructions.I_ADD:

                right = pop();
                left = pop();
                try {
                    push( (int) left + (int) right);

}
                catch (Exception e){
                    String tt = (String.valueOf(right)+String.valueOf(left));
                    tt=tt.replace("'","");
                    push (tt);}

                break;

            case Instructions.I_SUB:
                left = pop();
                right = pop();
                push( (int) left - (int) right);
                break;
            case Instructions.I_MUL:
                left = pop();
                right = pop();
                push((int) left * (int) right);
                break;

            case Instructions.I_DIV:
                left = pop();
                right = pop();
                push((int) left / (int) right);
                break;

            case Instructions.I_EQ:
                right = pop();
                left = pop();
                push(left.equals(right));
                break;

            case Instructions.I_NOT_EQ:
                right = pop();
                left = pop();
                push(!left.equals(right));
                break;

            case Instructions.I_GREATER:
                left = pop();
                right = pop();
                push((int) left > (int) right);
                break;

            case Instructions.I_GREATER_EQ:
                left = pop();
                right = pop();
                push((int) left >= (int) right);
                break;

            case Instructions.I_LESS:
                left = pop();
                right = pop();
                push((int) left < (int) right);
                break;

            case Instructions.I_LESS_EQ:
                left = pop();
                right = pop();
                push((int) left <= (int) right);
                break;

            case Instructions.I_LOGIC_OR:
                left = pop();
                right = pop();
                push((boolean) left || (boolean) right);
                break;

            case Instructions.I_LOGIC_AND:
                left = pop();
                right = pop();
                push((boolean) left && (boolean) right);
                break;

            case Instructions.I_JMP:
                ip = instr.label.position;
                break;

            case Instructions.I_JZ:
                target = instr.label.position;
                condition = pop();
                if (condition.equals(false)) {
                    ip = target;
                }
                break;

            case Instructions.I_CALL:
                target = instr.label.position;
                int numbArgs = Integer.valueOf(instr.args.get(0));
                Object[] args = new Object[numbArgs];
                for (int i = 0; i < args.length; i++) {
                    args[args.length - i - 1] = pop();
                }
                oldSp = sp;
                push(ip);
                push(oldSp);
                push(bp);
                ip = target;
                bp = sp;
                for (int i = 0; i < args.length; i++) {
                    stack[bp + i] = args[i];
                }
                break;

            case Instructions.I_RET_VALUE:
                value = pop();
                ret(value);
                break;

            case Instructions.I_TOSTRING:
                value = pop();
                String temp = String.valueOf(value);

                push(temp);
                break;
            case Instructions.I_TOINT:
                value = pop();
                String string = String.valueOf(value);
                string = string.substring(1, string.length()-1);
                Integer temp2=Integer.valueOf(string);

                push(temp2);
                break;


            case Instructions.I_EXIT:
                running = false;
                break;

            case Instructions.I_PRINT:
                value = pop();
                System.out.println("Print: " + value);
                break;

            case Instructions.I_PRINT_ARRAY:

                String i = instr.args.get(0);
                String j = instr.args.get(1);

                for(int a = 0; a < Integer.valueOf(j); a++) {
                    int ind = bp + Integer.valueOf(i) +a;
                    System.out.println("Print: " + stack[ind]) ;
                }
                break;

            case Instructions.I_SCAN:
                slotId = Integer.valueOf(instr.args.get(0));
                String varType = instr.args.get(1);
                try {
                    if (varType.equals("int")) {
                        System.out.println("Enter int type input: ");
                        int tempInt = Integer.valueOf(reader.next());
                        stack[bp + slotId] = tempInt;
                    } else if (varType.equals("string")) {
                        System.out.println("Enter string type input: ");
                        String tempString = "\'";
                        tempString += reader.next();
                        tempString += "\'";
                        stack[bp + slotId] = tempString;
                    } else if (varType.equals("bool")) {
                        System.out.println("Enter bool type input: ");
                        Boolean tempBoolean = reader.nextBoolean();
                        stack[bp + slotId] = tempBoolean;
                    }
                } catch (Exception e) {
                    System.out.println("Error: invalid input!");
                    running = false;
                }
                break;
            default:
                running = false;
                System.out.println("Error bad instruction: " + instr.instructionNumber);
        }
    }

    public void ret(Object value) {
        int oldIp = (int) stack[bp - 3];
        int oldSp = (int) stack[bp - 2];
        int oldBp = (int) stack[bp - 1];
        ip = oldIp;
        sp = oldSp;
        bp = oldBp;
        push(value);
    }

    public Object pop() {
        this.sp -= 1;
        return this.stack[sp];
    }

    public void push(Object value) {
        this.stack[this.sp] = value;
        this.sp += 1;
    }

    public Instruction readCode() {
        Instruction value = this.code.get(this.ip);
        this.ip += 1;
        return value;
    }

}
