package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FuncReturnNode implements JottTree {

        private final Token typeOrVoid;

        /**
         * Constructs a new FuncReturnNode with the provided TypeNode.
         * 
         * @param typeNode the TypeNode representing the type of the element being
         *                 returned
         */
        public FuncReturnNode(Token typeOrVoid) {
                this.typeOrVoid = typeOrVoid;
        }

        /**
         * Getter for the Token typeOrVoid
         * 
         * @return the Token typeOrVoid
         */
        public Token getToken() {
                return this.typeOrVoid;
        }

        /**
         * Parses list of tokens and check to see if it is an Number
         * 
         * @param tokens: ArrayList of tokens
         * @return FuncReturnNode
         * @throws Exception
         */
        public static FuncReturnNode parseFuncReturnNode(ArrayList<Token> tokens) throws Exception {

                // check if tokens arraylist is empty
                if (tokens.size() == 0) {
                        // throw excpetion
                        // CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token list is empty. <Func Return Node>", null);
                }

                Token t = tokens.get(0);
                if (t.getTokenType().equals(TokenType.ID_KEYWORD) && t.getToken().equals("Void")) {
                        tokens.remove(0);
                        FuncReturnNode funcReturnNode = new FuncReturnNode(t);
                        return funcReturnNode;
                }
                if ((t.getToken().equals("Double")) || (t.getToken().equals("Integer")) || (t.getToken().equals("String")) || (t.getToken().equals("Boolean"))) {
                        tokens.remove(0);
                        FuncReturnNode funcReturnNode = new FuncReturnNode(t);
                        return funcReturnNode;
                }
                else {
                        //throw exception
                        throw new SyntaxError("Not a <Type> Keyword, nor 'Void' inside of <FuncReturnNode>. Instead got a " + t.getTokenType() + " --> " + t.getToken(), t);
                }
        }

        public String convertToJott() {
                // Return the <type> as a string
                String value = typeOrVoid.getToken();
                return value;
        }

        public String convertToJava(String className) {
                return "";
        }

        public String convertToC() {
                return "";
        }

        public String convertToPython() {
                return "";
        }

        public boolean validateTree() {
                return true;
        }

}
