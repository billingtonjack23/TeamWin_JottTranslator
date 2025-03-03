package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;


public class VarDeclarationNode implements JottTree{

        //private final TypeNode typeNode;
        private final Token typeToken;
        private final IDNode idNode;
        private final AssignmentNode assignmentNode;
    
        /**
         * Constructs a new VarDeclarationNode with the provided TypeNode and IDNode.
         * @param typeNode the TypeNode representing the type of the variable being declared
         * @param idNode the IDNode representing the identifier of the variable being declared
         */
        public VarDeclarationNode(Token typeToken, IDNode idNode, AssignmentNode assignmentNode) {
                this.typeToken = typeToken;
                this.idNode = idNode;
                this.assignmentNode = assignmentNode;

        }

        /**
         * Getter for the TypeNode typeNode
         * @return the TypeNode typeNode
         */
        public Token getTypeToken() {
                return this.typeToken;
        }

        /**
         * Getter for the IDNode idNode
         * @return the IDNode idNode
         */
        public IDNode getIDNode() {
            return this.idNode;
    }


        /**
         * Parses list of tokens and check to see if it is an Number
         * @param tokens: ArrayList of tokens
         * @return VarDeclarationNode
         * @throws Exception 
         */
        public static VarDeclarationNode parseVarDeclarationNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token list is empty. <Variable Declaration Node>", null);
                }

                Token t = tokens.get(0);
                if ((t.getToken().equals("Double")) || (t.getToken().equals("Integer")) || (t.getToken().equals("String")) || (t.getToken().equals("Boolean"))) {
                        
                        Token typeToken = t;
                        tokens.remove(0);

                        t = tokens.get(1);
                        if (t.getTokenType().equals(TokenType.ASSIGN)) {

                                AssignmentNode assignmentNode = AssignmentNode.parseAssignmentNode(tokens);

                                IDNode idNode = assignmentNode.getIDNode();

                                VarDeclarationNode varDeclarationNode = new VarDeclarationNode(typeToken, idNode, assignmentNode);
                                return varDeclarationNode;
                        }
                        else {
                                IDNode idNode = IDNode.parseIDNode(tokens);

                                t = tokens.get(0);
                                if (t.getTokenType().equals(TokenType.SEMICOLON)) {
        
                                        tokens.remove(0);
        
                                        VarDeclarationNode varDeclarationNode = new VarDeclarationNode(typeToken, idNode, null);
                                        return varDeclarationNode;
                                }
                                else {
                                        throw new SyntaxError("Expected a semicolon ';' after <id> inside of <Variable Declaration>. Instead got " + t.getTokenType() + " --> " + t.getToken(), t);
                                }
                        }
                }
                else {
                        throw new SyntaxError("Expected a <Type> keyword inside of <Variable Declaration>.", t);
                }
        }


        public String convertToJott() {
                if (this.assignmentNode == null) {
                        String value = typeToken.getToken() + " " + idNode.convertToJott() + ";";
                        return value;
                }
                else {
                        String value = typeToken.getToken() + " " + assignmentNode.convertToJott();
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
