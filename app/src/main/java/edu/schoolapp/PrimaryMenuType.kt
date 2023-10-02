package edu.schoolapp

enum class PrimaryMenuType {
    TODO {
        override fun toString(): String {
            return "Todo"
        }
    },
    ASSIGNMENT {
        override fun toString(): String {
            return "Assignment"
        }
    },
    WEBLINK {
        override fun toString(): String {
            return "Web Link"
        }
    },
    MAIL {
        override fun toString(): String {
            return "Mail"
        }
    },
    DRIVE {
        override fun toString(): String {
            return "Drive"
        }
    },
    TIMETABLE {
        override fun toString(): String {
            return "Timetable"
        }
    },
    CALENDAR {
        override fun toString(): String {
            return "Calendar"
        }
    }
}