package com.chess.tests;

import com.chess.pgn.*;
import com.google.common.io.Resources;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

/*
* Source of using Mockito with legacy JUnit4: https://mincong.io/2019/09/13/init-mock/
* Using mockito for void methods: https://www.baeldung.com/mockito-void-methods
* */

@RunWith(MockitoJUnitRunner.class)
public class TestPGNUtilities {

    @Test
    public void testPGNPersistenceWhenValidFilePersists() throws IOException {
        final var fileUrl = Resources.getResource("com/chess/tests/pgn/t1.pgn");
        final var testFile = new File(fileUrl.getFile());
        final var mockPersistence = mock(PGNPersistence.class);
        doNothing().when(mockPersistence).persistGame(isA(ValidGame.class));

        PGNUtilities.persistPGNFile(testFile, mockPersistence);

        verify(mockPersistence, times(1)).persistGame(isA(ValidGame.class));
    }

    @Test
    public void testPGNPersistenceWhenInvalidFileNotPersists() throws IOException {
        final var fileUrl = Resources.getResource("com/chess/tests/pgn/invalid.pgn");
        final var testFile = new File(fileUrl.getFile());
        final var mockPersistence = mock(PGNPersistence.class);

        PGNUtilities.persistPGNFile(testFile, mockPersistence);

        verify(mockPersistence, times(0)).persistGame(any(Game.class));
    }

    @Test
    public void testPGNPersistenceWhenPersistValidGameThrowsException() throws IOException {
        assertThrows(IOException.class, () -> {
            final var fileUrl = Resources.getResource("com/chess/tests/pgn/t1.pgn");
            final var testFile = new File(fileUrl.getFile());
            final var mockPersistence = mock(PGNPersistence.class);
            doThrow(IOException.class).when(mockPersistence).persistGame(isA(ValidGame.class));
            PGNUtilities.persistPGNFile(testFile, mockPersistence);
        });
    }
}
