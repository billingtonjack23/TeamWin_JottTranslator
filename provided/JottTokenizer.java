package provided;

import java.io.CharArrayReader;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.*;

/*
 * Authors:
 *    Jacob Karam
 *    Jack Billington
 *    Jayson Salemmo
 *    Landon Spitzer
 *    Nick Azzarano
 * 
 * Start Date: 1/30/2024
 * Last Updated: 2/9/2024 Jacob Karam
 * 
 * Java Version:  21.0.2 JDK
 */

/**
 * This class is responsible for tokenizing Jott code.
 * 
 * @author 
 **/
@SuppressWarnings("unused")
public class JottTokenizer {

	/**
     * Takes in a filename and tokenizes that file into Tokens
     * based on the rules of the Jott Language
     * @param filename the name of the file to tokenize; can be relative or absolute path
     * @return an ArrayList of Jott Tokens
	 * @throws FileNotFoundException 
     */
    public static ArrayList<Token> tokenize(String filename) {

      /* File to be read, created from 'filename' */
      File file = new File(filename);

      /* Java scanner used to read the .jott code files */
      Scanner scan;
      try {
        scan = new Scanner(file);

              /* String that holds all characters from a single line of the .jott file */
      String line = new String();

      /* The array used to store each of the identified tokens in order */
      ArrayList<Token> tokens = new ArrayList<Token>();

      /* Tracks the current line number */
      int lineNumber = 0;

      /*strings to check if input lines contain letters or digits */
      String digits = "0123456789";
      String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

      /* Main tokenizer loop. Runs until it reaches end of file */
      mainLoop:
      while (scan.hasNextLine()) {

        lineNumber++;

        line = scan.nextLine();   // Get the line the scanner is currently looking at
        char[] charArray = line.toCharArray();  // Create an array of characters from 'line'
        int arrLength = charArray.length;   // The number of characters on current line

        /* Secondary tokenizer loop. Looks at an individual line of the .jott file */
        int i = 0;
        lineLoop:
        while (i < arrLength) {

          String curr = String.valueOf(charArray[i]);   // The character we are currently looking at

          // whitespace
          if (curr.equals(" ") || curr.equals("\t") || curr.equals("\n")) {     // whitespace
            i++;
            continue lineLoop;
          }


          // # (comment - throw away)
           else if (curr.equals("#")) {
            commentLoop:
            while (i < arrLength) {
              curr = String.valueOf(charArray[i]);   // The character we are currently looking at
              if (curr.equals("\n")) { // if end line, move to the next line
                i++;
                break commentLoop;
              }
              else {
                i++;
              }
            }

            //System.err.println("DID NOT GET TO A NEW LINE AFTER COMMENT");
            //return  null;
            //throw new RuntimeException(" DID NOT GET TO A NEW LINE AFTER COMMENT");
          }


          // , (COMMA)
          else if (curr.equals(",")) {                                                      // , (COMMA)
            Token comma = new Token(curr, filename, lineNumber, TokenType.COMMA);                     //creates comma token and adds to token list
            tokens.add(comma);
            i++;
            continue lineLoop;
          }


          // ] (R_BRACKET)
          else if (curr.equals("]")) {                                                      // ] (R_BRACKET)
            Token r_bracket = new Token(curr, filename, lineNumber, TokenType.R_BRACKET);           //creates r_bracket token and adds to token list
            tokens.add(r_bracket);
            i++;
            continue lineLoop;
          }


          /* [ (L_BRACKET) */
          else if (curr.equals("[")) {                                                      // ] (R_BRACKET)
            Token l_bracket = new Token(curr, filename, lineNumber, TokenType.L_BRACKET);             //creates l_bracket token and adds to token list
            tokens.add(l_bracket);
            i++;
            continue lineLoop;
          }


          /* } (R_BRACE) */
          else if (curr.equals("}")) { // } (R_BRACE)
            Token r_brace = new Token(curr, filename, lineNumber, TokenType.R_BRACE);                //creates r_brace token and adds to token list
            tokens.add(r_brace);
            i++;
            continue lineLoop;
          }


          /* { (L_BRACE) */
          else if (curr.equals("{")) { // } (L_BRACE)
            Token l_brace = new Token(curr, filename, lineNumber, TokenType.L_BRACE);                 //creates l_brace token and adds to token list
            tokens.add(l_brace);
            i++;
            continue lineLoop;
          }


          /* = (ASSIGN) */
          else if (curr.equals("=")) { // } (ASSIGN) || (REL_OP) */  
            if (!(i == arrLength - 1)) {                                      //if not at the end of the line,
              if (String.valueOf(charArray[i + 1]).equals("=")) {   // if next symbol is also an =, then it is a rel op not assign
                curr += "=";                                                  //append other "=" to token for entire rel op
                Token relop_equals = new Token(curr, filename, lineNumber, TokenType.REL_OP);       //create rel op token and add to token list
                tokens.add(relop_equals);
                i += 2;                                                             //increment by 2 since 2 characters are used for this token
              }
              else {                                                                          //else, create assign token and add to token list
                Token assign = new Token(curr, filename, lineNumber, TokenType.ASSIGN);
                tokens.add(assign);
                i++;
                continue lineLoop;
              }
            }
            else {                                                                        //if it is the last character in the line, then 
              Token assign = new Token(curr, filename, lineNumber, TokenType.ASSIGN);     //create assign token and add to token list
              tokens.add(assign);
              i++;
              continue lineLoop;
            }
          }

          /* <> (REL_OP) */
          else if (curr.equals("<") || curr.equals(">")) {                
            if (String.valueOf(charArray[i + 1]).equals("=")) {   /* = (REL_OP) */  //if > or < is followed by an "=",
              curr += "=";                                                                    //append "=" to current token,
              Token relop = new Token(curr, filename, lineNumber, TokenType.REL_OP);          //add token to token list
              tokens.add(relop);
              i += 2;                                                                          //increment by 2 since token is 2 characters long
            } 
            else {                                                                             //if > or < is not followed by "="
              Token relop = new Token(curr, filename, lineNumber, TokenType.REL_OP);          // add token to token list
              tokens.add(relop);
              i++;
            }
          }
          
          //* +-*/ (MATH_OP) */
          else if (curr.equals("+") || curr.equals("-") || curr.equals("*") || curr.equals("/")) {
            Token mathop = new Token(curr, filename, lineNumber, TokenType.MATH_OP);              //create math op token and add to token list
            tokens.add(mathop);
            i++;
          }
                    


          /* ; (SEMICOLON) */
          else if (curr.equals(";")) {
            Token semi_colon = new Token(curr, filename, lineNumber, TokenType.SEMICOLON);      //create semicolon token and add to token list
            tokens.add(semi_colon);
            i++;
          }

          /* DIGIT */
          else if (digits.contains(curr) || curr.equals(".")) {                   //if current symbol is a digit or "."
            String digitToken = "";                                                         //initialize empty string
            while(i < arrLength && digits.indexOf(charArray[i]) != -1) {                    //while current is a digit and not the last character in the line
              digitToken += charArray[i];                                                   //add digit to string
              i++;
            }
            if (charArray[i] == '.') {                                                      //if current symbol is a ".",
              if (!(i == arrLength - 1)) {                                                  //add to string and read all digits following "."
                digitToken += charArray[i];
                i++;
                if (digits.indexOf(charArray[i]) != -1) {
                  while(i < arrLength && digits.indexOf(charArray[i]) != -1) {
                    digitToken = digitToken + String.valueOf(charArray[i]);
                    i++;
                  }
                }
                else {                                                                      //if there are no digits following "."
                  System.err.println("Syntax error:");                                     //print error message and return null
                  System.err.println("Expecting digit following \".\"");
                  System.err.println(filename+ ":" + lineNumber); 
                  return null;
                }
              }
              else if (i == 0 || digits.indexOf(charArray[i - 1]) == (-1)){                  //if token starts with a "." and has no digits following "."
                //throw new Error("Expecting digit following \".\"");                           
                System.err.println("Syntax error:");                                        //print error message and return null
                System.err.println("Expecting digit following \".\"");
                System.err.println(filename+ ":" + lineNumber); 
                i++;                                                                  //DELETE LATER
                return null;
              }
              else {
                digitToken += String.valueOf(charArray[i]);                                   //if token does have a digit following ".", add to string
                i++;
              }
            }
            Token d = new Token(digitToken, filename, lineNumber, TokenType.NUMBER);          //create token with string and add to token list
            tokens.add(d);
          }
        
                  


          /*  LETTER */
          else if (letters.contains(curr)) {                                                          //if curr is a letter
            String currString = "";                                                                 //create curr string
            while (i < arrLength && (letters.indexOf(charArray[i]) != -1 || digits.indexOf(charArray[i]) != -1)) {       //while the next symbol is a char or num
              currString += (charArray[i]);                                                           //add to curr string
              i++;                                                                                    //update i (from main loop) for characters used in each token
            }                                                                                         //this way we don't use any symbols twice
            Token idToken = new Token(currString, filename, lineNumber, TokenType.ID_KEYWORD);  //creates token with all characters in string
            tokens.add(idToken);
          }

          /* : (COLON) */
          else if (curr.equals(":")) {
            String cString = ":";                                                               //initialize string with ":" character
            if(String.valueOf(charArray[i + 1]).equals(":"))                            //if the following character is also a ":"
            {
              cString += ":";                                                                           //add additional character to string,
              Token fcheader = new Token(cString, filename, lineNumber, TokenType.FC_HEADER);            //create token with string "::"
              tokens.add(fcheader);                                                                      //add to token list                                
              i+=2;                                                                                     //increment by 2 since token contains 2 symbols
            }
            else
            {
              Token colon = new Token(curr, filename, lineNumber, TokenType.COLON);             //if next token is not a ":", create token with ":" character
              tokens.add(colon);                                                                //add to token list
              i++;                                                                            
            }
          }

          /* != (RELOP) */
          else if (curr.equals("!"))
          {
            if (!(i == arrLength - 1)) {                                                        //if "!" character is not last character in the line
              if(String.valueOf(charArray[i + 1]).equals("=")) {                                //if the next character is am "="
              curr += "=";                                                                              // add "=" to create token "!="
              Token rel = new Token(curr, filename, lineNumber, TokenType.REL_OP);                      //create token with string ^
              tokens.add(rel);                                                                          //add token to token list
              i+=2;                                                                                     //increment by 2 since token is 2 characters long
              }
              else {
                System.err.println("Syntax Error:");                                        // "!" is not followed by "=", print error message
                System.err.println("Invalid token \"!\". \"!\" expects following \"=\"");   //
                System.err.println(filename + ":" + lineNumber);                              //
                return null;                                                                  //and return null
              }
            }
            else {                                                                             //if "!" is the last character of the line,
              System.err.println("Syntax Error:");                                           //print error message,
              System.err.println("Invalid token \"!\". \"!\" expects following \"=\"");      //
              System.err.println(filename + ":" + lineNumber);                                  //  
              return null;                                                                     //return null
            }
              //throw new Error("POOPY BUTT");                            probably the most well written and important line in our entire code
            }
            

          /* " (STRING) */
          else if (curr.equals("\"")) 
          {
            String stringToken = "\"";                                                        //initialize string token with "\"" character
            i++;
            stringLoop:
            while(i < arrLength) {                                                            //while not the last character of the line,
              curr = String.valueOf(charArray[i]);    
              stringToken = stringToken + curr;                                               //add next char to token string

              if (curr.equals("\"")) {                                               // if ends with "\"" then 
                i++;  
                Token st = new Token(stringToken, filename, lineNumber, TokenType.STRING);    //create token and add token string to token list
                tokens.add(st);
                continue lineLoop;
              }
              else {                                                                          //keep checking next character until you find "\""
                i++;
              }
            }
            /* PUT ERROR HERE -- NO END QUOTATION */                                          //if no "\"" follows initial one, then
            System.err.println("Syntax Error:");                                            //print error message and
            System.err.println("String Missing Closing \"");
            System.err.println(filename + ":" + lineNumber);                                    
            return null;                                                                      //return null
            //throw new Error("String Missing Closing \"");
            
          }
        }
      }
    scan.close();                                             //close scanner

    /* DELETE LATER 
    System.out.println("#######  OUR TOKENS:   #######");
    for (Token temp: tokens) {
      String help = temp.getToken();
      System.out.println(help);
    }
    */
		return tokens;

      } catch (FileNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    ArrayList<Token> TESTING = new ArrayList<Token>();                      //in case no file, return empty token list
    return TESTING;


	}


}