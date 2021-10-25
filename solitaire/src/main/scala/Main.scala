import scala.io.StdIn._
import scala.collection.mutable.Stack

object main {

  var gameState: GameState = GameState()

  //List of all coverd card stacks
  var coveredStacks: List[Stack[Card]] = gameState.coveredStacks

  //List of all uncovered card stacks
  var uncoveredStacks: List[Stack[Card]] = gameState.uncoveredStacks

  //List of all ace card stacks
  var aceStacks: List[Stack[Card]] = gameState.aceStacks

  //All stacks where playable cards could be/moves could be made
  var allStacks: List[Stack[Card]] = uncoveredStacks ++ aceStacks

  //Discard and draw stack
  val discardStack = gameState.discardStack
  val drawStack = gameState.drawStack

  //Cards to flip per draw
  var cardsToFlip = gameState.cardsToFlip

  //Generate and shuffle the deck, then push it all to a stack
  val deck = gameState.deck
  //Used for testing
  //val deck = Deck.generateDeck
  val deckStack: Stack[Card] = gameState.deckStack
  deckStack.pushAll(deck)

  //List of all the stacks (ace and solitaire) as well as the discard stack
  //Used for searching for cards to move
  var allStacksAndDiscardStack:List[Stack[Card]] = uncoveredStacks ++ aceStacks ++ List(discardStack)

  val gameStateHistory: Stack[GameState] = Stack[GameState](gameState)
  /*
  //////////////////////////////////////////////
  -------------------- MAIN --------------------
  //////////////////////////////////////////////

  This function deals out the cards to the proper 
  stacks, prints the board, and starts the game

  */
  def main(args: Array[String]): Unit = {
    //Deal out the cards into the stack
    dealGame()
    //Update the collections of stacks after the individual stacks have been updated
    updateGameWithoutPrint()
    //Begin the game
    playGame()
  }

  /*
  //////////////////////////////////////////////////////////////
  --------------------------------------------------------------
  -------------------- CONVERSION FUNCTIONS --------------------
  --------------------------------------------------------------
  //////////////////////////////////////////////////////////////

  Functions that handle converting values from or to cards

  */

  /*
  ////////////////////////////////////////////////////////////////
  -------------------- CONVERT VALUE FOR CARD --------------------
  ////////////////////////////////////////////////////////////////

  This function takes an integer card value (1-13) and converts it to the
  corresponding card value (A, J, Q, K)

  */
  def convertValueForCard(value: Int) = {
    //Ace conversion to card representation from value
    if(value == 1) {
      "A"
    }
    else if (value == 2) {
      "2"
    }
    else if (value == 3) {
      "3"
    }
    else if (value == 4) {
      "4"
    }
    else if (value == 5) {
      "5"
    }
    else if (value == 6) {
      "6"
    }
    else if (value == 7) {
      "7"
    }
    else if (value == 8) {
      "8"
    }
    else if (value == 9) {
      "9"
    }
    else if (value == 10) {
      "10"
    }
    //Jack conversion to card representation from value
    else if (value == 11) {
      "J"
    }
    //Queen conversion to card representation from value
    else if (value == 12) {
      "Q"
    }
    //King conversion to card representation from value
    else if (value == 13) {
      "K"
    }
    else {
      "ERROR"
    }
  }

  /*
  /////////////////////////////////////////////////////////////////
  -------------------- CONVERT VALUE FROM CARD --------------------
  /////////////////////////////////////////////////////////////////

  This function takes a string card value (A, 2, J, Q, K, etc) and 
  converts in to the corresponding numerical value (1-13)

  */
  def convertValueFromCard(value: String): Int = {
    //Ace conversion to value from card representation
    if(value.toLowerCase == "a") {
      1
    }
    else if(value.toLowerCase == "1") {
      1
    }
    else if (value.toLowerCase == "2") {
      2
    }
    else if (value.toLowerCase== "3") {
      3
    }
    else if (value.toLowerCase == "4") {
      4
    }
    else if (value.toLowerCase == "5") {
      5
    }
    else if (value.toLowerCase == "6") {
      6
    }
    else if (value.toLowerCase == "7") {
      7
    }
    else if (value.toLowerCase == "8") {
      8
    }
    else if (value.toLowerCase == "9") {
      9
    }
    else if (value.toLowerCase == "10") {
      10
    }
    //Jack conversion to value from card representation
    else if (value.toLowerCase == "j") {
      11
    }
    //Queen conversion to value from card representation
    else if (value.toLowerCase == "q") {
      12
    }
    //King conversion to value from card representation
    else if (value.toLowerCase == "k") {
      13
    }
    else {
      -1
    }
  }

  /*
  ///////////////////////////////////////////////////////////////
  -------------------- CONVERT SUIT FOR CARD --------------------
  ///////////////////////////////////////////////////////////////

  This function takes a single character string card suit (s, h, d, or c) and 
  converts in to the corresponding full suit name (spades, hearts, diamonds, or clubs)
  
  */
  def convertSuitForCard(suit: String): Suit.Value = {
    if(suit.toLowerCase == "s") {
      Suit.Spades
    }
    else if(suit.toLowerCase == "h") {
      Suit.Hearts
    }
    else if(suit.toLowerCase == "d") {
      Suit.Diamonds
    }
    else if(suit.toLowerCase == "c") {
      Suit.Clubs
    }
    else {
      Suit.None
    }
  }

  /*
  /////////////////////////////////////////////////////////////////////
  ---------------------------------------------------------------------
  -------------------- STRING CONVERSION FUNCTIONS --------------------
  ---------------------------------------------------------------------
  /////////////////////////////////////////////////////////////////////

  Functions that handle converting a card to a string for 
  printing to the board

  */

