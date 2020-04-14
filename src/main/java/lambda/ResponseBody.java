package lambda;

public class ResponseBody {

    private String answer;

    public ResponseBody(){
    }

    private ResponseBody(Builder builder) {
        setAnswer(builder.answer);
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static final class Builder {

        private String answer;

        public Builder() {
        }

        public Builder answer(String val) {
            answer = val;
            return this;
        }

        public ResponseBody build() {
            return new ResponseBody(this);
        }
    }
}
