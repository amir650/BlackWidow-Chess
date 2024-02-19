package com.chess.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// The parameterized test syntax for JUnit4 is different from JUnit5.
// In JUNit4, each parameterized test is represented as separate classes
// where in JUNit5, ParameterizedTest could be annotated multiple times in a
// single file. Please go to each of the classes inside the @Suite.SuiteClasses
// annotation to see the implementation of each parameterized test, which represents
// one partition each.
@RunWith(Suite.class)
@Suite.SuiteClasses({
    TestBishopPieceValidMoves.class,
    TestBishopPieceInvalidMoves.class,
    TestBishopPieceOOBMoves.class
})
public class TestBishopPiece {

}