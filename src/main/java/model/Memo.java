    package model;

    import java.util.Date;

    public class Memo {
        private String comment;
        private String userId;
        private Date date;

        public Memo(String comment, String userId, Date date) {
            this.comment = comment;
            this.userId = userId;
            this.date = date;
        }
    }
