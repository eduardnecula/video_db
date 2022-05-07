package main.Command.Rating;

import fileio.Input;
import org.json.simple.JSONArray;

/**
 * the class checks if it can apply rating, and depending on the answer
 * will create necessary objects that it will put in JSON ARRAY
 */
public class RatingMain {

    private Input input;
    private final int indexAction;
    private final JSONArray arrayResult;

    /**
     *
     * @param input from file
     * @param indexAction action id
     * @param arrayResult JSON array where I put the results
     */
    public RatingMain(final Input input, final int indexAction,
                      final JSONArray arrayResult) {
        this.input = input;
        this.indexAction = indexAction;
        this.arrayResult = arrayResult;
    }

    /**
     * the function that checks if it can apply rating
     * then create objects as you can
     * apply rating or not
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
     * how many times the user and movie are repeated in the action list
     * @param user user name
     * @param movie movie name
     * @param actionIdAvoid action id to avoid
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
     * @return input
     */
    public Input getInput() {
        return input;
    }

    /**
     * s
     * @param input input
     */
    public void setInput(final Input input) {
        this.input = input;
    }
}
