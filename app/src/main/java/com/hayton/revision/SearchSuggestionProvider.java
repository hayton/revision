package com.hayton.revision;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by hayton on 26/9/2016.
 */
public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "com.hayton.revision.SearchSuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionProvider(){
        setupSuggestions(AUTHORITY, MODE);
    }

}
