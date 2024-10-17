import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CodeGenerator {
    private final List<String> instructions = new ArrayList<>();

    public void generateProgramHeader() {
        instructions.add(".section .text");
        instructions.add(".globl _start");
        instructions.add("_start:");
    }

    public void generateOperand1(String operand) {
        instructions.add("mov $" + operand + ", %rax");
    }

    public void generateOperand2(String operand) {
        instructions.add("mov $" + operand + ", %rbx");
    }

    public void generateMathOp(String op) {
        if ("+".equals(op)) {
            instructions.add("add %rbx, %rax");
        } else if ("-".equals(op)) {
            instructions.add("sub %rbx, %rax");
        }
        instructions.add("mov %rax, %rdi");
    }

    public void generateResult(String result) {
        instructions.add("mov $" + result + ", %rbx");
        generateCompare();
    }

    public void generateCompare() {
        instructions.add("cmp %rbx, %rdi");
        instructions.add("jne not_equal");
        instructions.add("je end");
        generateNotEqualLabel();
    }

    public void generateNotEqualLabel() {
        instructions.add("not_equal:");
        instructions.add("mov $0, %rdi");
    }

    public void generateEnd() {
        instructions.add("end:");
        instructions.add("mov $60, %rax");
        instructions.add("syscall");
    }

    public String getCode() {
        return instructions.stream().collect(Collectors.joining("\n"));
    }
}
