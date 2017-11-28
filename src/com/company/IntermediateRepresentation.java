package com.company;

import java.util.ArrayList;

public class IntermediateRepresentation {

	public ArrayList<Instruction> instructions;
	public ArrayList<Integer> intInstructions;
	public ArrayList<Label> labels;
	//public ArrayList<Global> globals;
	public ArrayList<Integer> globals;
	
	public IntermediateRepresentation() {
		instructions = new ArrayList<Instruction>();
		intInstructions = new ArrayList<Integer>();
		labels = new ArrayList<Label>();
		//globals = new ArrayList<Global>();
		globals = new ArrayList<Integer>();
	}
	
	public void addInstr( Instruction instruction) {
		instructions.add(instruction);
	}
	
	public void print() {
		System.out.println( "---------------------------------------" );
		String columnsDescription = String.format( "%1$-4s" , "NUMB") 			 + "    " +
								    String.format( "%1$-15s",  "COMMAND TITLE" ) + "    " + 
								    String.format( "%1$-15s", "COMMAND ARGS");
		System.out.println( columnsDescription );
		System.out.println( "---------------------------------------" );
		for( int i = 0; i < instructions.size(); i++ ) {
			String str = String.format("%04d",  i ) + "    ";
			System.out.print( str );
			instructions.get( i ).printInstr();
		}
	}
	
	public int offset() {
		return instructions.size();
	}
	
	public Label placeLabel( Label label ) {
		label.position = this.instructions.size();
		return label;
	}
	
	public Label newLabel() {
		Label label = new Label();
		labels.add( label );
		return label;
	}
	///*
	public void addGlobal( int slot ) {
		this.globals.add( slot, instructions.size() ); 
	}
	//*/
	/*
	public void addGlobal( String name ) {
		this.globals.add( slot, new Global( name,  instructions.size() ) ); 
	}
	*/
}
