package node;

import java.util.ArrayList;

import exceptions.SyntaxError;
import provided.JottTree;
import provided.Token;


public class FuncBodyNode implements JottTree{

        private ArrayList<VarDeclarationNode> varDeclarationNodes_list;
        private BodyNode bodyNode;
    
        /**
         * Creates an instance of a token
         * @param token the token string
         * @param token.filename the name of the file the token came from
         * @param token.lineNum the number of the line in the file that the token appears on
         * @param token.type the type of this token
         */
        public FuncBodyNode(ArrayList<VarDeclarationNode> varDeclarationNodes_list, BodyNode bodyNode) {
            this.varDeclarationNodes_list = varDeclarationNodes_list;
            this.bodyNode = bodyNode;
        }

        /**
         * Getter for the ArrayList<VarDeclarationNode> varDeclarationNodes
         * @return the ArrayList<VarDeclarationNode> varDeclarationNodes
         */
        public ArrayList<VarDeclarationNode> getVarDeclarationNodes() {
            return varDeclarationNodes_list;
        }

        public BodyNode getBodyNode() {
            return bodyNode;
        }


        /**
         * Parses list of tokens and check to see if it is Program
         * @param tokens: ArrayList of tokens
         * @return FuncBodyNode
         * @throws Exception 
         */
        public static FuncBodyNode parseFuncBodyNode(ArrayList<Token> tokens) throws Exception
        {
                //check if tokens arraylist is empty
                if(tokens.size() == 0)
                {
                        //throw excpetion
                        //CREATE NEW CLASS AND THROW EXCEPTION
                        // SUGGESTED RENAME SYNTAX ERROR
                        throw new SyntaxError("Token List empty. <Func Body Node>", null);
                }

                ArrayList<VarDeclarationNode> varDeclarationNodes_list = new ArrayList<VarDeclarationNode>();

                Token t = tokens.get(0);
                while ((t.getToken().equals("Double")) || (t.getToken().equals("Integer")) || (t.getToken().equals("String")) || (t.getToken().equals("Boolean"))) {

                    VarDeclarationNode varDeclarationNode = VarDeclarationNode.parseVarDeclarationNode(tokens);

                    varDeclarationNodes_list.add(varDeclarationNode);

                    t = tokens.get(0);
                }

                BodyNode bodyNode = BodyNode.parseBodyNode(tokens);

                FuncBodyNode funcBodyNode = new FuncBodyNode(varDeclarationNodes_list, bodyNode);
                return funcBodyNode;

        }


        public String convertToJott() {
            String value = new String();
            for (int i = 0; i < varDeclarationNodes_list.size(); i++) {
                    VarDeclarationNode varDeclarationNode = varDeclarationNodes_list.get(i);
                    value = value + varDeclarationNode.convertToJott();
            }
            value = value + bodyNode.convertToJott();
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
