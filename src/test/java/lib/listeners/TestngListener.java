package lib.listeners;

import java.util.List;

import org.testng.IAnnotationTransformer;
import org.testng.IDataProviderListener;
import org.testng.IDataProviderMethod;
import org.testng.IRetryAnalyzer;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

public class TestngListener implements IAnnotationTransformer, IRetryAnalyzer, IDataProviderListener {

	int maxcount = 1;

	public boolean retry(ITestResult result) {
		if (!result.isSuccess() & maxcount < 2) {
			maxcount++;
			return true;
		}
		return false;
	}

	@Override
	public void afterDataProviderExecution(IDataProviderMethod arg0, ITestNGMethod arg1, ITestContext arg2) {

	}

	@Override
	public void beforeDataProviderExecution(IDataProviderMethod dp, ITestNGMethod meth, ITestContext test) {
		List<Integer> indices = dp.getIndices();
		for (Integer integer : indices) {
			System.out.println(integer);
		}
	}

}
