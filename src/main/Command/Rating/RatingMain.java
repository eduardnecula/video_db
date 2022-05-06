package main.Command.Rating;

import fileio.Input;
import org.json.simple.JSONArray;

/**
 * clasa verifica daca poate sa aplice rating, iar in functie de raspuns
 * va crea obiecte necesare pe care le va pune in JSON ARRAY
 */
public class RatingMain {

    private Input input;
    private final int indexAction;
    private final JSONArray arrayResult;

    /**
     *
     * @param input de la fisier
     * @param indexAction id actiune
     * @param arrayResult JSON array unde pun rezultatele
     */
    public RatingMain(final Input input, final int indexAction,
                      final JSONArray arrayResult) {
        this.input = input;
        this.indexAction = indexAction;
        this.arrayResult = arrayResult;
    }

    /**
     * functia care verifica daca poate aplica rating
     * apoi creaza obiecte in functie de modul in care poate
     * sa aplice rating sau nu
     */
    public void rating() {
        String user = input.getCommands().get(indexAction).getUsername();
        String movie = input.getCommands().get(indexAction).getTitle();
        Rating rating = new Rating(input, user, movie, indexAction);
        rating.applyRating();
        if (rating.chechRating()) {
            if (!rating.checkSeen()) {
                RatStandardNotSeen st = new RatStandardNotSeen(input, indexAction,
                                movie);
                arrayResult.add(st);
            } else {
                if (checkIfRated(user, movie, indexAction) == 0) {
                    arrayResult.add(rating);
                } else {
                    RatAlreadyAplied ra = new RatAlreadyAplied(input,
                            indexAction, movie);
                    arrayResult.add(ra);
                }
            }
        }
    }

    /**
     * de cate ori se repeta user-ul si movie-ul in lista de actiuni
     * @param user nume user
     * @param movie nume film
     * @param actionIdAvoid id actiune de ocolit
     * @return
     */
    public int checkIfRated(final String user, final String movie,
                            final int actionIdAvoid) {
        int actionId = input.getCommands().get(actionIdAvoid).getActionId();
        int nrActions = input.getCommands().size();
        int nrSeasons = input.getCommands().get(indexAction).getSeasonNumber();
        int nrRepetari = 0;
        for (int i = 0; i < nrActions; i++) {
            String userInFront = input.getCommands().get(i).getUsername();
            String titleInFront = input.getCommands().get(i).getTitle();
            int actionIdCommand = input.getCommands().get(i).getActionId();
            if (userInFront != null && titleInFront != null && actionId
                    != actionIdCommand && actionId >= actionIdCommand) {
                if (user.equals(userInFront) && movie.equals(titleInFront)) {
                    if (nrSeasons < 1) {
                        nrRepetari++;
                    }
                }
            }
        }
        return nrRepetari;
    }

    /**
     *
     * @return intorc input-ul
     */
    public Input getInput() {
        return input;
    }

    /**
     * s
     * @param input setez input-ul
     */
    public void setInput(final Input input) {
        this.input = input;
    }
}