  /*
  ///////////////////////////////////////////////////////////////
  -------------------- CONVERT CARD TO ASCII --------------------
  ///////////////////////////////////////////////////////////////

  This function takes a card (in value and suit form) are returns
  an ASCII representation of that card

  */
  def convertCardToASCII(card: Card):String = {
    val value = card.value
    val suit = card.suit
    val shortSuit = suit.toString.head
    //Hearts
    if(suit == Suit.Hearts) {
      //Single digit and single character (face cards)
      if(value != 10) {
        return(
        "-------------" + "," + 
        "| " + convertValueForCard(value) + shortSuit + "        |" + "," + 
        "|           |" + "," +
        "|           |" + "," +
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|        " + convertValueForCard(value) + shortSuit + " |" + "," + 
        "-------------")
      }
      //Double digit
      else if(value == 10) {
        return(
        "-------------" + "," + 
        "| " + convertValueForCard(value) + shortSuit + "       |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|       " + convertValueForCard(value) + shortSuit + " |" + "," + 
        "-------------")
      }
      else {
          return("ERROR IN convertCardtoASCII HEARTS ELSE")
      }
    }
    //Diamonds
    else if(suit == Suit.Diamonds) {
      //Single digit and single character (face cards)
      if(value != 10) {
        return(
        "-------------" + "," + 
        "| " + convertValueForCard(value) + shortSuit + "        |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|  " + shortSuit + "  " + shortSuit + "     |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "  " + shortSuit + "     |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|        " + convertValueForCard(value) + shortSuit + " |" + "," + 
        "-------------")
      }
      //Double digit
      else if(value == 10) {
        return(
        "-------------" + "," + 
        "| " + convertValueForCard(value) + shortSuit + "       |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|  " + shortSuit + "  " + shortSuit + "     |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "     " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "  " + shortSuit + "     |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|       " + convertValueForCard(value) + shortSuit + " |" + "," + 
        "-------------")
      }
      else {
          return("ERROR IN convertCardtoASCII DIAMONDS ELSE")
      }
    }
    //Spades
    else if(suit == Suit.Spades) {
      //Single digit and single character (face cards)
      if (value != 10) {
        return(
        "-------------" + "," + 
        "| " + convertValueForCard(value) + shortSuit + "        |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|        " + shortSuit + "  |" + "," + 
        "|        " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|        " + convertValueForCard(value) + shortSuit + " |" + "," + 
        "-------------")
      }
      //Double digit
      else if(value == 10) {
        return(
        "-------------" + "," + 
        "| " + convertValueForCard(value) + shortSuit + "       |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|        " + shortSuit + "  |" + "," + 
        "|        " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|       " + convertValueForCard(value) + shortSuit + " |" + "," + 
        "-------------")
      }
      else {
          return("ERROR IN convertCardtoASCII SPADES ELSE")
      }
    }
    //Clubs cards
    else if(suit == Suit.Clubs) {
      //Single digit and single character (face cards)
      if(value != 10) {
        return(
        "-------------" + "," + 
        "| " + convertValueForCard(value) + shortSuit + "        |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|        " + convertValueForCard(value) + shortSuit + " |" + "," + 
        "-------------")
      }
      //Double digit
      else if(value == 10) {
        return(
        "-------------" + "," + 
        "| " + convertValueForCard(value) + shortSuit + "       |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "        |" + "," + 
        "|  " + shortSuit + "  " + shortSuit + "  " + shortSuit + "  |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|       " + convertValueForCard(value) + shortSuit + " |" + "," + 
        "-------------")
      }
      else {
          return("ERROR IN convertCardtoASCII CLUBS ELSE")
      }
    }
    //Return empty card
    else if(suit == Suit.None) {
      return(
          "-------------" + "," + 
          "|           |" + "," + 
          "|           |" + "," + 
          "|           |" + "," + 
          "|           |" + "," + 
          "|           |" + "," +
          "|           |" + "," +
          "|           |" + "," + 
          "|           |" + "," + 
          "|           |" + "," + 
          "|           |" + "," + 
          "|           |" + "," + 
          "|           |" + "," + 
          "|           |" + "," + 
          "-------------")
    }
    //Error return
    else {
      return("ERROR IN convertCardtoASCII LAST ELSE")
    }
  }

  def convertCardToASCIIWithSize(size: Int): String = {
    //If size is 2 digits
    if(size > 9) {
      val stringSize = size.toString()
        "-------------" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|           |" + "," +
        "|    " + stringSize(0) + " " + stringSize(1) + "    |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "-------------"
    }
    //If size is one digit
    else {
        "-------------" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," +
        "|           |" + "," +
        "|     " + size + "     |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "|           |" + "," + 
        "-------------"
    }
  }

  /*
  //////////////////////////////////////////////////////////
  -------------------- PRINT EMPTY CARD --------------------
  //////////////////////////////////////////////////////////

  This function prints a placeholder card with no value to it
  Used to represent where cards can go (ace spots and discard spot)

  */
  def printEmptyCard():String = {
    "-------------" + "," + 
    "|           |" + "," + 
    "|           |" + "," + 
    "|           |" + "," + 
    "|           |" + "," +
    "|           |" + "," +
    "|           |" + "," + 
    "|           |" + "," + 
    "|           |" + "," + 
    "|           |" + "," + 
    "|           |" + "," + 
    "|           |" + "," + 
    "|           |" + "," + 
    "|           |" + "," + 
    "-------------"
  }

  /*
  //////////////////////////////////////////////////////////
  -------------------- PRINT BLANK CARD --------------------
  //////////////////////////////////////////////////////////

  This function creates a blank space the size of a card
  Used for spacing out elements

  */
  def printBlankCard():String = {
    "             " + "," + 
    "             " + "," + 
    "             " + "," + 
    "             " + "," + 
    "             " + "," +
    "             " + "," +
    "             " + "," + 
    "             " + "," + 
    "             " + "," + 
    "             " + "," + 
    "             " + "," + 
    "             " + "," + 
    "             " + "," + 
    "             " + "," + 
    "             "
  }

