package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class ReturnStmtNode implements JottTree{

        private final Expr expr;
        private final Token returnWord;

    
        /**
         * Creates an instance of a token
         * @param token the token string
         */
        public ReturnStmtNode(Expr expr, Token returnWord) {
                this.expr = expr;
                this.returnWord = returnWord;
        }

        /**
         * Getter for the Expr expr
         * @return the Expr expr
         */
        public Expr getExpr() {
                return expr;
        }


        /**
         * Parses list of tokens and check to see if it is an ID
         * @param tokens: ArrayList of tokens
         * @return returnStmtNode
         * @throws Exception 
         */
        public static ReturnStmtNode parseReturnStmtNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token List empty. <Return Statement Node>", null);
                }

                //get front of list
                Token t = tokens.get(0);

                //if (t.getTokenType().equals(TokenType.ID_KEYWORD) && (t.getToken().equals("Return") || t.getToken().equals("return"))) {
                if (t.getTokenType().equals(TokenType.ID_KEYWORD) && (t.getToken().equals("Return"))) {

                        Token returnWord = t;

                        tokens.remove(0);

                        Expr expr = Expr.parseExprNode(tokens);

                        t = tokens.get(0);

                        if (t.getTokenType().equals(TokenType.SEMICOLON)) {
                                tokens.remove(0);

                                ReturnStmtNode returnStmtNode = new ReturnStmtNode(expr, returnWord);
                                return returnStmtNode;
                        }
                        else {
                                throw new SyntaxError("Expected a semicolon ';' at the end of <return_statement>. Instead got " + t.getTokenType() + " --> " + t.getToken(), t);
                        }
                }
                else {
                        ReturnStmtNode returnStmtNode = new ReturnStmtNode(null, null);
                        return returnStmtNode;
                }
        }


        public String convertToJott() {
                if (this.expr == null) {
                        String empty = "";
                        return empty;
                }
                String value = returnWord.getToken() + " " + expr.convertToJott() + ";";
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
