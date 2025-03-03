package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class FuncDefParams_t implements JottTree {

        private final IDNode idNode;
        private final TypeNode typeNode;

        /**
         * Constructs a new FuncDefParams_t with the provided TypeNode.
         * 
         * @param typeNode the TypeNode representing the type of the element being
         *                 returned
         */
        public FuncDefParams_t(IDNode idNode, TypeNode typeNode) {
                this.idNode = idNode;
                this.typeNode = typeNode;
        }

        /**
         * Getter for the IDNode idNode
         * 
         * @return the IDNode idNode
         */
        public IDNode getIdNode() {
                return this.idNode;
        }

        /**
         * Getter for the TypeNode typeNode
         * 
         * @return the TypeNode typeNode
         */
        public TypeNode getTypeNode() {
                return this.typeNode;
        }

        /**
         * Parses list of tokens and check to see if it is an Number
         * 
         * @param tokens: ArrayList of tokens
         * @return FuncDefParams_t
         * @throws Exception
         */
        public static FuncDefParams_t parseFuncDefParams_t(ArrayList<Token> tokens) throws Exception {

                // check if tokens arraylist is empty
                if (tokens.size() == 0) {
                        // throw excpetion
                        // CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token list is empty. <Func Def Params_t>", null);
                }

                // get front of list
                Token t = tokens.get(0);

                if (t.getTokenType().equals(TokenType.COMMA)) {

                        tokens.remove(0);

                        if ((t.getToken().equals("Double")) || (t.getToken().equals("Integer")) || (t.getToken().equals("String")) || (t.getToken().equals("Boolean"))) {
                                throw new SyntaxError("Expected an <id> got a <Type> keyword: --> " + t.getToken(), t);
                        }

                        IDNode idNode = IDNode.parseIDNode(tokens);

                        t = tokens.get(0);
                        if (t.getTokenType().equals(TokenType.COLON)) {
                                tokens.remove(0);

                                TypeNode typeNode = TypeNode.parseTypeNode(tokens);

                                FuncDefParams_t funcDefParams_t = new FuncDefParams_t(idNode, typeNode);
                                return funcDefParams_t;
                        } else {
                                throw new SyntaxError(
                                                "Expected a colon ':' between <id> and <type> in func_def_params_t but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), t);
                        }
                } else {
                        throw new SyntaxError("Expected a comma ',' in list of parameters in func_def_params_t but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), t);
                }

        }

        public String convertToJott() {
                String value = "," + idNode.convertToJott() + ":" + typeNode.convertToJott();
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