  /*
  /////////////////////////////////////////////////////////////////////
  -------------------- CONVERT STACK SIZE TO ASCII --------------------
  /////////////////////////////////////////////////////////////////////

  This function takes a size of a stacdk and converts it to an ASCII representation 
  to be printed above the cards that have cards underneath them

  */
  def convertStackSizeToASCII(size: Int): String = {
    if (size == 0) {
      "             "
    }
    else if(size == 1) {
      "*            "
    }
    else if(size == 2) {
      "**           "
    }
    else if(size == 3) {
      "***          "
    }
    else if(size == 4) {
      "****         "
    }
    else if(size == 5) {
      "*****        "
    }
    else if(size == 6) {
      "******       "
    }
    else if(size == 7) {
      "*******      "
    }
    else if(size == 8) {
      "********     "
    }
    else if(size == 9) {
      "*********    "
    }
    else if(size == 10) {
      "**********   "
    }
    else if(size == 11) {
      "***********  "
    }
    else if(size == 12) {
      "************ "
    }
    else if(size == 13) {
      "*************"
    }
    else {
      "ERROR"
    }
  }

  /*
  ////////////////////////////////////////////////////
  -------------------- PRINT CARD --------------------
  ////////////////////////////////////////////////////

  This function takes a list of cards (represented as strings) and prints them
  
  */
  def printCard(card: List[String]) = {
    //Go through the lines of a card and print them
    //Usually this "card" is actually multiple cards represneted as a list of strings
    for(i <- card) {
      print(i)
    }
  }

  /*
  /////////////////////////////////////////////////////////
  -------------------- REFORMAT CARD LIST --------------------
  /////////////////////////////////////////////////////////

  This function takes a list of cards or turns it into a list of strings
  so that the cards can be printed horizontally

  */
  def reformatCardList(cards: List[List[String]]):List[String] = {
    //Make variables fr the return and the counter
    var returnLine = List[String]()
    var rowCounter = 0
    //For every row in the list of list of string
    while(rowCounter < 15) {
      //Add a horizontal space between the cards
      cards.foreach(card => returnLine = returnLine :+ (card(rowCounter) + "    "))
      //Add a new line for the next line
      returnLine = returnLine :+ "\n"
      //Increment
      rowCounter += 1
    }
    //Return the line
    returnLine
  }

  /*
  ////////////////////////////////////////////////////////////////////
  -------------------- CONVERT CARD PILE TO ASCII --------------------
  ////////////////////////////////////////////////////////////////////

  This function converts a whole stack of cards into a single card to be printed,
  allowing the player to see all cards, even cards on top of other cards 
  */
  def convertCardPileToASCII(cardStack: Stack[Card]): String = {
    //reverse the card stack to print cards in correct orders
    val reversedCardStack = cardStack.reverse
    //convert all cards in stack to printable versions
    var cardList = List[List[String]]()
    for(card <- reversedCardStack) {
      cardList = cardList :+ convertCardToASCII(card).split(",").toList
    }
    //reformat that list so they print horizontally
    var reformattedCardList = List[String]()
    reformattedCardList = reformatCardList(cardList)
    //set top of card which will never change
    var cardToPrint = "-------------" + ","
    //loop through all the lines of the cards and only select the ones that have the name of the card
    var index = 0
    var cardLengthIndex = 0
    for(c <- reformattedCardList) {
      if(index > cardList.size && index < cardList.size*2+1 && cardLengthIndex < 14) {
        cardToPrint = cardToPrint + (c.trim + ",")
        cardLengthIndex += 1
        index += 1
      }
      else {
        index += 1
      }
    }
    //add blank spots for all other spaces not taken up by stacked cards
    while(cardLengthIndex < 13) {
      cardToPrint = cardToPrint + "|           |" + ","
      cardLengthIndex += 1
    }
    //add bottom line
    cardToPrint = cardToPrint + ("-------------" + ",")
    //return
    cardToPrint
    
  }

  /*
  ////////////////////////////////////////////////////////////////////
  -------------------- PRINT SINGLE CARD OR STACK --------------------
  ////////////////////////////////////////////////////////////////////

  This function takes a stack of cards and deturmines if the top card should be printed, 
  or if the whole stack should be printed
  
  */
  def printSingleCardOrStack(stack: Stack[Card]): String = {
    //If the passed in stack has more than one card, call convertCardPileToASCII on that stack
    if(stack.length > 1) {
      convertCardPileToASCII(stack)
    }
    //If the passed in stack has exactly one card, convert just that card to ascii
    else if(stack.length == 1) {
      convertCardToASCII(stack.top)
    }
    //Otherwise, error out
    else {
      "ERROR IN PRINT SINGLE CARD OR STACK"
    }
  }

  /*
  /////////////////////////////////////////////////////////////
  -------------------------------------------------------------
  -------------------- MAIN GAME FUNCTIONS --------------------
  -------------------------------------------------------------
  /////////////////////////////////////////////////////////////

  Functions that are the main game functions that call other 
  helper functions

  */

