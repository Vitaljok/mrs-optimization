package phd.combinations;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

class SolutionTreeRenderer extends DefaultTreeCellRenderer {

    private Icon defaultIcon;
    private Icon solutionIcon;

    public SolutionTreeRenderer(Icon defaultIcon, Icon solutionIcon) {
        this.defaultIcon = defaultIcon;
        this.solutionIcon = solutionIcon;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        if (isSolution(value)) {
            this.setIcon(solutionIcon);
            //this.setToolTipText(((SolutionTreeNode) value).getUnits().toString());
            //this.setToolTipText("aaa");
        } else {

            this.setIcon(defaultIcon);
        }


        return this;
    }

    private boolean isSolution(Object value) {

        if (value instanceof SolutionTreeNode) {
            return ((SolutionTreeNode) value).isSolution();
        }

        return false;
    }
}
