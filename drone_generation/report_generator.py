from faker import Faker
import datetime
import random
fake = Faker()

delta = 20

# for i in range(10):
#     with open(f"drone_generation/reports/report_{i}.txt", "w") as f:
#         for _ in range(10):
#             ts = datetime.datetime.now() + datetime.timedelta(seconds=delta)
#             ts = ts.isoformat()
#             report = {'timestamp':ts, 'name': fake.name(), 'score':random.randint(0,100)}
#             f.write(str(report) + "\n")
#             delta += 20 + random.randint(0, 6)
            

latitude = 48.8582503
longitude = 2.294527

list_words = []
with open('bad_words.csv', 'r') as f:
    lines = f.readlines()
    list_words = [line.rstrip() for line in lines]

for _ in range(10):
    ts = datetime.datetime.now() + datetime.timedelta(seconds=delta)
    ts = ts.isoformat()
    dec_lat = random.random()/100
    dec_lon = random.random()/100
    nb_words = random.randint(1, 5)
    id = random.randint(1, 100)
    words = random.sample(list_words, nb_words)
    report = {'timestamp':ts, 'id': id, 'name': fake.name(), 'score':random.randint(0,100), 'latitude': latitude + dec_lat, 'longitude': longitude + dec_lon, 'words': words}
    print(str(report))
    delta += 20 + random.randint(0, 6)
    