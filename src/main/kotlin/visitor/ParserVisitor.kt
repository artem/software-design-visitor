package visitor

import token.*
import java.util.*

class ParserVisitor : TokenVisitor {
    private companion object {
        private val opPriority: Map<Operation, Int> = mapOf(
            Plus to 0, Minus to 0, Div to 1, Mul to 1
        )
    }

    private val stack = Stack<Token>()
    private val result = ArrayList<Token>()

    fun getResult(): List<Token> {
        while (!stack.empty()) {
            val lastToken = stack.peek()
            if (lastToken is Operation) {
                result.add(lastToken)
                stack.pop()
            } else {
                throw IllegalStateException("Only operations are allowed at the end")
            }
        }
        return result
    }

    override fun visit(token: NumberToken) {
        result.add(token)
    }

    override fun visit(token: Brace) {
        when (token) {
            is LeftBrace -> stack.push(token)
            is RightBrace -> {
                while (true) {
                    require(!stack.empty())
                    when (val lastToken = stack.peek()) {
                        is LeftBrace -> {
                            stack.pop()
                            break
                        }

                        is Operation -> {
                            result.add(lastToken)
                            stack.pop()
                        }

                        is RightBrace, is NumberToken -> throw IllegalStateException("Wrong stack state: ${stack.toList()}")
                    }
                }
            }
        }
    }

    override fun visit(token: Operation) {
        while (!stack.empty()) {
            val lastToken = stack.peek()
            if (lastToken !is Operation) {
                break
            }
            val curPriority = opPriority[token]
            val lastPriority = opPriority[lastToken]
            if (curPriority == null || lastPriority == null) {
                throw IllegalArgumentException("Some operations are unrecognized: $token $lastToken")
            }
            if (curPriority > lastPriority) {
                break
            }
            result.add(lastToken)
            stack.pop()
        }
        stack.push(token)
    }
}
