package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.Token;
import provided.TokenType;

public class AssignmentNode implements BodyStatement {
        
        private final IDNode idNode;
        private final Expr exprNode;

        /**
         * Constructs a new AssignmentNode with the provided IDNode, TokenType and ExprNode.
         * @param idNode the IDNode representing the identifier of the variable being declared
         * @param token the TokenType representing the identifier of the variable being declared
         * @param exprNode the ExprNode representing the identifier of the variable being declared
         */
        public AssignmentNode(IDNode idNode, Expr exprNode)
        {
                this.idNode = idNode;
                this.exprNode = exprNode;
        }

        /**
         * Getter for the IDNode idNode
         * @return the IDNode idNode
         */
        public IDNode getIDNode()
        {
                return this.idNode;
        }

        /**
         * Getter for the ExprNode exprNode
         * @return the ExprNode exprNode
         */
        public Expr getExprNode()
        {
                return this.exprNode;
        }


        /**
         * Parses list of tokens and check to see if it is an ID
         * @param tokens: ArrayList of tokens
         * @return AssignmentNode
         * @throws Exception 
         */
        public static AssignmentNode parseAssignmentNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token List empty. <AssignmentNode>", null);
                }

                IDNode idNode = IDNode.parseIDNode(tokens);
        
                //get front of list
                Token t = tokens.get(0);

                //if not correct node
                if (t.getTokenType().equals(TokenType.ASSIGN))
                {
                        //remove token from front of list
                        tokens.remove(0);

                        Expr exprNode = Expr.parseExprNode(tokens);

                        // FuncCallNode funcCallNode = new FuncCallNode(null, null, null);
                        // if (exprNode.getClass() == funcCallNode.getClass()) {
                        //         //return new node
                        //         AssignmentNode asmt = new AssignmentNode(idNode, exprNode);
                        //         return asmt;
                        // }

                        Token newT = tokens.get(0); 

                        if(newT.getTokenType().equals(TokenType.SEMICOLON))
                        {
                                //remove token from front of list
                                tokens.remove(0);

                                //return new node
                                AssignmentNode asmt = new AssignmentNode(idNode, exprNode);
                                return asmt;
                        }
                        else
                        {
                                //throw exception
                                throw new SyntaxError("Expected a semicolon ';' but got " + newT.getTokenType() + " --> " + newT.getToken(), newT);
                        }

                        
                }
                else {
                        //throw exception
                        throw new SyntaxError("Expected an assignment token '=', but got " + t.getTokenType() + " --> " + t.getToken(), t);
                        
                }
        }


        public String convertToJott() 
        {
                String s = "";
                s += idNode.convertToJott() + "=" + exprNode.convertToJott() + ";";
                return s;

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
                return idNode.validateTree() && exprNode.validateTree(); //need symbol table to ensure idNode is declared
        }                                                                //need type checking to ensure exprNode is of the same type as idNode
}                                                                        //need isType in exprNode