import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeTest {
    AbstractTicTacToe morpions;
    public static final int TAILLE = AbstractTicTacToe.BOARD_WIDTH;
    public static final int NB_CASES = AbstractTicTacToe.BOARD_WIDTH * AbstractTicTacToe.BOARD_HEIGHT;

    @BeforeEach
    public void setUp() {
        morpions = new TicTacToeModel();
    }

    @Test
    public void testInit() {
        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier doit jouer");
        testInvariant();
        // ----------------------
        // SÉQUENCE À COMPLÉTER
        // ----------------------
    }

    /**
     * Fonction à utiliser après chaque action, pour tester les conditions qui
     * doivent toujours être vraies
     */
    private void testInvariant() {
        assertTrue(morpions.numberOfRounds() >= 0, "Nombre de coups >= 0");
        assertTrue(morpions.numberOfRounds() <= NB_CASES, "Nombre de coups <= " + NB_CASES);
        // ----------------------
        // SÉQUENCE À COMPLÉTER
        // ----------------------
    }

    // On verifie que le joueur courant soit le bon
    @Test
    public void testGetJoueur() {
        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier joueur doit jouer");
        morpions.play(0, 1); // le premier a joué
        testInvariant();
        assertEquals(morpions.getTurn(), Owner.SECOND, "Le deuxième joueur doit jouer");
        morpions.play(1, 1); // le deuxième a joué
        testInvariant();
        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier joueur doit jouer");
        morpions.play(1, 2);
        assertEquals(morpions.getTurn(), Owner.SECOND, "Le deuxième joueur doit jouer");
        morpions.play(0, 2); // le deuxième a joué
        testInvariant();
        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier joueur doit jouer");


    }

    @Test
    public void testGetVainqueur() {
        //Le premier joueur doit gagner
        morpions.play(0, 0); // le premier a joué
        testInvariant();
        morpions.play(0, 1); // le deuxième a joué
        testInvariant();
        morpions.play(0, 2); // le premier a joué
        testInvariant();
        morpions.play(1, 0);
        testInvariant();
        morpions.play(1, 1);
        testInvariant();
        morpions.play(1, 2);
        testInvariant();
        morpions.play(2, 2);
        assertEquals(morpions.getWinner(), Owner.FIRST, "Le premier est le vainqueur");

        assertTrue(morpions.gameOver());
        //assertEquals(morpions.getTurn(), Owner.NONE);
    }

    /**
     * Scénario verifiant le premier coup joué, notamment:
     * position correcte ou non
     * non-fin de partie
     * identité du premier joueur
     */
    @Test
    public void testPremierCoup() {
        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier doit jouer");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                assertTrue(morpions.legalMove(i, j));
                morpions.play(i, j);
                testInvariant();

                if (i == 2 && j == 0) {
                    assertEquals(morpions.getWinner(), Owner.FIRST, "Le premier doit gagner.");
                    assertTrue(morpions.gameOver(), "La partie doit être terminé.");
                    break;
                } else {
                    assertFalse(morpions.legalMove(i, j));
                    assertFalse(morpions.gameOver());
                }
            }
        }
    }


    @Test
    public void testPartiePasFinie() {
        // Scénario explorant les situations non-fin de partie,
        // avec verificattion systématique de gameOver() == false
        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier doit jouer");

        morpions.play(0, 0);// 1
        assertFalse(morpions.gameOver());

        morpions.play(0, 1);// 2
        assertFalse(morpions.gameOver());

        morpions.play(0, 2);// 1
        assertFalse(morpions.gameOver());

        morpions.play(1, 0);// 2
        assertFalse(morpions.gameOver());

        morpions.play(1, 2);// 1
        assertFalse(morpions.gameOver());

        morpions.play(2, 2); // 2
        assertFalse(morpions.gameOver());

        morpions.play(1, 1); // 1
        assertFalse(morpions.gameOver());

        morpions.play(2, 0); // 2
        assertFalse(morpions.gameOver());

        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier doit jouer");


    }

    /**
     * Scénario tentant divers coups non autorisé,
     * avec verification systématique de legalMove()
     */
    @Test
    public void testControle() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == 2 && j == 2) {
                    break;
                } else {
                    morpions.play(i, j);
                    //assertFalse(morpions.gameOver(), "Le jeu n'est pas terminé");
                }
            }
        }
    }

    @Test
    public void testFinPartie() {
        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier doit jouer");

        // Scénario dans lequel personne ne gagne
        // mais la partie doit être terminé
        morpions.play(0, 1);// 1
        testInvariant();
        assertFalse(morpions.gameOver());

        morpions.play(0, 2);// 2
        testInvariant();
        assertFalse(morpions.gameOver());

        morpions.play(1, 1);// 1
        testInvariant();
        assertFalse(morpions.gameOver());

        morpions.play(2, 1);// 2
        testInvariant();
        assertFalse(morpions.gameOver());

        morpions.play(2, 2);// 1
        testInvariant();
        assertFalse(morpions.gameOver());

        morpions.play(0, 0); // 2
        testInvariant();
        assertFalse(morpions.gameOver());

        morpions.play(1, 2); // 1
        testInvariant();
        assertFalse(morpions.gameOver());

        morpions.play(1, 0); // 2
        testInvariant();
        assertFalse(morpions.gameOver());

        morpions.play(2, 0); // 1
        testInvariant();
        assertTrue(morpions.gameOver());
        assertEquals(morpions.getWinner(), Owner.NONE);

    }

    @Test
    public void testRestart() {
        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier doit jouer");

        morpions.play(1, 1);
        testInvariant();

        assertEquals(morpions.getSquare(1, 1), Owner.FIRST);
        morpions.play(0, 0);
        testInvariant();

        morpions.play(0, 1);
        testInvariant();

        morpions.play(2, 1);
        testInvariant();
        assertEquals(morpions.numberOfRounds(), 4);
        morpions.restart();
        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier doit jouer");
        assertEquals(morpions.numberOfRounds(), 0);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(morpions.getSquare(i, j), Owner.NONE);
            }
        }

    }

    @Test
    public void testNextPlayer() {
        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier joueur doit jouer");
        morpions.nextPlayer();
        assertEquals(morpions.getTurn(), Owner.SECOND, "Le deuxième joueur doit jouer");
    }

    @Test
    public void testPlay() {
        assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier joueur doit jouer.");
        assertTrue(morpions.legalMove(1, 1));
        morpions.play(1, 1);
        testInvariant();
        assertEquals(morpions.getSquare(1, 1), Owner.FIRST, "Le premier joueur doit jouer.");
        assertEquals(morpions.getTurn(), Owner.SECOND, "Le deuxième joueur doit jouer.");

    }
}
