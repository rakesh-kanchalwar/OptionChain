package com.rakeshk.optionchain;

import java.io.IOException;
import java.security.GeneralSecurityException;

//@SpringBootApplication
public class OptionchainApplication {

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    
    
	public static void main(String[] args) throws GeneralSecurityException, IOException {
			final String spreadsheetId = "1PTmpqZrVLAYeOxnqw-DeIX82gebYAxqVXNdr7CEERA8";
			boolean includeIntermediateStrikePrice = false;
			int minRange = 31500;
			int maxRange = 32000;
			String sheetNameWithHeader = "BankNifty!A1:B2";
			String sheetNameWithoutHeader = "BankNifty!A2:B2";
			boolean insertHeader = true;
			SheetService sheetService = new SheetService();
			sheetService.updateSheet(spreadsheetId, sheetNameWithHeader, sheetNameWithoutHeader, includeIntermediateStrikePrice, minRange, maxRange, insertHeader);
	}

}
