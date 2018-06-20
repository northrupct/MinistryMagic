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
import com.blackops.model.Minister;

public class MinisterTransferHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int removeIndex = -1;
    private int addIndex = -1; //Location where items were added
    private Assignment assignment = null;

	
    MinisterTransferHandler() {
	}
    
	MinisterTransferHandler(Assignment a) {
		assignment = a;
	}
	
    /**
     * We only support importing strings.
     */
    public boolean canImport(TransferHandler.TransferSupport info) {
        // Check for String flavor
    	if (!info.isDataFlavorSupported(MinisterTransferable.MINISTER_ITEM_DATA_FLAVOR)) {
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
	        JList<Minister> list = (JList<Minister>)c;
	        //indices = list.getSelectedIndices();
	        removeIndex = list.getSelectedIndex();
	        Minister f = list.getSelectedValue();
	        t = new MinisterTransferable(f);
    	}
       // Object[] values = list.get.getSelectedValues();
        
//        StringBuffer buff = new StringBuffer();
//
//        for (int i = 0; i < values.length; i++) {
//            Object val = values[i];
//            buff.append(val == null ? "" : val.toString());
//            if (i != values.length - 1) {
//                buff.append("\n");
//            }
//        }
        
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

        JList<Minister> list = (JList<Minister>)info.getComponent();
        DefaultListModel<Minister> listModel = (DefaultListModel<Minister>)list.getModel();
        JList.DropLocation dl = (JList.DropLocation)info.getDropLocation();
        int index = dl.getIndex();
        boolean insert = dl.isInsert();

        // Get the string that is being dropped.
        Transferable t = info.getTransferable();
        Minister data;
        try {
            data = (Minister)t.getTransferData(MinisterTransferable.MINISTER_ITEM_DATA_FLAVOR);
        } 
        catch (Exception e) { return false; }
                                
        // Wherever there is a newline in the incoming data,
        // break it into a separate item in the list.
//        String[] values = data.split("\n");
//        
        addIndex = index ;
//        addCount = values.length;
        
        // Perform the actual import.  
        if(insert)  listModel.add(index++, data);
        	
//        for (int i = 0; i < values.length; i++) {
//            if (insert) {
//                listModel.add(index++, values[i]);
//            } else {
//                // If the items go beyond the end of the current
//                // list, add them in.
//                if (index < listModel.getSize()) {
//                    listModel.set(index++, values[i]);
//                } else {
//                    listModel.add(index++, values[i]);
//                }
//            }
//        }
        
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
        JList<Minister> source = (JList<Minister>)c;
        DefaultListModel<Minister> listModel  = (DefaultListModel<Minister>)source.getModel();
        
        if (action == TransferHandler.MOVE) {
        	if(addIndex > -1 && addIndex < removeIndex) removeIndex++;
        	listModel.remove(removeIndex);
        }
        
        //indices = null;
        addIndex = -1;
    }
    
    public static class MinisterTransferable implements Transferable {

        public static final DataFlavor MINISTER_ITEM_DATA_FLAVOR = new DataFlavor(Minister.class, "java/Minister");
        private Minister listItem;

        public MinisterTransferable(Minister listItem) {
            this.listItem = listItem;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{MINISTER_ITEM_DATA_FLAVOR};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(MINISTER_ITEM_DATA_FLAVOR);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {

            return listItem;

        }
    }


}
