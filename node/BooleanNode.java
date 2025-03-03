package node;

import java.util.ArrayList;

import exceptions.SyntaxError;

import provided.Token;
import provided.TokenType;


public class BooleanNode implements Expr{

        private final Token token;
    
        /**
         * Creates an instance of a token
         * @param token the token string
         * @param token.filename the name of the file the token came from
         * @param token.lineNum the number of the line in the file that the token appears on
         * @param token.type the type of this token
         */
        public BooleanNode(Token token) {
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
         * Parses list of tokens and check to see if it is an ID
         * @param tokens: ArrayList of tokens
         * @return BooleanNode
         * @throws Exception 
         */
        public static BooleanNode parseBooleanNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token List empty. <Boolean Node>", null);
                }

                //get front of list
                Token t = tokens.get(0);

                //if not correct node
                if (t.getTokenType() != TokenType.ID_KEYWORD)
                {
                        //throw exception
                        throw new SyntaxError("Incorrect Node Type. Not a Bool / Keyword token. Got " + t.getTokenType() + " --> " + t.getToken(), t);
                }
                else if ((!t.getToken().equals("True")) && (!t.getToken().equals("False"))) {
                        //throw exception
                        throw new SyntaxError("Expected a Boolean keyword 'True' or 'False', but got " + t.getTokenType() + " --> " + t.getToken(), t);
                }
                else {
                        //remove token from front of list
                        tokens.remove(0);

                        //return new node
                        BooleanNode bool = new BooleanNode(t);
                        return bool;
                }
        }


        public String convertToJott() {
                String value = this.token.getToken();
                if (value.equals("True")){
                        return "True";
                }
                else {
                        return "False";
                }
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
