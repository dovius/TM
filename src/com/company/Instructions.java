package com.company;

public final class Instructions {
	//********LOCAL ALLOC********
	public static final int I_LOCAL_ALLOC = 0;
	
	//********MATHEMATICAL OPERATIONS********
	public static final int I_ADD = 1;
	public static final int I_SUB = 2;
	public static final int I_MUL = 3;
	public static final int I_DIV = 4;
	
	//********LOGIC OPERATIONS********
	public static final int I_LOGIC_OR 	= 5;
	public static final int I_LOGIC_AND = 6;
	
	//********COMPARISON OPERATIONS********
	public static final int I_GREATER 	 = 7;
	public static final int I_GREATER_EQ = 8;
	public static final int I_LESS 		 = 9;
	public static final int I_LESS_EQ 	= 10;
	public static final int I_EQ 		= 11;
	public static final int I_NOT_EQ 	= 12;
	
	//********SET, GET********
	public static final int I_SET = 13;
	public static final int I_GET = 14;
	
	//********JUMPS********
	public static final int I_JMP = 15;
	public static final int I_JZ  = 16;
	
	//********CALL, RETURN********
	public static final int I_CALL	    = 17;
	public static final int I_RET_VALUE = 18;
	
	//********PUSH, POP********
	public static final int I_PUSH_INT 	  = 19;
	public static final int I_PUSH_STRING = 20;
	public static final int I_PUSH_BOOL	  = 21;
	public static final int I_POP 		  = 22;
	
	//********SCAN, PRINT********
	public static final int I_SCAN  = 23;
	public static final int I_PRINT = 24;	
	
	//********EXIT********
	public static final int I_EXIT  = 25;

	//********ARRAY********
	public static final int I_ARRAY_VALUE_SET = 26;
	public static final int I_ARRAY_VALUE_GET = 27;
	public static final int I_ARRAY_GET = 28;
	public static final int I_TOSTRING= 29;
	public static final int I_TOINT = 30;
	public static final int I_PRINT_ARRAY = 31;
	private int instructionNumber;

	
	private Instructions(int instrNumb ) {
		this.instructionNumber = instrNumb;
	}
	
	public int getInstrNumb() {
		return this.instructionNumber;
	}
	
}
