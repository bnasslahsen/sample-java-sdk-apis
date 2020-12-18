package com.oracle.oci.sdk.sample.repository;

import com.oracle.bmc.core.ComputeClient;
import com.oracle.bmc.core.requests.GetInstanceRequest;
import com.oracle.bmc.core.requests.InstanceActionRequest;
import com.oracle.bmc.core.responses.GetInstanceResponse;
import com.oracle.oci.sdk.sample.config.PemConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author bnasslahsen
 */
@Component
public class ComputeRepository {

	@Autowired
	private PemConfig pemConfig;


	public void doAction(String ocid, String action) {
		ComputeClient computeClient = getComputeClient();
		InstanceActionRequest instanceActionRequest = InstanceActionRequest.builder()
				.instanceId(ocid)
				.action(action).build();
		computeClient.instanceAction(instanceActionRequest);
	}

	public String getInstanceStatus(String ocid) {
		ComputeClient computeClient = getComputeClient();
		GetInstanceRequest instanceRequest = GetInstanceRequest.builder().instanceId(ocid).build();
		GetInstanceResponse instanceResponse = computeClient.getInstance(instanceRequest);
		if (instanceResponse != null)
			return instanceResponse.getInstance().getLifecycleState().getValue();
		return null;
	}

	private ComputeClient getComputeClient() {
		final ComputeClient computeClient = new ComputeClient(pemConfig.getAuthenticationDetailsProvider());
		computeClient.setRegion(pemConfig.getRegion());
		return computeClient;
	}
}
