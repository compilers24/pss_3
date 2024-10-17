import java.util.List;

public class Scanner {
    private final String input;
    private int position;
    private final List<String> symbols = List.of("+", "-", "=", ".", ";");
    private final List<String> keywords = List.of("program", "begin", "end");

    public Scanner(String input) {
        this.input = input;
        this.position = 0;
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    private boolean isSymbol(char ch) {
        return symbols.contains(String.valueOf(ch));
    }

    private char current() {
        if (position >= input.length()) return '\0';
        return input.charAt(position);
    }

    private char next() {
        if (position >= input.length()) return '\0';
        return input.charAt(position++);
    }

    private void skipWhitespace() {
        while (current() == ' ' || current() == '\n' || current() == '\t' || current() == '\r') {
            next();
        }
    }

    private boolean isLetter(char ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }

    private boolean isKeyword(String str) {
        return keywords.contains(str);
    }

    public Token getToken() {
        skipWhitespace();
        char currentChar = current();

        if (currentChar == '\0') return null;

        if (isLetter(currentChar)) {
            StringBuilder value = new StringBuilder();
            while (isLetter(current()) || isDigit(current())) {
                value.append(next());
            }
            String valueStr = value.toString();

            if (isKeyword(valueStr)) {
                return new Token(TokenType.KEYWORD, valueStr);
            } else {
                return new Token(TokenType.IDENTIFIER, valueStr);
            }
        }


        if (isDigit(currentChar)) {
            StringBuilder value = new StringBuilder();
            while (isDigit(current())) {
                value.append(next());
            }
            
            if (isLetter(current())) {
                value.append(next());
                return new Token(TokenType.INVALID, value.toString());
            }
            return new Token(TokenType.NUMBER, value.toString());
        }

        if (isSymbol(currentChar)) {
            return new Token(TokenType.SYMBOL, String.valueOf(next()));
        }

        return new Token(TokenType.INVALID, String.valueOf(next()));
    }

}