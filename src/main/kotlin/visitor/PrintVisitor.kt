package visitor

import token.Brace
import token.NumberToken
import token.Operation
import token.Token

class PrintVisitor : TokenVisitor {
    private val sb = StringBuilder()

    private fun doVisit(token: Token) {
        sb.append(token.toString())
        sb.append(' ')
    }

    override fun visit(token: NumberToken) {
        doVisit(token)
    }

    override fun visit(token: Brace) {
        doVisit(token)
    }

    override fun visit(token: Operation) {
        doVisit(token)
    }

    override fun toString(): String {
        return sb.toString()
    }
}
