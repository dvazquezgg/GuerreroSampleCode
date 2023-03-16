import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.TreeMap;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JTextArea;

public class writer_interface extends JFrame implements ItemListener{

    private JPanel contentPane;
    private JTable PreviousArticlesList;
    private JTable Comments;
    static JLabel lblDetail, lblSelected;
    static JLabel lblInstruction, lblMessage;
    JComboBox sportCombo,leagueCombo,teamCombo;
    Map<String, String[]> map = new TreeMap<String, String[]>();

    Map<String, String[]> map1 = new TreeMap<String, String[]>();
    // writer_interface form = new writer_interface();

    String sports[]= {"","Soccer", "Basketball", "Baseball", "Football", "Tennis", "Hockey", "Racing", "Golf", "Cricket", "Rugby", "Olympics"};
    String Soccer[]= {"","Premier League", "Spanish La Liga", "French Ligue 1", "Italian Serie A", "German Bundesliga", "National Teams", "Liga MX", "MLS", "Womens Nations"};
    String Basketball[]= {"","NBA", "WNBA", "Euro Basket", "NCAA"};
    String Baseball[]= {"","MLB", "NCAA"};
    String Football[]= {"","NFL", "NCAA"};
    String Tennis[]= {"","Mens", "Womens"};
    String Hockey[]= {"","NHL"};
    String Racing[]= {"","F1", "Nascar"};
    String Golf[]= {"","Mens", "Womens"};
    String Cricket[]= {"","Mens National Teams", "Womens National Teams"};
    String Rugby[]= {"","Mens National Teams", "Womens National Teams"};
    String Olympics[]= {"","Track and Field", "Swimming", "Weightlifting",};
    String premierleagueteams[]= {"","Chelsea", "Arsenal", "Tottenham",};
    Connection connection = null;

    public void createOptionsMap() {

        map.put("Soccer", Soccer);
        map.put("Basketball", Basketball);
        map.put("Baseball", Baseball);
        map.put("Football", Football);
        map.put("Tennis", Tennis);
        map.put("Hockey", Hockey);
        map.put("Racing", Racing);
        map.put("Golf", Golf);
        map.put("Cricket", Cricket);
        map.put("Rugby", Rugby);
        map.put("Olympics", Olympics);
        map1.put("Premier League", premierleagueteams);
    }

    /**
     * Create the frame.
     */
    public writer_interface() {

        // Creating the contentPane to display the UIElements
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 884, 721);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel WelcomeWriter = new JLabel("Welcome Writer!");
        WelcomeWriter.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        WelcomeWriter.setBounds(77, 19, 166, 29);
        contentPane.add(WelcomeWriter);

        JLabel New_Article = new JLabel("New Article");
        New_Article.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        New_Article.setBounds(619, 12, 112, 42);
        contentPane.add(New_Article);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(25, 127, 446, 257);
        contentPane.add(scrollPane);

        JLabel sportselect = new JLabel("Sport");
        sportselect.setBounds(530, 73, 42, 16);
        contentPane.add(sportselect);

        JLabel leagueselect = new JLabel("League");
        leagueselect.setBounds(650, 73, 52, 16);
        contentPane.add(leagueselect);

        JLabel teamselect = new JLabel("Team");
        teamselect.setBounds(767, 73, 42, 16);
        contentPane.add(teamselect);

        teamCombo = new JComboBox();
        teamCombo.setBounds(730, 101, 112, 27);
        contentPane.add(teamCombo);
        teamCombo.addItemListener(this);

        leagueCombo = new JComboBox();
        leagueCombo.setBounds(619, 101, 112, 27);
        contentPane.add(leagueCombo);
        leagueCombo.addItemListener(this);

        sportCombo = new JComboBox();
        sportCombo.setModel(new DefaultComboBoxModel(sports)); // Initial Combo with set values
        sportCombo.setBounds(495, 101, 112, 27);
        contentPane.add(sportCombo);
        sportCombo.addItemListener(this);

        createOptionsMap();

        JScrollPane scrollPane_3 = new JScrollPane();
        scrollPane_3.setBounds(305, 447, 166, 245);
        contentPane.add(scrollPane_3);

        Comments = new JTable();
        scrollPane_3.setViewportView(Comments);

