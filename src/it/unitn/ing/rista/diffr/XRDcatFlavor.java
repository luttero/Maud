/*
 * @(#)XRDcatFlavor.java created 4/05/1999 Bologna-Verona
 *
 * Copyright (c) 1998 Luca Lutterotti All Rights Reserved.
 *
 * This software is the research result of Luca Lutterotti and it is
 * provided as it is as confidential and proprietary information.
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you
 * entered into with the author.
 *
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */

package it.unitn.ing.rista.diffr;

import java.awt.*;
import java.awt.datatransfer.*;

/**
 *  The XRDcatFlavor is a class to copy and paste XRDcat objects trough the
 *  clipboard
 *
 *
 * @version $Revision: 1.3 $, $Date: 2004/08/12 09:36:05 $
 * @author Luca Lutterotti
 * @since JDK1.1
 */

public class XRDcatFlavor implements Transferable, ClipboardOwner {

  public static DataFlavor xrdFlavor;
  private DataFlavor[] supportedFlavors = null;
  public static XRDcat xrdObj = null;
  String tempobj = new String("");

  public XRDcatFlavor(XRDcat xrdobj) {

    xrdObj = xrdobj;
    try {
      xrdFlavor = new DataFlavor(Class.forName("java.lang.String"), "String");
      supportedFlavors = new DataFlavor[1];
      supportedFlavors[0] = xrdFlavor;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

  }

  public synchronized DataFlavor[] getTransferDataFlavors() {
    return supportedFlavors;

  }

  public boolean isDataFlavorSupported(DataFlavor parFlavor) {

    return parFlavor.equals(xrdFlavor);

  }

  public synchronized Object getTransferData(DataFlavor parFlavor)
          throws UnsupportedFlavorException {
    if (parFlavor.equals(xrdFlavor))
      return tempobj;
//			return xrdObj;
    else
      throw new UnsupportedFlavorException(xrdFlavor);

  }

  public void lostOwnership(Clipboard parClipboard, Transferable parTransferable) {

  }
}

