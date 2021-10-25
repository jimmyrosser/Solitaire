import scala.collection.mutable.Stack

/*class GameState(
        coveredStacks: List[Stack[Card]], 
        uncoveredStacks: List[Stack[Card]], 
        aceStacks: List[Stack[Card]], 
        discardStack: Stack[Card], 
        drawStack: Stack[Card], 
        cardsToFlip: Int, 
        deck: Deck,
        deckStack: Stack[Card]) {
        //List of all coverd card stacks
        private var _coveredStacks: List[Stack[Card]] = List(Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card]())
        //List of all uncovered card stacks
        private var _uncoveredStacks: List[Stack[Card]] = List(Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card]())
        //List of all ace card stacks
        private var _aceStacks: List[Stack[Card]] = List(Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card]())

        private val _discardStack = Stack[Card]()
        private val _drawStack = Stack[Card]()
        private var _cardsToFlip = 1

        //Generate and shuffle the deck, then push it all to a stack
        private val _deck = Deck.shuffleDeck(Deck.shuffleDeck(Deck.generateDeck))
        private val _deckStack: Stack[Card] = Stack[Card]()
        _deckStack.pushAll(deck)

        def coveredStacks = _coveredStacks
        def uncoveredStacks = _uncoveredStacks
        def aceStacks = _aceStacks
        def discardStack = _discardStack
        def drawStack = _drawStack
        def deck = _deck
        def deckStack = _deckStack
        def cardsToFlip = _cardsToFlip


        
}*/
