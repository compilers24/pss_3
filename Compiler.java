import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

public class Compiler {
    private final String sourceFile;

    public Compiler(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public void compile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.sourceFile));
        StringBuilder code = new StringBuilder();
        String line = reader.readLine();
        while(line != null) {
            code.append(line).append(System.lineSeparator());
            line = reader.readLine();
        }

        List<Token> tokens = scan(code.toString());

        Parser parser = new Parser(tokens);
        String outputFile = parser.parse();

        assembleAndLink(outputFile.split("\\.")[0]);
    }

    private List<Token> scan(String code) {
        List<Token> tokens = new ArrayList<>();
        Scanner scanner = new Scanner(code);
        Token token;

        while ((token = scanner.getToken()) != null) {
            tokens.add(token);
        }
        return tokens;
    }

        private void assembleAndLink(String output) throws IOException{
            String compile = "as -o " + output + ".o " + output + ".s";
            String link = "ld -o " + output + " " + output + ".o";

            Runtime.getRuntime().exec(compile);
            Runtime.getRuntime().exec(link);
        }


    public static void main(String[] args) throws IOException {
        try {
            String filePath = args[0];
            Compiler compiler = new Compiler(filePath);
            compiler.compile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
