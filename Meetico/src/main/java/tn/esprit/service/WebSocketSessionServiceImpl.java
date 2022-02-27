package tn.esprit.service;

import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WebSocketSessionServiceImpl {
    
    private Map<String, Principal> sessionMap = new ConcurrentHashMap<>();
    
    public void setUserSession(String userName, Principal principal) {
        sessionMap.put(userName, principal);
    }
    
    public void removeSession(String userName) {
        sessionMap.remove(userName);
    }
    
    public boolean sessionExists(String userName) {
        return sessionMap.containsKey(userName);
    }
    
}
