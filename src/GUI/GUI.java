package GUI;

import setup.Account;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
    JList<String> accountJList;
    public GUI(List<Account> accountList){
        super("World Of Marcel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int width = 800;
        int height = 700;

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

        // PANEL 3 - BUTTON
        JPanel panel3 = new JPanel(new FlowLayout());
        Button loginButton = new Button("Login!");
        loginButton.setPreferredSize(new Dimension(90,50));
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        panel3.add(loginButton);
        add(panel3);

        setVisible(true);
        pack();
    }
}
