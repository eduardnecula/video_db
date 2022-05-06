package main.Recommandation;

import common.Constants;
import fileio.Input;
import main.Command.Rating.Rating;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationMain {

    private Input input;


    public RecommendationMain(final Input input) {
        this.input = input;
    }

    /**
     * Principala functie, care stabileste ce tip de recomandare are comanda,
     * apoi apeleaza cate o functie pentru fiecare tip in parte, iar aceste
     * functii vor crea obiecte ce se vor punt intr-un JSOn array
     * @param indexAction id actiune
     * @param arrayResult pentru afisare format JSON
     */
    public void recommendation(final int indexAction, final JSONArray arrayResult) {
        ArrayList<String> arrayList;
        String recommandation =
                input.getCommands().get(indexAction).getType();
        if (recommandation != null) {
            switch (recommandation) {
                case Constants.STANDARD:
                    standard(indexAction, arrayResult);
                    break;
                case Constants.BESTUNSEEN:
                    bestUnseen(indexAction, arrayResult);
                    break;
                case Constants.POPULAR:
                    popular(indexAction, arrayResult);
                    break;
                case Constants.FAVORITE:
                    favoriteRecommandation(indexAction, arrayResult);
                    break;
                case Constants.SEARCH:
                    arrayList = search(indexAction, arrayResult);
                    if (arrayList != null) {
                        arrayList = sortArrayList(sortArrayList(arrayList));
                    }
                    if (arrayList != null) {
                        String checkIfSearchError = arrayList.get(0);
                        if (checkIfSearchError != null
                                && checkIfSearchError.compareTo(Constants.NIMIC) == 0) {
                            RecSearchError searchError = new RecSearchError(input,
                                    indexAction);
                            arrayResult.add(searchError);
                        } else if (checkIfSearchError != null) {
                            RecSearch searchRecommendation =
                                    new RecSearch(input, indexAction,
                                            arrayList);
                            arrayResult.add(searchRecommendation);
                        }
                        break;
                    }
                default:
                    break;
            }
        }
    }

    /**
     *
     * @return nr de actiuni
     */
    public int getNrActions() {
        return input.getCommands().size();
    }
    /**
     *
     * @param index id user
     * @return nume utilizator
     */
    public String getUserAction(final int index) {
        return input.getCommands().get(index).getUsername();
    }
    /**
     *
     * @param index id actiune
     * @return numele filmului de la index
     */
    public String getTitle(final int index) {
        return input.getCommands().get(index).getTitle();
    }

    /**
     * @return intoarce rating-ul
     */
    public Map<String, String> getRating() {
        Map<String, String> map = new HashMap<>();
        int nrActions = getNrActions();
        for (int indexAction = 0; indexAction < nrActions; indexAction++) {
            String user = getUserAction(indexAction);
            String movie = getTitle(indexAction);
            Rating rating = new Rating(input, user, movie, indexAction);
            rating.applyRating();
            if (rating.chechRating()) {
                if (!rating.checkSeen()) {
                    map.put(user, movie);
                }
            }
        }
        return map;
    }

    /**
     * Functie ce se ocupa de categoria recommendation popular
     * @param indexAction id actiune
     * @param arrayResult pentru a pune obiecte in formal JSONArray
     */
    public void popular(final int indexAction, final JSONArray arrayResult) {
        RecPopular pr = new RecPopular(input,
                indexAction);

        //  daca un standard user cere ceva de la premium
        int nrUser = input.getCommands().get(indexAction).getNumber();
        String subscription =
                input.getUsers().get(nrUser).getSubscriptionType();
        if (subscription.compareTo(Constants.BASIC) == 0) {
            RecStandardInvalid si = new RecStandardInvalid(input, indexAction);
            arrayResult.add(si);
        } else {
            arrayResult.add(pr);
        }
    }

    /**
     * Functia sorteaza o lista primita
     * @param arrayList  lista de sortat
     * @return lista sortata
     */
    public ArrayList<String> sortArrayList(final ArrayList<String> arrayList) {
        if (arrayList != null) {
            Collections.sort(arrayList);
        }
        //  lista sortata
        assert arrayList != null;
        return new ArrayList<>(arrayList);
    }

    /**
     * @param indexAction id actiune
     * @param arrayResult pentru a pune obiecte in formal JSONArray
     */
    public void standard(final int indexAction, final JSONArray arrayResult) {
        RecStandard standardRecomandation =
                new RecStandard(input, indexAction,
                        getUserAction(indexAction));
        standardRecomandation.setMovie();
        if (standardRecomandation.checkStandard()) {
            arrayResult.add(standardRecomandation);
        }
    }
    /**
     * @param indexAction id actiune
     * @param arrayResult pentru a pune obiecte in formal JSONArray
     */
    public void bestUnseen(final int indexAction, final JSONArray arrayResult) {
        int nrComenzi = input.getCommands().size();
        int nrUsers = input.getUsers().size();
        String user = input.getCommands().get(indexAction).getUsername();
        String movieFinal = null;
        double max = Constants.MAX;
        if (user != null) {
            for (int i = 0; i < nrComenzi; i++) {
                String userFromCommands =
                        input.getCommands().get(i).getUsername();
                if (userFromCommands != null
                        && user.compareTo(userFromCommands) == 0) {
                    //  acum am acces doar la actiunile care au legatura cu
                    //  user-ul pe care il doreste problema
                    String movieWatchedCommand =
                            input.getCommands().get(i).getTitle();
                    //  iterez prin lista de filme ale userului si caut ce
                    //  filme nu am vazut
                    for (int j = 0; j < nrUsers; j++) {
                        //  iterez ca sa caut
                        //  filmele nevazute de user
                        String cautaUser =
                                input.getUsers().get(j).getUsername();
                        Map<String, Integer> movieWatchedUserHist =
                                input.getUsers().get(j).getHistory();
                        if (cautaUser != null && user.compareTo(cautaUser) == 0) {
                            for (String name : movieWatchedUserHist.keySet()) {
                                if (name != null) {
                                    Integer value =
                                            movieWatchedUserHist.get(name); // 1
                                    if (movieWatchedCommand != null
                                            && value != null
                                            && name.compareTo(movieWatchedCommand)
                                            != 0) {
                                        //  daca gasesc un film la user in
                                        //  comanda
                                        //  care este diferit de ce am eu
                                        //  iau grade-ul si il pun intr-un max
                                        if (value < max) {
                                            max = value;
                                            movieFinal = movieWatchedCommand;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        RecBestUnseen bestUnseen = new RecBestUnseen(input, movieFinal, indexAction,
                user);
        if (bestUnseen.checkValid()) {
            arrayResult.add(bestUnseen);
        }
    }

    /**
     *Caut un film in lista altor useri
     * Un film pe care nu l-am mai vazut
     * @param indexAction
     * @param arrayResult pentru a pune obiecte in formal JSONArray
     */
    public void favoriteRecommandation(final int indexAction,
                                       final JSONArray arrayResult) {

        String username = input.getCommands().get(indexAction).getUsername();
        if (username == null) {
            return;
        }
        //  trec prin toti userii
        int nrUseri = input.getUsers().size();
        //  nr de filme favorite
        ArrayList<String> listFavorite = null;
        for (int i = 0; i < nrUseri; i++) {
            String user = input.getUsers().get(i).getUsername();
            if (user == null) {
                continue;
            }
            //  daca gasesc user-ul, ma uit pe lista lui de history
            if (user.compareTo(username) == 0) {
                listFavorite = input.getUsers().get(i).getFavoriteMovies();
            }
        }
        // compar fiecare element din lista cu lista de favorite ale altora
        Map<String, Integer> map = new HashMap<>();
        //  trec prin toti userii, mai putin cel care da comanda
        for (int i = 0; i < nrUseri; i++) {
            String user = input.getUsers().get(i).getUsername();

            if (user == null) {
                continue;
            }
            if (user.compareTo(username) != 0) {
                //  ma uit in lista lui de favorite
                ArrayList<String> listaUserFav =
                        input.getUsers().get(i).getFavoriteMovies();
                if (listaUserFav == null) {
                    continue;
                }
                for (String movie : listaUserFav) {
                    if (movie == null) {
                        continue;
                    }
                    //  daca gasesc un film care sa nu se afle
                    //  in lista mea curenta
                    // trebuie o conditie
                    // ca acest film sa nu fie in istoric deja

                    assert listFavorite != null;
                    if (!listFavorite.contains(movie)) {
                        if (map.containsKey(movie)) {
                            int aux = map.get(movie);
                            aux++;
                            map.put(movie, aux);
                        } else {
                            map.put(movie, 1);
                        }
                    }
                }
            }

        }
        //  compar ce am in map cu lista de history a utilizatorului
        for (int i = 0; i < nrUseri; i++) {
            String user = input.getUsers().get(i).getUsername();
            if (user == null) {
                continue;
            }
            if (user.compareTo(username) == 0) {
                Map<String, Integer> history = input.getUsers().get(i).getHistory();
                if (history == null) {
                    continue;
                }
                for (Map.Entry<String, Integer> entry : history.entrySet()) {
                    map.remove(entry.getKey());
                }

            }
        }

        ArrayList<String> aux = new ArrayList<>(map.keySet());
        aux.sort(Collections.reverseOrder());
        String recommandation = null;
        if (aux.size() != 0) {
            recommandation = aux.get(aux.size() - 1);
        }
        if (recommandation != null)  {
            RecFavorite fr =
                    new RecFavorite(input, indexAction,
                            getUserAction(indexAction), recommandation);
            arrayResult.add(fr);
        } else {
            RecError sr = new RecError(input, indexAction);
            arrayResult.add(sr);
        }
    }
    /**
     * Functia cauta dupa genul actiunii si intoarce o lista cu rezultatul
     * cautarii
     * @param actionId id actiune
     * @param arrayResult pentru a pune obiecte in formal JSONArray
     */
    public ArrayList<String> search(final int actionId,
                                    final JSONArray arrayResult) {
        ArrayList<String> arrayList = new ArrayList<>();
        String username = input.getCommands().get(actionId).getUsername();
        String genre = input.getCommands().get(actionId).getGenre();
        if (username == null && genre == null) {
            return null;
        }
        switch (genre) {
            case Constants.ACTIONADVENTURE:
                arrayList = genreActionAdventure(username, genre);
                return arrayList;
            case Constants.ANIMATION:
                break;
            case Constants.SCIFI:
                searchGenre(genre, actionId, arrayResult);
                return null;
            default:
                arrayList.add(Constants.NIMIC);
                return arrayList;
        }
        return null;
    }

    /**
     * caut genul filmului pentru recomandate
     * @param actionId id actiune
     * @param arrayResult pentru a pune obiecte in formal JSONArray
     */
    void searchGenre(final String genre, final int actionId,
                     final JSONArray arrayResult) {
        //  am nevoie de lista history a lui user
        ArrayList<String> listaDorita = new ArrayList<>();
        String username = input.getCommands().get(actionId).getUsername();
        if (username == null) {
            return;
        }

        //  caut user-ul, pentru istoricul sau
        int nrUseri = input.getUsers().size();
        Map<String, Integer> history = new HashMap<>();
        for (int i = 0; i < nrUseri; i++) {
            String user = input.getUsers().get(i).getUsername();
            if (user == null) {
                continue;
            }
            if (user.compareTo(username) == 0) {
                history = input.getUsers().get(i).getHistory();
            }
        }
        ArrayList<String> list = new ArrayList<>(history.keySet());

        //  trebuie sa ma uit prin lista de filme pt genul dorit
        int nrFilme = input.getMovies().size();
        for (int i = 0; i < nrFilme; i++) {
            ArrayList<String> genreMovies =
                    input.getMovies().get(i).getGenres();
            if (genreMovies == null) {
                continue;
            }
            //  daca gasesc genul dorit
            if (genreMovies.contains(genre)) {
                //  voi compara filmul de aici
                String movie = input.getMovies().get(i).getTitle();
                if (movie == null) {
                    continue;
                }
                //  acest film trebuie sa il compar cu cel din
                //  lista de history a lui user
                if (!list.contains(movie)) {
                    listaDorita.add(movie);
                }
            }
        }

        if (listaDorita.isEmpty()) {
            RecSearchError sr = new RecSearchError(input, actionId);
            arrayResult.add(sr);
        } else {
            RecSearch sr = new RecSearch(input, actionId,
                    listaDorita);
            arrayResult.add(sr);
        }
    }
    /**
     *
     * @param userName nume utiliztor
     * @param genre genul filmului
     * @return
     */
    public ArrayList<String> genreActionAdventure(final String userName,
                                                  final String genre) {
        ArrayList<String> listaFilmeIntoarse = new ArrayList<>();
        int nrSerials = input.getSerials().size();
        for (int i = 0; i < nrSerials; i++) {
            List<String> listGenres = input.getSerials().get(i).getGenres();
            //  pentru fiecare gen, vad daca este ca al meu
            if (checkFindGendre(listGenres, genre)) {
                //  daca exista genul
                ArrayList<String> listaFilme =
                        getListSerialsWatched(userName);
                String nameSeries = input.getSerials().get(i).getTitle();
                if (listaFilme != null && nameSeries != null
                        && checkIfSerialExist(nameSeries, listaFilme)) {
                    //  daca serialul meu nu se afla in lista de filme vazute
                    listaFilmeIntoarse.add(nameSeries);
                }
            }
        }
        return listaFilmeIntoarse;
    }
    /**
     * in acesta lista caut sa vad daca genre exista
     * @param list lista primita
     * @param genre caut genul in lista
     * @return intorc true sau false daca gasesc
     */
    public boolean checkFindGendre(final List<String> list,
                                   final String genre) {
        for (String s : list) {
            if (genre.compareTo(s) == 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * Caut sa vad daca un serial dintr-o lista exista
     * @param title nume film
     * @param serials lista seriale
     * @return intorc true sau false daca un serial exista
     */
    public boolean checkIfSerialExist(final String title,
                                      final List<String> serials) {
        //  daca serialul exista in lista de seriale vazute
        for (String serial : serials) {
            if (title.compareTo(serial) == 0) {
                return false;
            }
        }
        return true;
    }
    /**
     *
     * @param user nume utilizator
     * @return
     * functia intoarce lista de seriale din comanzi
     * lista imi trebuie doar pentru un user specific
     */
    public ArrayList<String> getListSerialsWatched(final String user) {
        //  ma plimb prin toata lista de actiuni
        ArrayList<String> serialeDinComanda = new ArrayList<>();

        for (int i = 0; i < getNrActions(); i++) {
            //  acesta este user-ul din comanda
            String userCommand = input.getCommands().get(i).getUsername();
            //  acesta este titlul din comanda
            String titleCommand = input.getCommands().get(i).getTitle();
            if (titleCommand != null && userCommand != null && user.compareTo(userCommand) == 0) {
                serialeDinComanda.add(titleCommand);
            }
        }
        return serialeDinComanda;
    }
}
