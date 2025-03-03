package provided;
/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */
import java.util.ArrayList;
import node.ProgramNode;

/*
 * Authors:
 *    Jacob Karam
 *    Jack Billington
 *    Jayson Salemmo
 *    Landon Spitzer
 *    Nick Azzarano
 * 
 * Start Date: 2/13/2024
 * Last Updated: 3/8/2024 Nick Azzarano
 * 
 * Java Version:  21.0.2 JDK
 */

public class JottParser {

  private final JottTree jottTree;

  public JottParser(JottTree jottTree) {
        this.jottTree = jottTree;
}

  /**
   * Parses an ArrayList of Jotton tokens into a Jott Parse Tree.
   * 
   * @param tokens the ArrayList of Jott tokens to parse
   * @return the root of the Jott Parse Tree represented by the tokens.
   *         or null upon an error in parsing.
   */
  public static JottTree parse(ArrayList<Token> tokens) {
    try {
      JottTree jottTree = ProgramNode.parseProgramNode(tokens);
      return jottTree;

    } catch (Exception e) {
      System.err.println(e);
      return null;
    }
  }

  public String convertToJott(ArrayList<Token> tokens) {
      String jottString = "";
      jottString = jottString + jottTree.convertToJott();
      return jottString;
    }
}
