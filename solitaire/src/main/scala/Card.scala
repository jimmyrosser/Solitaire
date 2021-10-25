class Card(providedValue: Int, providedSuit: Suit.Value) {
    val value = providedValue
    val suit = providedSuit
    //val color = setColor

    /*private def setColor: Color.Value = {
        if(this.suit == Suit.Spades) {
            Color.Black
        }
        else if (this.suit == Suit.Clubs) {
            Color.Black
        }
        else if (this.suit == Suit.Hearts) {
            Color.Red
        }
        else if (this.suit == Suit.Diamonds) {
            Color.Red
        }
        else {
            println("Card has no suit in Card class")
            Color.None
        }
    }*/

    def getValue: Int = {
        value
    }

    def getSuit: Suit.Value = {
        suit
    }

    /*def getColor: Color.Value = {
        color
    }*/
}

