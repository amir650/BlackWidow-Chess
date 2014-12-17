package com.chess.com.chess.pgn;

import java.util.Map;

import com.google.common.collect.ImmutableMap.Builder;

public class PGNGameTags {

    private final Map<String,String> gameTags;

    private PGNGameTags(final TagsBuilder builder) {
        this.gameTags = builder.gameTags.build();
    }

    @Override
    public String toString() {
        return this.gameTags.toString();
    }

    public static class TagsBuilder {

        final Builder<String,String> gameTags;

        public TagsBuilder() {
            this.gameTags = new Builder<>();
        }

        public TagsBuilder addTag(final String tagKey,
                              final String tagValue) {
            this.gameTags.put(tagKey, tagValue);
            return this;
        }

        public PGNGameTags build() {
            return new PGNGameTags(this);
        }

    }

}
