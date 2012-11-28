/*
 * CombinationsView.java
 */
package phd.combinations;

import javax.swing.event.TreeSelectionEvent;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.Task;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * The application's main frame.
 */
public class CombinationsView extends FrameView {

    public CombinationsView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {

            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });

        this.jTreeSolutions.setCellRenderer(
                new SolutionTreeRenderer(
                resourceMap.getIcon("Tree.defaultIcon"),
                resourceMap.getIcon("tree.solutionIcon")));

        this.jTreeSolutions.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        this.jTreeSolutions.addTreeSelectionListener(new TreeSelectionListener() {

            public void valueChanged(TreeSelectionEvent e) {

                solutionList.clear();

                Object node = jTreeSolutions.getLastSelectedPathComponent();

                if (node != null && node instanceof SolutionTreeNode) {
                    for (Component c : ((SolutionTreeNode) node).getSolution()) {
                        solutionList.addElement(c);
                    }

                    if (!((SolutionTreeNode) node).isSolution()) {
                        solutionList.addElement("<incomplete>");
                    }
                }
            }
        });

    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = CombinationsApp.getApplication().getMainFrame();
            aboutBox = new CombinationsAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        CombinationsApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jSpinnerFUnitCount = new javax.swing.JSpinner();
        jButtonCreateComponents = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListComponents = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeSolutions = new javax.swing.JTree();
        jButtonExpand = new javax.swing.JButton();
        jButtonCollapse = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListSolution = new javax.swing.JList();
        jButtonCountNodes = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        jSpinnerFUnitCount.setModel(new javax.swing.SpinnerNumberModel(Byte.valueOf((byte)3), Byte.valueOf((byte)1), Byte.valueOf((byte)10), Byte.valueOf((byte)1)));
        jSpinnerFUnitCount.setName("jSpinnerFUnitCount"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(phd.combinations.CombinationsApp.class).getContext().getActionMap(CombinationsView.class, this);
        jButtonCreateComponents.setAction(actionMap.get("createComponents")); // NOI18N
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(phd.combinations.CombinationsApp.class).getContext().getResourceMap(CombinationsView.class);
        jButtonCreateComponents.setText(resourceMap.getString("jButtonCreateComponents.text")); // NOI18N
        jButtonCreateComponents.setName("jButtonCreateComponents"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jListComponents.setModel(this.componentList);
        jListComponents.setName("jListComponents"); // NOI18N
        jScrollPane1.setViewportView(jListComponents);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTreeSolutions.setModel(new DefaultTreeModel(treeRoot));
        jTreeSolutions.setName("jTreeSolutions"); // NOI18N
        jScrollPane2.setViewportView(jTreeSolutions);

        jButtonExpand.setAction(actionMap.get("expandTree")); // NOI18N
        jButtonExpand.setText(resourceMap.getString("jButtonExpand.text")); // NOI18N
        jButtonExpand.setName("jButtonExpand"); // NOI18N

        jButtonCollapse.setAction(actionMap.get("collapseTree")); // NOI18N
        jButtonCollapse.setText(resourceMap.getString("jButtonCollapse.text")); // NOI18N
        jButtonCollapse.setName("jButtonCollapse"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jListSolution.setModel(this.solutionList);
        jListSolution.setName("jListSolution"); // NOI18N
        jScrollPane3.setViewportView(jListSolution);

        jButtonCountNodes.setAction(actionMap.get("countNodes")); // NOI18N
        jButtonCountNodes.setName("jButtonCountNodes"); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jSpinnerFUnitCount, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCreateComponents)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonExpand)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCollapse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCountNodes, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerFUnitCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCreateComponents)
                    .addComponent(jButtonExpand)
                    .addComponent(jButtonCollapse)
                    .addComponent(jButtonCountNodes, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 506, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    // If expand is true, expands all nodes in the tree.
// Otherwise, collapses all nodes in the tree.
    public void expandAll(JTree tree, boolean expand) {
        TreeNode root = (TreeNode) tree.getModel().getRoot();
        // Traverse tree from root
        expandAll(tree, new TreePath(root), expand);
    }

    private void expandAll(JTree tree, TreePath parent, boolean expand) {
        // Traverse children
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }

        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }

    @Action
    public Task createComponents() {
        return new CreateComponentsTask(getApplication());
    }

    private class CreateComponentsTask extends org.jdesktop.application.Task<List<Component>, Void> {

        private Byte num;

        CreateComponentsTask(org.jdesktop.application.Application app) {
            // Runs on the EDT.  Copy GUI state that
            // doInBackground() depends on from parameters
            // to CreateComponentsTask fields, here.
            super(app);
            num = Byte.parseByte(jSpinnerFUnitCount.getModel().getValue().toString());
        }

        @Override
        protected List<Component> doInBackground() {
            this.setMessage("Generating components...");
            List<Component> result = new ArrayList<Component>();

            List<FunctionalUnit> units = new ArrayList<FunctionalUnit>();
            for (int i = 0; i < num; i++) {
                units.add(new FunctionalUnit(i + 1));
            }

            for (int i = 1; i < Math.pow(2, num); i++) {
                String pattern = String.format("%" + num + "s", Integer.toBinaryString(i)).replaceAll("\\s", "0");

                Component comp = new Component();

                for (int j = 0; j < num; j++) {
                    if (pattern.charAt(num - j - 1) == '1') {
                        comp.add(units.get(j));
                    }
                }

                result.add(comp);
            }

            this.setMessage("Generating components... Done!");
            return result;
        }

        @Override
        protected void succeeded(List<Component> result) {

            componentList.clear();
            treeRoot.removeAllChildren();

            ((DefaultTreeModel) jTreeSolutions.getModel()).reload();

            for (Component c : result) {
                componentList.addElement(c);
            }

            while (!result.isEmpty()) {
                treeRoot.add(new SolutionTreeNode(new ArrayList<Component>(result), num));
                result.remove(0);
            }

            jTreeSolutions.expandPath(new TreePath(jTreeSolutions.getModel().getRoot()));
            this.setMessage("Components created!");
        }
    }

    @Action
    public void expandTree() {
        this.expandAll(jTreeSolutions, true);
    }

    @Action
    public void collapseTree() {
        this.expandAll(jTreeSolutions, false);
    }

    @Action
    public void countNodes() {
        JOptionPane.showMessageDialog(this.mainPanel, "Number of nodes: " + this.getNodeCount(jTreeSolutions.getModel(),
                jTreeSolutions.getModel().getRoot()));
    }

    private int getNodeCount(TreeModel model, Object node) {
        int count = 1; // the node itself  
        int childCount = model.getChildCount(node);
        for (int i = 0; i < childCount; i++) {
            count += getNodeCount(model, model.getChild(node, i));
        }
        return count;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCollapse;
    private javax.swing.JButton jButtonCountNodes;
    private javax.swing.JButton jButtonCreateComponents;
    private javax.swing.JButton jButtonExpand;
    private javax.swing.JList jListComponents;
    private javax.swing.JList jListSolution;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinnerFUnitCount;
    private javax.swing.JTree jTreeSolutions;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    private DefaultListModel componentList = new DefaultListModel();
    private DefaultListModel solutionList = new DefaultListModel();
    private DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode("root");
}
