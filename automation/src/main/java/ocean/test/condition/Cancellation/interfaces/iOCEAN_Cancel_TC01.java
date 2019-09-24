package ocean.test.condition.Cancellation.interfaces;

import org.testng.annotations.Test;

import ocean.common.DataProviderClass;

public interface iOCEAN_Cancel_TC01 {

	@Test(dataProvider = "SearchContract", dataProviderClass = DataProviderClass.class)
	public void cancelContract(String contractId, String status) throws Exception;
}
