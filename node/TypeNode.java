package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;


public class TypeNode implements JottTree{

        private final Token token;
    
        /**
         * Creates an instance of a token
         * @param token the token string
         * @param token.filename the name of the file the token came from
         * @param token.lineNum the number of the line in the file that the token appears on
         * @param token.type the type of this token
         */
        public TypeNode(Token token) {
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
         * Parses list of tokens and check to see if it is a ID
         * @param tokens: ArrayList of tokens
         * @return TypeNode
         * @throws Exception 
         */
        public static TypeNode parseTypeNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token List empty. <Type Node>", null);
                }

                //get front of list
                Token t = tokens.get(0);

                //if not correct node
                if ((!t.getToken().equals("Double")) || (!t.getToken().equals("Integer")) || (!t.getToken().equals("String")) || (!t.getToken().equals("Boolean")) || (!t.getToken().equals("Void"))) {
                        //throw exception
                        throw new SyntaxError("Expected a <Type> keyword, but got a " + t.getTokenType() + " --> " + t.getToken(), t);
                }
                else {
                        //remove token from front of list
                        tokens.remove(0);

                        //return new node
                        TypeNode type = new TypeNode(t);
                        return type;
                }
        }


        public String convertToJott() {
                String typeString = this.token.getToken();
                return typeString;
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
