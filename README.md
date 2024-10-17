# PSS 3

This project involves building a compiler that scans, parses, and generates assembly (ASM) code for a simple custom language. The language consists of numbers, identifiers, special symbols, and specific keywords. The compiler will follow a set of rules defined in **EBNF (Extended Backus-Naur Form)**, check the grammar, and generate ASM code. The compiler will also verify that mathematical operations are correctly computed in the generated ASM code.

## Language Features

### Special Symbols
The language supports the following special symbols:
- `+` (addition)
- `-` (subtraction)
- `=` (assignment/comparison)
- `;` (statement termination)
- `.` (end of the program)

### Keywords
The following keywords are reserved:
- `program` (used to define the start of the program)
- `begin` (marks the start of the program's execution block)
- `end` (marks the end of the program's execution block)

## Compiler Workflow
The compiler will:
1. **Scan** the tokens from the source code (identifying keywords, numbers, operators, etc.).
2. **Parse** the scanned tokens and check for correct grammar according to the **EBNF** rules.
3. **Generate ASM code**, which performs the mathematical operations and checks if the result of the calculation matches the expected value.

## EBNF (Extended Backus-Naur Form)

The grammar of the language is defined using EBNF notation. The following are the rules that the compiler will use:

```
prg              = prgHeader "begin" expression "end" ".";
prgHeader        = "program" identifier ";";
expression       = simpleExpression ";";
simpleExpression = number mathOp number "=" number
mathOp           = "+" | "-"
```

### Example Program:

```plaintext
program example;
begin
    5 + 10 = 15;
end.
```

### What the Compiler Will Do:
- **Scan** the program and recognize the `program`, `begin`, `end` keywords, numbers, operators (`+`, `-`, `=`), and punctuation (`;`, `.`).
- **Parse** the tokens to ensure the grammar adheres to the EBNF rules. For example, it will check if the program starts with `program identifier;`, followed by `begin` and a valid mathematical expression like `5 + 10 = 15;`.
- **Generate ASM code** that performs the addition or subtraction and checks if the result matches the expected value (`15` in the above example).

### Output:
The generated assembly code will be able to calculate the expression and output the result in an exit code.
