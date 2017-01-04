using System;
using System.Collections.Generic;
using System.Linq;
using Microsoft.Owin;
using Owin;
using Microsoft.IdentityModel.Clients.ActiveDirectory;
using Microsoft.SqlServer.Management.AlwaysEncrypted.AzureKeyVaultProvider;
using System.Data.SqlClient;

[assembly: OwinStartup(typeof(WebAppKeyVaultNET.Startup))]

namespace WebAppKeyVaultNET
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            InitializeAzureKeyVaultProvider();
        }

        private static Microsoft.IdentityModel.Clients.ActiveDirectory.ClientCredential _clientCredential;

        static void InitializeAzureKeyVaultProvider()
        {
            string clientId = System.Environment.GetEnvironmentVariable("applicationADID");
            string clientSecret = System.Environment.GetEnvironmentVariable("applicationADSecret");

            _clientCredential = new ClientCredential(clientId, clientSecret);

            SqlColumnEncryptionAzureKeyVaultProvider azureKeyVaultProvider =
               new SqlColumnEncryptionAzureKeyVaultProvider(GetToken);

            Dictionary<string, SqlColumnEncryptionKeyStoreProvider> providers =
              new Dictionary<string, SqlColumnEncryptionKeyStoreProvider>();

            providers.Add(SqlColumnEncryptionAzureKeyVaultProvider.ProviderName, azureKeyVaultProvider);
            SqlConnection.RegisterColumnEncryptionKeyStoreProviders(providers);
        }

        public async static System.Threading.Tasks.Task<string> GetToken(string authority, string resource, string scope)
        {
            var authContext = new AuthenticationContext(authority);
            AuthenticationResult result = await authContext.AcquireTokenAsync(resource, _clientCredential);

            if (result == null)
                throw new System.InvalidOperationException("Failed to obtain the access token");

            return result.AccessToken;
        }

    }
}
