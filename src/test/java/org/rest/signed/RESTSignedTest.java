package org.rest.signed;

import javax.ws.rs.core.MediaType;

import junit.framework.Assert;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.security.doseta.DKIMSignature;
import org.jboss.resteasy.security.doseta.DosetaKeyRepository;
import org.jboss.resteasy.security.doseta.KeyRepository;
import org.junit.Test;

public class RESTSignedTest {
	private final String URL = "http://localhost:8080/RESTSigned/rest/signed/post";

	@SuppressWarnings("rawtypes")
	@Test
	public void testPOSTSign() throws Exception {
		DosetaKeyRepository repository = new org.jboss.resteasy.security.doseta.DosetaKeyRepository();
		repository.setKeyStorePath("RESTSigned.jks");
		repository.setKeyStorePassword("redhat");
		repository.setUseDns(false);
		repository.start();

		DKIMSignature signature = new DKIMSignature();
		signature.setDomain("sample.com");
		signature.setSelector("burke");

		ClientRequest request = new ClientRequest(URL);
		request.getAttributes().put(KeyRepository.class.getName(), repository);
		request.header("DKIM-Signature", signature);
		request.body(MediaType.TEXT_PLAIN, "Hello MAMA");
		ClientResponse response = request.post();
		Assert.assertEquals(response.getStatus(), 204);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testPOSTNotSigned() throws Exception {
		DosetaKeyRepository repository = new org.jboss.resteasy.security.doseta.DosetaKeyRepository();
		repository.setKeyStorePath("RESTSigned.jks");
		repository.setKeyStorePassword("redhat");
		repository.setUseDns(false);
		repository.start();

		ClientRequest request = new ClientRequest(URL);
		request.getAttributes().put(KeyRepository.class.getName(), repository);
		request.body(MediaType.TEXT_PLAIN, "Hello MAMA");
		ClientResponse response = request.post();
		Assert.assertEquals(response.getStatus(), 401);
	}
}
