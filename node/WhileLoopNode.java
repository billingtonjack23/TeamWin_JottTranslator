package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.Token;
import provided.TokenType;


public class WhileLoopNode implements BodyStatement{

        private final Expr expression;
        private final BodyNode bodyNode;
        private final Token whileWord;
    
        /**
         * Creates an instance of a token
         * @param token the token string
         * @param token.filename the name of the file the token came from
         * @param token.lineNum the number of the line in the file that the token appears on
         * @param token.type the type of this token
         */
        public WhileLoopNode(Expr expression, BodyNode bodyNode, Token whileWord) {
                this.expression = expression;
                this.bodyNode = bodyNode;
                this.whileWord = whileWord;
        }

        /**
         * Getter for the Token token
         * @return the Token token
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
         * Parses list of tokens and check to see if it is an Number
         * @param tokens: ArrayList of tokens
         * @return WhileLoopNode
         * @throws Exception 
         */
        public static WhileLoopNode parseWhileLoopNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token List empty. <While Loop Node>", null);
                }

                //if (!tokens.get(0).getToken().equals("While") && !tokens.get(0).getToken().equals("while")) {
                if (!tokens.get(0).getToken().equals("While")) {
                        throw new SyntaxError("Incorrect Token. Expected 'While' but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), tokens.get(0));
                }
                Token whileWord = tokens.get(0);
                tokens.remove(0);

                if (!tokens.get(0).getTokenType().equals(TokenType.L_BRACKET)) {
                        throw new SyntaxError("Incorrect Token. Expected '[' but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), tokens.get(0));
                }
                tokens.remove(0);

                Expr expression = Expr.parseExprNode(tokens);

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

                return new WhileLoopNode(expression, bodyNode, whileWord);

        }


        public String convertToJott() {
                String value = whileWord.getToken() + "[" + expression.convertToJott() + "]" + "{" + bodyNode.convertToJott() + "}";
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
