package GUI;

import characters.Character;
import characters.Enemy;
import grid.Cell;
import grid.Grid;
import shop.Potion;
import shop.Shop;
import spells.Spell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class GameBoardFrame extends JFrame implements ActionListener {
    private final int gridWidth;
    private final int gridHeight;
    private final characters.Character character;
    private boolean won = false, notDead = true;
    private final Map<Cell.CellType, List<String>> stories;
    private Scanner keyboard;
    private JLabel charStats, enemyStats, inventoryStatus;
    private JPanel GUIGrid;
    private List<JLabel> labelList;
    private JTextArea storyField, shoppingCart;
    private Grid grid;
    private final JButton up;
    private final JButton down;
    private final JButton right;
    private final JButton left;
    private JButton addToCart;
    private JButton removeFromCart;
    private JList<String> potionJList, potionInventoryJList;
    private List<Integer> shoppingCartIndexList;

    public GameBoardFrame(int gridWidth, int gridHeight, characters.Character character, Map<Cell.CellType, List<String>> stories){
        super("World Of Marcel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800;
        int height = 700;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.character = character;
        this.stories = stories;

        setMinimumSize(new Dimension(width, height));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JPanel emptyPanel = new JPanel();
        emptyPanel.setMaximumSize(new Dimension(width, height/10));
        add(emptyPanel);

        JPanel stats = new JPanel();
        stats.setLayout(new BoxLayout(stats, BoxLayout.X_AXIS));
        stats.setMaximumSize(new Dimension((int) (width/1.5), height/10));

        JPanel charStatsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        charStats = new JLabel();
        charStats.setFont(new Font("Serif", Font.BOLD, 18));
        charStatsPanel.add(charStats);

        JPanel enemyStatsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        enemyStats = new JLabel();
        enemyStats.setHorizontalAlignment(SwingConstants.RIGHT);
        enemyStats.setFont(new Font("Serif", Font.BOLD, 18));
        enemyStatsPanel.add(enemyStats);

        updateStats();

        stats.add(charStatsPanel);
        stats.add(enemyStatsPanel);
        add(stats);

        GUIGrid = new JPanel(new GridLayout(gridHeight, gridWidth));
        GUIGrid.setMaximumSize(new Dimension((int) (width/1.3), (int) (height/1.7)));
        labelList = new ArrayList<>(gridHeight*gridWidth);
        add(GUIGrid);

        JPanel controls = new JPanel();
        controls.setMaximumSize(new Dimension((int) (width/1.3), height/4));

        // story label
        JPanel storyPlace = new JPanel(new FlowLayout(FlowLayout.LEFT));
        storyPlace.setMinimumSize(new Dimension((int) (width/1.3*0.7), height/5));
        storyField = new JTextArea(8, 35);
        storyField.setLineWrap(true);
        storyField.setEditable(false);
        storyField.setText("Story here");
        storyField.setMinimumSize(new Dimension((int) (width/1.3*0.7), height/5));
        storyField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        storyPlace.add(storyField);
        controls.add(storyPlace);

        // buttons
        JPanel buttonsContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsContainer.setMaximumSize(new Dimension(width/4, height/5));
        JPanel buttons = new JPanel(new GridLayout(3,3));

        Icon icon = new ImageIcon("/home/magy/IdeaProjects/WorldOfMarcel/res/angle-up.png");
        up = new JButton(icon);
        up.addActionListener(this);
        icon = new ImageIcon("/home/magy/IdeaProjects/WorldOfMarcel/res/angle-down.png");
        down = new JButton(icon);
        down.addActionListener(this);
        icon = new ImageIcon("/home/magy/IdeaProjects/WorldOfMarcel/res/angle-left.png");
        left = new JButton(icon);
        left.addActionListener(this);
        icon = new ImageIcon("/home/magy/IdeaProjects/WorldOfMarcel/res/angle-right.png");
        right = new JButton(icon);
        right.addActionListener(this);

        buttons.add(new JLabel());
        buttons.add(up);
        buttons.add(new JLabel());
        buttons.add(left);
        buttons.add(new JLabel());
        buttons.add(right);
        buttons.add(new JLabel());
        buttons.add(down);
        buttons.add(new JLabel());

        buttonsContainer.add(buttons);
        controls.add(buttonsContainer);

        add(controls);

        runGUI();

        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }
    public void runGUI(){
        // start game with account
        grid = Grid.getInstance();
        grid = grid.genMap(gridWidth, gridHeight, character);
        initLabelList();
        updateGrid();

        System.out.println(grid.showAllGrid());
        System.out.println(grid);

//        while(!won && notDead){
//            System.out.print("Choose direction to move (N, S, E or W): ");
//            String direction = "TODO direction";
//            nextMove(direction, grid);
//        }
//        grid.getCharacter().incLevel();
//        grid.getCharacter().updateTraitsWithLevel();
//
//        System.out.println("Goodbye!");
    }

    private void initLabelList(){
        for(int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                labelList.add(new JLabel());
            }
        }
    }

    private void updateGrid(){
        int length = stories.get(grid.getCurrentCell().getType()).size();
        int index = new Random().nextInt(length);
        storyField.setText(stories.get(grid.getCurrentCell().getType()).get(index));

        for(int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                JLabel current = new JLabel();
                if(grid.get(i).get(j).isVisited()){
                    ImageIcon icon;
                    char character = grid.get(i).get(j).getObj().toCharacter();

                    if(grid.getCurrentCell().getOx() == j && grid.getCurrentCell().getOy() == i){
                        current.setBackground(Color.PINK);
                        current.setOpaque(true);
                    }

                    switch(character){
                        case 'N' -> {
                            current.setForeground(Color.gray);
                        }
                        case 'E' -> {
                            icon = new ImageIcon("/home/magy/IdeaProjects/WorldOfMarcel/res/angry.png");
                            Image image = icon.getImage();
                            Image newImage = image.getScaledInstance(50, 50,  Image.SCALE_DEFAULT);
                            icon = new ImageIcon(newImage);
                            current.setIcon(icon);
                            //Image newImage = fitimage(image, 200, 200);

                        }
                        case 'S' -> {
                            icon = new ImageIcon("/home/magy/IdeaProjects/WorldOfMarcel/res/store.png");
                            Image image = icon.getImage();
                            Image newImage = image.getScaledInstance(50, 50,  Image.SCALE_DEFAULT);
                            icon = new ImageIcon(newImage);
                            current.setIcon(icon);
                        }
                        case 'F' -> {
                            icon = new ImageIcon("/home/magy/IdeaProjects/WorldOfMarcel/res/check.png");
                            Image image = icon.getImage();
                            Image newImage = image.getScaledInstance(50, 50,  Image.SCALE_DEFAULT);
                            icon = new ImageIcon(newImage);
                            current.setIcon(icon);
                        }
                    }
                }
                else{
                    ImageIcon icon = new ImageIcon("/home/magy/IdeaProjects/WorldOfMarcel/res/question.png");
                    Image image = icon.getImage();
                    Image newImage = image.getScaledInstance(50, 50,  Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newImage);
                    current.setIcon(icon);
                }
                current.setHorizontalAlignment(SwingConstants.CENTER);
                current.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
                labelList.set(i*gridHeight + j, current);
            }
        }
        GUIGrid.removeAll();
        GUIGrid.revalidate();
        GUIGrid.repaint();
        for(JLabel label : labelList){
            GUIGrid.add(label);
        }
    }

    private void updateStats(){
        charStats.removeAll();
        enemyStats.removeAll();
        charStats.setText("<html>Your life: " + character.getCurrentLife() + "<br>Your mana: " + character.getCurrentMana() + "</html>");
        enemyStats.setText("<html><p style=\"text-align:right;\">" + character.getCurrentLife() + " :enemy's life</p>" + character.getCurrentMana() + " :enemy's mana</html>");
    }

    private void showStory(Cell element){
        int length = stories.get(element.getType()).size();
        int index = new Random().nextInt(length);
        storyField.setText(stories.get(element.getType()).get(index));
    }

    public void currentCellAction(Grid grid) {
        System.out.print(grid);

        if(grid.getCurrentCell().getObj().toCharacter() == 'N' && !grid.getCurrentCell().visitedMoreThanOnce()){
            showStory(grid.getCurrentCell());
        }

        if(grid.getCurrentCell().getObj().toCharacter() == 'F'){
            won = true;
            System.exit(0);
        }

        if(grid.getCurrentCell().getObj().toCharacter() == 'S'){
            showStory(grid.getCurrentCell());
            Shop shop = (Shop) grid.getCurrentCell().getObj();
            List<Potion> potionList = shop.lookAround(true);

            shoppingCartIndexList = new ArrayList<>();

            JPanel shoppingPanel = new JPanel();
            shoppingPanel.setLayout(new BoxLayout(shoppingPanel, BoxLayout.Y_AXIS));

            JPanel inventoryStatusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            inventoryStatus = new JLabel();
            inventoryStatus.setText("Coins: " + character.getInventory().getCoinNumber() +
                    ", max weight: " + character.getInventory().getMaxWeight() +
                    ", current weight: " + character.getInventory().getCurrentWeight());
            inventoryStatusPanel.add(inventoryStatus);
            shoppingPanel.add(inventoryStatusPanel);

            DefaultListModel<String> arrShop = new DefaultListModel<>();
            for(int i = 0; i < potionList.size(); i++){
                arrShop.add(i, potionList.get(i).toString());
            }
            potionJList = new JList<>(arrShop);
            potionJList.setLayoutOrientation(JList.VERTICAL);
            potionJList.setFont(new Font("Arial", Font.BOLD, 15));
            JScrollPane scrollPaneShop = new JScrollPane();
            scrollPaneShop.setViewportView(potionJList);
            scrollPaneShop.setPreferredSize(new Dimension(500, 100));
            shoppingPanel.add(scrollPaneShop);

            shoppingPanel.add(Box.createRigidArea(new Dimension(500, 15)));

            JPanel addToCartPanel = new JPanel();
            addToCart = new JButton("Add to cart!");
            addToCart.addActionListener(this);
            addToCartPanel.add(addToCart);
            shoppingPanel.add(addToCartPanel);

            shoppingPanel.add(Box.createRigidArea(new Dimension(500, 20)));

            JPanel inventorySetup = new JPanel();

            JPanel inventoryList = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JPanel removeButton = new JPanel(new FlowLayout(FlowLayout.CENTER));

            DefaultListModel<String> arrInventory = new DefaultListModel<>();
            potionInventoryJList = new JList<>(arrInventory);
            potionInventoryJList.setLayoutOrientation(JList.VERTICAL);
            potionInventoryJList.setFont(new Font("Arial", Font.BOLD, 15));
            JScrollPane scrollPaneInventory = new JScrollPane();
            scrollPaneInventory.setViewportView(potionInventoryJList);
            inventoryList.add(scrollPaneInventory);

            removeFromCart = new JButton("X");
            removeFromCart.addActionListener(this);
            removeFromCart.setBackground(Color.red);
            removeFromCart.setForeground(Color.white);
            removeButton.add(removeFromCart);

            inventorySetup.add(inventoryList);
            inventorySetup.add(removeButton);

            shoppingPanel.add(inventorySetup);

            int res = JOptionPane.showConfirmDialog(
                            null,
                            shoppingPanel,
                            "Potion shopping",
                            JOptionPane.OK_CANCEL_OPTION
            );

            if(res == JOptionPane.OK_OPTION){
                for(int index : shoppingCartIndexList){
                    character.buyPotion(shop.removePotion(index));
                }
            }
            else{
                storyField.setText("Exited store!\n");
            }
        }

        if(grid.getCurrentCell().getObj().toCharacter() == 'E' && !grid.getCurrentCell().visitedMoreThanOnce()) {
            Character character = grid.getCharacter();
            Enemy enemy = (Enemy) grid.getCurrentCell().getObj();

            showStory(grid.getCurrentCell());
            while(enemy.getCurrentLife() > 0 && character.getCurrentLife() > 0){
                boolean actionDone = false;

                while(!actionDone){
                    storyField.setText("YOUR TURN! -------------------------------");
                    storyField.setText("""
                            There are 3 options:
                            \t1. attack!
                            \t2. use ability!
                            \t3. use potion!""");
                    System.out.print("Choose your move: ");
                    int index = keyboard.nextInt() - 1;
                    switch (index) {
                        case 0 -> {
                            enemy.receiveDamage(character.getDamage());
                            actionDone = true;
                        }
                        case 1 -> {
                            Spell spell = character.getSpell();
                            if(spell == null){
                                storyField.setText("No spells left! Try attacking or a potion.");
                                break;
                            }
                            if(spell.getMana() <= character.getCurrentMana()){
                                actionDone = true;
                            }
                            character.useAbility(spell, enemy);
                        }
                        case 2 -> {
                            if(character.getInventory().getPotionNumber() == 0){
                                storyField.setText("No potions available!");
                            }
                            else{
                                character.getInventory().showPotions();
                                System.out.print("Choose potion: ");
                                int potionIndex = keyboard.nextInt() - 1;
                                storyField.setText("Chose " + character.getInventory().getPotion(potionIndex));
                                character.usePotion(potionIndex);
                                actionDone = true;
                            }
                        }
                        default -> storyField.setText("Invalid choice, try again!");
                    }
                }

                // enemy's turn
                if(enemy.getCurrentLife() > 0){
                    storyField.setText("\nENEMY's TURN! -------------------------------");

                    int chance = new Random().nextInt(4);
                    if(chance == 0){
                        Spell spell = enemy.getSpell();
                        if(spell == null){
                            storyField.setText("Enemy attacked you!");
                            character.receiveDamage(enemy.getDamage());
                        }
                        else{
                            enemy.useAbility(spell, character);
                            if(enemy.getCurrentMana() < spell.getMana()){
                                storyField.setText("Enemy attacked you!");
                                character.receiveDamage(enemy.getDamage());
                            }
                            else{
                                storyField.setText("Enemy put a spell on you!");
                            }
                        }
                    }
                    else{
                        storyField.setText("Enemy attacked you!");
                        character.receiveDamage(enemy.getDamage());
                    }
                }
            }
            if(enemy.getCurrentLife() <= 0){
                int earnedCoins = enemy.onDeathReturnCoins();
                character.getInventory().earnCoins(earnedCoins);
                storyField.setText("Congratulations, the enemy was defeated" +
                        " and you won " + earnedCoins + " coins!");

                character.newEnemyDefeated();
            }
            else{
                storyField.setText("You have been defeated. The future is black.");
                notDead = false;
            }
            keyboard.nextLine();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean goodMove = false;

        Object source = e.getSource();
        if (up.equals(source)) {
            goodMove = grid.goNorth();
        } else if (down.equals(source)) {
            goodMove = grid.goSouth();
        } else if (left.equals(source)) {
            goodMove = grid.goWest();
        } else if (right.equals(source)) {
            goodMove = grid.goEast();
        } else if (addToCart.equals(source)){
            if(potionJList.isSelectionEmpty())
                return;
            DefaultListModel<String> modelShop = (DefaultListModel<String>) potionJList.getModel();
            shoppingCartIndexList.add(potionJList.getSelectedIndex());

            DefaultListModel<String> modelInventory = (DefaultListModel<String>) potionInventoryJList.getModel();
            modelInventory.add(modelInventory.size(), potionJList.getSelectedValue());

            int coins = character.getInventory().getCoinNumber();
            int weight = character.getInventory().getCurrentWeight();

            for(int i = 0; i < modelInventory.size(); i++){
                String potion = modelInventory.get(i);
                StringTokenizer tokenizer = new StringTokenizer(potion);
                tokenizer.nextToken(", ");
                tokenizer.nextToken(", ");
                weight += Integer.parseInt(tokenizer.nextToken(", "));
                tokenizer.nextToken(", ");
                coins -= Integer.parseInt(tokenizer.nextToken(", "));
            }

            addToCart.setEnabled(coins > 0 && weight < character.getInventory().getMaxWeight());

            inventoryStatus.removeAll();
            inventoryStatus.setText("Coins: " + coins +
                    ", max weight: " + character.getInventory().getMaxWeight() +
                    ", current weight: " + weight);

            modelShop.removeElementAt(potionJList.getSelectedIndex());
            potionJList.repaint();
        } else if (removeFromCart.equals(source)){
            if(potionJList.isSelectionEmpty())
                return;
            DefaultListModel<String> modelShop = (DefaultListModel<String>) potionJList.getModel();
            shoppingCartIndexList.remove(potionInventoryJList.getSelectedIndex());

            DefaultListModel<String> modelInventory = (DefaultListModel<String>) potionInventoryJList.getModel();
            modelShop.add(modelShop.size(), potionInventoryJList.getSelectedValue());

            int coins = character.getInventory().getCoinNumber();
            int weight = character.getInventory().getCurrentWeight();

            for(int i = 0; i < modelInventory.size(); i++){
                String potion = modelInventory.get(i);
                StringTokenizer tokenizer = new StringTokenizer(potion);
                tokenizer.nextToken(", ");
                tokenizer.nextToken(", ");
                weight += Integer.parseInt(tokenizer.nextToken(", "));
                tokenizer.nextToken(", ");
                coins -= Integer.parseInt(tokenizer.nextToken(", "));
            }

            addToCart.setEnabled(coins > 0 && weight < character.getInventory().getMaxWeight());

            inventoryStatus.removeAll();
            inventoryStatus.setText("Coins: " + coins +
                    ", max weight: " + character.getInventory().getMaxWeight() +
                    ", current weight: " + weight);

            modelInventory.removeElementAt(potionJList.getSelectedIndex());
            potionJList.repaint();
        }
        if(goodMove){
            updateGrid();
            currentCellAction(grid);
            updateStats();
        }
        else{
            if(grid.getCurrentCell().getType() != Cell.CellType.SHOP)
                storyField.setText("Wrong move, try again!");
        }
    }
}
