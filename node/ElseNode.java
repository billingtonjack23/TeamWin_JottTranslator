package node;


import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;


public class ElseNode implements JottTree{

        private final BodyNode bodyNode;
    
        /**
         * Constructs a new ElseNode with the provided TypeNode.
         * @param typeNode the TypeNode representing the type of the element being returned
         */
        public ElseNode(BodyNode bodyNode) {
                this.bodyNode = bodyNode;
        }

        /**
         * Getter for the TypeNode typeNode
         * @return the TypeNode typeNode
         */
        public BodyNode getbodyNode() {
                return this.bodyNode;
        }


        /**
         * Parses list of tokens and check to see if it is an Number
         * @param tokens: ArrayList of tokens
         * @return ElseNode
         * @throws Exception 
         */
        public static ElseNode parseElseNode(ArrayList<Token> tokens) throws Exception
        {
            // //check if tokens arraylist is empty
            // if(tokens.size() == 0)
            // {
            //         //throw excpetion
            //         //CREATE NEW CLASS AND THROW EXCEPTION
            //         // SUGGESTED RENAME SYNTAX ERROR
            //         throw new SyntaxError("Token list is empty.", null);
            // }


            if (tokens.isEmpty()) {
                    throw new SyntaxError("Token list is empty. <Else Node>", null);
            }

            if (!tokens.get(0).getToken().equals("Else")) {
                //throw new SyntaxError("Incorrect Token. Expected 'Else'.", tokens.get(0));
                ElseNode elseNode = new ElseNode(null);
                return elseNode;
            }

            tokens.remove(0);

            if (!tokens.isEmpty() && tokens.get(0).getTokenType() == TokenType.L_BRACE) {
                tokens.remove(0); // remove left brace

                BodyNode bodynode = BodyNode.parseBodyNode(tokens);

                if (!tokens.isEmpty() && tokens.get(0).getTokenType().equals(TokenType.R_BRACE)) {
                    tokens.remove(0); // remove right brace

                    // return the Else node
                    return new ElseNode(bodynode);
                } 
                else
                {
                    throw new SyntaxError("Missing right brace '}', instead got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), tokens.get(0));
                }
            }
            else
            {
                throw new SyntaxError("Missing left brace '{', instead got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), tokens.get(0));
            }

        }


        public String convertToJott() {
            if (this.bodyNode == null) {
                String empty = "";
                return empty;
            }
            else {
                String value = "Else{" + bodyNode.convertToJott() + "}";
                return value;
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
