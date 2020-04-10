import kotlin.system.exitProcess

fun main() {
    game()
}

fun game() {
    //gameloop
    
    var gesuchtArray = genFourDigitNum()//arrayOf<Int>(5, 6, 9, 7)
    var eingabeArray: String //oder array size 4 -> Array(4){i->i}
    var resultArray = Array(4) { int -> int } //wird number coded ergebnisse erhalten

    println(
        "-------------------------------------------\n" +
                "Hints encoding: " +
                "\n[2] = richtige zahl, richtige position" +
                "\n[1] = richtige zahl, falsche position" +
                "\n[0] = falsche zahl, falsche position" +
                "\n-------------------------------------------\n"
    )
    do {
        do {
            println("4 stellige-distinc-digit zahl(q for quit): ")
            eingabeArray = readLine().toString()

            if (eingabeArray.toLowerCase() == "q")
                exitProcess(1)

            if (checkCorrectInput(eingabeArray))
                if (checkDigitAmt(eingabeArray))
                    if (checkStringUnique(eingabeArray))
                        break
        } while (true)

        for (pos in 0..3) { //vergleicht elem[x] in eingabe mit elem in lösung[x]
            var result: Int = 0
            var current: Int = Character.getNumericValue(eingabeArray[pos])
            for (digit in gesuchtArray) {
                if (current == digit) {
                    result++
                    if (current == gesuchtArray[pos]) {
                        result++
                    }
                    break
                }
            }
            resultArray[pos] = result
        }

        resultArray = resultArray.sortedArrayDescending() //sorting array for proper result-print


        if (checkWin(resultArray)) {
            print("You guessed it right! Of course the number was ")
            printArray(gesuchtArray)
            println("\n... a new number to guess is being generate ...")
            gesuchtArray = genFourDigitNum()
        } else {
            printArray(resultArray)
            println("\n")
        }

    } while (true)
}

fun checkCorrectInput(numberInput: String): Boolean {
    //returns TRUE if numberInput:String consists ONLY of Integers
    return numberInput.toIntOrNull() != null
}

fun checkDigitAmt(numberInput: String): Boolean {
    //returns TRUE if sting has length 4
    return (numberInput.length == 4)
}

fun checkStringUnique(numberInput: String): Boolean {
    //returns TRUE is every Character in the stirng is distinct
    return (numberInput.toList().distinct().size == numberInput.length)
}

fun genFourDigitNum(): Array<Int> {
    //TODO generiert teilweise 3 digit zahlen
    //generate 4 digit number with distinct digits
    var num: Int

    do {
        num = (Math.random() * 10000).toInt()
        //println("generated: $num")
    } while (num.toString().length != num.toString().toList().distinct().size)

    //jetz wirds hässlich, aber ich habe den core code als erstes implementiert und muss hiermit trixen
    var numArray = Array<Int>(4) { int -> int }
    var numString = num.toString()

    for (index in 0..3)
        numArray[index] = numString[index].toString().toInt()


    return numArray
}

fun printArray(resultArray: Array<Int>) {
    //prints an array w/ arbitrary size
    for (number in resultArray) {
        print("[$number]")
    }
}

fun checkWin(resultArray: Array<Int>): Boolean {
    //checks win condition of game => all resultarray[x] == 2
    for (digit in resultArray) {
        if (digit != 2)
            return false
    }
    return true
}


