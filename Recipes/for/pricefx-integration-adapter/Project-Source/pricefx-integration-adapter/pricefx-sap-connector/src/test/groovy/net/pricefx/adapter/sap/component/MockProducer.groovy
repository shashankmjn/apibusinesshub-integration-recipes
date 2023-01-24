package net.pricefx.adapter.sap.component

import com.sap.it.api.exception.InvalidContextException
import com.sap.it.api.securestore.exception.SecureStoreException
import net.pricefx.adapter.sap.operation.CredentialsOperation
import net.pricefx.connector.common.connection.PFXOperationClient
import net.pricefx.connector.common.util.ConnectionUtil

class MockProducer extends Producer {
    private PFXOperationClient pfxOperationClient

    MockProducer(org.apache.camel.Endpoint endpoint, PFXOperationClient pfxOperationClient) {
        super(endpoint)
        this.pfxOperationClient = pfxOperationClient
    }

    @Override
    protected PFXOperationClient createPfxClient(CredentialsOperation credentialsOperation) {
        return pfxOperationClient
    }

    @Override
    protected CredentialsOperation createCredentialsOperation() throws SecureStoreException, MalformedURLException, InvalidContextException {
        return new CredentialsOperation("token", "partition","app.pricefx.eu"){
            @Override
            protected void init(String securityMaterial, String host, String partition){
                setJwtToken(securityMaterial)
                setPricefxHost(host)
                setClientId(partition)
            }
        }
    }
}