  /*
  ////////////////////////////////////////////////////
  -------------------- PRINT GAME --------------------
  ////////////////////////////////////////////////////

  This function prints the current state of the game

  */
  def printGame() = {

    //Makes a list of the ace card spots above the main section of the game. Adds the top card to the list and adds an empty card if there are no cards in the stack
    var aceCardList = List[List[String]]()
    for(stack <- aceStacks) {
      aceCardList = aceCardList :+ convertCardToASCII(stack.top).split(",").toList
    }
    //Call reformatCardList to arrange the cards horizontally
    var aceCards = reformatCardList(aceCardList)
    //Print the cards
    printCard(aceCards)

    //Space seperation between ace section and main section
    println()
    println()
    println()

    //Makes a list of the solitaire spots in the main section of the game. Adds the top card to the list and adds an empty card if there are no cards in the stack
    var cardList = List[List[String]]()
    //If the stacks are empty, add an empty card, otherwise, print the single card or the whole stack (if multiple cards are present)
    for(i <- 0 to 6) {
      if(uncoveredStacks(i).isEmpty) {
        cardList = cardList :+ printEmptyCard().split(",").toList
      }
      else {
        if(i == 1) {
          print("             ")
          for(stack <- coveredStacks) {
            print("    " + convertStackSizeToASCII(stack.size))
          }
          println()
        }
        cardList = cardList :+ printSingleCardOrStack(uncoveredStacks(i)).split(",").toList
      }
    }
    //Call reformatCardList to arrange the cards horizontally
    var cards = reformatCardList(cardList)
    //Print the cards
    for(i <- cards) {
      print(i)
    }

    //Makes space between the main section and the draw section
    println()
    println()
    println()

    //Makes a list of the discard deck and draw deck below the main section of the game with blank cards for spacing. 
    //Adds the top card to the list and adds an empty card if there are no cards in the stack
    var drawCardsList = List[List[String]]()
    //Add blank cards to get spacing correct
    drawCardsList = 
      drawCardsList :+ printBlankCard().split(",").toList 
        :+ printBlankCard().split(",").toList 
        :+ printBlankCard().split(",").toList 
        :+ printBlankCard().split(",").toList 
        :+ printBlankCard().split(",").toList
    //If the discard stack is empty, add an empty card
    if(discardStack.isEmpty) {
      drawCardsList = drawCardsList :+ printEmptyCard().split(",").toList
    }
    //Otherwise, check the top card of the discard stack and add it to the list
    else {
      drawCardsList = drawCardsList :+ convertCardToASCII(discardStack.top).split(",").toList
    }
    //Print the number of cards in the stack in the middle of the draw deck
    drawCardsList = drawCardsList :+ convertCardToASCIIWithSize(drawStack.size).split(",").toList
    //call reformatCardList to arrange the cards horizontally
    var drawCards = reformatCardList(drawCardsList)
    //Print the drawcards reformatted list of cards
    printCard(drawCards)
  }

  /*
  ///////////////////////////////////////////////////
  -------------------- DEAL GAME --------------------
  ///////////////////////////////////////////////////

  This function deals cards out like one would in solitaire

  */
  def dealGame() = {
    //Loop through the uncovered stacks and add cards to them
    for(i <- 0 to 6) {
      uncoveredStacks(i).push(deckStack.pop())
      //Loop through the covered stacks and add cards to them decreasing as the deal progresses
      for(j <- i to 5) {
        coveredStacks(j).push(deckStack.pop())
      }
    }
      
    //pushes the rest of the deck to the draw pile
    drawStack.pushAll(deckStack)

    //update lists of stacks to reflect the delt cards
    rebuildStacks()
  }

  /*
  ///////////////////////////////////////////////////
  -------------------- PLAY GAME --------------------
  ///////////////////////////////////////////////////

  This function begins the game by asking the user their name, 
  setting game options, and takes user input

  */
  def playGame() = {
    //var to stop program when user wants to
    var running = true

    //prompt user for name
    println("What is your name?")

    //read in name
    var name = readLine()
    //Print introductory info, rules, and mechanics
    println(s"Hello, $name! \nWelcome to Solitaire!")
    println()
    println("Just a quick explanation of how game is structured.")
    println()
    println("The four piles at the top are the ace piles and can be called by command as: A1, A2, A3, and A4.")
    println("The seven piles in the second row are the main solitaire piles and can be called by command as: S1, S2, S3, S4, S5, S6, and S7.")
    println("The two piles in the last row are the discard pile and the draw pile respectively. When you draw, a card will be removed from the draw pile and placed face up in the discard pile to be used.")
    println("Commands to use will print when the game is started.")
    println()
    println("Happy shuffling!")
    println()
    //prompt for play style
    println("Would you like to flip 1 card at a time or three cards at a time? (1 or 3)")
    var flipInput = readLine()
    
    //set cards to flip to 3
    if(flipInput == "3") {
      cardsToFlip = 3
    }
    //set cards to flip to 1
    else if(flipInput == "1") {
      cardsToFlip = 1
    }
    //Input error checking
    else if(flipInput != "1") {
      println("Invalid input. Flipping 1 card at a time.")
    }

    //Print valid commands
    println("Commands:")
    println("draw -             draws 1 or 3 cards from the draw deck and places it face up in the discard deck to be used")
    println()
    println("move __ to __ -    allows you to move a card (KD, AS, 6C, JH, etc) to a specific pile (A1, S4, A3, S7, etc.) \n \t Example: \"move AS to A3\" moves the ace of spades (if it is in play) to the third ace pile. \n \t Example: \"move KD to S4\" moves the king of diamonds (if it is in play) to the fourth solitaire pile.")
    println()
    println("moveall __ to __ - allows you to move a card (KD, AS, 6C, JH, etc) and any subsequent cards in the pile to a specific pile (A1, S4, A3, S7, etc.) \n \t Example: \"move 10D to S4\" moves the 10 of diamonds (if it is in play) and any cards placed on top of the 10 of diamonds to the fourth solitaire pile. \n")
    println()
    println("q -                quit the game")

    println()

    //Print the game board
    updateGame()

    //handle input during game
    while(running) {
      var input = readLine()
      handleInput(input)
      //exits the game
      if(input.toLowerCase == "q") {
        println(s"Goodbye $name!")
        running = false
      }
      if(hasWon()) {
        println("Congratulations! You won Solitaire!")
        running = false
      }
    }
  }

