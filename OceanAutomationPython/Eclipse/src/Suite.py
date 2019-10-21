from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver import ActionChains
import time 


# Suite is Main class, from where execution starts
class Suite():
    # Desired capabilities. Which needs to be passed in appium to identify environment
    desired_Capabilities = dict(
        # Platform on which automation would be executed
        platformName='Windows',
        # Machine name on which execution is being done
        deviceName='22193-TZD-ALU',
        # Application being Automated
        app='C:\\Users\\mohit.goel\\AppData\\Local\\OceanQa\\Ocean.exe')

    ocean_Windows_Driver = webdriver.Remote('http://127.0.0.1:4723/wd/hub', desired_Capabilities)
    
    # # Case 1, Search for a Price Sheet
    newProgramCode = 'SNE-Test'
    actualProgramCode = 'SNE'
    # Click Pricing Tab
    oceanElement = WebDriverWait(ocean_Windows_Driver, 10).until(EC.presence_of_element_located((By.NAME, "Pricing")))
    oceanElement.click()
    # Click Price Sheet List Tab
    oceanElement = WebDriverWait(ocean_Windows_Driver, 10).until(EC.presence_of_element_located((By.NAME, "Price Sheet List")))
    oceanElement.click()
    
    time.sleep(10)     
    try:
        # Type newProgramCode 
        oceanElement = WebDriverWait(ocean_Windows_Driver, 10).until(EC.presence_of_element_located((By.XPATH, "//*[@Name='Code']//*[@AutomationId='FilterCriterionTextBoxEditor']")))
        oceanElement.send_keys(newProgramCode)
        # Verify Program code = newProgramCode exists in search results, if exists delete the same
        time.sleep(5) 
        oceanElement = WebDriverWait(ocean_Windows_Driver, 10).until(EC.presence_of_element_located((By.XPATH, "//*[@Name='Code' and @AutomationId = 'Cell_CODE' and @ClassName='DataCell']")))
        programCode = oceanElement.get_attribute('Name')      
        print(programCode)  
        oceanElement.click
        time.sleep(5) 
        # Perform right click to delete the program code = newProgramCode
        actionChains = ActionChains(ocean_Windows_Driver)
        actionChains.context_click(oceanElement).perform()       
        time.sleep(5) 
        # Click Delete to delete price sheet
        oceanElement = WebDriverWait(ocean_Windows_Driver, 10).until(EC.presence_of_element_located((By.XPATH, "//*[@Name='Delete' and @ClassName = 'TextBlock']")))
        oceanElement.click() 
        # Click Ok, to confirm delete
        oceanElement = WebDriverWait(ocean_Windows_Driver, 10).until(EC.presence_of_element_located((By.XPATH, "//*[@Name='Delete' and @ClassName = 'TextBlock']")))
        oceanElement.click()
        # Press Ok for Final Delete 
        print("pass")     
    except:
        print("fail");
       
    # Type Actual Program code    
    oceanElement = WebDriverWait(ocean_Windows_Driver, 10).until(EC.presence_of_element_located((By.XPATH, "//*[@Name='Code']//*[@AutomationId='FilterCriterionTextBoxEditor']")))
    oceanElement.click
    oceanElement.send_keys(actualProgramCode) 
    time.sleep(5) 
    oceanElement.click
    # Right Click
    oceanElement = WebDriverWait(ocean_Windows_Driver, 10).until(EC.presence_of_element_located((By.NAME, actualProgramCode)))
    actionChains = ActionChains(ocean_Windows_Driver)
    actionChains.context_click(oceanElement).perform()
    time.sleep(5) 
    # Click Clone as Sub-Master
    oceanElement = WebDriverWait(ocean_Windows_Driver, 10).until(EC.presence_of_element_located((By.XPATH, "//*[@Name='Clone as a Sub Master' and @ClassName = 'TextBlock']")))
    oceanElement.click
    time.sleep(5) 
    # Click Ok   
    oceanElement = WebDriverWait(ocean_Windows_Driver, 10).until(EC.presence_of_element_located((By.XPATH, "//*[@AutomationId='SubMaster']")))
    oceanElement.click
    time.sleep(5)
    # Verify clone is successfully created
    oceanElement.send_keys(newProgramCode)
    time.sleep(5)
    # Get for program code
    oceanElement = WebDriverWait(ocean_Windows_Driver, 10).until(EC.presence_of_element_located((By.NAME, "Code")))
    programCode = oceanElement.get_attribute('Name')      
    print(programCode)
    time.sleep(5) 
    
