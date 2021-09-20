package com.craftinginterpreters.lox;

import java.util.List;

class LoxFunction implements LoxCallable {
    private final Stmt.Function declaration;
    private final Enviornment closure;

    private final Boolean isInitializer;

    LoxFunction(Stmt.Function declaration, Enviornment closure,
        boolean isInitializer) {
        this.declaration = declaration;
        this.closure = closure;
        this.isInitializer = isInitializer;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Enviornment enviornment = new Enviornment(closure);
        for (int i = 0; i < declaration.params.size(); i++) {
            enviornment.define(declaration.params.get(i).lexeme,
                arguments.get(i));
        }

        try {
            interpreter.executeBlock(declaration.body, enviornment);
        } catch (Return returnValue) {
            if (isInitializer) return closure.getAt(0, "this");

            return returnValue.value;
        }

        if (isInitializer) return closure.getAt(0, "this");
        return null;
    }

    @Override
    public int arity() {
        return declaration.params.size();
    }

    @Override
    public String toString() {
        return "<fn " + declaration.name.lexeme + " >";
    }
    
    LoxFunction bind(LoxInstance instance) {
        Enviornment enviornment = new Enviornment(closure);
        enviornment.define("this", instance);
        return new LoxFunction(declaration, enviornment, isInitializer);
    }
}
