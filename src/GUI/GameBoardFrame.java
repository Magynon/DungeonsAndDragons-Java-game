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
    private final Map<Cell.CellType, List<String>> stories;
    private final JLabel charStats;
    private final JLabel enemyStats;
    private JLabel inventoryStatus;
    private final JPanel GUIGrid;
    private final List<JLabel> labelList;
    private final JTextArea storyField;
    private Grid grid;
    private final JButton up;
    private final JButton down;
    private final JButton right;
    private final JButton left;
    private JButton addToCart, attack, ability, potion;
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
        charStats.setText("<html>Your life: " + character.getCurrentLife() + "<br>Your mana: " + character.getCurrentMana() + "</html>");
        charStatsPanel.add(charStats);

        JPanel enemyStatsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        enemyStats = new JLabel();
        enemyStats.setHorizontalAlignment(SwingConstants.RIGHT);
        enemyStats.setFont(new Font("Serif", Font.BOLD, 18));
        enemyStatsPanel.add(enemyStats);

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
                        case 'N' -> current.setForeground(Color.gray);
                        case 'E' -> {
                            icon = new ImageIcon("/home/magy/IdeaProjects/WorldOfMarcel/res/angry.png");
                            Image image = icon.getImage();
                            Image newImage = image.getScaledInstance(50, 50,  Image.SCALE_DEFAULT);
                            icon = new ImageIcon(newImage);
                            current.setIcon(icon);
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
                labelList.set(i*gridWidth + j, current);
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
        Enemy enemy = (Enemy) grid.getCurrentCell().getObj();
        charStats.removeAll();
        enemyStats.removeAll();
        if(character.getCurrentLife() > 0){
            charStats.setText("<html>Your life: " + character.getCurrentLife() + "<br>Your mana: " + character.getCurrentMana() + "</html>");
        }
        else{
            enemyStats.setText("");
            charStats.setText("");
            return;
        }
        if(enemy.getCurrentLife() > 0){
            enemyStats.setText("<html><p style=\"text-align:right;\">" + enemy.getCurrentLife() + " :enemy's life</p>" + enemy.getCurrentMana() + " :enemy's mana</html>");
        }
        else{
            enemyStats.setText("");
        }
    }

    private void showStory(Cell element){
        int length = stories.get(element.getType()).size();
        int index = new Random().nextInt(length);
        storyField.setText(stories.get(element.getType()).get(index));
    }

    public void currentCellAction(Grid grid) throws InterruptedException {
        System.out.print(grid);

        if(grid.getCurrentCell().getObj().toCharacter() == 'N' && !grid.getCurrentCell().visitedMoreThanOnce()){
            showStory(grid.getCurrentCell());
        }

        if(grid.getCurrentCell().getObj().toCharacter() == 'F'){
            JLabel performance = new JLabel(
                    character.getEnemiesDefeated() + " enemies defeated, " +
                    character.getInventory().getCoinNumber() + " coins gained, " +
                    character.getXp() + " experience, " +
                    character.getLevel() + " level reached.");
            dispose();

            JOptionPane.showConfirmDialog(
                    null,
                    performance,
                    "The end.",
                    JOptionPane.OK_CANCEL_OPTION
            );
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
            addToCart.setEnabled(character.getInventory().getCoinNumber() > 0 &&
                    character.getInventory().getCurrentWeight() < character.getInventory().getMaxWeight());
            addToCartPanel.add(addToCart);
            shoppingPanel.add(addToCartPanel);

            shoppingPanel.add(Box.createRigidArea(new Dimension(500, 20)));

            DefaultListModel<String> arrInventory = new DefaultListModel<>();
            potionInventoryJList = new JList<>(arrInventory);
            potionInventoryJList.setLayoutOrientation(JList.VERTICAL);
            potionInventoryJList.setFont(new Font("Arial", Font.BOLD, 15));
            JScrollPane scrollPaneInventory = new JScrollPane();
            scrollPaneInventory.setViewportView(potionInventoryJList);

            shoppingPanel.add(scrollPaneInventory);

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
                storyField.setText("""
                            YOUR TURN! -------------------------------
                            There are 3 options:
                            \t1. attack!
                            \t2. use ability!
                            \t3. use potion!""");

                JPanel combatChoices = new JPanel();
                attack = new JButton("Attack!");
                ability = new JButton("Ability!");
                potion = new JButton("Potion!");

                attack.addActionListener(this);
                ability.addActionListener(this);
                potion.addActionListener(this);

                combatChoices.add(attack);
                combatChoices.add(ability);
                combatChoices.add(potion);

                JOptionPane.showOptionDialog(
                        null,
                        combatChoices,
                        "Choose how to combat!",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null, new Object[]{}, null
                );

                // enemy's turn
                if(enemy.getCurrentLife() > 0){
                    storyField.setText("");
                    int chance = new Random().nextInt(4);
                    if(chance == 0){
                        Spell spell = enemy.getSpell();
                        if(spell == null){
                            storyField.setText("""
                                    ENEMY's TURN! -------------------------------
                                    Enemy attacked you!""");
                            character.receiveDamage(enemy.getDamage());
                            updateStats();
                        }
                        else{
                            enemy.useAbility(spell, character);
                            if(enemy.getCurrentMana() < spell.getMana()){
                                storyField.setText("""
                                        ENEMY's TURN! -------------------------------
                                        Enemy attacked you!""");
                                character.receiveDamage(enemy.getDamage());
                                updateStats();
                            }
                            else{
                                storyField.setText("""
                                        ENEMY's TURN! -------------------------------
                                        Enemy put a spell on you!""");
                                updateStats();
                            }
                        }
                    }
                    else{
                        storyField.setText("""
                                ENEMY's TURN! -------------------------------
                                Enemy attacked you!""");
                        character.receiveDamage(enemy.getDamage());
                        updateStats();
                    }
                }
            }
            storyField.removeAll();
            if(enemy.getCurrentLife() <= 0){
                int earnedCoins = enemy.onDeathReturnCoins();
                character.getInventory().earnCoins(earnedCoins);
                storyField.setText("Congratulations, the enemy was defeated" +
                        " and you won " + earnedCoins + " coins!");
                character.newEnemyDefeated();
            }
            else{
                storyField.setText("You have been defeated. The future is black.");
                up.setEnabled(false);
                down.setEnabled(false);
                left.setEnabled(false);
                right.setEnabled(false);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean goodMove = false;
        Character character = grid.getCharacter();

        if(addToCart == null){
            addToCart = new JButton();
        }

        Object source = e.getSource();
        if (up.equals(source)) {
            goodMove = grid.goNorth();
        } else if (down.equals(source)) {
            goodMove = grid.goSouth();
        } else if (left.equals(source)) {
            goodMove = grid.goWest();
        } else if (right.equals(source)) {
            goodMove = grid.goEast();
        } else if(addToCart.equals(source)){
            if(potionJList.isSelectionEmpty())
                return;

            DefaultListModel<String> modelShop = (DefaultListModel<String>) potionJList.getModel();
            DefaultListModel<String> modelInventory = (DefaultListModel<String>) potionInventoryJList.getModel();

            String potionToAdd = potionJList.getSelectedValue();
            int coins = character.getInventory().getCoinNumber();
            int weight = character.getInventory().getCurrentWeight();

            StringTokenizer tokenizer = new StringTokenizer(potionToAdd);
            tokenizer.nextToken(", ");
            tokenizer.nextToken(", ");
            weight += Integer.parseInt(tokenizer.nextToken(", "));
            tokenizer.nextToken(", ");
            coins -= Integer.parseInt(tokenizer.nextToken(", "));

            for(int i = 0; i < modelInventory.size(); i++){
                String potion = modelInventory.get(i);
                tokenizer = new StringTokenizer(potion);
                tokenizer.nextToken(", ");
                tokenizer.nextToken(", ");
                weight += Integer.parseInt(tokenizer.nextToken(", "));
                tokenizer.nextToken(", ");
                coins -= Integer.parseInt(tokenizer.nextToken(", "));
            }

            if(coins >= 0 && weight <= character.getInventory().getMaxWeight()){
                shoppingCartIndexList.add(potionJList.getSelectedIndex());
                modelInventory.add(modelInventory.size(), potionJList.getSelectedValue());
                inventoryStatus.removeAll();
                inventoryStatus.setText("Coins: " + coins +
                        ", max weight: " + character.getInventory().getMaxWeight() +
                        ", current weight: " + weight);
                modelShop.removeElementAt(potionJList.getSelectedIndex());
                potionJList.repaint();
            }

            addToCart.setEnabled(coins > 0 && weight < character.getInventory().getMaxWeight());
        } else if(attack.equals(source)){
            Enemy enemy = (Enemy) grid.getCurrentCell().getObj();
            enemy.receiveDamage(character.getDamage());
            storyField.setText("You attacked the enemy!");
            updateStats();
            JOptionPane.getRootFrame().dispose();
        } else if(ability.equals(source)){
            Enemy enemy = (Enemy) grid.getCurrentCell().getObj();
            Spell spell = character.getSpell();
            if(spell == null){
                storyField.setText("No spells left! Try attacking or a potion.");
            }
            else{
                character.useAbility(spell, enemy);
                updateStats();
                JOptionPane.getRootFrame().dispose();
            }
        } else if(potion.equals(source)){
            if(character.getInventory().getPotionNumber() == 0){
                storyField.setText("No potions available!");
            }
            else{
                Object[] potionList = new Object[character.getInventory().getPotionNumber()];

                for(int i = 0; i < character.getInventory().getPotionNumber(); i++){
                    potionList[i] = i + " " + character.getInventory().getPotion(i);
                }

                String potion = (String) JOptionPane.showInputDialog(
                        null,
                        "Choose a potion!",
                        "Use potion!",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        potionList,
                        "Pick one!"
                );

                StringTokenizer token = new StringTokenizer(potion);
                int potionIndex = Integer.parseInt(token.nextToken());

                storyField.setText("Chose " + character.getInventory().getPotion(potionIndex));
                character.usePotion(potionIndex);
                updateStats();
                JOptionPane.getRootFrame().dispose();
            }
        }

        if(goodMove){
            updateGrid();
            try {
                currentCellAction(grid);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            if(grid.getCurrentCell().getType() == Cell.CellType.ENEMY){
                updateStats();
            }
        }
        else{
            if(grid.getCurrentCell().getType() != Cell.CellType.SHOP && grid.getCurrentCell().getType() != Cell.CellType.ENEMY)
                storyField.setText("Wrong move, try again!");
        }
    }
}