  def hasWon(): Boolean = {
    var hasWon = true
    for(stack <- aceStacks) {
      if(stack.size != 14) {
        hasWon = false
      }
    }
    hasWon
  }
  /*
  ////////////////////////////////////////////////////////////
  ------------------------------------------------------------
  -------------------- UPDATING FUNCTIONS --------------------
  ------------------------------------------------------------
  ////////////////////////////////////////////////////////////

  Functions that handle updating different aspects of the game 
  (stacks, etc)

  */

  /*
  /////////////////////////////////////////////////////
  -------------------- UPDATE GAME --------------------
  /////////////////////////////////////////////////////

  This function updates the game by updating the stacks, 
  rebuilding the lists of stacks, and reprinting the board

  */
  def updateGame(): Unit = {
    //Push blank cards onto any necessary stacks and pop from covered stack if necessary
    fixStacksAfterMove()
    //Rebuild the collections of stacks after modiftying the individual stacks with moves
    rebuildStacks()
    updateGameState()
    //Reprint the board after updates
    printGame()
  }

  /*
  /////////////////////////////////////////////////////
  -------------- UPDATE GAME WITHOUT PRINT-------------
  /////////////////////////////////////////////////////

  This function updates the game by updating the stacks, 
  rebuilding the lists of stacks, but does not print the board

  */
  def updateGameWithoutPrint(): Unit = {
    //Push blank cards onto any necessary stacks and pop from covered stack if necessary
    fixStacksAfterMove()
    //Rebuild the collections of stacks after modiftying the individual stacks with moves
    rebuildStacks()
  }

  /*
  ///////////////////////////////////////////////////////////////
  -------------------- FIX STACKS AFTER MOVE --------------------
  ///////////////////////////////////////////////////////////////

  This function checks if a stack is empty and pushes a blank card if it is to prevent errors,
  it also checks if there are covered cards that need to be flipped; 
  if there are, they get popped and pushed to the uncovered stack

  */
  def fixStacksAfterMove() = {
    //Set temporary card variable
    var card: Card = null

    //If the first uncovered stack is empty, push on a blank card
    if(uncoveredStacks(0).isEmpty) {
      uncoveredStacks(0).push(Card(-1, Suit.None))
    }
    //If the rest of the solitaire stacks are empty, push on a blank card
    for(i <- 1 to 6) {
      if(uncoveredStacks(i).isEmpty && coveredStacks(i-1).isEmpty) {
        //Push on a blank card
        uncoveredStacks(i).push(Card(-1, Suit.None))
      }
      else if(uncoveredStacks(i).isEmpty && !coveredStacks(i-1).isEmpty) {
        //Pop the card from the covered stack and push it to the uncovered stack
        card = coveredStacks(i-1).pop()
        uncoveredStacks(i).push(card)
      }
    }

    //If the ace stacks are empty, push on a blank card
    for(i <- 0 to 3) {
      if(aceStacks(i).isEmpty) {
        aceStacks(i).push(Card(-1, Suit.None))
      }
    }
  }

  /*
  ////////////////////////////////////////////////////////
  -------------------- REBUILD STACKS --------------------
  ////////////////////////////////////////////////////////

  This function rebuilds the lists of stacks after stacks have been updated

  */
 
  def rebuildStacks(): Unit = {
    //Rebuild allStack collection
    allStacks = uncoveredStacks ++ aceStacks
    //Rebuild allStacksAndDiscardStack collection
    allStacksAndDiscardStack = uncoveredStacks ++ aceStacks ++ List(discardStack)
  }

  /*
  ////////////////////////////////////////////////////////
  -------------------- UPDATE GAME STATE -----------------
  ////////////////////////////////////////////////////////

  This function updates the game state with the new stacks 
  and adds the previous game state to a history

  */  

  def updateGameState() = {
    if(gameState != null) {
      gameStateHistory.push(gameState)
    }
    gameState = GameState(uncoveredStacks, coveredStacks, aceStacks, discardStack, drawStack, cardsToFlip, deck, deckStack)
  }

  /*
  ////////////////////////////////////////////////////////////
  ------------------------------------------------------------
  -------------------- SEARCHING FUNCTIONS -------------------
  ------------------------------------------------------------
  ////////////////////////////////////////////////////////////

  Functions that handle searching for/in different things

  */

  /*
  //////////////////////////////////////////////////////////
  -------------------- FIND CARD ON TOP --------------------
  //////////////////////////////////////////////////////////

  This function locates a card using its value and suit and returns 
  a (boolean, int) tuple of if the card was found, and what stack it is 
  located in in allStacksAndDiscardStack

  */
  def findCardOnTop(value: Int, suit: String): (Boolean, Int) = {
    //instantiate variables for counting and returning
    var foundAndNum = (false, -1)
    var counter = 0
    //go through all 13 stacks
    while(counter < 12) {
      if(!allStacksAndDiscardStack(counter).isEmpty) {
        //set current top card
        val current = allStacksAndDiscardStack(counter).top
        //if the card matches the values apssed in, set tuple to reflect that and kills the loop
        if(current.value == value && current.suit == convertSuitForCard(suit)) {
          foundAndNum = (true, counter)
          counter = 12
        }
        //Go to next stack
        else {
          counter += 1
        }
      }
      else {
        counter += 1
      }
    }
    //return tuple
    foundAndNum
  }

