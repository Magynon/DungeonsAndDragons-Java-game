# WorldOfMarcel

This project implements a story-based adventure game and showcases Java OOP principles. Most classes are identically created to the pattern offered as guide.
 - The Game class is the parent class for both GUI and CLI modes and holds useful game-related information, such as stories, accounts and methods for running the game.
 - The Account class holds the game-related information that belongs to an account, including its Information (Credentials, favorite games and personal information).
 - The Grid class holds the data belonging to the game grid, such as width height, traversal methods, generation method. The grid must have at least 2 shops and 4 enemies,
which was implemented with a Random object (as with many other features in the game). The grid itself is an ArrayList of ArrayLists (matrix) of Cells.
 - A Cell has coordinates, a type (Empty, Shop, Enemy or Finish) and a visited boolean, which helps with printing the grid and making sure a revisited cell doesn't behave
like a non-visited one.
 - The Entity class is parent for both Character and Enemy and has common attributes to both children, such as mana, life, protection to spells, spell list, methods for
regenerations, damage-received and damage-done computing methods.
 - The Character class is the parent class for Mage, Warrior and Rogue, which only differ by several characteristics such as the weight of the inventory, immunity to spells
and main attribute. It holds the name, the Inventory, game-related features (xp, current level), attributes, potion buying method.
 - The Enemy behaves like a character, only more rudimentary, as in its actions are randomized.
 - The Inventory holds the potions, the current number of coins, methods for adding and removing potions from the list.
 - The Potions are HealthPotion and ManaPotion, which both regenerate their element.
 - Inside the Shop, the Character can buy potions, however, he is limited by the current Inventory weight and number of coins left.

The data (account information and cell stories) is parsed from a JSON file with an open source package (github.com/stleary/JSON-java).
</br>
For the GUI, I decided to make it a bit more intuitive than specified, just to make sure I have a playable end-product. Most of it is a combination of JPanels, JFrames, JLabels,
JButtons, JOptionPanes and the grid itself is a JPanel with a GridLayout, containing JLabels, which in turn, hold images, which are rendered every time a move is made.
The login page lets the user select one account and then input the password. In case of a wrong password, the user is prompted to try again. Then, the user chooses the character
and inputs the desired dimensions of the game. The game then starts and shows information inside the story area.
</br>
Inside the shop, the character is only allowed to add to the cart as many objects as possible according to the inventory weight and coins remaining. During combat, in case of no spells
left or no mana left, the character is prompted to choose one of the remaining options.
</br>This project took somewhere around 30-40 hours of work, spread throughout a month (1 or more hours per day) and I found it to be really fun to write, in this way. As opposed
to non-OOP, this approach was more puzzle-like, as in pieces of code are written in the beginning, only to be assembled later on.
