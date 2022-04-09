## Question 1
### Requirements
- Every report must be kept
- Statistical analysis will be performed at a later date (easy to process processed data)
- Daily reports will be around 200Gb
- Only 1% of reports will be alerts

### Question
What technical/business constraints should the data storage component of the program architecture meet to fulfill the requirement described by the customer in paragraph «Statistics» ? 
So what kind of component(s) (listed in the lecture) will the architecture need?

### Technical Constraints
- be able to store large amounts/quantities of data
- store data forever
- must be accessible
- geared towards reporting/analysis


### Business Constraints
- Reporting must be easy and intuitive to do
- Data must be easily read and interpreted by anyone (not just data scientists)
- Data must be consistent for reporting

### Components
#### Distributed Data Warehouse
- Data warehouse distributed over network of machines
- Is centered towards reporting/analysis
- CAP Theorem: CP because reports must be consistent meaning data access must be consistent
- Contains structured data therefore easier to interpret, query, analyse
- Can be used by anyone (not just data scientists)
- Can store large amounts of data for indefinite amount of time
