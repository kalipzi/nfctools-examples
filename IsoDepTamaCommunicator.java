package org.nfctools.examples.hce;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.nfctools.io.ByteArrayReader;
import org.nfctools.io.ByteArrayWriter;
import org.nfctools.spi.tama.AbstractTamaCommunicator;
import org.nfctools.spi.tama.request.DataExchangeReq;
import org.nfctools.spi.tama.request.InListPassiveTargetReq;
import org.nfctools.spi.tama.response.DataExchangeResp;
import org.nfctools.spi.tama.response.InListPassiveTargetResp;
import org.nfctools.utils.NfcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IsoDepTamaCommunicator extends AbstractTamaCommunicator {

	private Logger log = LoggerFactory.getLogger(getClass());
	private int messageCounter = 0;
	private static final byte[] CLA_INS_P1_P2 = { 0x00, (byte)0xA4, 0x04, 0x00 };
	private static final byte[] AID_ANDROID = { (byte)0xF0, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06 };
        String dataIn;
        Excel excel=new Excel();

	public IsoDepTamaCommunicator(ByteArrayReader reader, ByteArrayWriter writer) {
		super(reader, writer);
	}

	private byte[] createSelectAidApdu(byte[] aid) {
		byte[] result = new byte[6 + aid.length];
		System.arraycopy(CLA_INS_P1_P2, 0, result, 0, CLA_INS_P1_P2.length);
		result[4] = (byte)aid.length;
		System.arraycopy(aid, 0, result, 5, aid.length);
		result[result.length - 1] = 0;
		return result;
	}

	public void connectAsInitiator() throws IOException {
		while (true) {
			InListPassiveTargetResp inListPassiveTargetResp = sendMessage(new InListPassiveTargetReq((byte)1, (byte)0,
					new byte[0]));
                        System.out.printf("%d\n",inListPassiveTargetResp.getNumberOfTargets());
			if (inListPassiveTargetResp.getNumberOfTargets() > 0) {
				log.info("TargetData: " + NfcUtils.convertBinToASCII(inListPassiveTargetResp.getTargetData()));
				if (inListPassiveTargetResp.isIsoDepSupported()) {
					log.info("IsoDep Supported");
					byte[] selectAidApdu = createSelectAidApdu(AID_ANDROID);
					DataExchangeResp resp = sendMessage(new DataExchangeReq(inListPassiveTargetResp.getTargetId(),
							false, selectAidApdu, 0, selectAidApdu.length));
					 dataIn = new String(resp.getDataOut());
                                        log.info("Received: " + dataIn);
                                        try {
			File file = new File("C:\\Users\\Zahra\\Documents\\NetBeansProjects\\PHONE.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
				if ((line = bufferedReader.readLine()).equals(dataIn) ) { 
//                                    dataIn.startsWith("0")
				stringBuffer.append(line);
                               	fileReader.close();
			System.out.println("Contents of file:");
			System.out.println(stringBuffer.toString());
                        exchangeData(inListPassiveTargetResp);

						
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
				
                                               
					
				}
				else {
					log.info("IsoDep NOT Supported");
				}
				break;
			}
			else {
				try {
					Thread.sleep(100);
				}
				catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	private void exchangeData(InListPassiveTargetResp inListPassiveTargetResp) throws IOException {
		DataExchangeResp resp;
		String dataInn;
		while (true) {
			byte[] dataOut = ("are you sure?").getBytes();
//                        "Message from desktop: " + messageCounter++
			resp = sendMessage(new DataExchangeReq(inListPassiveTargetResp.getTargetId(), false, dataOut, 0,
					dataOut.length));
			dataInn = new String(resp.getDataOut());
			log.info("Received: " + dataInn);
                         log.info("hello: " );
                         if(dataInn.equals("ok"))
                         excel.Excel(dataIn);
		}
}}
