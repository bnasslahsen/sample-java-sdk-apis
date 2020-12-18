package com.oracle.oci.sdk.sample.api;

import com.oracle.oci.sdk.sample.model.MetricDetails;
import com.oracle.oci.sdk.sample.repository.ComputeRepository;
import com.oracle.oci.sdk.sample.repository.MetricDetailsRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bnasslahsen
 */
@RestController
@RequestMapping("/api")
public class SpotLightResource {

	private final MetricDetailsRepository metricDetailsRepository;

	private final ComputeRepository computeRepository;


	public SpotLightResource(
			MetricDetailsRepository metricDetailsRepository,
			ComputeRepository computeRepository) {
		this.metricDetailsRepository = metricDetailsRepository;
		this.computeRepository = computeRepository;
	}


	@PostMapping("/instances/{ocid}/{action}")
	public void doAction(@PathVariable String ocid, @PathVariable String action) {
		computeRepository.doAction(ocid, action);
	}

	@GetMapping("/metrics")
	public MetricDetails getMetrics(@RequestParam String ocid, @RequestParam String compartmentId) {
		return metricDetailsRepository.getMetrics(ocid, compartmentId);
	}

	@GetMapping("/instances")
	public String getMetrics(@RequestParam String ocid) {
		return computeRepository.getInstanceStatus(ocid);
	}


}
