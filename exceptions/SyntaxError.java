package exceptions;

import provided.Token;

/**
 * Syntax Error custom exception
 * Used for parsing nodes and breaking the deep tree recursion
 */
public class SyntaxError extends Exception {
    String message;
    Token token;

    /**
     * Constructor for the synatx err
     * @param message: The message that is being passed in for the exception
     * @param token: The token that is being parsed
     */
    public SyntaxError(String message, Token token) {
        this.message = message;
        this.token = token;
    }

    /**
     * The final message sent to the exception after recieving the token and message from the node func.
     */
    public String getMessage() {
        if (token == null) {
            return this.message;
        }
        //return "\n\nSyntax Error:\nMessage: " + this.message + "\nCulprit Token: " + token.getToken() + "\tToken Type: " + token.getTokenType() + "\nFilename: " + token.getFilename() + "\nLine Number: " + token.getLineNum();
        return "\n\nSyntax Error:\nMessage:\t" + this.message + "\nLine ##: " + token.getLineNum() + " in\t" + token.getFilename();
    }

    
}