        JButton showcomments = new JButton("Show Comments");
        showcomments.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                connection = Sqliteconnection.dbConnector();
                try {
                    String query2 = "select * from Comments";
                    ;
                    PreparedStatement pstt = connection.prepareStatement(query2);
                    ResultSet rs = pstt.executeQuery();
                    Comments.setModel(DbUtils.resultSetToTableModel(rs));

                    pstt.close();
                    rs.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            };


        });
        showcomments.setBounds(319, 406, 132, 29);
        contentPane.add(showcomments);

        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(497, 140, 345, 433);
        contentPane.add(scrollPane_1);

        JEditorPane articletext = new JEditorPane();
        scrollPane_1.setViewportView(articletext);

        JButton submitarticle = new JButton("Submit");
        submitarticle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    connection = Sqliteconnection.dbConnector();
                    String query = "insert into Articles(Sport, League, Team, Article)values(?,?,?,?)";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setString(1, (String) sportCombo.getSelectedItem());
                    pst.setString(2, (String) leagueCombo.getSelectedItem());
                    pst.setString(3, (String) teamCombo.getSelectedItem());
                    pst.setString(4, articletext.getText());
                    pst.execute();

                    JOptionPane.showMessageDialog(null, "Comment has been saved");
                    pst.close();
                    submitarticle.setBounds(614, 617, 117, 29);
                    contentPane.add(submitarticle);


                }catch(Exception e1) {

                }
            }
        });

        System.out.println(map);
        lblSelected = new JLabel("Nothing selected yet");
        lblSelected.setBounds(25, 396, 446, 16);
        lblMessage = new JLabel("Football selected");
        lblMessage.setBounds(25, 424, 446, 16);
        contentPane.add(lblSelected);
        contentPane.add(lblMessage);


        PreviousArticlesList = new JTable();
        scrollPane.setViewportView(PreviousArticlesList);

        JButton btnNewButton = new JButton("Show My Previous Articles");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                connection = Sqliteconnection.dbConnector();
                try {
                    String query2 = "select * from Articles";
                    ;
                    PreparedStatement pstt = connection.prepareStatement(query2);
                    ResultSet rs = pstt.executeQuery();
                    PreviousArticlesList.setModel(DbUtils.resultSetToTableModel(rs));

                    pstt.close();
                    rs.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            };


        });
        btnNewButton.setBounds(25, 84, 251, 29);
        contentPane.add(btnNewButton);

        JButton BackButtonReaders = new JButton("Back");
        BackButtonReaders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                register_and_login_interface object = new register_and_login_interface();
                register_and_login_interface.main(null);
            }
        });
        BackButtonReaders.setBounds(25, 645, 117, 29);
        contentPane.add(BackButtonReaders);

        JScrollPane scrollPane_2 = new JScrollPane();
        scrollPane_2.setBounds(25, 422, 263, 211);
        contentPane.add(scrollPane_2);

        JTextArea showingarticle = new JTextArea();
        scrollPane_2.setViewportView(showingarticle);

    }

    public void itemStateChanged(ItemEvent event) {

        String sportValue = "None";
        String leagueValue = "None";
        String teamValue = "None";

        // if the state combobox is changed
        if (event.getSource() == sportCombo) {
            leagueCombo.removeAllItems(); // Empty the second combo
            lblSelected.setText("");

            // Get the value selected in main combo
            sportValue = sportCombo.getSelectedItem().toString();
            lblMessage.setText(sportValue + " selected");

            String optionsForLeague[] = new String[(map.get(sportValue)).length];

            if (optionsForLeague == null) {
                leagueCombo.addItem("Select Sport Above");
            } else {
                for(int i = 0; i < (map.get(sportValue)).length; i++) {
                    optionsForLeague[i] = map.get(sportValue)[i];
                    System.out.println(optionsForLeague[i]);
                    leagueCombo.addItem(optionsForLeague[i]);
                }
            }
        } else if (event.getSource() == leagueCombo){
            if (leagueCombo.getSelectedItem() != null) {
                leagueValue = leagueCombo.getSelectedItem().toString();
            }
        } else if (event.getSource() == teamCombo){
            if (teamCombo.getSelectedItem() != null) {
                teamValue = teamCombo.getSelectedItem().toString();
            }
        }

        System.out.println(sportValue);
        System.out.println(leagueValue);
        System.out.println(teamValue);
    }
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    writer_interface frame = new writer_interface();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}