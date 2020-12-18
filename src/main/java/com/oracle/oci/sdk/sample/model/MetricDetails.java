package com.oracle.oci.sdk.sample.model;

import java.text.DecimalFormat;

/**
 * @author bnasslahsen
 */
public class MetricDetails {

	private String cpuUtilization;

	private String memoryUtilization;

	private String diskIopsRead;

	private String diskIopsWritten;

	private DecimalFormat df = new DecimalFormat("#.00");

	public MetricDetails(Double cpuUtilization, Double memoryUtilization, Double diskIopsRead, Double diskIopsWritten) {
		this.cpuUtilization = getFormattedMetric(cpuUtilization);
		this.memoryUtilization = getFormattedMetric(memoryUtilization);
		if (diskIopsRead != null)
			this.diskIopsRead = diskIopsRead.intValue() + " operations";
		if (diskIopsRead != null)
			this.diskIopsWritten = diskIopsWritten.intValue() + " operations";
	}

	private String getFormattedMetric(Double utilization) {
		if (utilization != null)
			return df.format(utilization) + " %";
		return null;
	}

	public String getCpuUtilization() {
		return cpuUtilization;
	}

	public void setCpuUtilization(String cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}

	public String getMemoryUtilization() {
		return memoryUtilization;
	}

	public void setMemoryUtilization(String memoryUtilization) {
		this.memoryUtilization = memoryUtilization;
	}

	public String getDiskIopsRead() {
		return diskIopsRead;
	}

	public void setDiskIopsRead(String diskIopsRead) {
		this.diskIopsRead = diskIopsRead;
	}

	public String getDiskIopsWritten() {
		return diskIopsWritten;
	}

	public void setDiskIopsWritten(String diskIopsWritten) {
		this.diskIopsWritten = diskIopsWritten;
	}
}
