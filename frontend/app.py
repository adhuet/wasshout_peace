from flask import Flask, render_template
from kafka import KafkaConsumer, TopicPartition
from ast import literal_eval

app = Flask('Wasshout peace')

# alerts = [{'name': 'declan', 'score': 22},
# {'name': 'zola', 'score': 30},
# {'name': 'marie', 'score': 80},
# {'name': 'ziva', 'score': 18},
# {'name': 'zenny', 'score': 5},
# {'name': 'rick', 'score': 12},
# {'name': 'dave', 'score': 19},
# {'name': 'garry', 'score': 20}]

alerts = []

@app.route('/')
def home():
    bootstrap_servers = ['localhost:9092']
    topic_name = 'reports'
    consumer = KafkaConsumer(bootstrap_servers=bootstrap_servers)
    tp = TopicPartition(topic_name, 0)
    consumer.assign([tp])
    consumer.seek_to_end(tp)
    lastOffset = consumer.position(tp)
    consumer.seek_to_beginning(tp)

    for msg in consumer:
        print(msg.value)
        alerts.append(literal_eval(msg.value.decode('utf-8')))
        if msg.offset == lastOffset - 1:
            break

    consumer.close()

    # print('Consumer closed')
    # print('Nb of alerts:', len(alerts))
    # print('alerts:', alerts)

    # print('\nTYPES:')
    # print(type(alerts))
    # print(type(alerts[0]))
        
    return render_template('index.html', alerts=alerts)

if __name__ == '__main__':
    app.run()