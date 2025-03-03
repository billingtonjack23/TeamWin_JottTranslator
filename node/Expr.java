package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

/**
 * Interface for all <expr> nodes
 *
 * @author
 */
public interface Expr extends JottTree{
    
    public static Expr parseExprNode(ArrayList<Token> tokens) throws Exception {
        if (tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD)) {
            if (tokens.get(0).getToken().equals("True") || tokens.get(0).getToken().equals("False")) {
                return BooleanNode.parseBooleanNode(tokens);
            }
        }
        if (tokens.get(0).getTokenType().equals(TokenType.STRING)) {
            return StringLiteralNode.parseStringLiteralNode(tokens);
        }
        if (tokens.get(0).getTokenType().equals(TokenType.SEMICOLON)) {
            throw new SyntaxError("Expected an <expr> but got a " + tokens.get(0).getTokenType() + " --> " + tokens.get(0).getToken(), tokens.get(0));
        }
        
        Operand leftOp = Operand.parseOperandNode(tokens);
        if (tokens.get(0).getTokenType().equals(TokenType.MATH_OP) || tokens.get(0).getTokenType().equals(TokenType.REL_OP)) {
            
            Token operator = tokens.get(0);
            tokens.remove(0);
            Operand rightOp = Operand.parseOperandNode(tokens);

            BinaryOperationNode binOpNode = new BinaryOperationNode(leftOp, operator, rightOp);

            return binOpNode;
        }
        else {
            return leftOp;
        }
    }
}
