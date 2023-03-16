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
    String sportValue;
    String leagueValue;
    String teamValue;
    Map<String, String[]> map = new TreeMap<String, String[]>();
    Map<String, String[]> map1 = new TreeMap<String, String[]>();
    // writer_interface form = new writer_interface();

    int update_control = -1;
    String[] sports= {" ","Soccer", "Basketball", "Baseball", "Football", "Olympics"};
    String[] Soccer= {" ","Premier League", "La Liga", "Ligue 1", "Serie A", "Bundesliga"};
    String[] Basketball= {" ","NBA", "WNBA", "Euro Basket", "NCAA"};
    String[] Baseball= {" ","MLB", "NCAA"};
    String[] Football= {" ","NFL", "NCAA"};
    String[] Olympics= {" ","Track and Field", "Swimming", "Weightlifting",};
    String[] premierleagueteams= {" ","Chelsea", "Arsenal", "Tottenham",};
    String[] laligateams = {" ", "FC Barcelona", "Real Madrid", "Atletico de Madrid"};
    String[] ligue1teams = {" ", "PSG", "Lyon", "Marseille"};
    String[] serieateams = {" ", "AC Milan", "Juventus", "Napoli"};
    String[] bundesligateams = {" ", "FC Bayern", "Dortmund", "RB Leipzig"};
    String[] nbateams = {" ", "Chicago Bulls", "Miami Heat", "Detroit Pistons"};
    String[] wnbateams = {" ", "Chicago Sky", "Las Vegas Aces", "New York Liberty"};
    String[] eurobasketteams = {" ", "Olimpia Milano", "Real Madrid", "Maccabi Tel Aviv"};
    String[] ncaabteams = {" ", "Duke", "Kentucky", "Gonzaga"};
    String[] nflteams = {" ", "Chicago Bears", "Pittsburgh Steelers", "Carolina Panthers"};
    String[] ncaafteams = {" ", "Alabama", "Clemson", "Ohio State"};
    String[] mlbteams = {" ", "Chicago Cubs", "Texas Rangers", "Minesotta Twins"};
    String[] collegebaseballteams = {" ", "Texas", "LSU", "Texas A&M"};
    String[] trackandfield = {" ", "Jamaica", "USA", "Canada"};
    String[] swimming = {" ", "USA", "China", "Great Britain"};
    Connection connection = null;

    public void createOptionsMap() {

        map.put(" ", new String[] {" "});
        map.put("Soccer", Soccer);
        map.put("Basketball", Basketball);
        map.put("Baseball", Baseball);
        map.put("Football", Football);
        map.put("Olympics", Olympics);

        map1.put(" ", new String[] {" "});
        map1.put("Premier League", premierleagueteams);
        map1.put("La Liga", laligateams);
        map1.put("Ligue 1", ligue1teams);
        map1.put("Serie A", serieateams);
        map1.put("Bundesliga", bundesligateams);
        map1.put("NBA", nbateams);
        map1.put("WNBA", wnbateams);
        map1.put("Euro Basket", eurobasketteams);
        map1.put("NCAAB", ncaabteams);
        map1.put("MLB", mlbteams);
        map1.put("College Baseball", collegebaseballteams);
        map1.put("NFL", nflteams);
        map1.put("NCAAF", ncaafteams);
        map1.put("Track and Field", trackandfield);
        map1.put("Swimming", swimming);
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

        JComboBox comboBox = (JComboBox) event.getSource();
        int index = getIndex(comboBox);
        String value = (String) comboBox.getSelectedItem();
        JComboBox nextComboBox = null;
        if (index < 2 && index != update_control) {
            if(index == 0 ){
                nextComboBox = leagueCombo;
                update_control = 1;
                updateComboBox(nextComboBox, value);
            } else if(index == 1){
                nextComboBox = teamCombo;
                update_control = 2;
                updateComboBox(nextComboBox, value);
            }
        }
    }


    private int getIndex(JComboBox comboBox) {
        if(comboBox == sportCombo) {
            return 0;
        } else if(comboBox == leagueCombo) {
            return 1;
        } else if(comboBox == teamCombo) {
            return 2;
        }
        return -1;
    }

    private void updateComboBox(JComboBox comboBox, String value) {
        // Clear the combo box and add new items based on the selection in the previous combo box
        System.out.println("Updating combo box: " + comboBox.getName() + " with value: " + value);
        comboBox.removeAllItems();
        if(comboBox == leagueCombo) {
            String optionsForLeague[] = map.get( value);
            for (String option: optionsForLeague) {
                leagueCombo.addItem(option);
            }
        } else if(comboBox == teamCombo) {
            String optionsForTeam[] = map1.get(value);
            for (String option: optionsForTeam) {
                teamCombo.addItem(option);
            }
        }
        update_control = -1;
        // Add new items based on the selected value in the previous combo box
        // ...
    }

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