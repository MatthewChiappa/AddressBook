import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class AddressBook implements ActionListener {
  
  JFrame addFrame = new JFrame("Add Contact");
  JFrame removeFrame = new JFrame("Remove Contact");
  JFrame findFrame = new JFrame("Find Contact");
  
  JButton add = new JButton("Add");
  JButton remove = new JButton("Remove");
  JButton find = new JButton("Find");
  JTextArea display = new JTextArea();
  JScrollPane pane = new JScrollPane(display);
  
  JButton cancelAdd = new JButton("Cancel");
  JButton okAdd = new JButton("Ok");
  JTextField nameAdd = new JTextField();
  JTextField addressAdd = new JTextField();
  JTextField numberAdd = new JTextField();
  
  JButton cancelRemove = new JButton("Cancel");
  JButton okRemove = new JButton("Ok");
  JTextField removeField = new JTextField(20);
  
  JButton cancelFind = new JButton("Cancel");
  JButton okFind = new JButton("Ok");
  JTextField findField = new JTextField(20);
  
  Vector<Person> contactVect = new Vector<Person>();
  
  public AddressBook() {
   
    JFrame mainFrame = new JFrame("Address Book");
    
    JPanel buttons = new JPanel(new GridLayout(1,3));
    JPanel addButtons = new JPanel(new GridLayout(1,2));
    JPanel addInfo = new JPanel(new GridLayout(3,1));
    JPanel addLabels = new JPanel(new GridLayout(3,1));
    JPanel displayPanel = new JPanel(new FlowLayout());
    JPanel removeButtons = new JPanel(new GridLayout(1,2));
    JPanel findButtons = new JPanel(new GridLayout(1,2));
    
    JScrollPane scrollBar = new JScrollPane(display);
    display.setLineWrap(true);
    
    scrollBar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scrollBar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    
    //mainFrame edits
    mainFrame.setSize(320, 360);
    mainFrame.setLocationRelativeTo(null);
    mainFrame.setResizable(false);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // add edits
    addFrame.setSize(320, 240);
    addFrame.setLocationRelativeTo(null);
    addFrame.setResizable(false);
    
    addButtons.add(cancelAdd);
    addButtons.add(okAdd);
    
    addInfo.add(nameAdd);
    addInfo.add(addressAdd);
    addInfo.add(numberAdd);
    
    addLabels.add(new JLabel("Name: "));
    addLabels.add(new JLabel("Address: "));
    addLabels.add(new JLabel("Phone Number:"));
    
    addFrame.add(addLabels, BorderLayout.WEST);
    addFrame.add(addInfo, BorderLayout.CENTER);
    addFrame.add(addButtons, BorderLayout.SOUTH);
    
    // remove
    removeFrame.setSize(300, 180);
    removeFrame.setLocationRelativeTo(null);
    removeFrame.setResizable(false);
    
    removeFrame.add(removeField);
    removeFrame.add(new JLabel("Enter The Name You Would Like To Remove", JLabel.CENTER), SpringLayout.NORTH);
    
    cancelRemove.addActionListener(this);
    okRemove.addActionListener(this);
    
    removeButtons.add(cancelRemove);
    removeButtons.add(okRemove);
    
    removeFrame.add(removeButtons, BorderLayout.SOUTH);

    // find
    findFrame.setSize(300, 180);
    findFrame.setLocationRelativeTo(null);
    findFrame.setResizable(false);
    
    findFrame.add(findField);
    findFrame.add(new JLabel("Enter The Name You Would Like To Find", JLabel.CENTER), SpringLayout.NORTH);
    
    cancelFind.addActionListener(this);
    okFind.addActionListener(this);
    
    findButtons.add(cancelFind);
    findButtons.add(okFind);
    
    findFrame.add(findButtons, BorderLayout.SOUTH);
    
    // button panel for mainFrame
    add.addActionListener(this);
    remove.addActionListener(this);
    find.addActionListener(this);
    buttons.add(add);
    buttons.add(remove);
    buttons.add(find);
    
    // buttons for addFrame
    okAdd.addActionListener(this);
    cancelAdd.addActionListener(this);
    
    // contact display edits
    display.setEditable(false);
    scrollBar.setPreferredSize(new Dimension(280, 300));
    displayPanel.add(scrollBar, BorderLayout.CENTER);
    
    // adding everything to mainFrame
    mainFrame.add(buttons, BorderLayout.SOUTH);
    mainFrame.add(displayPanel, BorderLayout.NORTH);
    mainFrame.setVisible(true);
    
  }
  
  public static void main(String args[]) {
    
    AddressBook book = new AddressBook();
    File textFile = new File("book.txt");
    
    if(!textFile.exists()) {
      try {
        textFile.createNewFile();
      }
      catch(IOException e) {
        System.out.println(e.getMessage());
        System.exit(0);
      }
    }
    else {
      try {
        book.readFile(textFile);
      }
      catch (IOException e) {
        System.out.println(e.getMessage());
        System.exit(0);
      }
    }
    
  }
  
  public void actionPerformed(ActionEvent e) {
   
    if((JButton)e.getSource() instanceof JButton) {
     
      if (e.getSource() == add) {
        addFrame.setVisible(true);
      }
      else if (e.getSource() == remove) {
        removeFrame.setVisible(true);
      }
      else if (e.getSource() == find) {
        findFrame.setVisible(true);
      }
      else if (e.getSource() == okAdd) {
        if((!nameAdd.getText().equals("")) && 
           (!addressAdd.getText().equals("")) && 
           (!numberAdd.getText().equals(""))) {
          addContact(nameAdd.getText(), addressAdd.getText(), numberAdd.getText());
          addFrame.setVisible(false);
          nameAdd.setText("");
          addressAdd.setText("");
          numberAdd.setText("");
        }
        else {
          JFrame error = new JFrame("Error");
          error.add(new JLabel("Some of the fields were incorectly filled out!", SwingConstants.CENTER));
          error.setSize(300, 150);
          error.setResizable(false);
          error.setLocationRelativeTo(null);
          error.setVisible(true);
        }
      }
      else if (e.getSource() == cancelAdd) {
        addFrame.setVisible(false);
        nameAdd.setText("");
        addressAdd.setText("");
        numberAdd.setText("");
      }
      else if (e.getSource() == cancelRemove) {
        removeFrame.setVisible(false);
        removeField.setText("");
      }
      else if (e.getSource() == okRemove) {
        try {
          removeContact(removeField.getText());
        }
        catch (IOException ex) {
          System.out.println(ex.getMessage());
          System.exit(0);
        }
        removeFrame.setVisible(false);
        removeField.setText("");
      }
      else if (e.getSource() == cancelFind) {
        findFrame.setVisible(false);
        findField.setText("");
      }
      else if (e.getSource() == okFind) {
          findContact(findField.getText());
      }
    }
    
  }
    
  
  public void addContact(String name, String address, String number) {
    try {
      File file = new File("book.txt");
    
      FileWriter write = new FileWriter(file.getAbsoluteFile(), true);
      BufferedWriter buffWrite = new BufferedWriter(write);
    
      buffWrite.write("\n" + name + "\n" + address + "\n" + number);
      buffWrite.close();
      readFile(file);
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
      System.exit(0);
    }
  }
  
  public void removeContact(String name) throws IOException {
    
    Iterator iterate = contactVect.iterator();
    boolean found = false;
    int i = 0;
    
    while (iterate.hasNext()) {
      
      Person temp = (Person)iterate.next();
      if (temp.getName().toUpperCase().equals(name.toUpperCase())) {
        found = true;
        break;
      }
      i++;
    }
    
    if (found) {
      contactVect.removeElementAt(i);
      FileWriter write = new FileWriter(new File("book.txt"));
      BufferedWriter buffWrite = new BufferedWriter(write);
      boolean first = true;
      
      iterate = contactVect.iterator();
      
      while (iterate.hasNext()) {
        
        Person temp = (Person)iterate.next();
        
        if (first) {
          buffWrite.write(temp.getName() + "\n" + temp.getAddress() + "\n" + temp.getNumber());
        }
        else {
          buffWrite.write("\n" + temp.getName() + "\n" + temp.getAddress() + "\n" + temp.getNumber());
        }
      }
      
      buffWrite.close();
      readFile(new File("book.txt"));
    }
    else {
      JFrame error = new JFrame("Error");
      error.add(new JLabel("Name Not Found!", SwingConstants.CENTER));
      error.setSize(300, 150);
      error.setResizable(false);
      error.setLocationRelativeTo(null);
      error.setVisible(true);
    }
    
  }
  
  public void findContact(String name) {
    
    Iterator iterate = contactVect.iterator();
    boolean found = false;
    int i = 0;
    Person temp = null;
    
    while (iterate.hasNext()) {
      
      temp = (Person)iterate.next();
      if (temp.getName().toUpperCase().equals(name.toUpperCase())) {
        found = true;
        break;
      }
      i++;
    }
    
    if (found) {
      JFrame foundFrame = new JFrame("Name Found");
      JPanel contact = new JPanel(new GridLayout(3,1)); 
      
      foundFrame.add(new JLabel("Name Found!\n", SwingConstants.CENTER), BorderLayout.NORTH);
      contact.add(new JLabel(temp.getName()));
      contact.add(new JLabel(temp.getAddress()));
      contact.add(new JLabel(temp.getNumber()));
      
      foundFrame.add(contact);
      foundFrame.setSize(300, 150);
      foundFrame.setResizable(false);
      foundFrame.setLocationRelativeTo(null);
      foundFrame.setVisible(true);
    }
    else {
      JFrame error = new JFrame("Error");
      error.add(new JLabel("Name Does Not Exist In Address Book!", SwingConstants.CENTER));
      error.setSize(300, 150);
      error.setResizable(false);
      error.setLocationRelativeTo(null);
      error.setVisible(true);
      findFrame.setVisible(false);
      findField.setText("");
    }
    
  }
  
  public void readFile(File file) throws IOException {
    
    BufferedReader scan = new BufferedReader(new FileReader(file));
    String currLine;
    int count = 0;
    
    display.setText("");
    
    try {
      while ((currLine = scan.readLine()) != null) {
        display.append(currLine + "\n");
        count++;
        
        if (count == 3) {
          count = 0;
          display.append("\n");
        }
      }
      
      contactVect = newVector(new File("book.txt"));
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
      System.exit(0);
    }
    
  }
  
  public Vector<Person> newVector(File file) {
    
    Vector<Person> newVect = new Vector<Person>();
    String currLine;
    int count = 0;
    
    try {
      BufferedReader scan = new BufferedReader(new FileReader(file));
      
      while ((currLine = scan.readLine()) != null) {
        Person newPerson = new Person(currLine, scan.readLine(), scan.readLine());
        newVect.add(newPerson);
      }
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
      System.exit(0);
    }
    
    return newVect;
    
  }
  
}