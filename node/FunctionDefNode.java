package node;

import java.util.ArrayList;


import provided.JottTree;
import provided.Token;
import provided.TokenType;
import exceptions.*;

public class FunctionDefNode implements JottTree{

        private final IDNode idNode;
        private final FuncDefParams funcDefParams;
        private final FuncReturnNode funcReturnNode;
        private final FuncBodyNode funcBodyNode;

        public FunctionDefNode (IDNode idNode, FuncDefParams funcDefParams, FuncReturnNode funcReturnNode, FuncBodyNode funcBodyNode){
                this.idNode = idNode;
                this.funcDefParams = funcDefParams;
                this.funcReturnNode = funcReturnNode;
                this.funcBodyNode = funcBodyNode;
        }

        public static FunctionDefNode parseFunctionDefNode(ArrayList<Token> tokens) throws Exception
               {
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token list is empty. <Func Def Node>", null);
                }

                //get front of list
                Token def = tokens.get(0);

                if (def.getTokenType().equals(TokenType.ID_KEYWORD) && def.getToken().equals("Def")) {
                        tokens.remove(0);

                        IDNode idNode = IDNode.parseIDNode(tokens);

                        Token t = tokens.get(0);
                        if (t.getTokenType().equals(TokenType.L_BRACKET)) {
                                tokens.remove(0);

                                FuncDefParams funcDefParams = FuncDefParams.parseFunctionDefParams(tokens);

                                t = tokens.get(0);
                                Token t2 = tokens.get(1);
                                if (t.getTokenType().equals(TokenType.R_BRACKET) && t2.getTokenType().equals(TokenType.COLON)) {
                                        tokens.remove(0);
                                        tokens.remove(0);

                                        FuncReturnNode funcReturnNode = FuncReturnNode.parseFuncReturnNode(tokens);

                                        t = tokens.get(0);
                                        if (t.getTokenType().equals(TokenType.L_BRACE)) {
                                                tokens.remove(0);

                                                FuncBodyNode funcBodyNode = FuncBodyNode.parseFuncBodyNode(tokens);

                                                if (tokens.isEmpty()) {
                                                        throw new SyntaxError("<func_def> missing closing }. There's no more tokens left", null);
                                                }

                                                t = tokens.get(0);
                                                
                                                if (t.getTokenType().equals(TokenType.R_BRACE)) {

                                                        tokens.remove(t);

                                                        FunctionDefNode functionDefNode = new FunctionDefNode(idNode, funcDefParams, funcReturnNode, funcBodyNode);
                                                        return functionDefNode;
                                                }
                                                else {
                                                        throw new SyntaxError("Expected a right curly brace '}' at the end of <function_def> node. Got a " + t.getTokenType() + " --> " + t.getToken(), t);
                                                }
                                        }
                                        else {
                                                throw new SyntaxError("Expected a left curly brace '{' after <function_return> node inside of <function_def> node. Got a " + t.getTokenType() + " --> " + t.getToken(), t);
                                        }
                                }
                                else {
                                        throw new SyntaxError("Expected a right bracket ']' and a colon ':' after <func_def_params> node inside of <function_def> node. Got " + t.getTokenType() + " --> " + t.getToken(), t);
                                }
                        }
                        else {
                                throw new SyntaxError("Expected a left bracket '[' after <id> node inside of <function_def> node. Got a " + t.getTokenType() + " --> " + t.getToken(), t);
                        }
                }
                else {
                        throw new SyntaxError("Expected 'Def' at start of <function_def> node. Got a " + def.getTokenType() + " --> " + def.getToken(), def);
                }
        }


        public String convertToJott() {
                String value = "Def " + idNode.convertToJott() + "[" + funcDefParams.convertToJott() + "]:" + funcReturnNode.convertToJott() + "{" + funcBodyNode.convertToJott() + "}";
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
