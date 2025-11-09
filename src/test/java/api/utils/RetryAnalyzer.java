package api.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
	private int retryCount = 0;
    private static final int maxRetryCount = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            retryCount++;
            System.out.println("Retrying test " + result.getName() + " (" + retryCount + "/" + maxRetryCount + ")");
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return true;
        }
        return false;
    }
}