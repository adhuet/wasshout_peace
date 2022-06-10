# wasshout_peace
Peaceland and peacemakers SPARK project by Declan CHLASTA, Gautier PICARD, Charlie BROSSE, Adrien HUET

Our project is composed of four components: 
1. drone_generation, that simulates the reports generation and sends them over a kafka topic called 'reports'
2. alert_detection, that reads from the topic 'reports', filters the peacescores < 20 to send those to another kafka topic called 'alerts'
3. report_storage, that retrieves all the reports to store them in a datalake
4. analyse_reports, that reads from the datalake to make analysis on the reports

We also have a frontend in Python Flask that reads from the topic 'alerts' to display the alerts (score < 20) in a friendly UI