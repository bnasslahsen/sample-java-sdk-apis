package com.oracle.oci.sdk.sample.repository;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.oracle.bmc.monitoring.MonitoringClient;
import com.oracle.bmc.monitoring.model.SummarizeMetricsDataDetails;
import com.oracle.bmc.monitoring.requests.SummarizeMetricsDataRequest;
import com.oracle.bmc.monitoring.responses.SummarizeMetricsDataResponse;
import com.oracle.oci.sdk.sample.config.PemConfig;
import com.oracle.oci.sdk.sample.model.MetricDetails;
import com.oracle.oci.sdk.sample.model.OCIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author bnasslahsen
 */
@Component
public class MetricDetailsRepository {

	@Autowired
	private PemConfig pemConfig;

	private static final Logger LOGGER = LoggerFactory.getLogger(MetricDetailsRepository.class);

	public MetricDetails getMetrics( String ocid,String compatmentId) {
		MetricDetails metricDetails = null;
		String namespace = getNameSpace(ocid);
		if (namespace != null) {
			Double memoryUtilization = getMetric(ocid, "MemoryUtilization", namespace, compatmentId);
			Double cpuUtilization = getMetric(ocid, "CpuUtilization", namespace, compatmentId);
			Double diskIopsRead = getMetric(ocid, "DiskIopsRead", namespace,compatmentId);
			Double diskIopsWritten = getMetric(ocid, "diskIopsWritten", namespace, compatmentId);
			metricDetails = new MetricDetails(cpuUtilization, memoryUtilization, diskIopsRead, diskIopsWritten);
		}
		return metricDetails;
	}

	private Double getMetric(String ocid, String metricName, String namespace, String compatmentId) {

		Date NOW = new Date();
		Date START = new Date(NOW.getTime() - TimeUnit.MINUTES.toMillis(1));

		final String query = metricName + "[1m]{resourceId = \"" + ocid + "\"}.max()";
		try (MonitoringClient monitoringClient = getMonitoringClient()) {
			final SummarizeMetricsDataRequest request =
					SummarizeMetricsDataRequest.builder()
							.compartmentId(compatmentId)
							.summarizeMetricsDataDetails(
									SummarizeMetricsDataDetails.builder()
											.namespace(namespace)
											.query(query)
											.startTime(START)
											.endTime(NOW)
											.build())
							.build();
			LOGGER.debug("Request constructed:\n%s\n\n", request.getSummarizeMetricsDataDetails());
			final SummarizeMetricsDataResponse response =
					monitoringClient.summarizeMetricsData(request);
			LOGGER.debug("%s\n", response.getItems().toString());
			if (!CollectionUtils.isEmpty(response.getItems())){
				return response.getItems().get(0).getAggregatedDatapoints().get(0).getValue();
			}
			return null;
		}
	}

	private String getNameSpace(String ocid) {
		String result = null;
		String resourceType = OCIDUtils.getResourceType(ocid);
		if (resourceType != null) {
			switch (resourceType) {
				case "instance":
					result = "oci_computeagent";
					break;
				default:
					break;
			}
		}
		return result;
	}

	private MonitoringClient getMonitoringClient() {
		final MonitoringClient monitoringClient = new MonitoringClient(pemConfig.getAuthenticationDetailsProvider());
		monitoringClient.setRegion(pemConfig.getRegion());
		return monitoringClient;
	}
}
