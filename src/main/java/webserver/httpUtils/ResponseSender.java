package webserver.httpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseSender {

    private static final Logger logger = LoggerFactory.getLogger(ResponseSender.class);

    private Response res;
    public ResponseSender(Response res) {
        this.res = res;
    }


//    private void makeResponseLine(ReqLine reqLine) {
//        res.getResLine().setVersion(reqLine.getVersion());
//
//        if (isSignUp(reqLine.getQuery())) {
//            res.getResLine().setStatCode(302);
//            res.getResLine().setStatText("FOUND");
//            return;
//    }

//    private void makeHeaderAndBody(ReqLine reqLine) throws IOException {
//        Map<String, String> header = new HashMap<>();
//        if(res.getResLine().getStatCode() == 302)
//        {
//            header.put("Location: ", Paths.HOME_PATH);
//            res.setResHeader(header);
//            return;
//        }
//    }

    public void sendRes(OutputStream out) throws IOException
    {
        DataOutputStream dos = new DataOutputStream(out);

        // resLine
        dos.writeBytes(res.getResLine().getVersion() + " " +
                res.getResLine().getStatCode() + " " +
                res.getResLine().getStatText() + "\r\n");
        // header
        res.getResHeader().getAll().entrySet().forEach(elem ->
                {
                    logger.debug(elem.getKey() + elem.getValue());
                    try {
                        dos.writeBytes(elem.getKey() +": "+ elem.getValue() + "\r\n");
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                }
        );
        dos.writeBytes("\r\n");
        // body
        if(res.getResBody() != null)
            dos.write(res.getResBody(), 0, res.getResBody().length);
        dos.flush();
    }
}
