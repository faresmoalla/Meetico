package tn.esprit.meetico.message;


public class ResponseMessage {
	 private String content;

	    public ResponseMessage() {
	    }

	    public ResponseMessage(String content) {
	        this.content = content;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }
}

