package GUI;

import grid.Cell;
import setup.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class GUI extends JFrame implements ActionListener {
    private final JList<String> accountJList;
    private final List<Account> accountList;
    private final JPanel panel3;
    Map<Cell.CellType, List<String>> stories;

    public GUI(List<Account> accountList, Map<Cell.CellType, List<String>> stories){
        super("World Of Marcel - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800;
        int height = 700;
        this.accountList = accountList;
        this.stories = stories;

        setMinimumSize(new Dimension(width, height));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // PANEL 1 - TITLE
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
        panel1.setMaximumSize(new Dimension((int) (width /1.2), height/4));

        JPanel emptyPanel = new JPanel();
        emptyPanel.setMinimumSize(new Dimension(width, height/3));
        panel1.add(Box.createRigidArea(new Dimension(width, height/4)));

        JLabel gameName = new JLabel("WORLD OF MARCEL");
        gameName.setFont(new Font("Serif", Font.BOLD, 50));
        JPanel helper1 = new JPanel();
        helper1.add(gameName);
        panel1.add(helper1);
        panel1.add(Box.createRigidArea(new Dimension(width, height/16)));

        JLabel text1 = new JLabel("Please choose an account:");
        JPanel helper2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        helper2.setMaximumSize(new Dimension(width/2, height));
        helper2.add(text1);
        panel1.add(helper2);
        panel1.add(Box.createRigidArea(new Dimension(width, 10)));

        add(panel1);

        // PANEL 2 - ACC LIST
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());

        DefaultListModel<String> arr = new DefaultListModel<>();

        for(int i = 0; i < accountList.size(); i++){
            arr.add(i, accountList.get(i).getInformation().getName());
        }

        accountJList = new JList<>(arr);
        accountJList.setLayoutOrientation(JList.VERTICAL);
        accountJList.setFont(new Font("Arial", Font.BOLD, 15));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(accountJList);
        scrollPane.setPreferredSize(new Dimension((int) (width /1.5), 200));
        panel2.add(scrollPane);
        add(panel2);

        // PANEL 3 - WRONG PASSWORD TEXT
        panel3 = new JPanel(new FlowLayout());
        JLabel wrongPass = new JLabel("Wrong password!");
        wrongPass.setForeground(Color.RED);
        panel3.add(wrongPass);
        panel3.setVisible(false);
        add(panel3);

        // PANEL 4 - BUTTON
        JPanel panel4 = new JPanel(new FlowLayout());
        Button loginButton = new Button("Login!");
        loginButton.setPreferredSize(new Dimension(90,50));
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.addActionListener(this);
        panel4.add(loginButton);
        add(panel4);

        setVisible(true);
        pack();
        setLocationRelativeTo(null);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(accountJList.isSelectionEmpty())
            return;

        String name = accountJList.getSelectedValue();
        for(Account account : accountList){
            if(account.getInformation().getName().equals(name)){
                System.out.println(account.getInformation().getCredentials().getEmail());
                final JFrame parent = new JFrame();
                String password = JOptionPane.showInputDialog(parent,
                        "Please input the password!", null);
                if(password.equals(account.getInformation().getCredentials().getPassword())){
                    Object[] charList = new Object[account.getCharacters().size()];

                    for(int i = 0; i < account.getCharacters().size(); i++){
                        charList[i] = i + " " + account.getCharacters().get(i).getName();
                    }
                    JFrame charFrame = new JFrame();
                    String character = (String) JOptionPane.showInputDialog(
                            charFrame,
                            "Choose a character!",
                            "Character input",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            charList,
                            "Pick one"
                    );

                    JTextField widthField = new JTextField(5);
                    JTextField heightField = new JTextField(5);

                    JPanel dimensions = new JPanel();
                    dimensions.add(new JLabel("Width: "));
                    dimensions.add(widthField);
                    dimensions.add(new JLabel("Height: "));
                    dimensions.add(heightField);

                    int gridWidth = 0;
                    int gridHeight = 0;

                    while(gridHeight * gridWidth < 8){
                        int res = JOptionPane.showConfirmDialog(
                                null,
                                dimensions,
                                "Grid dim's: WxH > 7!",
                                JOptionPane.OK_CANCEL_OPTION
                        );

                        if(res == JOptionPane.CANCEL_OPTION){
                            System.exit(0);
                        }

                        gridWidth = Integer.parseInt(widthField.getText());
                        gridHeight = Integer.parseInt(heightField.getText());
                        System.out.println("Width: " + gridWidth + "; height: " + gridHeight);
                    }

                    new GameBoardFrame(gridWidth, gridHeight, account.getCharacters().get(character.charAt(0)), stories);
                    dispose();
                }
                else{
                    System.out.println("Wrong password! Try again");
                    panel3.setVisible(true);
                }
            }
        }
    }

}
