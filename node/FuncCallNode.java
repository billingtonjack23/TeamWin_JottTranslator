package node;

import java.util.ArrayList;

import exceptions.SyntaxError;

import provided.Token;
import provided.TokenType;


public class FuncCallNode implements Operand, BodyStatement{

        private final IDNode idNode;
        private final ParamsNode paramsNode;
        private Boolean bodyStmtSemiColon;
    
        /**
         * Creates an instance of a token
         * @param token the token string
         * @param token.filename the name of the file the token came from
         * @param token.lineNum the number of the line in the file that the token appears on
         * @param token.type the type of this token
         */
        public FuncCallNode(IDNode idNode, ParamsNode paramsNode) {
                this.idNode = idNode;
                this.paramsNode = paramsNode;
                this.bodyStmtSemiColon = false;
        }

        /**
         * Getter for the IDNode idNode
         * @return the IDNode idNode
         */
        public IDNode getIdNode() {
                return idNode;
        }

        /**
         * Getter for the ParamsNode paramsNode
         * @return the ParamsNode paramsNode
         */
        public ParamsNode getParamsNode() {
                return paramsNode;
        }

        public Boolean getBodyStmtSemiColon() {
                return bodyStmtSemiColon;
        }

        public void setBodyStmtSemiColon(Boolean flag) {
                if (flag) {
                        this.bodyStmtSemiColon = true;
                }
        }


        /**
         * Parses list of tokens and check to see if it is an Number
         * @param tokens: ArrayList of tokens
         * @return FuncCallNode
         * @throws Exception 
         */
        public static FuncCallNode parseFuncCallNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token List empty. <Func Call Node>", null);
                }

                //get front of list
                Token t = tokens.get(0);

                //if not correct node
                if (t.getTokenType().equals(TokenType.FC_HEADER))
                {
                        tokens.remove(0);
                        IDNode idNode = IDNode.parseIDNode(tokens);

                        t = tokens.get(0);
                        if (t.getTokenType().equals(TokenType.L_BRACKET)) {
                                tokens.remove(0);
                                ParamsNode paramsNode = ParamsNode.parseParamsNode(tokens);

                                t = tokens.get(0);
                                if (t.getTokenType().equals(TokenType.R_BRACKET)) {
                                        tokens.remove(0);


                                        FuncCallNode funcCallNode = new FuncCallNode(idNode, paramsNode);
                                        return funcCallNode;
                                }
                                else {
                                        //throw error
                                        throw new SyntaxError("Expecting Right Bracket ']' but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), t);
                                }
                        }
                        else {
                                // throw error
                                throw new SyntaxError("Expecting Left Bracket '[' but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), t);
                        }
                }
                else {
                        // throw error
                        throw new SyntaxError("Expecting FC_Header '::' but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), t);
                }
        }


        public String convertToJott() {
                String value = "::" + idNode.convertToJott() + "[" + paramsNode.convertToJott() + "]";
                if (bodyStmtSemiColon) {
                        return value + ";";
                }
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
