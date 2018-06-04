package edu.schoolapp;

public enum PrimaryMenuType {
    TODO {
        @Override
        public String toString() {
            return "Todo";
        }
    }, ASSIGNMENT {
        @Override
        public String toString() {
            return "Assignment";
        }
    }, WEBLINK {
        @Override
        public String toString() {
            return "Web Link";
        }
    }, MAIL {
        @Override
        public String toString() {
            return "Mail";
        }
    }, DRIVE {
        @Override
        public String toString() {
            return "Drive";
        }
    }, TIMETABLE {
        @Override
        public String toString() {
            return "Timetable";
        }
    }, CALENDAR {
        @Override
        public String toString() {
            return "Calendar";
        }
    }
}
