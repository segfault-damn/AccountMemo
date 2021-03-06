package main.ui;

import main.model.Account;
import main.persistence.Reader;
import main.persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AccountUi implements ActionListener, KeyListener {

    private static final String ACCOUNT_FILE = "./data/accounts.txt";

    private JFrame accountFrame;
    private JPanel menu;
    private JPanel viewPanel;
    private JPanel starPanel;
    private JPanel settingPanel;

    private HashMap<String, Account> accountMap;

    private JTextField nameField;
    private JTextField accountField;
    private JTextField passwordField;
    private JTextArea searchArea;
    private JTextArea viewArea;
    private JScrollPane scrollPane;

    private JLabel message;

    private JButton view;
    private JButton addConfirm;
    private JButton star;
    private JButton settingButton;

    private JButton viewBack;
    private JButton starBack;
    private JButton settingBack;

    private JButton delete;
    private JButton setStar;
    private JButton removeStar;
    private JButton searchButton;

    private Box box;
    private Icon starImage;
    private Icon viewImage;
    private Icon backImage;
    private Icon settingImage;
    private Icon setStarImage;
    private Icon removeStarImage;
    private Icon deleteImage;
    private Icon searchImage;


    public AccountUi() {
        accountFrame = new JFrame("Account Memo");
        loadAccounts();

        accountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accountFrame.setLayout(null);
        accountFrame.setBounds(0,0,1000,750);

        loadImage();
        initializePanel();
        initializeStar();
        initializeView();
        initializeSetting();
        accountFrame.setContentPane(menu);

        accountFrame.setResizable(false);
        accountFrame.setVisible(true);

        saveAccounts();
    }


    private void loadAccounts() {
        try {
            accountMap = new HashMap<>();

            Map<String,Account> accounts = Reader.read(new File(ACCOUNT_FILE));

            accountMap = (HashMap<String, Account>) accounts;


        } catch (IOException e) {
            accountMap = new HashMap<>();
        }
    }


    private void saveAccounts() {
        try {

            Writer accountWriter = new Writer(new File(ACCOUNT_FILE));

            for (String name : accountMap.keySet()) {
                accountWriter.write(accountMap.get(name),name);
            }
            accountWriter.close();


        } catch (FileNotFoundException e) {
            System.out.println("Unable to save file...");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }


    public void initializePanel() {
        menu = new JPanel();
        menu.setLayout(null);
        menu.setBounds(0,0,1000,750);
        JLabel title = new JLabel("Add Account");
        title.setFont(new Font("",Font.PLAIN,60));
        title.setBounds(300,80,600,80);
        title.setForeground(Color.BLACK);
        menu.add(title);

        initializeLabel();
        initializeTextField();
        initializeButton();

        menu.setBackground(Color.LIGHT_GRAY);

    }

    public void initializeLabel() {


        Font textFont = new Font("",Font.PLAIN,40);

        JLabel name = new JLabel("Name");
        name.setFont(textFont);
        JLabel account = new JLabel("Account");
        account.setFont(textFont);
        JLabel password = new JLabel ("Password");
        password.setFont(textFont);

        menu.add(name);
        name.setBounds(100,250,200,80);
        menu.add(account);
        account.setBounds(100,330,200,80);
        menu.add(password);
        password.setBounds(100,410,200,80);
    }

    public void initializeTextField() {


        Font textFont = new Font("",Font.PLAIN,40);

        nameField = new JTextField(15);
        nameField.setFont(textFont);
        accountField = new JTextField(30);
        accountField.setFont(textFont);
        passwordField = new JTextField(30);
        passwordField.setFont(textFont);

        menu.add(nameField);
        nameField.setBounds(300,250,400,70);
        menu.add(accountField);
        accountField.setBounds(300,330,600,70);
        menu.add(passwordField);
        passwordField.setBounds(300,410,600,70);

        Font messageFont = new Font("",Font.ITALIC,25);
        message = new JLabel();
        message.setFont(messageFont);
        message.setBounds(300,180,600,50);
        message("");
        menu.add(message);
    }

    public void message(String s) {
        message.setText(s);
        message.setForeground(Color.BLACK);
    }

    public void initializeButton() {


        Font ButtonFont = new Font("",Font.PLAIN,40);

        addConfirm = new JButton("Add");
        addConfirm.setFont(ButtonFont);
        addConfirm.setBackground(Color.DARK_GRAY);
        addConfirm.setForeground(Color.white);
        addConfirm.addActionListener(this);

        star = new JButton(starImage);
        star.setBackground(Color.GRAY);
        star.addActionListener(this);

        view = new JButton(viewImage);
        view.setBackground(Color.GRAY);
        view.addActionListener(this);

        settingButton = new JButton(settingImage);
        settingButton.setBackground(Color.LIGHT_GRAY);
        settingButton.addActionListener(this);

        addConfirm.setBounds(400,540,200,70);
        star.setBounds(170,540,80,80);
        view.setBounds(760,540,80,80);
        settingButton.setBounds(850,80,80,80);

        menu.add(addConfirm);
        menu.add(star);
        menu.add(view);
        menu.add(settingButton);
    }


    //               STAR
    //-----------------------------------------------------

    public void initializeStar() {
        starPanel = new JPanel();
        starPanel.setLayout(null);
        starPanel.setBounds(0,0,1000,750);

        JTextArea viewArea = new JTextArea();
        viewArea.setBounds(225,100,600, 550);
        viewArea.setFont(new Font("", Font.PLAIN, 30));
        viewArea.setForeground(Color.white);
        viewArea.setText("\n");
        viewArea.setBackground(Color.GRAY);


        viewArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(viewArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(150,50,700, 600);
        scrollPane.setBackground(Color.GRAY);



        for (String name : accountMap.keySet()) {
            if (accountMap.get(name).getStar()) {

                viewArea.append("Name: " + name + space(name.length()) + "❤\n"
                        + "Acct:   " + accountMap.get(name).getAccountName() + "\n"
                        + "Pswd: " + accountMap.get(name).getPassword() + "\n" + "\n");
            }
        }
        starPanel.add(scrollPane);

        setStarBackButton();
        starPanel.add(starBack);

        starPanel.setBackground(Color.GRAY);
    }

    public void setStarBackButton() {
        starBack = new JButton(backImage);
        starBack.setBounds(20,30,60,60);
        starBack.setBackground(Color.LIGHT_GRAY);
        starBack.addActionListener(this);

    }

    //               VIEW
    //-------------------------------------------------------

    public void initializeView() {
        viewPanel = new JPanel();
        viewPanel.setLayout(null);
        viewPanel.setBounds(0,0,1000,750);



        // NEW UPDATE**----------------------------------------------------------------

        // initializing search area...
        searchArea = new JTextArea();
        searchArea.setBounds(600,70,200, 50);
        searchArea.setFont(new Font("", Font.PLAIN, 30));
        searchArea.setText("");
        searchArea.setBackground(Color.white);
        searchArea.addKeyListener(this);

        // initializing search button
        searchButton = new JButton(searchImage);
        searchButton.setBounds(810,70,50,50);
        searchButton.setBackground(Color.LIGHT_GRAY);
        searchButton.addActionListener(this);
        //------------------------------------------------------------------------------

        loadviewArea(accountMap);



        viewPanel.add(searchArea);
        viewPanel.add(searchButton);
        setViewBackButton();
        viewPanel.add(viewBack);
        viewPanel.setBackground(Color.GRAY);
    }

    public String space(int num) {
        String s = "                                                ";
        s = s.substring(num);
        return s;
    }

    public void setViewBackButton() {
        viewBack = new JButton(backImage);
        viewBack.setBounds(20,30,60,60);
        viewBack.setBackground(Color.LIGHT_GRAY);
        viewBack.addActionListener(this);

    }

    public void loadviewArea(HashMap<String,Account> map_show) {
        if(viewArea != null) {
            viewPanel.remove(viewArea);
        }
        if(scrollPane != null) {
            viewPanel.remove(scrollPane);
        }
        viewArea = new JTextArea();
        viewArea.setBounds(225,200,600, 450);
        viewArea.setFont(new Font("", Font.PLAIN, 30));
        viewArea.setForeground(Color.white);
        viewArea.setText("\n");
        viewArea.setBackground(Color.GRAY);
        viewArea.setEditable(false);


        scrollPane = new JScrollPane(viewArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(150,150,700, 500);
        scrollPane.setBackground(Color.GRAY);


        for (String name : map_show.keySet()) {
            if (map_show.get(name).getStar()) {

                viewArea.append("Name: " + name + space(name.length()) + "❤\n"
                        + "Acct:   " + map_show.get(name).getAccountName() + "\n"
                        + "Pswd: " + map_show.get(name).getPassword() + "\n" + "\n");
            } else {
                viewArea.append("Name: " + name + "\n"
                        + "Acct:   " + map_show.get(name).getAccountName() + "\n"
                        + "Pswd: " + map_show.get(name).getPassword() + "\n" + "\n");
            }
        }

        viewPanel.add(scrollPane);
        viewPanel.updateUI();

    }





    //-------------------------SETTING------------------
    public void initializeSetting() {
        settingPanel = new JPanel();
        settingPanel.setLayout(null);
        settingPanel.setBounds(0,0,1000,750);

        box = Box.createVerticalBox();
        box.setBackground(Color.LIGHT_GRAY);
        box.setBounds(225,130,600, 350);
        JScrollPane scrollPane = new JScrollPane(box);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(150,80,700, 400);
        scrollPane.setBackground(Color.GRAY);

        Font boxFont = new Font("",Font.PLAIN,38);
        for (String name : accountMap.keySet()) {
            JCheckBox checkBox;
            checkBox = new JCheckBox(name);


            checkBox.setFont(boxFont);
            box.add(checkBox);
        }

        settingPanel.add(scrollPane);

        setButton();

        settingPanel.add(delete);
        settingPanel.add(setStar);
        settingPanel.add(removeStar);
        settingPanel.add(settingBack);
        settingPanel.setBackground(Color.GRAY);
    }

    public void setButton() {
        settingBack = new JButton(backImage);
        settingBack.setBounds(20,30,60,60);
        settingBack.setBackground(Color.LIGHT_GRAY);
        settingBack.addActionListener(this);

        setStar = new JButton(setStarImage);
        setStar.setBounds(890,280,80,80);
        setStar.setBackground(Color.LIGHT_GRAY);
        setStar.addActionListener(this);

        removeStar = new JButton(removeStarImage);
        removeStar.setBounds(890,400,80,80);
        removeStar.setBackground(Color.LIGHT_GRAY);
        removeStar.addActionListener(this);

        delete = new JButton(deleteImage);
        delete.setBounds(890,70,80,80);
        delete.setBackground(Color.LIGHT_GRAY);
        delete.addActionListener(this);

    }




    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        performMenu(source);
        performSetting(source);
        performSearch(source);
        if (source == viewBack) {
            accountFrame.setContentPane(menu);
        } else if (source == starBack) {
            accountFrame.setContentPane(menu);
            message("");
        } else if (source == settingBack) {
            accountFrame.setContentPane(menu);
            message("");
        }
    }
    //------NEW-------------------------------------------------------------------
    public void performSearch(Object source) {
        if (source == searchButton) {
            HashMap<String, Account> display = search(searchArea.getText());
            loadviewArea(display);
        }
    }
//------------------------------------------------------------------------------------------
    // search the map and return what should be displayed
    public HashMap<String, Account> search(String s) {
        HashMap<String,Account> result = new HashMap<>();
        Set<String> keys = accountMap.keySet();
        for (String keyString: keys) {

            if(s.length() <= keyString.length()) {
                String tempKey = keyString;
                keyString = keyString.substring(0, s.length());

                if (keyString.equalsIgnoreCase(s)) {
                    result.put(tempKey, accountMap.get(tempKey));
                }
            }
        }
        return result;
    }


    //----------------------------------------------------------------------------
    public void performMenu(Object source) {
        if (source == addConfirm) {
            Account account = new Account(accountField.getText(),passwordField.getText());
            accountMap.put(nameField.getText(),account);
            message("Account " + nameField.getText() + " has been added.");
            nameField.setText("");
            accountField.setText("");
            passwordField.setText("");
            saveAccounts();
        } else if (source == star) {
            initializeStar();
            accountFrame.setContentPane(starPanel);

        } else if (source == view) {
            initializeView();
            accountFrame.setContentPane(viewPanel);
        } else if (source == settingButton) {
            initializeSetting();
            accountFrame.setContentPane(settingPanel);
        }
    }

    public void performSetting(Object source) {
        if (source == delete) {
            int num = accountMap.size();
            for (int i = 0; i < num; i++) {
                JCheckBox checkBox = (JCheckBox) box.getComponent(i);
                if (checkBox.isSelected()) {
                    accountMap.remove(checkBox.getText());
                }
            }

            initializeSetting();
            JLabel message = new JLabel("Selected accounts have been deleted!");
            message.setForeground(Color.white);
            message.setBounds(240,550,800,40);
            message.setFont(new Font("",Font.PLAIN,30));
            settingPanel.add(message);
            accountFrame.setContentPane(settingPanel);

            saveAccounts();

        } else if (source == setStar) {
                int num = accountMap.size();
                for (int i = 0; i < num; i++) {
                    JCheckBox checkBox = (JCheckBox) box.getComponent(i);
                    if (checkBox.isSelected()) {
                        accountMap.get(checkBox.getText()).markAsTrue();
                    }
                }

                initializeSetting();
                JLabel message = new JLabel("Selected accounts have been marked!");
                message.setForeground(Color.white);
                message.setBounds(240,550,800,40);
                message.setFont(new Font("",Font.PLAIN,30));
                settingPanel.add(message);
                accountFrame.setContentPane(settingPanel);

                saveAccounts();
        } else if (source == removeStar) {
                int num = accountMap.size();
                for (int i = 0; i < num; i++) {
                    JCheckBox checkBox = (JCheckBox) box.getComponent(i);
                    if (checkBox.isSelected()) {
                        accountMap.get(checkBox.getText()).markAsFalse();
                    }
                }

                initializeSetting();
                JLabel message = new JLabel("Selected accounts have been unmarked!");
                message.setForeground(Color.white);
                message.setBounds(240,550,800,40);
                message.setFont(new Font("",Font.PLAIN,30));
                settingPanel.add(message);
                accountFrame.setContentPane(settingPanel);

                saveAccounts();
            }
    }

// new------------------------------------------------------------------------------
    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getSource() == searchArea) {
            HashMap<String, Account> display = search(searchArea.getText());
            loadviewArea(display);
        }
    }


    public void loadImage() {
        String sep = System.getProperty("file.separator");
        ImageIcon starIcon = new ImageIcon(System.getProperty("user.dir") + sep + "Images" + sep
                + "starButton.png");
        ImageIcon viewIcon = new ImageIcon(System.getProperty("user.dir") + sep + "Images" + sep
                + "viewButton.png");

        ImageIcon backIcon = new ImageIcon(System.getProperty("user.dir") + sep + "Images" + sep
                + "backButton.png");
        ImageIcon settingIcon = new ImageIcon(System.getProperty("user.dir") + sep + "Images" + sep
                + "settingButton.png");
        ImageIcon deleteIcon = new ImageIcon(System.getProperty("user.dir") + sep + "Images" + sep
                + "deleteButton.png");
        ImageIcon setStarIcon = new ImageIcon(System.getProperty("user.dir") + sep + "Images" + sep
                + "setStarButton.png");
        ImageIcon removeStarIcon = new ImageIcon(System.getProperty("user.dir") + sep + "Images" + sep
                + "removeStarButton.png");
        ImageIcon searchIcon = new ImageIcon(System.getProperty("user.dir") + sep + "Images" + sep
                + "search.jpg");

        starImage = new ImageIcon(starIcon.getImage().getScaledInstance(80,80,1));
        viewImage = new ImageIcon(viewIcon.getImage().getScaledInstance(80,60,1));
        backImage = new ImageIcon(backIcon.getImage().getScaledInstance(60,60,1));
        settingImage = new ImageIcon(settingIcon.getImage().getScaledInstance(80,80,1));
        deleteImage = new ImageIcon(deleteIcon.getImage().getScaledInstance(80,80,1));
        setStarImage = new ImageIcon(setStarIcon.getImage().getScaledInstance(80,80,1));
        removeStarImage = new ImageIcon(removeStarIcon.getImage().getScaledInstance(80,80,1));
        searchImage = new ImageIcon(searchIcon.getImage().getScaledInstance(50,50,1));
    }

}
