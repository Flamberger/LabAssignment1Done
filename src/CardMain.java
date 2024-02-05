public class CardMain {
    public static void main(String[] args) {
        Deck d = new Deck(true);
        System.out.println(d);


        Hand h = new Hand();
//        for(int i = 0; i<5; i++) {
//            h.addCard(d.deal());
//        }
        h.addCard(d.dealSpecificCard("10","Diamonds"));
        h.addCard(d.dealSpecificCard("Q","Diamonds"));
        h.addCard(d.dealSpecificCard("J","Diamonds"));
        h.addCard(d.dealSpecificCard("9","Diamonds"));
        h.addCard(d.dealSpecificCard("K","Diamonds"));

        System.out.println(h);
        System.out.println(h.getHandRank());
    }
}
