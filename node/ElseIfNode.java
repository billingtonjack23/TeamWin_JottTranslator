package node;


import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class ElseIfNode implements JottTree{

    private final Expr exprNode;
    private final BodyNode bodyNode;

    public ElseIfNode(Expr exprNode, BodyNode bodyNode) {
        this.exprNode = exprNode;
        this.bodyNode = bodyNode;
    }

    public Expr getConditionNode() {
        return this.exprNode;
    }

    public BodyNode getBodyNode() {
        return this.bodyNode;
    }

    public static ElseIfNode parseElseIfNode(ArrayList<Token> tokens) throws Exception
    {
        if (tokens.isEmpty()) {
            throw new SyntaxError("Token List empty. <ElseIf Node>", null);
        }

        if (!tokens.get(0).getToken().equals("Elseif")) {
            throw new SyntaxError("Incorrect Token. Expected 'ElseIf' but got " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), tokens.get(0));
        }

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

        return new ElseIfNode(exprNode, bodyNode);
    }


    public String convertToJott() {
        String value = "Elseif[" + exprNode.convertToJott() + "]" + "{" + bodyNode.convertToJott() + "}";
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