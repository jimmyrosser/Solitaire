object Suit extends Enumeration {
  type Suit = Value
  val Spades, Clubs, Hearts, Diamonds, None = Value

  /*override def toString(suit: Suit.Value):String = {
    if(suit == Suit.Clubs) {
      "clubs"
    }
    else if(suit == Suit. Spades) {
      "spades"
    }
    else if(suit == Suit.Hearts) {
      "hearts"
    }
    else if(suit == Suit.Diamonds) {
      "diamonds"
    }
    else {
      "none"
    }
  }*/
}
