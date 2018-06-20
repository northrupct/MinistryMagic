package com.blackops.view;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import com.blackops.model.Assignment;
import com.blackops.model.Family;

public class AssignmentTransferHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int removeIndex = -1;
    private int addIndex = -1; //Location where items were added
    private int addCount = 0;  //Number of items added.
    private Assignment assignment;

    AssignmentTransferHandler() {
		assignment = null;
	}
    
	AssignmentTransferHandler(Assignment a) {
		assignment = a;
	}
	
    /**
     * We only support importing strings.
     */
    public boolean canImport(TransferHandler.TransferSupport info) {
        // Check for String flavor
    	if (!info.isDataFlavorSupported(FamilyTransferable.FAMILY_ITEM_DATA_FLAVOR)) {
            return false;
        }
        return true;
   }

	
    /**
     * Bundle up the selected items in a single list for export.
     * Each line is separated by a newline.
     */
    protected Transferable createTransferable(JComponent c) {
    	Transferable t = null;
    	if(c instanceof JList<?>) {
	        JList<Family> list = (JList)c;
	        removeIndex = list.getSelectedIndex();
	        Family f = list.getSelectedValue();
	        t = new FamilyTransferable(f);
    	}
 
        return t;
    }
    
    /**
     * We support both copy and move actions.
     */
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
    }
    
    /**
     * Perform the actual import.  This demo only supports drag and drop.
     */
    public boolean importData(TransferHandler.TransferSupport info) {
        if (!info.isDrop()) {
            return false;
        }

        JList<Family> list = (JList<Family>)info.getComponent();
        DefaultListModel<Family> listModel = (DefaultListModel<Family>)list.getModel();
        JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
        int index = dl.getIndex();
        boolean insert = dl.isInsert();

        // Get the string that is being dropped.
        Transferable t = info.getTransferable();
        Family data;
        try {
            data = (Family)t.getTransferData(FamilyTransferable.FAMILY_ITEM_DATA_FLAVOR);
        } 
        catch (Exception e) { return false; }
                                
        // Wherever there is a newline in the incoming data,
        // break it into a separate item in the list.
        //String[] values = data.split("\n");
//        
        addIndex = index;
//        addCount = values.length;
        
        // Perform the actual import.  
        if(insert) listModel.add(index, data);

        if(assignment != null) {
        	assignment.setChanged(true);
        	data.setChanged(true);
        }else {
        	data.setChanged(false);
        }
        return true;
    }

    /**
     * Remove the items moved from the list.
     */
    protected void exportDone(JComponent c, Transferable data, int action) {
        JList<Family> source = (JList<Family>)c;
        DefaultListModel<Family> listModel  = (DefaultListModel<Family>)source.getModel();
        
        if (action == TransferHandler.MOVE) {
        	if(addIndex > -1 && addIndex < removeIndex) removeIndex++;
        	listModel.remove(removeIndex);
        }
        
        addIndex = -1;
    }
    
    public static class FamilyTransferable implements Transferable {

        public static final DataFlavor FAMILY_ITEM_DATA_FLAVOR = new DataFlavor(Family.class, "java/Family");
        private Family listItem;

        public FamilyTransferable(Family listItem) {
            this.listItem = listItem;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{FAMILY_ITEM_DATA_FLAVOR};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(FAMILY_ITEM_DATA_FLAVOR);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

            return listItem;

        }
    }


}
