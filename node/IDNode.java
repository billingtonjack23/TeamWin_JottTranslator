package node;

import java.util.ArrayList;

import exceptions.SyntaxError;

import provided.Token;
import provided.TokenType;

public class IDNode implements Operand{

        private final Token token;
    
        /**
         * Creates an instance of a token
         * @param token the token string
         */
        public IDNode(Token token) {
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
         * @return IDNode
         * @throws Exception 
         */
        public static IDNode parseIDNode(ArrayList<Token> tokens) throws SyntaxError
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token List empty. <ID Node>", null);
                }

                //get front of list
                Token t = tokens.get(0);

                //if not correct node
                if (!t.getTokenType().equals(TokenType.ID_KEYWORD)) {
                        throw new SyntaxError("Expected <id> but got " + t.getTokenType() + " --> " + t.getToken(), t);
                }

                //remove token from front of list
                tokens.remove(0);

                //return new node
                IDNode id = new IDNode(t);
                return id;

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
                String t = String.valueOf(this.token.getToken().charAt(0));
                if (t.equals(t.toLowerCase())){
                        return true;
                }
                return false;
        }
        

}
