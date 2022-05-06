package utils;

import actor.ActorsAwards;
import common.Constants;
import entertainment.Genre;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * The class contains static methods that helps with parsing.
 *
 * We suggest you add your static methods here or in a similar class.
 */
public final class Utils {
    /**
     * for coding style
     */
    private Utils() {
    }

    /**
     * Transforms a string into an enum
     * @param genre of video
     * @return an Genre Enum
     */
    public static Genre stringToGenre(final String genre) {
        switch (genre.toLowerCase()) {
            case "action":
                return Genre.ACTION;
            case "adventure":
                return Genre.ADVENTURE;
            case "drama":
                return Genre.DRAMA;
            case "comedy":
                return Genre.COMEDY;
            case "crime":
                return Genre.CRIME;
            case "romance":
                return Genre.ROMANCE;
            case "war":
                return Genre.WAR;
            case "history":
                return Genre.HISTORY;
            case "thriller":
                return Genre.THRILLER;
            case "mystery":
                return Genre.MYSTERY;
            case "family":
                return Genre.FAMILY;
            case "horror":
                return Genre.HORROR;
            case "fantasy":
                return Genre.FANTASY;
            case "science fiction":
                return Genre.SCIENCE_FICTION;
            case "action & adventure":
                return Genre.ACTION_ADVENTURE;
            case "sci-fi & fantasy":
                return Genre.SCI_FI_FANTASY;
            case "animation":
                return Genre.ANIMATION;
            case "kids":
                return Genre.KIDS;
            case "western":
                return Genre.WESTERN;
            case "tv movie":
                return Genre.TV_MOVIE;
            default:
                return null;
        }
    }

    /**
     * Transforms a string into an enum
     * @param award for actors
     * @return an ActorsAwards Enum
     */
    public static ActorsAwards stringToAwards(final String award) {
        switch (award) {
            case "BEST_SCREENPLAY":
                return ActorsAwards.BEST_SCREENPLAY;
            case "BEST_SUPPORTING_ACTOR":
                return ActorsAwards.BEST_SUPPORTING_ACTOR;
            case "BEST_DIRECTOR":
                return ActorsAwards.BEST_DIRECTOR;
            case "BEST_PERFORMANCE":
                return ActorsAwards.BEST_PERFORMANCE;
            case "PEOPLE_CHOICE_AWARD":
                return ActorsAwards.PEOPLE_CHOICE_AWARD;
            default:
                return null;
        }
    }

    /**
     * Transforms an array of JSON's into an array of strings
     * @param array of JSONs
     * @return a list of strings
     */
    public static ArrayList<String> convertJSONArray(final JSONArray array) {
        if (array != null) {
            ArrayList<String> finalArray = new ArrayList<>();
            for (Object object : array) {
                finalArray.add((String) object);
            }
            return finalArray;
        } else {
            return null;
        }
    }

    /**
     * Transforms an array of JSON's into a map
     * @param jsonActors array of JSONs
     * @return a map with ActorsAwardsa as key and Integer as value
     */
    public static Map<ActorsAwards, Integer> convertAwards(final JSONArray jsonActors) {
        Map<ActorsAwards, Integer> awards = new LinkedHashMap<>();

        for (Object iterator : jsonActors) {
            awards.put(stringToAwards((String) ((JSONObject) iterator).get(Constants.AWARD_TYPE)),
                    Integer.parseInt(((JSONObject) iterator).get(Constants.NUMBER_OF_AWARDS)
                            .toString()));
        }

        return awards;
    }

    /**
     * Transforms an array of JSON's into a map
     * @param movies array of JSONs
     * @return a map with String as key and Integer as value
     */
    public static Map<String, Integer> watchedMovie(final JSONArray movies) {
        Map<String, Integer> mapVideos = new LinkedHashMap<>();

        if (movies != null) {
            for (Object movie : movies) {
                mapVideos.put((String) ((JSONObject) movie).get(Constants.NAME),
                        Integer.parseInt(((JSONObject) movie).get(Constants.NUMBER_VIEWS)
                                .toString()));
            }
        } else {
            System.out.println("NU ESTE VIZIONAT NICIUN FILM");
        }

        return mapVideos;
    }
}
