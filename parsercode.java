import java.util.ArrayList;
import java.util.List; 

// AST Node Classes

abstract class Statement {
    // Base class for different types of statements
}


public class ConstantStatement extends Statement {
 private String constant;
 private Expression expression;

 public ConstantStatement(String constant, Expression expression) {
 this.constant = constant;
 this.expression = expression;
 }

 // Getters
 public String getConstant() {
 return constant;
 }

 public Expression getExpression() {
 return expression;
 }
}


public class AlphaBitKeywordStatement extends Statement {
    private String keyword;
    private List<Expression> expressions;

    public AlphaBitKeywordStatement(String keyword, List<Expression> expressions) {
        this.keyword = keyword;
        this.expressions = expressions;
    }

    // Getters
    public String getKeyword() {
        return keyword;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }
}public class IdentifierStatement extends Statement {
    private String identifier;
    private Expression expression;

    public IdentifierStatement(String identifier, Expression expression) {
        this.identifier = identifier;
        this.expression = expression;
    }

    // Getters
}

public class OperatorStatement extends Statement {
    private Expression left;
    private String operator;
    private Expression right;

    public OperatorStatement(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    // Getters
}public abstract class Expression {
    // Base class for different types of expressions
}public class ArithmeticExpression extends Expression {
    private Expression left;
    private String operator;
    private Expression right;

    public ArithmeticExpression(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    // Getters
    public Expression getLeft() {
        return left;
    }

    public String getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }
}
public class LogicalExpression extends Expression {
    private Expression left;
    private String operator;
    private Expression right;

    public LogicalExpression(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    // Getters
    public Expression getLeft() {
        return left;
    }

    public String getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }
}
public class FunctionCallExpression extends Expression {
    private String functionName;
    private List<Expression> arguments;

    public FunctionCallExpression(String functionName, List<Expression> arguments) {
        this.functionName = functionName;
        this.arguments = arguments;
    }

    // Getters
}

public class IdentifierExpression extends Expression {
    private String identifier;

    public IdentifierExpression(String identifier) {
        this.identifier = identifier;
    }

    // Getter
}
class Program {
    private List<FunctionDeclaration> functionDeclarations;

    public Program(List<FunctionDeclaration> functionDeclarations) {
        this.functionDeclarations = functionDeclarations;
    }
class FunctionDeclaration {
    private String name;
    private List<Parameter> parameters;
    private List<Statement> body;

    public FunctionDeclaration(String name, List<Parameter> parameters, List<Statement> body) {
        this.name = name;
        this.parameters = parameters;
        this.body = body;
    }

    // Getters and other methods as needed
}
class AlphaBitParser {
    private List<Token> tokens;
    private int currentTokenIndex;

    public AlphaBitParser(List<Token> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }
public Program parseProgram() {
        List<FunctionDeclaration> functions = new ArrayList<>();
        while (currentTokenIndex < tokens.size()) {
            if (currentTokenIsFunctionDeclaration()) {
                functions.add(parseFunctionDeclaration());
            } else {
                // Handle error or unrecognized token
                reportError("Expected function declaration");
            }
        }
        return new Program(functions);
    }

private FunctionDeclaration parseFunctionDeclaration() {
        // Assume function declarations start with 'function' keyword
        advanceToken(); // Move past 'function'
        String functionName = getTokenContent();
        advanceToken(); // Move past function name
        expectToken("(");
        List<Parameter> parameters = parseParameters();
        expectToken(")");
        expectToken("{");
        List<Statement> body = parseFunctionBody();
        expectToken("}");
        return new FunctionDeclaration(functionName, parameters, body);
    }

    private List<Parameter> parseParameters() {
        List<Parameter> parameters = new ArrayList<>();
        while (!currentTokenEquals(")")) {
            // Assume parameters are in the form: type name
            String type = getTokenContent();
            advanceToken();
            String name = getTokenContent();
            parameters.add(new Parameter(type, name));
            advanceToken();
            if (currentTokenEquals(",")) {
                advanceToken(); // Skip the comma
            }
        }
        return parameters;
    }

