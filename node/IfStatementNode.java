package node;

import java.util.ArrayList;

import exceptions.SyntaxError;

import provided.Token;
import provided.TokenType;

public class IfStatementNode implements BodyStatement {

        private final Expr expression;
        private final BodyNode bodyNode;
        private final ArrayList<ElseIfNode> elseIfNodes;
        private final ElseNode elseNode;
        private final Token ifWord;
    

        public IfStatementNode(Expr expression, BodyNode bodyNode, ArrayList<ElseIfNode> elseIfNodes, ElseNode elseNode, Token ifWord) {
                this.expression = expression;
                this.bodyNode = bodyNode;
                this.elseIfNodes = elseIfNodes;
                this.elseNode = elseNode;
                this.ifWord = ifWord;
        }

        /**
         * Getter for the Expr expression
         * @return the Expr expression
         */
        public Expr getExpression() {
                return this.expression;
        }

        /**
         * Getter for the BodyNode bodyNode
         * @return the BodyNode bodyNode
         */
        public BodyNode getBodyNode() {
                return this.bodyNode;
        }

        /**
         * Getter for the ArrayList<ElseIfNode> elseIfNodes
         * @return the ArrayList<ElseIfNode> elseIfNodes
         */
        public ArrayList<ElseIfNode> getElseIfNodes() {
                return this.elseIfNodes;
        }

        /**
         * Getter for the ElseNode elseNode
         * @return the ElseNode elseNode
         */
        public ElseNode getElseNode() {
                return this.elseNode;
        }


        /**
         * Parses list of tokens and check to see if it is an ID
         * @param tokens: ArrayList of tokens
         * @return IfStatementNode
         * @throws Exception 
         */
        public static IfStatementNode parseIfStatementNode(ArrayList<Token> tokens) throws Exception
        {

            if (tokens.isEmpty()) {
                throw new SyntaxError("Token List empty. <If Statement>", null);
            }

            //if (!tokens.get(0).getToken().equals("If") && !tokens.get(0).getToken().equals("if")) {
            if (!tokens.get(0).getToken().equals("If")) {
                throw new SyntaxError("Incorrect Token. Expected 'If' but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), tokens.get(0));
            }

            Token ifWord = tokens.get(0);

            tokens.remove(0);

            if (!tokens.get(0).getTokenType().equals(TokenType.L_BRACKET)) {
                throw new SyntaxError("Incorrect Token. Expected '[' but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), tokens.get(0));
            }

            tokens.remove(0);

            Expr exprNode = Expr.parseExprNode(tokens);

            if (!tokens.get(0).getTokenType().equals(TokenType.R_BRACKET)) {
                throw new SyntaxError("Incorrect Token. Expected ']' but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), tokens.get(0));
            }

            tokens.remove(0);

            if (!tokens.get(0).getTokenType().equals(TokenType.L_BRACE)) {
                throw new SyntaxError("Incorrect Token. Expected '{' but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), tokens.get(0));
            }

            tokens.remove(0);

            BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

            if (!tokens.get(0).getTokenType().equals(TokenType.R_BRACE)) {
                throw new SyntaxError("Incorrect Token. Expected '}' but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), tokens.get(0));
            }

            tokens.remove(0);

            ArrayList<ElseIfNode> elseIfNodes = new ArrayList<>();
            
            while (!tokens.isEmpty() && tokens.get(0).getToken().equals("Elseif")) {
                elseIfNodes.add(ElseIfNode.parseElseIfNode(tokens));
            }
        
            ElseNode elseNode = ElseNode.parseElseNode(tokens);

            return new IfStatementNode(exprNode, bodyNode, elseIfNodes, elseNode, ifWord);

        }


        public String convertToJott() {
                String value = ifWord.getToken() + "[" + expression.convertToJott() + "]" + "{" + bodyNode.convertToJott() + "}";
                for (int i = 0; i < elseIfNodes.size(); i++) {
                        value = value + elseIfNodes.get(i).convertToJott();
                }
                value = value + elseNode.convertToJott();
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
