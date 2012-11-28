/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package phd.combinations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Vitaljok
 */
public class SolutionTreeNode extends DefaultMutableTreeNode {

    private boolean areChildrenDefined = false;
    private Component data;
    private List<Component> childList;
    private Set<FunctionalUnit> units;
    private List<Component> solution;
    private Byte num;

    public SolutionTreeNode(List<Component> compList, Byte num) {

        super(compList.get(0));
        this.data = compList.get(0);
        compList.remove(this.data);
        childList = compList;
        this.num = num;
    }

    public Set<FunctionalUnit> getUnits() {
        if (units == null) {
            units = new HashSet<FunctionalUnit>();

            if (this.getParent() instanceof SolutionTreeNode) {
                units.addAll(((SolutionTreeNode) this.getParent()).getUnits());
            }

            units.addAll(data);
        }

        return units;
    }

    public List<Component> getSolution() {
        if (solution == null) {
            solution = new ArrayList<Component>();

            if (this.getParent() instanceof SolutionTreeNode) {
                solution.addAll(((SolutionTreeNode) this.getParent()).getSolution());
            }

            solution.add(data);
        }

        return solution;
    }

    public boolean isSolution() {

        if (this.getUnits().size() == num) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isLeaf() {
        return (false);
    }

    @Override
    public int getChildCount() {
        if (!areChildrenDefined) {
            defineChildNodes();
        }
        return (super.getChildCount());
    }

    private void defineChildNodes() {
        // You must set the flag before defining children if you
        // use "add" for the new children. Otherwise you get an infinite
        // recursive loop, since add results in a call to getChildCount.
        // However, you could use "insert" in such a case.
        areChildrenDefined = true;

        
//        if (this.isSolution())
//            return;

        while (!this.childList.isEmpty()) {
            SolutionTreeNode node = new SolutionTreeNode(new ArrayList<Component>(childList), num);

            add(node);
                        
//            if (node.isSolution()) {
//                remove(node);
//            }
            this.childList.remove(0);
        }
    }
}
