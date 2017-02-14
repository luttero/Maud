/*
 * @(#)DoubleColumnQPLDataFile.java created 28/03/2000 Le Mans
 *
 * Copyright (c) 2000 Luca Lutterotti All Rights Reserved.
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

package it.unitn.ing.rista.diffr.data;

import it.unitn.ing.rista.diffr.*;

import java.io.*;
import java.lang.*;
import java.util.*;

import it.unitn.ing.rista.util.*;

/**
 * The DoubleColumnQPLDataFile is a class to load datafile
 * consisting of only two columns, one with the q
 * and the other with the intensity. First line contains lambda for conversion.
 * Plot in Log format. Weights in I.
 *
 *
 * @version $Revision: 1.8 $, $Date: 2006/01/19 14:45:55 $
 * @author Luca Lutterotti
 * @since JDK1.1
 */

public class DoubleColumnQPLDataFile extends DoubleColumnQDataFile {

  public DoubleColumnQPLDataFile(XRDcat aobj, String alabel) {
    super(aobj, alabel);
    identifier = ".dcq";
  }

  public DoubleColumnQPLDataFile() {
    identifier = ".dcq";
  }

  public boolean readallSpectra() {

    boolean loadSuccessfull = false;
    boolean tmpB = isAbilitatetoRefresh;
    isAbilitatetoRefresh = false;
    BufferedReader reader = getReader();
    if (reader != null) {
      try {

        dspacingbase = false;
        Vector x = new Vector(100, 100);
        Vector y = new Vector(100, 100);

        datanumber = 0;

        String token1 = new String("");
        String token2 = new String("");

        String linedata = null;
        StringTokenizer st = null;

//          String linedata = reader.readLine();
//          StringTokenizer st = new StringTokenizer(linedata, "' ,\t\r\n");
//          	if (st.hasMoreTokens())
//          		token1 = st.nextToken();
        double lambda = getDataFileSet().getInstrument().getRadiationType().getMeanRadiationWavelength(); //Double.valueOf(token1).doubleValue();

        linedata = reader.readLine();
        while (linedata != null) {

          st = new StringTokenizer(linedata, "' ,\t\r\n");

          if (st.hasMoreTokens())
            token1 = st.nextToken();
          if (st.hasMoreTokens())
            token2 = st.nextToken();

          datanumber++;
          x.addElement(token1);
          y.addElement(token2);

          linedata = reader.readLine();
        }

        initData(datanumber);

        double twotheta = 0.0;
        for (int i = 0; i < datanumber; i++) {
          token1 = (String) x.elementAt(i);
          token2 = (String) y.elementAt(i);
          twotheta = 2 * MoreMath.asind(Double.valueOf(token1).doubleValue() * lambda / (4 * Math.PI));
          setXData(i, twotheta);
          double intensityValue = Double.valueOf(token2).doubleValue();
          if (intensityValue < 0.0) // we will not accept it, we suppose is an error
            intensityValue = 0.0;
          setYData(i, intensityValue);
          double tmpweight = intensityValue;
          if (tmpweight <= 0.0)
            tmpweight = 0.0;
          else
            tmpweight = Math.abs(MoreMath.log10(tmpweight));
          if (tmpweight != 0.0)
            setWeight(i, 1.0 / tmpweight);
          else
            setWeight(i, 1.0);
        }
        loadSuccessfull = true;

      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error in loading the data file! Try to remove this data file");
      }
      try {
        reader.close();
      } catch (IOException e) {
      }
    }
    isAbilitatetoRefresh = tmpB;
    return loadSuccessfull;
  }
}
