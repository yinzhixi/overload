package org.iot.client.common;

import java.util.HashMap;
import java.util.Map;

public class ClientKey {
    public static final String SERVER_KEY = "2a0142f066a8182386602775b14eb6c534dd0ee4e8f4a79abf97976a488d66da4d2b6b734d43e7282f9bdc7e9ec46ee3b9f412602c655fe53250ce0bbeb53643";
    
    private static Map<String,String> clientKeyMap = new HashMap<String,String>(){
        
        private static final long serialVersionUID = 8831442226138309701L;
        {
            put("QSY_00001", "2a0142f066a8182386602775b14eb6c534dd0ee4e8f4a79abf97976a488d66da4d2b6b734d43e7282f9bdc7e9ec46ee3b9f412602c655fe53250ce0bbeb53643");
            put("QSY_00002", "2a0142f066a8182386602775b14eb6c534dd0ee4e8f4a79abf97976a488d66da4d2b6b734d43e7282f9bdc7e9ec46ee3b9f412602c655fe53250ce0bbeb53643");
            put("QSY_00003", "2a0142f066a8182386602775b14eb6c534dd0ee4e8f4a79abf97976a488d66da4d2b6b734d43e7282f9bdc7e9ec46ee3b9f412602c655fe53250ce0bbeb53643");
            put("QSY_00004", "2a0142f066a8182386602775b14eb6c534dd0ee4e8f4a79abf97976a488d66da4d2b6b734d43e7282f9bdc7e9ec46ee3b9f412602c655fe53250ce0bbeb53643");
            put("QSY_00005", "2a0142f066a8182386602775b14eb6c534dd0ee4e8f4a79abf97976a488d66da4d2b6b734d43e7282f9bdc7e9ec46ee3b9f412602c655fe53250ce0bbeb53643");
            put("QSY_Weight_System_WebView", "2a0142f066a8182386602775b14eb6c534dd0ee4e8f4a79abf97976a488d66da4d2b6b734d43e7282f9bdc7e9ec46ee3b9f412602c655fe53250ce0bbeb53643");
        }
    };
    
    public static String getClientKey(String clientId) {
        return clientKeyMap.get(clientId);
    }
    public static Map<String,String> getClientKeyMap(){
        return clientKeyMap;
    }
}
