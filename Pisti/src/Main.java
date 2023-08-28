import java.util.Random;
import java.util.Scanner;

public class Main {
	private static Random rnd = new Random();
	private static Scanner sc = new Scanner(System.in);
	private static Score score = new Score();
	private static String[] cards;
	private static String[] computer;
	private static String[] human;
	private static String[] floor;
	private static String[] computerCards;
	private static String[] humanCards;
	private static String playerName;
	private static int computerCardNumber;
	private static int humanCardNumber;
	private static int computerPistiNumber;
	private static int humanPistiNumber;
	private static int floorCardNumbers;
	private static int remainedCardNumber;
	private static int lastWinner;

	public static void main(String[] args) {
		System.out.println("Welcome to Pisti game");
		score.ShowScoreBoard();
		StartTheGame();		
	}

	public static void CreateCards() {
		remainedCardNumber = 52;
		cards = new String[remainedCardNumber];
		for (int i = 0; i < 52; i++) {
			Integer number;
			String suit;
			String cardNum;
			if (0 <= i && i < 13) {
				number = i + 1;
				suit = "Spades ♠";
			} else if (13 <= i && i < 26) {
				number = i - 12;
				suit = "Club ♣";
			} else if (26 <= i && i < 39) {
				number = i - 25;
				suit = "Heart ♥";
			} else {
				number = i - 38;
				suit = "Diamond ♦";
			}
			if (number.equals(1))
				cardNum = "A";
			else if (number.equals(11))
				cardNum = "J";
			else if (number.equals(12))
				cardNum = "Q";
			else if (number.equals(13))
				cardNum = "K";
			else
				cardNum = number.toString();
			cards[i] = suit + " " + cardNum;
		}
	}

	public static void Shuffle() {
		for (int i = cards.length - 1; i >= 0; i--) {
			int index = rnd.nextInt(i + 1);
			String a = cards[index];
			cards[index] = cards[i];
			cards[i] = a;
		}
	}
	
	public static void Cut() {

		int cut = rnd.nextInt(52);
		String[] a = new String[cards.length];	

		for (int i = 0; i < cards.length; i++) {
			int newIndex = (i + cut) % cards.length;
			a[newIndex] = cards[i];
		}

		for(int i=0; i< a.length; i++) {
			cards[i] = a[i];
		}		
	}

	public static String[] CreateNewDeck() {
		String[] deck = new String[4];
		for (int i = 0; i < deck.length; i++) {
			deck[i] = ChooseCard();
		}
		return deck;
	}

	public static String ChooseCard() {
		String card = cards[remainedCardNumber - 1];
		remainedCardNumber--;
		return card;
	}

	public static void Compare(String thrown, boolean pc) {
		String onTheFloor = floorCardNumbers > 0 ? floor[floorCardNumbers - 1] : "";
		floor[floorCardNumbers] = thrown;
		floorCardNumbers++;
		String[] detail = thrown.split(" ");
		String[] detail2 = onTheFloor.split(" ");
		boolean pisti = floorCardNumbers == 2 && detail[2].equalsIgnoreCase(detail2[2]);
		if (onTheFloor.isEmpty() == false && (detail[2].equalsIgnoreCase(detail2[2]) || detail[2].equalsIgnoreCase("J"))) {
			if (pisti) {
				if (pc)
					computerPistiNumber++;
				else
					humanPistiNumber++;
				System.out.println("----------PİŞTİ----------");
			} else {
				for (int i = 0; i < floorCardNumbers; i++) {
					if (pc) {
						computerCards[computerCardNumber] = floor[i];
						computerCardNumber++;
					} else {
						humanCards[humanCardNumber] = floor[i];
						humanCardNumber++;
					}
				}
				System.out.println("--------------------");
			}
			lastWinner = pc ? 1 : 2;
			floor = new String[52];
			floorCardNumbers = 0;
		}
	}

	public static void ShowMyHand(String[] deck, boolean pc) {
		int a = 0;
		String output = "";
		for (int i = 0; i < deck.length; i++) {
			if (deck[i].isEmpty() == false) {
				a++;
				String ayrac = "";
				if (a > 1)
					ayrac = ", ";
				output += ayrac + (i + 1) + "-" + deck[i];
			}
		}
		int choose = a;
		if (pc == false) {
			System.out.println("Cards on your hand: " + output);
			System.out.print("Your choose: ");
			choose = sc.nextInt();
			
			while (choose < 1 || choose > 4 || deck[choose - 1].isEmpty()) {
				System.out.println("You should select the card by entering the number with it");
				System.out.print("Your choose: ");
				choose = sc.nextInt();
			}
		} else
			System.out.println("Computer played... " + deck[choose - 1]);
		Compare(deck[choose - 1], pc);
		deck[choose - 1] = "";
	}

