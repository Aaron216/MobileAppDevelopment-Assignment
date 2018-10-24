package au.edu.curtin.madassignment.Model;

class GameSchema {
    class PlayerTable {
        static final String NAME = "player";
        class Cols {
            static final String ROW_LOCATION = "row_location";
            static final String COL_LOCATION = "col_location";
            static final String CASH = "cash";
            static final String HEALTH = "health";
            static final String EQUIPMENT_MASS = "equipment_mass";
            static final String HAS_JADE_MONKEY = "has_jade_monkey";
            static final String HAS_ROADMAP = "has_roadmap";
            static final String HAS_ICE_SCRAPER = "has_ice_scraper";
        }
    }

    class PlayerItemTable {
        static final String NAME = "player_item";
        class Cols {
            static final String TYPE = "type";
            static final String DESCRIPTION = "description";
            static final String VALUE = "value";
            static final String MASS = "mass";
            static final String HEALTH = "health";
        }
    }

    class AreaTable {
        static final String NAME = "area";
        class Cols {
            static final String ROW_LOCATION = "row_location";
            static final String COL_LOCATION = "col_location";
            static final String IS_TOWN = "is_town";
            static final String DESCRIPTION = "description";
            static final String IS_STARRED = "is_starred";
            static final String IS_EXPLORED = "is_explored";

        }
    }

    class AreaItemTable {
        static final String NAME = "area_item";
        class Cols {
            static final String ROW_LOCATION = "row_location";
            static final String COL_LOCATION = "col_location";
            static final String TYPE = "type";
            static final String DESCRIPTION = "description";
            static final String VALUE = "value";
            static final String MASS = "mass";
            static final String HEALTH = "health";
        }
    }
}
