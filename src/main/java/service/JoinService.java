package service;

import db.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.RequestHandler;

public class JoinService {

    private User user;
    private String userId;
    private static final Logger logger = LoggerFactory.getLogger(JoinService.class);

    public String joinUser(String[] parseData) {
        userId = "";
        try{

            int idxUserIdData = 0;
            int idxPasswordData = 0;
            int idxNameData = 0;
            int idxEmailData = 0;
            for (int i = 0; i < parseData.length; i++) {
                if(parseData[i].equals("userId")) idxUserIdData = i+1;
                if(parseData[i].equals("password")) idxPasswordData = i+1;
                if(parseData[i].equals("name")) idxNameData = i+1;
                if(parseData[i].equals("email")) idxEmailData = i+1;
            }
            logger.debug("InsertParseData : {}", parseData[idxUserIdData]);
            logger.debug("InsertParseData : {}", parseData[idxPasswordData]);
            logger.debug("InsertParseData : {}", parseData[idxNameData]);
            logger.debug("InsertParseData : {}", parseData[idxEmailData]);
            user = new User(parseData[idxUserIdData],
                    parseData[idxPasswordData],
                    parseData[idxNameData],
                    parseData[idxEmailData]);
            Database.addUser(user);
        } catch (ArrayIndexOutOfBoundsException e){

        } catch (IndexOutOfBoundsException e){

        }
        return userId;
    }
}
