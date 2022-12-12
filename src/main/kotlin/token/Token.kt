package token

import visitor.TokenVisitor

sealed class Token {
    abstract fun accept(tokenVisitor: TokenVisitor)
}

sealed class Operation : Token() {
    override fun accept(tokenVisitor: TokenVisitor) {
        tokenVisitor.visit(this)
    }
}

object Plus : Operation() {
    override fun toString(): String {
        return "PLUS"
    }
}

object Minus : Operation() {
    override fun toString(): String {
        return "MINUS"
    }
}

object Mul : Operation() {
    override fun toString(): String {
        return "MUL"
    }
}

object Div : Operation() {
    override fun toString(): String {
        return "DIV"
    }
}

data class NumberToken(val number: Int) : Token() {
    override fun accept(tokenVisitor: TokenVisitor) {
        tokenVisitor.visit(this)
    }

    override fun toString(): String {
        return "NUMBER($number)"
    }
}

sealed class Brace : Token() {
    override fun accept(tokenVisitor: TokenVisitor) {
        tokenVisitor.visit(this)
    }
}

object LeftBrace : Brace() {
    override fun toString(): String {
        return "LEFT"
    }
}

object RightBrace : Brace() {
    override fun toString(): String {
        return "RIGHT"
    }
}
