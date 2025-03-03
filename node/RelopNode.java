package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;
import provided.TokenType;

public class RelopNode implements JottTree{

    private final Token token;

    /**
     * Creates an instance of a token
     * @param token the token string
     */
    public RelopNode(Token token) {
            this.token = token;
    }

    /**
     * Getter for the Token token
     * @return the Token token
     */
    public Token getToken() {
            return token;
    }


    /**
     * Parses list of tokens and check to see if it is an ID
     * @param tokens: ArrayList of tokens
     * @return IDNode
     * @throws Exception 
     */
    public static RelopNode parseRelopNode(ArrayList<Token> tokens) throws Exception
    {
            //check if tokens arraylist is empty
            if(tokens.size() == 0)
            {
                    //throw excpetion
                    //CREATE NEW CLASS AND THROW EXCEPTION
                    // SUGGESTED RENAME SYNTAX ERROR
                    throw new SyntaxError("Token List empty. <Rel Op Node>", null);
            }

            //get front of list
            Token t = tokens.get(0);

            //if not correct node
            if (t.getTokenType() != TokenType.REL_OP) {
                    throw new SyntaxError("Incorrect Node Type. Not a relation Operator. Instead got " + t.getTokenType() + " --> " + t.getToken(), t);
            }

            //remove token from front of list
            tokens.remove(0);

            //return new node
            RelopNode relop = new RelopNode(t);
            return relop;

    }

    @Override
    public String convertToJott() {
        String op = this.token.getToken();
        return op;
    }

    @Override
    public String convertToJava(String className) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToJava'");
    }

    @Override
    public String convertToC() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToC'");
    }

    @Override
    public String convertToPython() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convertToPython'");
    }

    @Override
    public boolean validateTree() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validateTree'");
    }
}
