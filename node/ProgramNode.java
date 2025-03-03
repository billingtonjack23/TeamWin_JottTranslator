package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;



public class ProgramNode implements JottTree{

        private ArrayList<FunctionDefNode> function_DefNodes;
    
        /**
         * Creates an instance of a token
         * @param token the token string
         * @param token.filename the name of the file the token came from
         * @param token.lineNum the number of the line in the file that the token appears on
         * @param token.type the type of this token
         */
        public ProgramNode(ArrayList<FunctionDefNode> function_DefNodes) {
                this.function_DefNodes = function_DefNodes;
        }

        /**
         * Getter for the ArrayList<FunctionDefNode> function_DefNodes
         * @return the ArrayList<FunctionDefNode> function_DefNodes
         */
        public ArrayList<FunctionDefNode> getFunctionDefNodes() {
                return function_DefNodes;
        }


        /**
         * Parses list of tokens and check to see if it is Program
         * @param tokens: ArrayList of tokens
         * @return ProgramNode
         * @throws Exception 
         */
        public static ProgramNode parseProgramNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token list is empty. <Program Node>", null);
                }

                ArrayList<FunctionDefNode> function_DefNodes = new ArrayList<>();

                while (!tokens.isEmpty()) {
                        FunctionDefNode function_def = FunctionDefNode.parseFunctionDefNode(tokens);
                        
                        function_DefNodes.add(function_def);
                }

                //return new node
                ProgramNode program = new ProgramNode(function_DefNodes);
                return program;

        }


        public String convertToJott() {
                String value = "";
                for (int i = 0; i < function_DefNodes.size(); i++) {
                        FunctionDefNode tempDefNode = function_DefNodes.get(i);
                        value = value + tempDefNode.convertToJott();
                }
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
                for (FunctionDefNode tempDefNode : function_DefNodes) {
                        if (!tempDefNode.validateTree()) {
                            return false;
                        }
                }
                return true;
        }
        

}
