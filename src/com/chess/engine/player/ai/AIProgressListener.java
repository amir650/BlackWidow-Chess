package com.chess.engine.player.ai;

// Import the functional interface for AI progress updates
@FunctionalInterface
public interface AIProgressListener {
    void onAIProgress(String progress);
}
