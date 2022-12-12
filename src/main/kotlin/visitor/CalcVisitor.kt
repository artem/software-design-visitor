package visitor

import token.*
import java.lang.UnsupportedOperationException
import java.util.*

class CalcVisitor : TokenVisitor {
    private companion object {
        private val operations: Map<Operation, (Int, Int) -> Int> = mapOf(
            Plus to Int::plus, Minus to Int::minus, Mul to Int::times, Div to Int::div
        )
    }

    private val stack = Stack<Int>()

    override fun visit(token: Brace) {
        throw UnsupportedOperationException("Reverse polish required")
    }

    override fun visit(token: NumberToken) {
        stack.add(token.number)
    }

    override fun visit(token: Operation) {
        require(stack.size >= 2)
        val x = stack.pop()
        val y = stack.pop()
        val op = operations[token] ?: throw IllegalArgumentException("Unrecognized operation")
        stack.push(op(y, x))
    }

    fun getResult(): Int {
        require(stack.size == 1)
        return stack.peek()
    }
}
