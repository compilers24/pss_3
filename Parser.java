import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int position = 0;
    private String programName = "";
    private final CodeGenerator generator = new CodeGenerator();

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public String parse() throws IOException {
        // Parse the code
        parseProgram();
        String asmCode = generator.getCode();
        String asmFilePath = programName + ".s";
        
        try (FileWriter writer = new FileWriter(asmFilePath)) {
            writer.write(asmCode);
        }
        
        return asmFilePath;
    }

    private void parseProgram() {
        parseProgramHeader();
        if (consume("begin") != null) {
            generator.generateProgramHeader();
            parseStatementSeq();
            if (consume("end") == null || consume(".") == null) {
                throw new RuntimeException("Expected 'end.'");
            }
            generator.generateEnd();
        } else {
            throw new RuntimeException("Program should start with 'begin' keyword");
        }
    }

    private void parseStatementSeq() {
        parseExpression();
    }

    private void parseExpression() {
        parseOperand();
        generator.generateOperand1(this.peek().value);
        position++;
        Token op = this.peek();
        if (isMathOp(op)) {
            consume();
            parseOperand();
            generator.generateOperand2(this.peek().value);
            generator.generateMathOp(op.value);
            position++;

            if (consume("=") == null) {
                throw new RuntimeException("Expected '=' sign");
            }

            parseOperand();
            generator.generateResult(this.peek().value);
            position++;

            if (consume(";") == null) {
                throw new RuntimeException("Expected ';' at the end of expression");
            }
        } else {
            throw new RuntimeException("Expected math operand");
        }
    }

    private boolean isMathOp(Token token) {
        return "+".equals(token.value) || "-".equals(token.value);
    }

    private Token peek() {
        return tokens.get(position);
    }

    private void parseOperand() {
        if (consumeNumber() == null) {
            throw new RuntimeException("Expected a number operand");
        }
    }

    private Token consumeNumber() {
        if (tokens.get(position).type == TokenType.NUMBER) {
            return tokens.get(position);
        }
        return null;
    }

    private void parseProgramHeader() {
        if (consume("program") != null) {
            Token programToken = consumeIdentifier();
            if (programToken != null) {
                programName = programToken.value;
                if (consume(";") == null) {
                    throw new RuntimeException("Expected ';' after program declaration");
                }
            } else {
                throw new RuntimeException("Expected Program Identifier");
            }
        } else {
            throw new RuntimeException("Program should start with 'program' keyword");
        }
    }

    private Token consumeIdentifier() {
        if (tokens.get(position).type == TokenType.IDENTIFIER) {
            return tokens.get(position++);
        }
        return null;
    }

    private Token consume(String expected) {
        if (position >= tokens.size()) {
            return null;
        }
        
        if (tokens.get(position).value.equals(expected)) {
            return tokens.get(position++);
        }
        return null;
    }

    private void consume() {
        if (position >= tokens.size()) {
            return;
        }
        position++;
    }
}