    private List<Statement> parseFunctionBody() {
        List<Statement> statements = new ArrayList<>();
        while (!currentTokenEquals("}")) {
            statements.add(parseStatement());
            // Expecting '}' at the end of the function body
        }
        return statements;
    }
private Statement parseStatement() {
    if (currentTokenType().equals("ALPHA_BIT_KEYWORD")) {
        return parseAlphaBitKeywordStatement();
    } else if (currentTokenType().equals("IDENTIFIER")) {
        return parseIdentifierStatement();
    } else if (currentTokenType().equals("CONSTANT")) {
        return parseConstantStatement();
    } else if (isOperatorPeek()) {
        return parseOperatorStatement();
    } else {
        reportError("Unexpected statement");
        return null; // Placeholder return, should not reach here ideally
    }
}
private boolean isArithmeticOperatorPeek() {
    if (currentTokenIndex < tokens.size()) {
        String token = getTokenContent(currentTokenIndex);
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }
    return false;
}
private boolean isLogicalOperatorPeek() {
    if (currentTokenIndex < tokens.size()) {
        String token = getTokenContent(currentTokenIndex);
        return token.equals("==") || token.equals("!=") || token.equals("<") || token.equals(">")
               || token.equals("<=") || token.equals(">=") || token.equals("&&") || token.equals("||") || token.equals("!");
    }
    return false;
}

private boolean isOperatorPeek() {
    return isArithmeticOperatorPeek() || isLogicalOperatorPeek();
}
private Statement parseAlphaBitKeywordStatement() {
    String keyword = getTokenContent(currentTokenIndex);
    advanceToken(); // Move past the keyword
    expectToken("(");
    List<Expression> expressions = parseExpressions();
    expectToken(")");
    expectToken(";");
    return new AlphaBitKeywordStatement(keyword, expressions);
}

private Expression parseExpression() {
    if (isArithmeticOperatorPeek()) {
        return parseArithmeticExpression();
    } else if (isLogicalOperatorPeek()) {
        return parseLogicalExpression();
    } else if (currentTokenType().equals("IDENTIFIER")) {
        return parseIdentifierExpression();
    } else if (currentTokenType().equals("CONSTANT")) {
        return parseConstantExpression();
    } else {
        reportError("Unexpected expression");
        return null; // Placeholder return, should be replaced with actual logic
    }
}

private Expression parseArithmeticExpression() {
    Expression left = parseExpression();
    String operator = getTokenContent(currentTokenIndex);
    advanceToken(); // Move past the operator
    Expression right = parseExpression();
    return new ArithmeticExpression(left, operator, right);
}

private Expression parseLogicalExpression() {
    Expression left = parseExpression();
    String operator = getTokenContent(currentTokenIndex);
    advanceToken(); // Move past the operator
    Expression right = parseExpression();
    return new LogicalExpression(left, operator, right);
}

private Expression parseIdentifierExpression() {
    String identifier = getTokenContent(currentTokenIndex);
    advanceToken(); // Move past the identifier
    return new IdentifierExpression(identifier);
}

private Expression parseConstantExpression() {
    String constant = getTokenContent(currentTokenIndex);
    advanceToken(); // Move past the constant
    return new ConstantExpression(constant);
}

private List<Expression> parseExpressions() {
    List<Expression> expressions = new ArrayList<>();
    while (!currentTokenEquals(")") && !currentTokenEquals(";")) {
        expressions.add(parseExpression());
        if (currentTokenEquals(",")) {
            advanceToken(); // Move past the comma
        }
    }
    return expressions;
}
private String currentTokenType() {
    return tokens.get(currentTokenIndex).getType();
}

private Statement parseOperatorStatement() {
    Expression left = parseExpression();
    String operator = getTokenContent(currentTokenIndex);
    advanceToken(); // Move past the operator
    Expression right = parseExpression();
    expectToken(";");
    return new OperatorStatement(left, operator, right);
}

private void reportError(String errorMessage) {
    throw new RuntimeException("Parse error: " + errorMessage);
}

// Token class definition (assuming this is how your Token class looks)
class Token {
    private String type;
    private String content;

    public Token(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}

// Parameter class definition
class Parameter {
    private String type;
    private String name;

    public Parameter(String type, String name) {
        this.type = type;
        this.name = name;
    }

    // Getters
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
class ConstantExpression extends Expression {
    private String value;

    public ConstantExpression(String value) {
        this.value = value;
    }

    // Getter
    public String getValue() {
        return value;
    }
}
