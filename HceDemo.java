package org.nfctools.examples.hce;

import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;
import org.nfctools.examples.TerminalUtils;

public class HceDemo {

	public void run() throws CardException {
           
              TerminalFactory terminalFactory = TerminalFactory.getDefault();
		CardTerminals cardTerminals = terminalFactory.terminals();
            CardTerminal cardTerminal = cardTerminals.list().get(0);
//        if(cardTerminals.list().size()>0){
          System.out.println(cardTerminal.getName());
//        }
		HostCardEmulationTagScanner tagScanner = new HostCardEmulationTagScanner(cardTerminal);
		tagScanner.run();
	}

	public static void main(String[] args) throws CardException {      
		new HceDemo().run();
	}
}
