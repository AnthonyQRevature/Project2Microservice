package com.example.report.server;

public enum ReportStatus
{
	OPEN(0),
	RESOLVED(1);

	public Integer value;

	ReportStatus(Integer val)
	{
		this.value = val;
	}

	public static ReportStatus of(Integer value)
	{
		return switch(value)
		{
			case 0 -> OPEN;
			case 1 -> RESOLVED;
			default -> OPEN;
		};
	}
}