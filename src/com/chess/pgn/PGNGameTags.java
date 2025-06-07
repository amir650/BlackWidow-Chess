package com.chess.pgn;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class PGNGameTags {

    private final Map<String, String> gameTags;

    PGNGameTags(final TagsBuilder builder) {
        this.gameTags = Collections.unmodifiableMap(new HashMap<>(builder.gameTags));
    }

    /**
     * Returns the value for a tag or a default if not present.
     */
    public String getTagOrDefault(String key, String defaultValue) {
        return this.gameTags.getOrDefault(key, defaultValue);
    }

    /**
     * Returns the raw tag map (unmodifiable).
     */
    public Map<String, String> getAllTags() {
        return this.gameTags;
    }

    @Override
    public String toString() {
        return this.gameTags.toString();
    }

    public static class TagsBuilder {

        private final Map<String, String> gameTags;

        public TagsBuilder() {
            this.gameTags = new HashMap<>();
        }

        public TagsBuilder addTag(final String tagKey, final String tagValue) {
            this.gameTags.put(tagKey, tagValue);
            return this;
        }

        public PGNGameTags build() {
            return new PGNGameTags(this);
        }
    }
}
