from flask import Flask, render_template
from kafka import KafkaConsumer, TopicPartition
from ast import literal_eval

app = Flask('Wasshout peace')



@app.route('/')
def home():
    alerts = []
    bootstrap_servers = ['localhost:9092']
    topic_name = 'reports'
    consumer = KafkaConsumer(bootstrap_servers=bootstrap_servers)
    tp = TopicPartition(topic_name, 0)
    consumer.assign([tp])

    consumer.seek_to_end(tp)
    lastOffset = consumer.position(tp)
    consumer.seek_to_beginning(tp)

    for msg in consumer:
        alerts.append(literal_eval(msg.value.decode('utf-8')))
        if msg.offset == lastOffset - 1:
            break

    consumer.close()
        
    return render_template('index.html', alerts=alerts)

    


if __name__ == '__main__':
    app.run()