package com.chess.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestKnight.class,
                     TestBishop.class,
                     TestRook.class,
                     TestQueen.class,
                     TestBoard.class,
                     TestStaleMate.class,
                     TestPlayer.class,
                     TestCheckmate.class,
                     TestMiniMax.class,
                     TestAlphaBeta.class,
                     TestCastling.class,
                     TestPawn.class,
                     TestPawnStructure.class,
                     TestFENParser.class
                     /*TestPGNParser.class*/})
public class ChessTestSuite {
}
