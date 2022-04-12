package tn.esprit.meetico.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import tn.esprit.meetico.util.SMSResponse;

@Service
public class MessageServiceImpl {

	@Value("ACc8c758752234bf7b1d1a205a28881fc8")
	private String accountSID;

	@Value("74da898118c99f7033368e83cb679b1c")
	private String accountAuthToken;

	@Value("+16098535521")
	private String twilloSenderNumber;

	public void sendSMS(SMSResponse smsResponse) {
		
		try {
			Twilio.init(accountSID, accountAuthToken);
			String smsText = smsResponse.getText();
			String mobileNumber = smsResponse.getMobileNumber();
			PhoneNumber recieverPhoneNumber = new PhoneNumber(mobileNumber);
			PhoneNumber senderTwilloPhoneNumber = new PhoneNumber(twilloSenderNumber);
			com.twilio.rest.api.v2010.account.Message.creator(recieverPhoneNumber, senderTwilloPhoneNumber, smsText).create();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
