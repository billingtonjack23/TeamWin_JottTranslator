package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class ParamsNode implements JottTree {

        private final Expr exprNode;
        private ArrayList<Params_tNode> param_TNodes;
    
        /**
         * Creates an instance of a params node
         * @param param_TNodes array of params_tNodes
         * @param exprNode an expression node
         */
        public ParamsNode(Expr exprNode, ArrayList<Params_tNode> param_TNodes) {
                this.exprNode = exprNode;
                this.param_TNodes = param_TNodes;
        }

        /**
         * Getter for the ArrayList<Params_tNode> param_TNodes
         * @return the ArrayList<Params_tNode> param_TNodes
         */
        public ArrayList<Params_tNode> getparam_TNodes() {
                return param_TNodes;
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
         * Parses ExprNode and the list of Params_TNodes
         * @param tokens: ArrayList of tokens
         * @return Params Node
         * @return null
         * @throws Exception 
         */
        public static ParamsNode parseParamsNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token List empty. <Params Node>", null);
                }

                // check to see if next token is a R Bracket
                Token t = tokens.get(0);

                // if it is then there are no params so return null
                if (t.getTokenType().equals(TokenType.R_BRACKET))
                {
                        ParamsNode paramsNode = new ParamsNode(null, null);
                        return paramsNode;
                }


                Expr exprNode = Expr.parseExprNode(tokens);

                ArrayList<Params_tNode> param_TNodes = new ArrayList<>();

                //there can be at a lot of these so we need a while loop to check
                while(tokens.get(0).getTokenType().equals(TokenType.COMMA))
                {
                        Params_tNode params_tNode = Params_tNode.parseParams_tNode(tokens);
                        param_TNodes.add(params_tNode);
                }
            
                ParamsNode paramsNode = new ParamsNode(exprNode, param_TNodes);
                return paramsNode;
        }


        public String convertToJott() 
        {  
                String s = "";
                if(exprNode == null)
                {
                        return s;
                }

                s += exprNode.convertToJott();
                while(!param_TNodes.isEmpty())
                {
                        Params_tNode p = param_TNodes.get(0);
                        s += p.convertToJott();
                        param_TNodes.remove(0);
                }
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
                return true;
        }
        

}


