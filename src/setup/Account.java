package setup;

import exceptions.InformationIncompleteException;

import java.util.ArrayList;
import java.util.HashSet;

public class Account {
    final private Information information;
    final private ArrayList<characters.Character> characters;
    private int numberOfPlayedGames = 0;

    public Account(Information information, ArrayList<characters.Character> characters){
        this.information = information;
        this.characters = characters;
    }

    public ArrayList<characters.Character> getCharacters() {
        return characters;
    }

    public Information getInformation() {
        return information;
    }

    public int getNumberOfPlayedGames() {
        return numberOfPlayedGames;
    }

    public void incrementNumberOfPlayedGames(){
        numberOfPlayedGames++;
    }

    @Override
    public String toString() {
        return "Account:\n" +
                information +
                "\nCharacters: " + characters +
                "\nNumberOfPlayedGames: " + numberOfPlayedGames;
    }

    public static class Information{
        private final Credentials credentials;
        private final HashSet<String> favoriteGames;
        private final String name, country;

        public Information(InformationBuilder builder){
            credentials = builder.credentials;
            favoriteGames = builder.favoriteGames;
            name = builder.name;
            country = builder.country;
        }

        public String getName() {
            return name;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public HashSet<String> getFavoriteGames() {
            return favoriteGames;
        }

        public String getCountry() {
            return country;
        }

        @Override
        public String toString() {
            return  "Credentials:\n\t" + credentials +
                    "\nName: " + name +
                    "\nCountry: " + country +
                    "\nFavorite games: " + favoriteGames;
        }

        public static class InformationBuilder{
            private Credentials credentials;
            private HashSet<String> favoriteGames;
            private String name, country;

            public InformationBuilder(){ }

            public InformationBuilder credentials(String email, String password){
                credentials = new Credentials(email, password);
                return this;
            }

            public InformationBuilder favoriteGames(ArrayList<String> arr){
                favoriteGames = new HashSet<>(arr);
                return this;
            }

            public InformationBuilder name(String name){
                this.name = name;
                return this;
            }

            public InformationBuilder country(String country){
                this.country = country;
                return this;
            }

            public Information build() throws InformationIncompleteException {
                Information info = new Information(this);
                validation(info);
                return info;
            }

            private void validation(Information info) throws InformationIncompleteException {
                if(info.getName() == null || info.getCredentials() == null){
                    throw new InformationIncompleteException("Incomplete Information!");
                }
            }
        }
    }
}
