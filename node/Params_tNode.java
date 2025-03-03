package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class Params_tNode implements JottTree {

        private final Expr expr;

        /**
         * Constructs a new Params_tNode with the provided TypeNode.
         * 
         * @param typeNode the TypeNode representing the type of the element being
         *                 returned
         */
        public Params_tNode(Expr expr) {
                this.expr = expr;
        }

        /**
         * Getter for the Expr expr
         * 
         * @return the Expr expr
         */
        public Expr getExpr() {
                return this.expr;
        }

        /**
         * Parses list of tokens and check to see if it is an Number
         * 
         * @param tokens: ArrayList of tokens
         * @return Params_tNode
         * @throws Exception
         */
        public static Params_tNode parseParams_tNode(ArrayList<Token> tokens) throws Exception {

                // check if tokens arraylist is empty
                if (tokens.size() == 0) {
                        // throw excpetion
                        // CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token list is empty. <Params_t Node>", null);
                }

                // get front of list
                Token t = tokens.get(0);

                if (t.getTokenType().equals(TokenType.COMMA)) {

                        tokens.remove(0);

                        Expr expr = Expr.parseExprNode(tokens);

                        Params_tNode params_tNode = new Params_tNode(expr);

                        return params_tNode;
                } else {
                        throw new SyntaxError("Expected a comma ',' in list of parameters, but got " + t.getTokenType() + " --> " + t.getToken(), t);
                }

        }

        public String convertToJott() {
                String value = "," + expr.convertToJott();
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
