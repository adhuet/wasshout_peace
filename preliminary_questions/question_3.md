## QUESTION 3

### What their team did

By saying the team of Data Scientists could not handle the load of input data, we can deduce a few symptoms:
- Their program was too slow to process the stream of data
- Their machine may have crashed
- Some data could have been lost due to storage performance

These symptoms of failure could have been caused by:
- too much data provided from the stream to the processing machine (CPU usage increasing leading to a crash)
- too many requests made to store and process the data

Also, it is said they failed to scale up this process meaning their scaling method was not adequate for streams bigger than the first wave of reports (200GB). 

### Solutions

Although this program could be scaled up by buying more efficient processors (vertical scaling), the optimal solution would be to scale out by creating a distributed architecture:
- Distributed computing can handle bigger streams and provide more resources to process the incoming data.
- Distributed storage provides more entry points by dividing the read/write tasks among machines.

Finally, these problems could have been avoided by hiring a few data engineers on this team. 