# Toy Language Interpreter (Java)

A Java implementation of a Toy Programming Language Interpreter that supports
expressions, control flow, file operations, heap memory management, and concurrency.

The project includes an interactive **JavaFX GUI** that allows users to visualize
the interpreter’s internal state and execute programs step-by-step.

This project was developed as part of an Advanced Programming Methods course.

---

## Features

- Expression evaluation
  - Arithmetic expressions
  - Logical expressions
  - Relational expressions

- Statements
  - Variable declaration
  - Assignment
  - Print
  - If statements
  - While loops
  - Compound statements

- Memory management
  - Symbol table
  - Heap allocation
  - Heap read/write operations

- File handling
  - Open file
  - Read file
  - Close file

- Concurrency
  - `fork` statement for parallel execution

- Execution logging

- Interactive GUI built with **JavaFX**

---

## Core Components

**ProgramState**
- Execution stack
- Symbol table
- Heap
- Output list
- File table

**Statements**
- Represent the instructions of the toy language
- Each statement implements an execution method

Examples:
- AssignStmt
- IfStmt
- WhileStmt
- PrintStmt
- forkStmt

**Expressions**
- Represent evaluatable values

Examples:
- ArithExp
- LogicExp
- RelationalExp
- VarExp
- ValueExp

---

## Architecture

The interpreter follows a MVC layered architecture with:

- JavaFX GUI
- Controller
- Repository
- Model (statements, expressions, etc.)

---

## GUI

The JavaFX interface allows for:

- Real-time Debugging: Provides a step-by-step execution view, allowing for deep inspection of the Stack, Heap, and Symbol Table at any point in the lifecycle.
- Concurrency Monitoring: Visualizes active thread counts and their respective execution stacks.

---

