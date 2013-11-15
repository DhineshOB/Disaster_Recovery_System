/**
 * PersistentInputStream.java
 *
 * (C) Copyright IBM Corp. 2005, 2009
 *
 * THIS FILE IS PROVIDED UNDER THE TERMS OF THE ECLIPSE PUBLIC LICENSE 
 * ("AGREEMENT"). ANY USE, REPRODUCTION OR DISTRIBUTION OF THIS FILE 
 * CONSTITUTES RECIPIENTS ACCEPTANCE OF THE AGREEMENT.
 *
 * You can obtain a current copy of the Eclipse Public License from
 * http://www.opensource.org/licenses/eclipse-1.0.php
 *
 * @author: Roberto Pineiro, IBM, roberto.pineiro@us.ibm.com  
 * @author: Chung-hao Tan, IBM ,chungtan@us.ibm.com
 * 
 * 
 * Change History
 * Flag       Date        Prog         Description
 *------------------------------------------------------------------------------- 
 * 1535756    2006-08-07  lupusalex    Make code warning free
 * 2807325    2009-06-22  blaschke-oss Change licensing from CPL to EPL
 *
 */
package org.sblim.wbem.http.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PersistentInputStream extends FilterInputStream {

	boolean iClosable = false;

	boolean iClosed = false;

	public PersistentInputStream(InputStream is) {
		this(is, false);
	}

	public PersistentInputStream(InputStream is, boolean closable) {
		super(is);
		this.iClosable = closable;
	}

	public synchronized void close() throws IOException {
		if (!iClosed) {
			iClosed = true;
			if (iClosable) in.close();
		} else throw new IOException("Error while closing the input stream. It was already closed");
	}
}
