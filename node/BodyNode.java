package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;



public class BodyNode implements JottTree{

        private ArrayList<BodyStatement> bodyStatement_list;
        private ReturnStmtNode returnStmtNode;
    
        /**
         * Creates an instance of a token
         * @param token the token string
         * @param token.filename the name of the file the token came from
         * @param token.lineNum the number of the line in the file that the token appears on
         * @param token.type the type of this token
         */
        public BodyNode(ArrayList<BodyStatement> bodyStatement_list, ReturnStmtNode returnStmtNode) {
            this.bodyStatement_list = bodyStatement_list;
            this.returnStmtNode = returnStmtNode;
        }

        /**
         * Getter for the ArrayList<BodyStatement> bodyStatement_list
         * @return the ArrayList<BodyStatement> bodyStatement_list
         */
        public ArrayList<BodyStatement> getBodyStatements() {
            return bodyStatement_list;
        }

        public ReturnStmtNode gReturnStmtNode() {
            return returnStmtNode;
        }


        /**
         * Parses list of tokens and check to see if it is Program
         * @param tokens: ArrayList of tokens
         * @return BodyNode
         * @throws Exception 
         */
        public static BodyNode parseBodyNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token List empty. <BodyNode>", null);
                }

                ArrayList<BodyStatement> bodyStatement_list = new ArrayList<>();

                while (!tokens.isEmpty() && !tokens.get(0).getToken().equals("Return") && !tokens.get(0).getToken().equals("}") && !tokens.get(0).getToken().equals("return")) {

                    BodyStatement bodyStatement = BodyStatement.parBodyStatement(tokens);

                    bodyStatement_list.add(bodyStatement);
                }

                ReturnStmtNode returnStmtNode = ReturnStmtNode.parseReturnStmtNode(tokens);

                BodyNode bodyNode = new BodyNode(bodyStatement_list, returnStmtNode);
                return bodyNode;

        }


        public String convertToJott() {
            String value = new String();
            for (int i = 0; i < bodyStatement_list.size(); i++) {
                    BodyStatement bodyStatement = bodyStatement_list.get(i);
                    value = value + bodyStatement.convertToJott();
            }
            value = value + returnStmtNode.convertToJott();
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
