package json;

import characters.CharacterFactory;
import exceptions.InformationIncompleteException;
import grid.Cell;
import org.json.JSONArray;
import org.json.JSONObject;
import setup.Account;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONParser {
    private final List<Account> accountList;
    private final Map<Cell.CellType, List<String>> stories;

    public List<Account> getAccountList() {
        return accountList;
    }

    public Map<Cell.CellType, List<String>> getStories() {
        return stories;
    }

    public JSONParser() throws IOException, InformationIncompleteException {
        Path location = Path.of("src/json/input.json");
        //Path location = Path.of("input.json");
        System.out.println(location.toAbsolutePath());

        String json = Files.readString(location);
        JSONObject object = new JSONObject(json);
        JSONArray accounts = object.getJSONArray("accounts");

        accountList = new ArrayList<>(accounts.length());

        for(int i = 0; i < accounts.length(); i++){
            JSONObject curr_acc = accounts.getJSONObject(i);

            String country = curr_acc.getString("country");
            String email = curr_acc.getString("email");
            String password = curr_acc.getString("password");
            String name = curr_acc.getString("name");
            ArrayList<String> favoriteGames = new ArrayList<>();

            JSONArray favGames = curr_acc.getJSONArray("favGames");

            for(int j = 0; j < favGames.length(); j++){
                favoriteGames.add(favGames.getString(j));
            }

            ArrayList<characters.Character> characterArray = new ArrayList<>();
            CharacterFactory characterFactory = new CharacterFactory();
            JSONArray characters = curr_acc.getJSONArray("characters");

            for(int j = 0; j < characters.length(); j++){
                characterArray.add(characterFactory.createCharacter(characters.getString(j)));
            }

            accountList.add(new Account(
                    new Account.Information.InformationBuilder()
                            .country(country)
                            .credentials(email, password)
                            .favoriteGames(favoriteGames)
                            .name(name)
                            .build()
                    ,
                    characterArray
            ));
        }

        this.stories = new HashMap<>();
        JSONArray stories = object.getJSONArray("stories");
        for(int i = 0; i < stories.length(); i++){
            JSONObject currStory = stories.getJSONObject(i);
            String type = currStory.getString("type");
            JSONArray story = currStory.getJSONArray("storyArr");
            ArrayList<String> cellStories = new ArrayList<>();

            for(int j = 0; j < story.length(); j++){
                cellStories.add(story.getString(j));
            }

            switch (type) {
                case "N" -> this.stories.put(Cell.CellType.EMPTY, cellStories);
                case "S" -> this.stories.put(Cell.CellType.SHOP, cellStories);
                case "E" -> this.stories.put(Cell.CellType.ENEMY, cellStories);
            }
        }
    }
}
