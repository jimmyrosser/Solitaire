import scala.collection.mutable.Stack

class GameState(
        private val _coveredStacks: List[Stack[Card]],
        private val _uncoveredStacks: List[Stack[Card]],
        private val _aceStacks: List[Stack[Card]],
        private val _discardStack: Stack[Card],
        private val _drawStack: Stack[Card],
        private val _cardsToFlip: Int,
        private val _deck: List[Card],
        private val _deckStack: Stack[Card]) 
        {

        def this() {
                GameState(
                        List(Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card]()),
                        List(Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card]()),
                        List(Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card]()),
                        Stack[Card](),
                        Stack[Card](),
                        1,
                        Deck.shuffleDeck(Deck.shuffleDeck(Deck.generateDeck))
                        Stack[Card]().pushAll(deck)
                )
                /*
                //List of all coverd card stacks
                private val _coveredStacks: List[Stack[Card]] = List(Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card]())
                //List of all uncovered card stacks
                private val _uncoveredStacks: List[Stack[Card]] = List(Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card]())
                //List of all ace card stacks
                private val _aceStacks: List[Stack[Card]] = List(Stack[Card](), Stack[Card](), Stack[Card](), Stack[Card]())

                private val _discardStack = Stack[Card]()
                private val _drawStack = Stack[Card]()
                private var _cardsToFlip = 1

                //Generate and shuffle the deck, then push it all to a stack
                private val _deck = Deck.shuffleDeck(Deck.shuffleDeck(Deck.generateDeck))
                private val _deckStack: Stack[Card] = Stack[Card]()
                _deckStack.pushAll(deck)
                */
        }

        def coveredStacks = _coveredStacks
        def uncoveredStacks = _uncoveredStacks
        def aceStacks = _aceStacks
        def discardStack = _discardStack
        def drawStack = _drawStack
        def deck = _deck
        def deckStack = _deckStack
        def cardsToFlip = _cardsToFlip   
}