  /*
  /////////////////////////////////////////////////////////////////
  -------------------- GET STACK FROM TOP CARD --------------------
  /////////////////////////////////////////////////////////////////

  This function takes a card and returns the stack that that card is in

  */
  def getStackFromCard(targetCard: Card): Stack[Card] = {
    //Set a return stack variable
    var stackToReturn = Stack[Card]()
    //Loop through all the stacks (minus the draw stack)
    for(stack <- allStacks) {
      //Loop through all the cards in a specific stack
      for(card <- stack) {
        //If the card is the same as the target card by value comparison, return that stack
        if(card.value == targetCard.value && card.suit == targetCard.suit) {
          stackToReturn = stack
        }
      }
    }
    stackToReturn

  }

  /*
  ///////////////////////////////////////////////////////////////
  ---------------------------------------------------------------
  -------------------- VERIFICATION FUNCTIONS -------------------
  ---------------------------------------------------------------
  ///////////////////////////////////////////////////////////////

  Functions that validate/verify things

  */

  /*
  ///////////////////////////////////////////////////////
  -------------------- VALIDATE CARD --------------------
  ///////////////////////////////////////////////////////

  This function validates that a value, suit combination 
  representation of a card passed in is a valid card 

  */
  def validateCard(value: Int, suit: String): Boolean = {
    var isCard = true
    //Check that the value of the card is within the specified bounds
    if(value < 0 || value > 14) {
      isCard = false
    }
    //Check that the suit of the card is a valid suit
    if(suit.toLowerCase != "s" && suit.toLowerCase != "c" && suit.toLowerCase != "d" && suit.toLowerCase != "h") {
      isCard = false
    }
    isCard
  }

  /*
  ////////////////////////////////////////////////////////
  -------------------- VALIDATE STACK --------------------
  ////////////////////////////////////////////////////////

  This function validates that a category, value combination 
  representation of a stack passed in is a valid stack

  */
  def validateStack(category: String, value: Int):Boolean = {
    if((category.toLowerCase == "a" || category.toLowerCase == "s") && (value > 0 && value < 8)) {
      true
    }
    else {
      false
    }
  }

  /*
  ////////////////////////////////////////////////////
  -------------------- CHECK MOVE --------------------
  ////////////////////////////////////////////////////

  This function checks if 2 cards can be placed together based on their value, suit, and location

  */
  def checkMove(card1: Card, card2: Card, location: String): Boolean = {
    //Check if the card is being moved to a solitaire stack
    if(location.toLowerCase == "s") {
      //Check if the card values match up accoridng to solitaire rules
      if(card2.value == card1.value + 1) {
        //Check if the suits match up according to solitaire rules
        if((card1.suit == Suit.Hearts || card1.suit == Suit.Diamonds) && (card2.suit == Suit.Spades || card2.suit == Suit.Clubs)) {
          true
        }
        else if((card1.suit == Suit.Spades || card1.suit == Suit.Clubs) && (card2.suit == Suit.Hearts || card2.suit == Suit.Diamonds)) {
          true
        }
        else {
          false
        }
      }
      else if(card2.suit == Suit.None) {
          true
        }
      else {
        false
      }
    }
    //Check if the card is being moved to an ace stack
    else if (location.toLowerCase == "a") {
      //Check if the card values match up accoridng to solitaire rules
      if(card1.value == card2.value + 1) {
        //Check if the suits match up according to solitaire rules
        if(card1.suit == card2.suit) {
          true
        }
        else {
          false
        }
      }
      else if(card2.suit == Suit.None) {
        true
      }
      else {
        false
      }
    }
    else {
      println("ERROR IN CHECKMOVE")
      false
    }
  }

  /*
  ///////////////////////////////////////////////////////////////
  -------------------- CHECK IF TOP IS BLANK --------------------
  ///////////////////////////////////////////////////////////////

  This function takes a stack of cards and returns a boolean of 
  if the top card is a blank card.

  */
  def checkIfTopIsBlank(stack: Stack[Card]):Boolean = {
    //Check the values of the top card
    if(stack.top.value == -1 && stack.top.suit == Suit.None) {
      true
    }
    else {
      println("top card of stack is not empty")
      false
    }
  }

  /*
  /////////////////////////////////////////////////////////
  ---------------------------------------------------------
  -------------------- MOVING FUNCTIONS -------------------
  ---------------------------------------------------------
  /////////////////////////////////////////////////////////

  Functions that handle moving cards

  */

  /*
  ///////////////////////////////////////////////////
  -------------------- MOVE CARD --------------------
  ///////////////////////////////////////////////////

  This function moves a card from its current position to a new stack

  */
  def moveCard(cardToMove: Card, currentCardIndex: Int, newLocRow: String, newLocCol: Int) = {
    //Make a temporary card
    var card: Card = null
    //Check if the card is being moved to a solitaire stack
    if (newLocRow.toLowerCase == "s") {
      //Set the target stack to move the card to
      val targetStack = uncoveredStacks(newLocCol-1)
      //Check to make sure that this is a valiud move
      if(checkMove(Card(cardToMove.value, cardToMove.suit), targetStack.top, "s")) {
        //Check if card is a king and call respective functiomn
        if(cardToMove.value == 13) {
          handleKingToEmptyStack(newLocCol)
          card = cardToMove
          popCorrectStack(currentCardIndex)
        }
        //If the card exists, pop the correct stack with a helper function
        else if(currentCardIndex != -1) {
          card = popCorrectStack(currentCardIndex)
        }
        //Set the card to the card to move
        else {
          card = cardToMove
        }
        //Push the card
        targetStack.push(card)
      }
      else {
        println("Invalid move, please try again: moveCard push new card")
      }
    }
    //Check if the card is being moved to an ace stack
    else if (newLocRow.toLowerCase == "a") {
      val targetStack = aceStacks(newLocCol-1)
      if(checkMove(Card(cardToMove.value, cardToMove.suit), targetStack.top, "a")) {
          if(currentCardIndex != -1) {
            card = popCorrectStack(currentCardIndex)
          }
          else {
            card = cardToMove
          }
          targetStack.push(card)
      }
      else {
        println("Invalid move, please try again: moveCard push new card")
      }
    }
  }


