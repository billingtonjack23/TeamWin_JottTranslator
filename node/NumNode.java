package node;

import java.util.ArrayList;

import exceptions.SyntaxError;

import provided.Token;
import provided.TokenType;


public class NumNode implements Operand{

        private final Token token;
    
        /**
         * Creates an instance of a token
         * @param token the token string
         * @param token.filename the name of the file the token came from
         * @param token.lineNum the number of the line in the file that the token appears on
         * @param token.type the type of this token
         */
        public NumNode(Token token) {
                this.token = token;
        }

        /**
         * Getter for the Token token
         * @return the Token token
         */
        public Token getToken() {
                return token;
        }


        /**
         * Parses list of tokens and check to see if it is an Number
         * @param tokens: ArrayList of tokens
         * @return NumNode
         * @throws Exception 
         */
        public static NumNode parseNumNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token List empty. <Num Node>", null);
                }

                //get front of list
                Token t = tokens.get(0);

                //if not correct node
                if (t.getTokenType() != TokenType.NUMBER)
                {
                        //throw exception
                        throw new SyntaxError("Incorrect Node Type. Not a number. Instead got " + t.getTokenType() + " --> " + t.getToken(), t);
                }

                //remove token from front of list
                tokens.remove(0);

                //return new node
                NumNode num = new NumNode(t);
                return num;
        }


        public String convertToJott() {
                String value = this.token.getToken();
                return value;
        }
        
        public String convertToJava(String className){
                return "";
        }
        
        public String convertToC(){
                return "";
        }
        
        public String convertToPython(){
                return "";
        }
        
        public boolean validateTree(){
                return true;
        }
        

}
