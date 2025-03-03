package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

/**
 * Interface for all <body_stmt> nodes
 *
 * @author
 */
public interface BodyStatement extends JottTree{
    
    public static BodyStatement parBodyStatement(ArrayList<Token> tokens) throws Exception{
        if (tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD) && (tokens.get(0).getToken().equals("If") || tokens.get(0).getToken().equals("if"))) {
            return IfStatementNode.parseIfStatementNode(tokens);
        }
        if (tokens.get(0).getTokenType().equals(TokenType.ID_KEYWORD) && (tokens.get(0).getToken().equals("While") || tokens.get(0).getToken().equals("while"))) {
            return WhileLoopNode.parseWhileLoopNode(tokens);
        }
        if (tokens.get(0).getTokenType().equals(TokenType.FC_HEADER)) {
            FuncCallNode funcCallNode = FuncCallNode.parseFuncCallNode(tokens);

            Token semiToken = tokens.get(0);

            if (semiToken.getTokenType().equals(TokenType.SEMICOLON)) {
                tokens.remove(0);

                funcCallNode.setBodyStmtSemiColon(true);
                // the semi colon does follow the function call, so we're gucci
                return funcCallNode;
            }
            else {
                // throw error because the semicolon doesn't follow the function call

                throw new SyntaxError("Expected a semicolon token ';' following this function call, but got " + semiToken.getTokenType() + " --> " + semiToken.getToken(), semiToken);
            }
        }
        Token t1 = tokens.get(0);
        Token t2 = tokens.get(1);
        if (t1.getTokenType().equals(TokenType.ID_KEYWORD) && t2.getTokenType().equals(TokenType.ASSIGN)) {
            return AssignmentNode.parseAssignmentNode(tokens);
        }
        else {
            throw new SyntaxError("Expected a body statement, but got a " + t1.getTokenType() + " --> " + t1.getToken(), t1);
        }
    }
}
