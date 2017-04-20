import java.lang.Math;

/**
 * Purpose: MonteCarlo simulation of Crown and Anchor.
 * User inputs the number of simulations like so -> java CrownAndAnchor posInt
 * posInt -> number of games to be played. Should be huge for good sample
 *
 * Hard coded the bets to be between 1 - 1000 b/c as long as it's positive, it
 * does not matter.
 *
 * Input: Number of simulations
 *
 * Output: The analysis that the player will always end up w/ roughly 92%
 * of the amount that the player bets over x games. After many simulations,
 * the game favors the house, not the player.
 *
 * @author NICHOLAS SUNGA
 */
public class CrownAndAnchor {
    public static void main(String[] args) {
        int numberOfGames = Integer.valueOf(Integer.parseInt(args[0]));

        int bet = (int) Math.floor((Math.random() * 1000)) + 1;
        int costOfBets = 0;
        int[] diceThrow = new int[3];
        int winningSide = 0;

        int balance = 0;

        for (int i = 0; i < numberOfGames; i++) {
            costOfBets = costOfBets + bet;
            diceThrow[0] = (int) Math.floor((Math.random() * 6)) + 1;
            diceThrow[1] = (int) Math.floor((Math.random() * 6)) + 1;
            diceThrow[2] = (int) Math.floor((Math.random() * 6)) + 1;
            winningSide = (int) Math.floor((Math.random() * 6)) + 1;
            balance = balance + winCondition(diceThrow, winningSide, bet);
        }

        double percentage = ((double)balance / (double)costOfBets) * 100;
        System.out.print("Percent of what the player keeps in relation to how much they bet: ");
        System.out.println(percentage);
    }

    /**
     * Returns the amt gained if the player wins
     *
     * @param diceThrow -> 3 dice per game
     * @param winningSide -> the side that determines a win. Hard coded to be
     *                       random
     * @param bet -> the amount that was bet
     * @return int -> the amount won after a game
     */
    public static int winCondition(int[] diceThrow, int winningSide, int bet) {
        int matches = 0;

        for (int i = 0; i < diceThrow.length; i++) {
            if (diceThrow[i] == winningSide) {
                matches = matches + 1;
            }
        }

        return (matches == 0) ? 0 : (bet + bet * matches);
    }
}
