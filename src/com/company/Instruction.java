package com.company;


import java.util.ArrayList;

public class Instruction {
	public Label label;
	public ArrayList<String> args;
	public int instructionNumber;

	
	public Instruction() {
		this.label = null;
		this.args = new ArrayList<String>();
	}
	
	public String getArgs() {
		String output = "";
		if( args != null ) {
			for( int i = 0; i < args.size(); i++ ) {
				output += args.get( i ) + " ";
			}
		}
		return output;
	}
	
	public String getLabel() {
		String output = "";
		if( label != null ) {
			output += String.valueOf( label.position );
		}
		return output;
	}
	
	public void printInstr() {
		String output = String.format( "%1$-20s", getInstructionNameByNumber( this.instructionNumber ) + "    ");
		output += String.format( "%1$-15s", this.getArgs() + this.getLabel() );
		System.out.println( output );
	}
	
	public String getInstructionNameByNumber( int instrNumb ) {
		switch( instrNumb ) {
			case 0:
				return "I_LOCAL_ALLOC";
			case 1:
				return "I_ADD";
			case 2:
				return "I_SUB";
			case 3:
				return "I_MUL";
			case 4:
				return "I_DIV";
			case 5:
				return "I_LOGIC_OR";
			case 6:
				return "I_LOGIC_AND";
			case 7:
				return "I_GREATER";
			case 8:
				return "I_GREATER_EQ";
			case 9:
				return "I_LESS";
			case 10:
				return "I_LESS_EQ";
			case 11:
				return "I_EQ";
			case 12:
				return "I_NOT_EQ";
			case 13:
				return "I_SET";
			case 14:
				return "I_GET";
			case 15:
				return "I_JMP";
			case 16:
				return "I_JZ";
			case 17:
				return "I_CALL";
			case 18:
				return "I_RET_VALUE";
			case 19:
				return "I_PUSH_INT";
			case 20:
				return "I_PUSH_STRING";
			case 21:
				return "I_PUSH_BOOL";
			case 22:
				return "I_POP";
			case 23:
				return "I_SCAN";
			case 24:
				return "I_PRINT";
			case 25:
				return "I_EXIT";
			case 26:
				return "I_ARRAY_VAL_SET";
			case 27:
				return "I_ARRAY_VAL_GET";
			case 28:
				return "I_ARRAY_GET";
			default:
				return "I_UNKNOWN";
		}

	}
}
