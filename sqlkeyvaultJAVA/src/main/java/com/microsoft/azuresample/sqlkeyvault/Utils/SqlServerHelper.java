package com.microsoft.azuresample.sqlkeyvault.Utils;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import com.microsoft.sqlserver.jdbc.*;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class SqlServerHelper {

    public static String clientidAD;

    public static String clientkeyAD;

    public static String sqlurl;

    private static ExecutorService service = Executors.newFixedThreadPool(10);

    private static SQLServerKeyVaultAuthenticationCallback authenticationCallback = new SQLServerKeyVaultAuthenticationCallback() {
        @Override
        public String getAccessToken(String authority, String resource, String scope) {
            AuthenticationResult result = null;
            try{
                AuthenticationContext context = new AuthenticationContext(authority, false, service);
                System.out.println("### INIT KeyVault / AD clientID: " + clientidAD);
                ClientCredential cred = new ClientCredential(
                        clientidAD,
                        clientkeyAD);

                Future<AuthenticationResult> future = context.acquireToken(resource, cred, null);
                result = future.get();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return result.getAccessToken();
        }
    };

    private static SQLServerColumnEncryptionAzureKeyVaultProvider akvProvider;
    private static Map<String, SQLServerColumnEncryptionKeyStoreProvider> providerMap;

    static {
    }

    public static SQLServerConnection GetConnection() throws SQLException {
        SQLServerConnection connection = (SQLServerConnection) DriverManager.getConnection(sqlurl);
        return connection;
    }

    @PostConstruct
    public void Init(){
        Map<String, String> env = System.getenv();
        clientidAD = env.get("SQLSERVER_CLIENTID");
        clientkeyAD = env.get("SQLSERVER_CLIENTKEY");
        sqlurl = env.get("SQLSERVER_URL");

        System.out.println("### INIT of SqlServerHelper called.");

        try {
            akvProvider = new SQLServerColumnEncryptionAzureKeyVaultProvider(authenticationCallback, service);
        } catch (SQLServerException e) {
            e.printStackTrace();
        }

        providerMap = new HashMap<>();
        providerMap.put("AZURE_KEY_VAULT", akvProvider);

        try {
            SQLServerConnection.registerColumnEncryptionKeyStoreProviders(providerMap);
        } catch (SQLServerException e) {
            e.printStackTrace();
        }
    }
}
