import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DependentCombos extends JFrame implements ItemListener {

    private JPanel panel1;


    private static JComboBox cbSport;
    private static JComboBox cbDetail;
    // label
    static JLabel lblInstruction, lblMessage;
    static JLabel lblDetail, lblSelected;
    /* This map will hold your options */
    private static Map<String, List<String>> modelDetail = new LinkedHashMap<String, List<String>>();

    /**
     * This method will simply build your data model in a Map
     *
     */
    private static void buildDataModel() {
        modelDetail.put("Football",
                Arrays.asList("Mahomes", "Allen", "Rodgers", "Brady"));
        modelDetail.put("Soccer",
                Arrays.asList("Neymar", "Messi", "Ronaldo", "Mbappe"));
        modelDetail.put("Basketball",
                Arrays.asList("Durant", "James", "Antetokounmpo", "Dončić"));
    }
    // main class
    public static void main(String[] args)
    {


    // create a object
        DependentCombos form = new DependentCombos();


    // array of string containing cities
    String sports[] = { "Football", "Soccer", "Basketball" };

    // create comboBoxes
    cbSport = new JComboBox(sports);
    cbDetail = new JComboBox();
    buildDataModel();  // fill information in model

    // add ItemListener to the combos to detect selections
    cbSport.addItemListener(form);
    // detail gets its own ItemListener
    cbDetail.addItemListener(e -> {
        if (e.getSource() == cbDetail){
            if (cbDetail.getSelectedItem() != null) {
                String selectedValue = cbDetail.getSelectedItem().toString();
                lblSelected.setText(selectedValue + " selected");
            }
        }
    });

    // create labels
    lblInstruction = new JLabel("select your sport ");
    lblMessage = new JLabel("Football selected");

        lblDetail = new JLabel("select a player ");
        lblSelected = new JLabel("Nothing selected yet");

    // set color of text
        lblInstruction.setForeground(Color.red);
        lblMessage.setForeground(Color.blue);

    // create a new panel
    JPanel p = new JPanel();

        p.add(lblInstruction);

    // add combobox to panel
        p.add(cbSport);

        p.add(lblMessage);

        p.add(lblDetail);

        // add combobox to panel
        p.add(cbDetail);

        p.add(lblSelected);

    // add panel to frame
        form.add(p);

    // set the size of frame
        form.setSize(400, 300);

        form.show();
}

    /**
     * This method creates a ItemListener for the main combo in the form
     * Neccesary because of the implements clause in the class declaration
     * @param e
     */
    public void itemStateChanged(ItemEvent e)
    {
        cbDetail.removeAllItems(); // Empty the second combo
        lblSelected.setText("Nothing selected yet");
        // if the state combobox is changed
        if (e.getSource() == cbSport) {
            // Get the value selected in main combo
            String selectedValue = cbSport.getSelectedItem().toString();
            // Get corresponding values for the selection
            List<String> termNames = modelDetail.get(selectedValue);
            lblMessage.setText(selectedValue + " selected");
            if (termNames == null) {
                cbDetail.addItem("Select Sport Above");
            } else {
                // add the values to the second combo
                for (String name : termNames) {
                    cbDetail.addItem(name);
                }
            }
        } else if (e.getSource() == cbDetail){

        }

    }

}
