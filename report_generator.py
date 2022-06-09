from faker import Faker
import datetime
import random
fake = Faker()

delta = 20

for i in range(10):
    with open(f"drone_generation/reports/report_{i}.txt", "w") as f:
        for _ in range(10):
            ts = datetime.datetime.now() + datetime.timedelta(seconds=delta)
            ts = ts.isoformat()
            report = {'timestamp':ts, 'name': fake.name(), 'score':random.randint(0,100)}
            f.write(str(report) + "\n")
            delta += 20 + random.randint(0, 6)
            

    