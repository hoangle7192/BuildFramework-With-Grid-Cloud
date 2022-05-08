ser ProjectPath = %~dp0
java -jar -Dwebdriver.chrome.driver="%ProjectPath%\chromedriver.exe" -Dwebdriver.gecko.driver="%ProjectPath%\geckodriver.exe" selenium-server-standalone-3.141.59.jar -role webdriver -hub http://10.20.22.89:4444/grid/register/ -port 5555

java -jar selenium-server-standalone-3.141.59.jar -role node -nodeConfig nodeConfig.json
pause