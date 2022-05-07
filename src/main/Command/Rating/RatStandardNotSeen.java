package main.Command.Rating;

import fileio.Input;

/**
 * The class is useful when I receive the rating order for a movie
 * which I did not see, in which case I display an error message
 */
public class RatStandardNotSeen {
    private final Input input;
    private final int index;
    private final String movieToRecommend;

    /**
     *
     * @param input from file
     * @param index id action
     * @param movie movie to add
     */
    public RatStandardNotSeen(final Input input, final int index,
                              final String movie) {
        this.input = input;
        this.index = input.getCommands().get(index).getActionId();
        this.movieToRecommend = movie;
    }

    /**
     *
     * @return input
     */
    public Input getInput() {
        return input;
    }

    /**
     *
     * @return action id
     */
    public int getIndex() {
        return index;
    }

    /**
     *
     * @return it will be displayed in JSON array
     */
    @Override
    public String toString() {
        return "{" + "\"id\"" + ":" + index + ","
                + "\"message\"" + ":"
                + "\"error -> " + movieToRecommend
                + " is not seen" + "\"" + "}";
    }
}