	public static void Play(boolean pc) {
		System.out.println("Card number on the floor: " + floorCardNumbers);
		if (floorCardNumbers > 0)
			System.out.println("Last card on the floor: " + floor[floorCardNumbers - 1]);
		if (pc)
			ShowMyHand(computer, true);
		else
			ShowMyHand(human, false);
	}
	
	public static void DealTheCardsLeftOnTheFloor() {
		if (floorCardNumbers > 0) {
			for (int i = 0; i < floorCardNumbers; i++) {
				if (lastWinner == 1) {
					computerCards[computerCardNumber] = floor[i];
					computerCardNumber++;
				} else {
					humanCards[humanCardNumber] = floor[i];
					humanCardNumber++;
				}
			}
		}
	}

	public static void StartTheGame() {
		CreateCards();
		Shuffle();
		Cut();
		floor = new String[52];
		computerCards = new String[52];
		humanCards = new String[52];
		playerName = "Gamer";
		computerCardNumber = 0;
		humanCardNumber = 0;
		computerPistiNumber = 0;
		humanPistiNumber = 0;
		lastWinner = 2;
		String[] deck = CreateNewDeck();
		for (int i = 0; i < deck.length; i++) {
			floor[i] = deck[i];
		}
		floorCardNumbers = 4;
		while (remainedCardNumber > 0) {
			computer = CreateNewDeck();
			human = CreateNewDeck();
			for (int i = 0; i < 4; i++) {
				Play(true);
				Play(false);
			}
		}
		DealTheCardsLeftOnTheFloor();
		FinishTheGame();
	}

	public static void FinishTheGame() {
		System.out.print("\r\nPlease enter your name ");
		String name = sc.next();
		while (name == null || name.isEmpty()) {
			System.out.print("\r\nPlease enter your name");
			name = sc.next();
		}
		playerName = name;
		int ComputerPoint = computerPistiNumber * 10;
		int HumanPoint = humanPistiNumber * 10;
		for (int i = 0; i < computerCardNumber; i++) {
			String[] detail = computerCards[i].split(" ");
			if (detail[1].equalsIgnoreCase("♦") && detail[2].equalsIgnoreCase("10"))
				ComputerPoint += 3;
			else if (detail[1].equalsIgnoreCase("♣") && detail[2].equalsIgnoreCase("2"))
				ComputerPoint += 2;
			else
				ComputerPoint += 1;
		} 
		for (int i = 0; i < humanCardNumber; i++) {
			String[] detail = humanCards[i].split(" ");
			if (detail[1].equalsIgnoreCase("♦") && detail[2].equalsIgnoreCase("10"))
				HumanPoint += 3;
			else if (detail[1].equalsIgnoreCase("♣") && detail[2].equalsIgnoreCase("2"))
				HumanPoint += 2;
			else
				HumanPoint += 1;
		}
		computerCardNumber += computerPistiNumber * 2;
		humanCardNumber += humanPistiNumber * 2;
		if (computerCardNumber > humanCardNumber)
			ComputerPoint += 3;
		else if (computerCardNumber < humanCardNumber)
			HumanPoint += 3;
		System.out.println("Cards that left on the floor: " + floorCardNumbers);
		System.out.println("-----Computer-----");
		System.out.println("Point: " + ComputerPoint + ", Pişti Number: " + computerPistiNumber + ", Card number: "
				+ computerCardNumber + "\r\n");
		System.out.println("-----" + playerName + "-----");
		System.out.println("Point: " + HumanPoint + ", Pişti Number: " + humanPistiNumber + ", Card number: "
				+ humanCardNumber + "\r\n");
		System.out.print("Game result: ");
		if (HumanPoint == ComputerPoint)
			System.out.println("Draw");
		else if (HumanPoint > ComputerPoint) {
			System.out.println(playerName + " won");
			score.Write(playerName + " Skor " + HumanPoint);
		} else if (HumanPoint < ComputerPoint)
			System.out.println("Computer won");
		score.ShowScoreBoard();
	}
}


