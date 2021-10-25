/*class Deck {
    val deck: List[Card] = List()
}*/
import scala.util.Random

object Deck {
    def generateDeck: List[Card] = {
        var deck: List[Card] = List()
        for(s <- Suit.values.filter(p => p != Suit.None)) {
            for(v <- 1 to 13) {
                val newCard = new Card(v, s)
                //println("New card added! Suit: " + s + " Value: " + v)
                deck = newCard :: deck
                //println(deck.size)
            }
        }
        deck
    }

    def shuffleDeck(deck: List[Card]): List[Card] = {
        val shuffledDeck = Random.shuffle(deck)
        shuffledDeck
    }
}
