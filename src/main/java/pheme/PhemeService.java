package pheme;

import timedelayqueue.PubSubMessage;

import java.io.File;
import java.util.*;

// TODO: write a description for this class
// TODO: complete all methods, irrespective of whether there is an explicit TODO or not
// TODO: write clear specs
// TODO: State the rep invariant and abstraction function
// TODO: what is the thread safety argument?
public class PhemeService {

    public static final int DELAY = 1000; // 1 second or 1000 milliseconds
    private File twitterCredentialsFile;
    private final Map<String, String> user=Collections.synchronizedMap(new LinkedHashMap<>());
    private final ArrayList<UUID> userid=new ArrayList<>();
    private final List<ArrayList<String>> sub= Collections.synchronizedList(new ArrayList<>());


    public PhemeService(File twitterCredentialsFile) {
        this.twitterCredentialsFile = twitterCredentialsFile;
    }

    public void saveState(String configDirName) {

    }

    /**
     * add a user to PhemeService
     * @param userID the id of the user to be added
     * @param userName a string of name of the user to be added
     * @param hashPassword a hashed version of the password
     * @return true if the user was successfully added and false otherwise
     */
    public boolean addUser(UUID userID, String userName, String hashPassword) {
        if(user.containsKey(userName)){
            return false;
        }else{
            user.put(userName,hashPassword);
            userid.add(userID);
            return true;
        }
    }

    /**
     * remove the given user
     * @param userName a string of the name of the user to be removed
     * @param hashPassword a hashed version of the password to be removed
     * @return true if the user was removed successfully and false otherwise
     */

    public boolean removeUser(String userName, String hashPassword) {
        if(user.containsKey(userName)){
            if (user.get(userName).equals(hashPassword)){
                ArrayList<String> username=new ArrayList<>(user.keySet());
                int index=username.indexOf(userName);
                user.remove(userName);
                userid.remove(index);
                return true;
            }
        }
        return false;
    }

    /**
     * cancel subscription with given Twitter username
     * @param userName a string of the name of the user
     * @param hashPassword a hashed version of the password of the user
     * @param twitterUserName name of the Twitter user to be canceled
     * @return true if the subscription has been successfully canceled and false otherwise
     */

    public boolean cancelSubscription(String userName,
                                      String hashPassword,
                                      String twitterUserName) {
        // check if the user is existed
        if(user.containsKey(userName)&&user.get(userName).equals(hashPassword)){
            boolean existed=false;
            for(ArrayList<String> arr:sub){
                if (arr.get(0).equals(userName) && arr.get(1).equals(twitterUserName)) {
                    existed = true;
                    break;
                }
            }
            if(existed) {
                sub.removeIf(x -> x.get(0).equals(userName) && x.get(1).equals(twitterUserName));
                return true;
            }
        }
        return false;
    }

    /**
     *
     * cancel subscription with given Twitter username
     * @param userName a string of the name of the user
     * @param hashPassword a hashed version of the password of the user
     * @param twitterUserName name of the Twitter user to be canceled
     * @param pattern the pattern string made by Twitter User
     * @return true if the subscription has been successfully canceled and false otherwise
     */

    public boolean cancelSubscription(String userName,
                                      String hashPassword,
                                      String twitterUserName,
                                      String pattern) {
        if(user.containsKey(userName)&&user.get(userName).equals(hashPassword)){
            boolean existed=false;
            for(ArrayList<String> arr:sub){
                if (arr.size() == 3 && arr.get(0).equals(userName) && arr.get(1).equals(twitterUserName) && arr.get(2).equals(pattern)) {
                    existed = true;
                    break;
                }
            }
            if(existed) {
                sub.removeIf(x -> x.size() > 2 && x.get(0).equals(userName) && x.get(1).equals(twitterUserName) && x.get(2).equals(pattern));
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param userName
     * @param hashPassword
     * @param twitterUserName
     * @return
     */

    public boolean addSubscription(String userName, String hashPassword,
                                   String twitterUserName) {
        // check if the user is existed
        if(user.containsKey(userName)&&user.get(userName).equals(hashPassword)){
            for(ArrayList<String> a:sub){
                //check duplicate
                if(a.size()==2&&a.get(0).equals(userName)&&a.get(1).equals(twitterUserName)){
                    return false;
                }
            }
            ArrayList<String> new_sub=new ArrayList<>(2);
            new_sub.add(userName);
            new_sub.add(twitterUserName);
            sub.add(new_sub);
            return true;
        }
        return false;
    }

    /**
     *
     * @param userName
     * @param hashPassword
     * @param twitterUserName
     * @param pattern
     * @return
     */

    public boolean addSubscription(String userName, String hashPassword,
                                   String twitterUserName,
                                   String pattern) {
        // check if the user is existed
        if(user.containsKey(userName)&&user.get(userName).equals(hashPassword)){
            for(ArrayList<String> a:sub){
                //check duplicate
                if(a.size()==3&&a.get(0).equals(userName)&&a.get(1).equals(twitterUserName)&&a.get(2).equals(pattern)){
                    return false;
                }
            }
            ArrayList<String> new_sub=new ArrayList<>();
            new_sub.add(userName);
            new_sub.add(twitterUserName);
            new_sub.add(pattern);
            sub.add(new_sub);
            return true;
        }
        return false;
    }


    /**
     *
     * @param userName
     * @param hashPassword
     * @param msg
     * @return
     */
    public boolean sendMessage(String userName,
                               String hashPassword,
                               PubSubMessage msg) {

        return false;
    }


    /**
     *
     * @param msgID
     * @param userList
     * @return
     */
    public List<Boolean> isDelivered(UUID msgID, List<UUID> userList) {
        return new ArrayList<Boolean>();
    }


    /**
     *
     * @param msgID
     * @param user
     * @return
     */
    public boolean isDelivered(UUID msgID, UUID user) {
        return false;
    }


    /**
     *
     * @param userName
     * @return
     */
    public boolean isUser(String userName) {
        return false;
    }


    /**
     *
     * @param userName
     * @param hashPassword
     * @return
     */
    public PubSubMessage getNext(String userName, String hashPassword) {
        return PubSubMessage.NO_MSG;
    }


    /**
     *
     * @param userName
     * @param hashPassword
     * @return
     */
    public List<PubSubMessage> getAllRecent(String userName, String hashPassword) {
        return new ArrayList<PubSubMessage>();
    }
}