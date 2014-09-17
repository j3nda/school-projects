package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


abstract public class BaseTest
{
	protected Log logger;
	protected TestSummaryEntity progressEntity = new TestSummaryEntity();


	public void setLogger(Log logger)
	{
		this.logger = logger;
	}


	protected Log getLogger()
	{
		if (logger == null || !(logger instanceof Log)) {
			logger = LogFactory.getLog(getClass());
		}
		return logger;
	}


	public TestSummaryEntity getSummary()
	{
		return progressEntity;
	}


	protected void Assert(Object testingCondition, Object finalCondition, String failedOutputMessage)
	{
		if (progressEntity.assertCounter == 0) {
			getLogger().info("=============================================================================");
			getLogger().info("NEW-TESTS: "+getClass());
			getLogger().info("-----------------------------------------------------------------------------");
		}

		progressEntity.assertCounter++;
		if (!testingCondition.equals(finalCondition)) {
			progressEntity.assertFailed++;
			progressEntity.assertFailedMessages.add(failedOutputMessage);
			getLogger().error("test: "+getClass()+"; FAILED: "+failedOutputMessage);
		}
	}


	public void runAllTests()
	{
		if (progressEntity.assertCounter > 0) {
			if (progressEntity.assertFailed > 0) {
				getLogger().info("-----------------------------------------------------------------------------");
			}
			getLogger().info("SUMMARY: "+getClass());
			if (progressEntity.assertFailed > 0) {
				getLogger().error(" FAIL: "+progressEntity.assertFailed+"x <"+getClass()+">");

			} else {
				getLogger().info("   FAIL: "+progressEntity.assertFailed+"x");
			}
			getLogger().info("     OK: "+(progressEntity.assertCounter-progressEntity.assertFailed)+"x");
			getLogger().info("--TOTAL: "+progressEntity.assertCounter+"x");
			getLogger().info("=============================================================================");

		} else {
			getLogger().info("WARNING: "+getClass()+"; haven't any test cases!");
			getLogger().info("=============================================================================");
		}
	}


}
