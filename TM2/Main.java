import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			Lexer lex = new Lexer("input2.txt");
			lex.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