  /*
  /////////////////////////////////////////////////////////////
  -------------------- MOVE MULTIPLE CARDS --------------------
  /////////////////////////////////////////////////////////////

  This function takes a main card to move, the current stack 
  that it is in (which contains the other cards to be moved 
  with it), and the stack to move to and moves the cards.

  */
  def moveMultipleCards(mainCardToMove: Card, currentStack: Stack[Card], stackToMoveTo: Stack[Card]) = {
    if(stackToMoveTo.isEmpty || checkIfTopIsBlank(stackToMoveTo)) {
      if(currentStack.size > 1) {
        //Get the cards to move with the targetcard and the stack that it is in
        val cardsSelected = getSubsetOfCardStack(mainCardToMove, currentStack)
        //Move those selected cards to the correct stack
        stackToMoveTo.pushAll(cardsSelected)
      }
      else {
        println("Please use the move command to accomplish this")
      }
    }
    else if(!checkIfTopIsBlank(stackToMoveTo) && checkMove(mainCardToMove, stackToMoveTo.top, "s")) {
      //Check is the stack has more than 1 card in it
      if(currentStack.size > 1) {
        //Get the cards to move with the targetcard and the stack that it is in
        val cardsSelected = getSubsetOfCardStack(mainCardToMove, currentStack)
        //Move those selected cards to the correct stack
        stackToMoveTo.pushAll(cardsSelected)
      }
      else {
        println("Please use the move command to accomplish this")
      }
    }
    else {
        println("That is an invalid move")
      }
  }

  /*
  ////////////////////////////////////////////////////////////
  ------------------------------------------------------------
  -------------------- MOVING HELPER FUNCTIONS ---------------
  ------------------------------------------------------------
  ////////////////////////////////////////////////////////////

  Functions that handle searching for/in different things

  */

  /*
  ///////////////////////////////////////////////////////////
  -------------------- POP CORRECT STACK --------------------
  ///////////////////////////////////////////////////////////

  This function pops the correct stack based off a given index and returns that card

  */
  def popCorrectStack(currentCardIndex: Int): Card = {
    var card: Card = null
    if(currentCardIndex < 7) {
      card = uncoveredStacks(currentCardIndex).pop()
    }
    else if (currentCardIndex == 11) {
      card = discardStack.pop
    }
    else if (currentCardIndex > 7) {
      card = aceStacks(currentCardIndex-7).pop()
    }

    if(card == null) {
      println("ERROR IN POPCORRECTSTACK")
    }
    card
  }

  /*
  //////////////////////////////////////////////////////////////////
  -------------------- GET SUBSET OF CARD STACK --------------------
  //////////////////////////////////////////////////////////////////

  This function takes a target card that needs to be moved and the 
  current stack it is in and returns a stack of all cards subsequent 
  (including) the target card.

  */
  def getSubsetOfCardStack(targetCard: Card, currentStack: Stack[Card]): Stack[Card] = {
    //Set temporary stack and temporary card
    val tmpStack = Stack[Card]()
    var card = Card(-1, Suit.None)
    //While the target card is not equal to the current card
    while(currentStack.top.value != targetCard.value) {
      card = currentStack.pop()
      tmpStack.push(card)
    }
    //Run one more time to pop the target card
    card = currentStack.pop()
    tmpStack.push(card)
    tmpStack
  }

  /*
  ////////////////////////////////////////////////////////////////////
  -------------------- HANDLE KING TO EMPTY STACK --------------------
  ////////////////////////////////////////////////////////////////////

  This function check to see if the stack that a king is being moved 
  to has a blank card on it. Ifit does, it pops the blank card in 
  perparation for the king to be pushed.

  */
  def handleKingToEmptyStack(newLocCol: Int) = {
    //get the stack to check
    val targetStack = uncoveredStacks(newLocCol-1)
    //check if the stack is empty
    if(targetStack.isEmpty) {
      println("ERROR 1 IN HANDLE KING TO EMPTY STACK")
    }
    //if the top card is blank
    else if(checkIfTopIsBlank(targetStack)) {
      targetStack.pop()

    }
    else {
      println("Error 2 in handleKingToEmptyStack")
    }

  }
  /*
  ////////////////////////////////////////////////////////////
  ------------------------------------------------------------
  -------------------- INPUT FUNCTIONS -----------------------
  ------------------------------------------------------------
  ////////////////////////////////////////////////////////////

  Functions that handle input from the user

  */
  
