package com.chess.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({TestCheckmate.class, TestStaleMate.class, TestBoard.class, TestPlayer.class, TestMiniMax.class,
                     TestAlphaBeta.class, TestKnight.class, TestPawn.class, TestBishop.class, TestRook.class, TestQueen.class,
                     TestCastling.class})
public class ChessTestSuite {


}
