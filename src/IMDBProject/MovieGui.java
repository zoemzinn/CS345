package IMDBProject;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MovieGui extends JFrame {

  private Movie movie;
  private ArrayList<String> actors;
  private JPanel displayPanel;


  public MovieGui(Movie movie) throws IOException {
    this.movie = movie;
    this.actors = Call.getActors(movie.getId());
    buildGui();
    this.setVisible(true);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.pack();
  }

  /**
   * Helper method for building the GUI.
   */
  private void buildGui() throws IOException {
    getContentPane().add(getDisplayPanel());
  }

  private Component getDisplayPanel() throws IOException {
    displayPanel = new JPanel(new BorderLayout());
    displayPanel.setPreferredSize(new Dimension(800, 800));
    buildContents();
    return displayPanel;
  }

  /**
   * Builds our contents within the display panel.
   *
   * @throws IOException if the url messes up from nested methods
   */
  private void buildContents() throws IOException {
    // image added to display panel by itself
    try {
      URL icon = new URL(movie.getImage());
      ImageIcon image = new ImageIcon(icon);
      while (image.getIconHeight() > 1000) {
        image = new ImageIcon(image.getImage().getScaledInstance(image.getIconWidth() / 2,
            image.getIconHeight() / 2, Image.SCALE_DEFAULT));
      }
      displayPanel.add(new JLabel(image), BorderLayout.CENTER);
    } catch (Exception e) {
      System.out.println("Problem with image of movie");
    }
    // description stuff is in its own panel inside main displayPanel
    JPanel descPanel = new JPanel(new BorderLayout());
    displayPanel.add(descPanel, BorderLayout.EAST);

    JLabel title = new JLabel(movie.getTitle());
    title.setFont(new Font("Comic Sans MS", Font.PLAIN, 40));
    descPanel.add(title, BorderLayout.NORTH);

    descPanel.add(new JScrollPane(getTreeView()), BorderLayout.CENTER);

    JLabel description = new JLabel(Call.getDescription(movie.getId()));
    description.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
    displayPanel.add(description, BorderLayout.SOUTH);
  }

  private Component getTreeView() {
    JTree tree = new JTree();
    tree = new JTree();
    DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
    for (String actor : actors) {
      rootNode.add(new DefaultMutableTreeNode(actor));
    }
    TreeModel model = new DefaultTreeModel(rootNode);
    tree.setModel(model);
    tree.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
    tree.setRootVisible(false);

    JScrollPane treeView = new JScrollPane(tree);
    return treeView;
  }


}