  /*
  //////////////////////////////////////////////////////
  -------------------- HANDLE INPUT --------------------
  //////////////////////////////////////////////////////

  This function moves handles all commands from the user

  */
  def handleInput(line: String) = {
    //print help commands
    if(line.toLowerCase == "help") {
      println("Commands:")
      println("draw -             draws 1 or 3 cards from the draw deck and places it face up in the discard deck to be used")
      println()
      println("move __ to __ -    allows you to move a card (KD, AS, 6C, JH, etc) to a specific pile (A1, S4, A3, S7, etc.) \n \t Example: \"move AS to A3\" moves the ace of spades (if it is in play) to the third ace pile. \n \t Example: \"move KD to S4\" moves the king of diamonds (if it is in play) to the fourth solitaire pile.")
      println()
      println("moveall __ to __ - allows you to move a card (KD, AS, 6C, JH, etc) and any subsequent cards in the pile to a specific pile (A1, S4, A3, S7, etc.) \n \t Example: \"move 10D to S4\" moves the 10 of diamonds (if it is in play) and any cards placed on top of the 10 of diamonds to the fourth solitaire pile. \n")
      println()
      println("q -                quit the game")
      println()
    }
    //draws a card from the deck and adds it face up to the discard pile
    else if(line.toLowerCase == "draw")
    {
      //check if the deck stack is empty, if so, push the discard stack to the deck stack, revcerse it, and clear the discard stack
      if(drawStack.isEmpty) {
        drawStack.pushAll(discardStack).reverse
        discardStack.clear()
        updateGame()
      }
      //Check if 3 cards are being flipped and if there are enough cards in the draw stack
      else if(cardsToFlip == 3 && drawStack.length >= 3) {
        var card1 = drawStack.pop
        discardStack.push(card1)
        var card2 = drawStack.pop
        discardStack.push(card2)
        var card3 = drawStack.pop
        discardStack.push(card3)
        updateGame()
      }
      //Check if 3 cards are being flipped and if there are not enough (2) cards in the draw stack
      else if(cardsToFlip == 3 && drawStack.length == 2) {
        var card1 = drawStack.pop
        discardStack.push(card1)
        var card2 = drawStack.pop
        discardStack.push(card2)
        updateGame()
      }
      //Check if 3 cards are being flipped and if there are not enough (1) cards in the draw stack
      else if(cardsToFlip == 3 && drawStack.length == 1) {
        var card1 = drawStack.pop
        discardStack.push(card1)
        updateGame()
      }
      //Check if 1 card is being flipped
      else if(cardsToFlip == 1) {
        discardStack.push(drawStack.pop)
        updateGame()
      }
      updateGame()
    }
    else if (line.split(" ").length < 4 ) {
      println("That is an invalid command. Type \"help\" for a list and description of valid commands")
    }
    //If the player wants to move multiple cards at once
    else if (line.toLowerCase().contains("moveall")) {
      val lineSubstring = line.split(" ")
      val cardToMove = lineSubstring(1)
      var cardToMoveSuit = ""
      var cardToMoveValue = -1
      var currentStack = Stack[Card]()
      val stackToMoveToString = lineSubstring(3)
      var stackToMoveToRow = ""
      var stackToMoveToColumn = -1
      var stackToMoveTo = Stack[Card]()
      var validCommand = false

      if(cardToMove.size == 3 && (validateCard(convertValueFromCard(cardToMove(0).toString + cardToMove(1).toString), cardToMove(2).toString))) {
        cardToMoveValue = convertValueFromCard(cardToMove(0).toString + cardToMove(1).toString)
        cardToMoveSuit = cardToMove(2).toString
        validCommand = true
      }
      else if ((validateCard(convertValueFromCard(cardToMove(0).toString), cardToMove(1).toString))) {
        cardToMoveValue = convertValueFromCard(cardToMove(0).toString)
        cardToMoveSuit = cardToMove(1).toString
        validCommand = true
      }
      currentStack = getStackFromCard(Card(cardToMoveValue, convertSuitForCard(cardToMoveSuit)))
      stackToMoveToColumn = convertValueFromCard(stackToMoveToString(1).toString)
      stackToMoveTo = uncoveredStacks(stackToMoveToColumn-1)
      if(cardToMoveValue == 13 && checkIfTopIsBlank(stackToMoveTo)) {
          stackToMoveTo.pop
      }
      if (validCommand) {
        moveMultipleCards(Card(cardToMoveValue, convertSuitForCard(cardToMoveSuit)), currentStack, stackToMoveTo)
        updateGame()
      }
      updateGame()
    }
    //If the player wants to move one card
    else if(line.toLowerCase().contains("move")) {
      val lineSubstring = line.split(" ")
      val cardToMove = lineSubstring(1)
      var cardToMoveSuit = ""
      var cardToMoveValue = -1
      val stackToMoveTo = lineSubstring(3)
      var stackToMoveToRow = ""
      var stackToMoveToColumn = -1
      var validCommand = false

      //If the card is a 10
      if(cardToMove.size == 3 
        && (validateCard(convertValueFromCard(cardToMove(0).toString + cardToMove(1).toString), cardToMove(2).toString))
        && (validateStack(stackToMoveTo(0).toString, convertValueFromCard(stackToMoveTo(1).toString))))
        {
          cardToMoveValue = convertValueFromCard(cardToMove(0).toString + cardToMove(1).toString)
          cardToMoveSuit = cardToMove(2).toString
          validCommand = true
        }
      //If the card is anything except a 10  
      else if ((validateCard(convertValueFromCard(cardToMove(0).toString), cardToMove(1).toString)) 
        && (validateStack(stackToMoveTo(0).toString, convertValueFromCard(stackToMoveTo(1).toString)))) 
        {
          cardToMoveValue = convertValueFromCard(cardToMove(0).toString)
          cardToMoveSuit = cardToMove(1).toString
          validCommand = true
        }
      stackToMoveToRow = stackToMoveTo(0).toString
      stackToMoveToColumn = convertValueFromCard(stackToMoveTo(1).toString)
      if (validCommand) {
        //check if card is actually in play
        val isCardInPlay = findCardOnTop(cardToMoveValue, cardToMoveSuit)
        val currentLoc = isCardInPlay._2
        if(isCardInPlay._1) {
          moveCard(Card(cardToMoveValue, convertSuitForCard(cardToMoveSuit)), currentLoc, stackToMoveToRow, stackToMoveToColumn)
          updateGame()
        }
        else {
          println("That card is not available")
        }
      }
    }
    //Error catching
    else {
      println("That is an invalid command. Type \"help\" for a list and description of valid commands")
    }
  }
}

//Make a networked interface that stores top scores, lowest moves, points, time, etc
//Make a database to store player information
//Make login page that connects with database
//Figure out how to display card representation in a web interface
//Push this network to a server (somewhere??? AWS, Heroku, Raspberry Pi)