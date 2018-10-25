/*
Curtin University
Mobile Application Development
Assignment
Aaron Musgrave 25/10/2018

Game Schema Class
Responsible for storing constants for database schema
 */

package au.edu.curtin.madassignment.Database;

public class GameSchema {
    public class PlayerTable {
        public static final String NAME = "player";
        public class Cols {
            public static final String ID = "player_id";
            public static final String ROW_LOCATION = "row_location";
            public static final String COL_LOCATION = "col_location";
            public static final String CASH = "cash";
            public static final String HEALTH = "health";
            public static final String EQUIPMENT_MASS = "equipment_mass";
            public static final String HAS_JADE_MONKEY = "has_jade_monkey";
            public static final String HAS_ROADMAP = "has_roadmap";
            public static final String HAS_ICE_SCRAPER = "has_ice_scraper";
        }
    }

    public class PlayerItemTable {
        public static final String NAME = "player_item";
        public class Cols {
            public static final String ID = "p_item_id";
            public static final String TYPE = "type";
            public static final String DESCRIPTION = "description";
            public static final String VALUE = "value";
            public static final String MASS = "mass";
            public static final String HEALTH = "health";
        }
    }

    public class AreaTable {
        public static final String NAME = "area";
        public class Cols {
            public static final String ID = "area_id";
            public static final String ROW_LOCATION = "row_location";
            public static final String COL_LOCATION = "col_location";
            public static final String IS_TOWN = "is_town";
            public static final String DESCRIPTION = "description";
            public static final String IS_STARRED = "is_starred";
            public static final String IS_EXPLORED = "is_explored";

        }
    }

    public class AreaItemTable {
        public static final String NAME = "area_item";
        public class Cols {
            public static final String ID = "a_item_id";
            public static final String ROW_LOCATION = "row_location";
            public static final String COL_LOCATION = "col_location";
            public static final String TYPE = "type";
            public static final String DESCRIPTION = "description";
            public static final String VALUE = "value";
            public static final String MASS = "mass";
            public static final String HEALTH = "health";
        }
    }
}
