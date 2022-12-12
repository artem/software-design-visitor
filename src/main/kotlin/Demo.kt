import token.Tokenizer
import visitor.CalcVisitor
import visitor.ParserVisitor
import visitor.PrintVisitor

fun main() {
    val input = readln()

    val tokens = Tokenizer().tokenize(input)
    val printVisitor = PrintVisitor()
    for (token in tokens) {
        token.accept(printVisitor)
    }
    println("Expression after tokenization: $printVisitor")

    val parseVisitor = ParserVisitor()
    for (token in tokens) {
        token.accept(parseVisitor)
    }
    val parsedTokens = parseVisitor.getResult()
    val printVisitor2 = PrintVisitor()
    val calcVisitor = CalcVisitor()
    for (token in parsedTokens) {2
        token.accept(printVisitor2)
        token.accept(calcVisitor)
    }
    println("Expression after transformation to RPN: $printVisitor2")
    println("Result of evaluation: ${calcVisitor.getResult()}")
}
