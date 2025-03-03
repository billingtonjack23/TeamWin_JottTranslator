package node;


import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.Token;
import provided.TokenType;

/**
 * Interface for all <operand> nodes
 *
 * @author
 */
public interface Operand extends Expr{
    
    public static Operand parseOperandNode(ArrayList<Token> tokens) throws Exception{
        if (tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD)) {
            return IDNode.parseIDNode(tokens);
        }
        if (tokens.get(0).getTokenType().equals(TokenType.NUMBER)) {
            return NumNode.parseNumNode(tokens);
        }
        if (tokens.get(0).getTokenType().equals(TokenType.FC_HEADER)) {
            return FuncCallNode.parseFuncCallNode(tokens);
        }
        if ((tokens.get(0).getTokenType().equals(TokenType.MATH_OP)) && (tokens.get(0).getToken().equals("-"))) {
            tokens.remove(0);
            Token t = tokens.get(0);
            if (t.getTokenType() == TokenType.NUMBER) {
                Token temp = new Token("-" + t.getToken(), t.getFilename(), t.getLineNum(), t.getTokenType());
                tokens.remove(0);
                tokens.add(0, temp);
                return NumNode.parseNumNode(tokens);
            }
            else {
                throw new SyntaxError("Expected a Number token following the '-' sign. Instead got " + t.getTokenType() + " --> " + t.getToken(), t);
            }
        }
        else {
            Token t = tokens.get(0);
            throw new SyntaxError("Expected Operand Node but got " + t.getToken(), tokens.get(0));
        }
    }

}
