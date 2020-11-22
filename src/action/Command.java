package action;

import entertainment.Video;
import main.Database;
import user.User;

public final class Command extends Action {
    private String username;
    private String title;
    private Double grade;
    private int season;

    public Command(final int actionId, final String type, final String actionType,
            final String user, final String title, final Double grade, final int season) {
        super(actionId, actionType, type);
        this.username = user;
        this.title = title;
        this.season = season;
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(final Double grade) {
        this.grade = grade;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(final int season) {
        this.season = season;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Executa comanda in functie de tipul comenzii si creeaza mesajul
     * instantei curente
     */
    public void execute(final Database db) {
        final User user = db.getUser(this.username);
        final Video show = db.getShow(this.title);

        if (this.getType().equals("view")) {
            this.message = user.viewVideo(this.title);
        } else if (this.getType().equals("favorite")) {
            this.message = user.addFavourite(show);
        } else {
            this.message = user.giveRating(show, this.grade, season);
        }
    }
}
