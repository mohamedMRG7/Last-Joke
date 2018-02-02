package com.example.moham.lastjoke.Database;

import android.provider.BaseColumns;

/**
 * Created by moham on 1/29/2018.
 */

public class JokeContract {



    public static class JokeEntry implements BaseColumns {


        public static final String TABLE_NAME="jokes";



        public static final String COLUMN_USERNAME="username";
        public static final String COLUMN_EMAIL="email";
        public static final String COLUMN_USER_UNIQ_ID="user_uniq_id";
        public static final String COLUMN_USER_ICON="icon";
        public static final String COLUMN_JOKE="joke";
        public static final String COLUMN_HAPPYNUM="happy_num";
        public static final String COLUMN_SADNUM="sad_num";
        public static final String COLUMN_LANGUAGE="language";
    }


    class FollowersEntry implements BaseColumns
    {
        public static final String TABLE_NAME="follwers";



        public static final String COLUMN_USERNAME="username";
        public static final String COLUMN_EMAIL="email";
        public static final String COLUMN_USER_UNIQ_ID="user_uniq_id";
    }
}
