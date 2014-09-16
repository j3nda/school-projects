package test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class BaseTest
{
	protected Log logger;
	protected int assertCounter = 0;
	protected int assertFailed  = 0;


	protected Log getLogger()
	{
		if (logger == null || !(logger instanceof Log)) {
			logger = LogFactory.getLog(getClass());
		}
		return logger;
	}


	protected void Assert(Object testingCondition, Object finalCondition, String failedOutputMessage)
	{
		if (assertCounter == 0) {
			getLogger().info("=============================================================================");
			getLogger().info("NEW-TESTS: "+getClass());
			getLogger().info("-----------------------------------------------------------------------------");
		}

		assertCounter++;
		if (!testingCondition.equals(finalCondition)) {
			assertFailed++;
			getLogger().error("test: "+getClass()+"; FAILED: "+failedOutputMessage);
		}
	}


	public void runAllTests()
	{
		if (assertCounter > 0) {
			if (assertFailed > 0) {
				getLogger().info("-----------------------------------------------------------------------------");
			}
			getLogger().info("SUMMARY: "+getClass());
			if (assertFailed > 0) {
				getLogger().error(" FAIL: "+assertFailed+"x <"+getClass()+">");

			} else {
				getLogger().info("   FAIL: "+assertFailed+"x");
			}
			getLogger().info("     OK: "+(assertCounter-assertFailed)+"x");
			getLogger().info("--TOTAL: "+assertCounter+"x");
			getLogger().info("=============================================================================");

		} else {
			getLogger().info("WARNING: "+getClass()+"; haven't any test cases!");
			getLogger().info("=============================================================================");
		}
	}


}
