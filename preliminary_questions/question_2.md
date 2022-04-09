## Question 2
### Requirements
- Trigger alert when the peacescore is bad
- Alert must contain peacewatcher's location and citizen's name
- Alert must be triggered as quickly as possible, so that peacemakers react fast

### Question
What business constraint should the architecture meet to fulfill the requirement describe in the paragraph «Alert»? Which component to choose?

### Business Constraints
- Accuracy: we can't trigger false alerts (as few as possible)
- Speed: the faster the alert is emitted, the faster the situation is solved
- Volume: process large amount of reports


### Components
#### Real-time Data Processing
- One Real-time data processing to check the report for an alert
- Trigger an alert if peacescore is below threshold
- We need real-time to meet the speed constraint
- Processes report by report in parallel
#### Standard Data Processing
- One standard data processing to process/clean peacewatcher's raw data and output a stream.
- Accuracy is insured in this processing
- This processing needs to handle volume as it will get data from multiple peacewatchers
