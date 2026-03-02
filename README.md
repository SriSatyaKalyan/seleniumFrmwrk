# README
The idea of this framework is to refine my Selenium skillset whilst consistently practicing AI tool integration. Specifically with this particular framework, I am using Claude Code.
Every scenario or test script in this framework has a purpose and I will not be adding test scripts for the sake of it. I will add comments above the scripts mentioning why I have implemented the script and what the idea behind practicing it would be. 
The idea is to add one script a day. Feel free to go over and add your comments.

## Commands to run
### maven
- mvn test
- mvn test -Dcucumber.filter.tags="@tag_name"
- mvn clean && mvn test -Dcucumber.filter.tags="@testing"

### To-do
- https://github.com/cucumber/cucumber-expressions#readme

### Test Scripts
- [ ] Have a consistent architecture across different test scripts
- [ ] Implement Iframe testing
- [ ] All Cucumber commands
- [ ] Most Selenium complex commands

### DevOps
- [ ] @maintenance tags run once a weekend - exist in login.feature. There should be a certain order to these tests so
they run in a specific order
- [ ] Consistent Jenkins integration with Grafana Dashboards

### AI 
- [ ] Add Agentic AI integration