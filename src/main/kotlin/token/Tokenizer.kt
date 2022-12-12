package token

class Tokenizer {
    private val tokens = ArrayList<Token>()
    private var curState: State = StartState()

    fun tokenize(s: String): List<Token> {
        curState = StartState()
        for (ch in s) {
            curState.process(ch)
        }
        curState.createToken()
        assert(curState is StartState)
        curState = EndState()
        return tokens
    }

    private abstract inner class State {
        abstract fun process(ch: Char)
        abstract fun createToken()
    }

    private inner class StartState : State() {
        override fun process(ch: Char) {
            when (ch) {
                in '0'..'9' -> {
                    curState = NumberState()
                    curState.process(ch)
                }
                '(' -> tokens.add(LeftBrace)
                ')' -> tokens.add(RightBrace)
                '+' -> tokens.add(Plus)
                '-' -> tokens.add(Minus)
                '*' -> tokens.add(Mul)
                '/' -> tokens.add(Div)
                else -> {
                    if (!ch.isWhitespace()) {
                        curState = ErrorState()
                    }
                }
            }
        }

        override fun createToken() {}
    }

    private inner class NumberState : State() {
        private var number = 0;

        override fun process(ch: Char) {
            when (ch) {
                in '0'..'9' -> {
                    number = number * 10 + (ch - '0')
                }
                else -> {
                    createToken()
                    curState.process(ch)
                }
            }
        }

        override fun createToken() {
            tokens.add(NumberToken(number))
            curState = StartState()
        }
    }

    private inner class ErrorState : State() {
        override fun process(ch: Char) {}
        override fun createToken() {}
    }

    private inner class EndState : State() {
        override fun process(ch: Char) {}
        override fun createToken() {}
    }
}
