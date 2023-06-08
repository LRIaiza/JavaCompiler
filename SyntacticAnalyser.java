
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;

public class SyntacticAnalyser {

    public static ParseTree parse(List<Token> tokens) throws SyntaxException {
        HashMap<TreeNode.Label, HashMap<Token.TokenType, Integer>> table = createTable();
        Deque<TreeNode> stack = new ArrayDeque<>();
        ParseTree tree = new ParseTree();
        tree.setRoot(new TreeNode(TreeNode.Label.prog, null));
        TreeNode currentNode = tree.getRoot();

        stack.push(currentNode);

        int rule = 0;

        if (tokens.size() == 0 ){
            throw new SyntaxException("No tokens");
        }

        for(Token token : tokens) {
            boolean isTerminal = false;
            while(!isTerminal)
            {
                if(stack.getFirst().getLabel().equals(TreeNode.Label.epsilon)){

                    currentNode.updateParent(tree.getRoot().getLastChild());
                    currentNode.getParent().addChild(stack.getFirst());
                    stack.pop();
                }

                if(!stack.getFirst().getLabel().equals(TreeNode.Label.terminal)){
                    if(!stack.getFirst().getLabel().equals(TreeNode.Label.prog)) {
                        currentNode = stack.getFirst();
                        currentNode.getParent().addChild(stack.getFirst());
                        //stack.getFirst().getParent().addChild(new TreeNode(stack.getFirst().getLabel(), token, stack.getFirst().getParent()));
                    }

                    try {
                        rule = table.get(currentNode.getLabel()).get(token.getType());
                    } catch (NullPointerException e) {
                        throw new SyntaxException("Syntax Error with rule null error " + stack.getFirst().getLabel() + "Rule =" + rule);
                    }

                    switch (rule) {
                        case 1 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE, "}"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE, "}"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.los, new Token(Token.TokenType.TYPE, ""), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE, "{"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN, ")"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ARGS, "args"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.STRINGARR, "String[]"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN, "("), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.MAIN, "main"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.VOID, "void"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.STATIC, "static"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PUBLIC, "public"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE, "{"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID, ""), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.CLASS, "class"), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PUBLIC, "public"), currentNode));
                        }
                        case 2 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.los, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.stat, currentNode));
                        }
                        case 3 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.epsilon, currentNode));
                        }
                        case 4 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.whilestat, currentNode));
                        }
                        case 5 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.forstat, currentNode));
                        }
                        case 6 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.ifstat, currentNode));
                        }
                        case 7 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.assign, currentNode));
                        }
                        case 8 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.decl, currentNode));
                        }
                        case 9 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.print, currentNode));
                        }
                        case 10 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
                        }
                        case 11 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.los, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.WHILE), currentNode));
                        }
                        case 12 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.los, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.forarith, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SEMICOLON), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.forstart, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.FOR), currentNode));
                        }
                        case 13 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.decl, currentNode));
                        }
                        case 14 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.assign, currentNode));
                        }
                        case 15 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.epsilon, currentNode));
                        }
                        case 16 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.arithexpr, currentNode));
                        }
                        case 17 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.epsilon, currentNode));
                        }
                        case 18 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.elseifstat, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.los, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.IF), currentNode));
                        }
                        case 19 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.elseifstat, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RBRACE), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.los, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LBRACE), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.elseorelseif, currentNode));
                        }
                        case 20 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.epsilon, currentNode));
                        }
                        case 21 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.possif, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ELSE), currentNode));
                        }
                        case 22 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.IF), currentNode));
                        }
                        case 23 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.epsilon, currentNode));
                        }
                        case 24 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.expr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ASSIGN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), currentNode));
                        }
                        case 25 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.possassign, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.type, currentNode));
                        }
                        case 26 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.expr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ASSIGN), currentNode));
                        }
                        case 27 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.epsilon, currentNode));
                        }
                        case 28 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.printexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PRINT), currentNode));
                        }
                        case 29 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TYPE), currentNode));
                        }
                        case 30 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TYPE), currentNode));
                        }
                        case 31 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TYPE), currentNode));
                        }
                        case 32 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
                        }
                        case 33 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.charexpr, currentNode));
                        }
                        case 34 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SQUOTE), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.CHARLIT), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.SQUOTE), currentNode));
                        }
                        case 35 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.boolop, currentNode));
                        }
                        case 36 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.epsilon, currentNode));
                        }
                        case 37 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.booleq, currentNode));
                        }
                        case 38 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.boollog, currentNode));
                        }
                        case 39 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.EQUAL), currentNode));
                        }
                        case 40 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.NEQUAL), currentNode));
                        }
                        case 41 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.AND), currentNode));
                        }
                        case 42 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.OR), currentNode));
                        }
                        case 43 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.relexprprime, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.arithexpr, currentNode));
                        }
                        case 44 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TRUE), currentNode));
                        }
                        case 45 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.FALSE), currentNode));
                        }
                        case 46 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.arithexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.relop, currentNode));
                        }
                        case 47 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.epsilon, currentNode));
                        }
                        case 48 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LT), currentNode));
                        }
                        case 49 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LE), currentNode));
                        }
                        case 50 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.GT), currentNode));
                        }
                        case 51 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.GE), currentNode));
                        }
                        case 52 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.arithexprprime, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.term, currentNode));
                        }
                        case 53 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.arithexprprime, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.term, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.PLUS), currentNode));
                        }
                        case 54 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.arithexprprime, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.term, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.MINUS), currentNode));
                        }
                        case 55 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.epsilon, currentNode));
                        }
                        case 56 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.termprime, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.factor, currentNode));
                        }
                        case 57 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.termprime, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.factor, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.TIMES), currentNode));
                        }
                        case 58 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.termprime, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.factor, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.DIVIDE), currentNode));
                        }
                        case 59 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.termprime, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.factor, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.MOD), currentNode));
                        }
                        case 60 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.epsilon, currentNode));
                        }
                        case 61 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.RPAREN), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.arithexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.LPAREN), currentNode));
                        }
                        case 62 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.ID), currentNode));
                        }
                        case 63 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.NUM), currentNode));
                        }
                        case 64 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.boolexpr, currentNode));
                            stack.push(new TreeNode(TreeNode.Label.relexpr, currentNode));
                        }
                        case 65 -> {
                            stack.pop();
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.DQUOTE), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.STRINGLIT), currentNode));
                            stack.push(new TreeNode(TreeNode.Label.terminal, new Token(Token.TokenType.DQUOTE), currentNode));
                        }
                    }
                } else if (stack.getFirst().getLabel().equals(TreeNode.Label.terminal) && stack.getFirst().getToken().get().getType().equals(token.getType())){
                    // Add terminal
                    stack.getFirst().getParent().addChild(new TreeNode(stack.getFirst().getLabel(), token, stack.getFirst().getParent()));
                    stack.pop();
                    isTerminal = true;
                } else {
                    throw new SyntaxException("Syntax Error.with rule " + rule + " Current node = " + stack.getFirst().getToken());
                }
            }
        }
        return tree;
    }

    private static HashMap<TreeNode.Label, HashMap<Token.TokenType, Integer>> createTable() {
        HashMap<TreeNode.Label, HashMap<Token.TokenType, Integer>> table = new HashMap<>();

        table.put(TreeNode.Label.prog, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.PUBLIC, 1);
        }});

        table.put(TreeNode.Label.los, new HashMap<Token.TokenType, Integer>() {{
            put( Token.TokenType.ID, 2 );
            put( Token.TokenType.WHILE, 2 );
            put( Token.TokenType.FOR, 2 );
            put( Token.TokenType.IF, 2 );
            put(Token.TokenType.SEMICOLON, 2 );
            put( Token.TokenType.PRINT, 2);
            put( Token.TokenType.TYPE, 2);
            put(Token.TokenType.RBRACE, 3);
        }});
        table.put(TreeNode.Label.stat, new HashMap<Token.TokenType, Integer>() {{
            put( Token.TokenType.WHILE, 4);
            put( Token.TokenType.FOR, 5);
            put( Token.TokenType.IF, 6);
            put( Token.TokenType.ID, 7);
            put(Token.TokenType.TYPE, 8);
            put(Token.TokenType.PRINT, 9 );
            put(Token.TokenType.SEMICOLON, 10);
        }});
        table.put(TreeNode.Label.whilestat, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.WHILE, 11);
        }});
        table.put(TreeNode.Label.forstat, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.FOR, 12);
        }});
        table.put(TreeNode.Label.forstart, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.TYPE, 13);
            put(Token.TokenType.ID, 14);
            put(Token.TokenType.SEMICOLON, 15);
        }});
        table.put(TreeNode.Label.forarith, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.ID, 16);
            put(Token.TokenType.LPAREN, 16);
            put(Token.TokenType.NUM, 16);
            put(Token.TokenType.RPAREN, 17);

        }});
        table.put(TreeNode.Label.ifstat,new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.IF, 18);
        }});
        table.put(TreeNode.Label.elseifstat, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.ELSE, 19);
            put(Token.TokenType.IF, 20);
            put(Token.TokenType.WHILE, 20);
            put(Token.TokenType.FOR, 20);
            put(Token.TokenType.ID, 20);
            put(Token.TokenType.SEMICOLON, 20);
            put(Token.TokenType.PRINT, 20);
            put(Token.TokenType.TYPE, 20);
            put(Token.TokenType.RBRACE, 20);
        }});
        table.put(TreeNode.Label.elseorelseif, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.ELSE, 21);
        }});
        table.put(TreeNode.Label.possif, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.IF, 22);
            put(Token.TokenType.LBRACE, 23);
        }});
        table.put(TreeNode.Label.assign, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.ID, 24);
        }});
        table.put(TreeNode.Label.decl, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.TYPE, 25);

        }});
        table.put(TreeNode.Label.possassign, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.ASSIGN, 26);
            put(Token.TokenType.SEMICOLON, 27);
        }});
        table.put(TreeNode.Label.print, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.PRINT, 28);
        }});
        table.put(TreeNode.Label.type, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.TYPE, 29);
        }});
        table.put(TreeNode.Label.expr, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.ID, 32);
            put(Token.TokenType.LPAREN, 32);
            put(Token.TokenType.NUM, 32);
            put(Token.TokenType.TRUE, 32);
            put(Token.TokenType.FALSE, 32);
            put(Token.TokenType.SQUOTE, 33);
        }});
        table.put(TreeNode.Label.charexpr, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.SQUOTE, 34);
        }});
        table.put(TreeNode.Label.boolexpr, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.AND, 35);
            put(Token.TokenType.OR, 35);
            put(Token.TokenType.NEQUAL, 35);
            put(Token.TokenType.EQUAL, 35);
            put(Token.TokenType.SEMICOLON, 36);
            put(Token.TokenType.RPAREN, 36);

        }});
        table.put(TreeNode.Label.boolop, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.NEQUAL, 37);
            put(Token.TokenType.EQUAL, 37);
            put(Token.TokenType.OR, 38);
            put(Token.TokenType.AND, 38);

        }});
        table.put(TreeNode.Label.booleq, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.EQUAL, 39);
            put(Token.TokenType.NEQUAL, 40);

        }});
        table.put(TreeNode.Label.boollog, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.AND, 41);
            put(Token.TokenType.OR, 42);
        }});
        table.put(TreeNode.Label.relexpr, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.ID, 43);
            put(Token.TokenType.LPAREN, 43);
            put(Token.TokenType.NUM, 43);
            put(Token.TokenType.TRUE, 44);
            put(Token.TokenType.FALSE, 45);
        }});
        table.put(TreeNode.Label.relexprprime, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.GE, 46);
            put(Token.TokenType.LE, 46);
            put(Token.TokenType.GT, 46);
            put(Token.TokenType.LT, 46);
            put(Token.TokenType.SEMICOLON, 47);
            put(Token.TokenType.OR, 47);
            put(Token.TokenType.RPAREN, 47);
            put(Token.TokenType.AND, 47);
            put(Token.TokenType.NEQUAL, 47);
            put(Token.TokenType.EQUAL, 47);
        }});
        table.put(TreeNode.Label.relop, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.LT, 48);
            put(Token.TokenType.LE, 49);
            put(Token.TokenType.GT, 50);
            put(Token.TokenType.GE, 51);
        }});
        table.put(TreeNode.Label.arithexpr, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.ID, 52);
            put(Token.TokenType.LPAREN, 52);
            put(Token.TokenType.NUM, 52);
        }});
        table.put(TreeNode.Label.arithexprprime, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.PLUS, 53);
            put(Token.TokenType.MINUS, 54);
            put(Token.TokenType.SEMICOLON, 55);
            put(Token.TokenType.OR, 55);
            put(Token.TokenType.AND, 55);
            put(Token.TokenType.RPAREN, 55);
            put(Token.TokenType.GE, 55);
            put(Token.TokenType.LE, 55);
            put(Token.TokenType.GT, 55);
            put(Token.TokenType.LT, 55);
            put(Token.TokenType.NEQUAL, 55);
            put(Token.TokenType.EQUAL, 55);
        }});
        table.put(TreeNode.Label.term, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.ID, 56);
            put(Token.TokenType.LPAREN, 56);
            put(Token.TokenType.NUM, 56);
        }});
        table.put(TreeNode.Label.termprime, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.TIMES, 57);
            put(Token.TokenType.DIVIDE, 58);
            put(Token.TokenType.MOD, 59);
            put(Token.TokenType.SEMICOLON, 60);
            put(Token.TokenType.OR, 60);
            put(Token.TokenType.AND, 60);
            put(Token.TokenType.RPAREN, 60);
            put(Token.TokenType.GE, 60);
            put(Token.TokenType.LE, 60);
            put(Token.TokenType.GT, 60);
            put(Token.TokenType.LT, 60);
            put(Token.TokenType.NEQUAL, 60);
            put(Token.TokenType.EQUAL, 60);
            put(Token.TokenType.PLUS, 60);
            put(Token.TokenType.MINUS, 60);

        }});
        table.put(TreeNode.Label.factor, new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.LPAREN, 61);
            put(Token.TokenType.ID, 62);
            put(Token.TokenType.NUM, 63);
        }});
        table.put(TreeNode.Label.printexpr,new HashMap<Token.TokenType, Integer>() {{
            put(Token.TokenType.ID, 64);
            put(Token.TokenType.LPAREN, 64);
            put(Token.TokenType.NUM, 64);
            put(Token.TokenType.TRUE, 64);
            put(Token.TokenType.FALSE, 64);
            put(Token.TokenType.DQUOTE, 65);
        }});

        return table;
    }
    // The following class may be helpful.
    class Pair<A, B> {
        private final A a;
        private final B b;

        public Pair(A a, B b) {
            this.a = a;
            this.b = b;
        }

        public A fst() {
            return a;
        }

        public B snd() {
            return b;
        }

        @Override
        public int hashCode() {
            return 3 * a.hashCode() + 7 * b.hashCode();
        }

        @Override
        public String toString() {
            return "{" + a + ", " + b + "}";
        }

        @Override
        public boolean equals(Object o) {
            if ((o instanceof Pair<?, ?>)) {
                Pair<?, ?> other = (Pair<?, ?>) o;
                return other.fst().equals(a) && other.snd().equals(b);
            }

            return false;
        }
    }
}

