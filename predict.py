import sys
import joblib
import pandas as pd



# Load model and symptoms list
model = joblib.load('model.pkl')

# Full list of symptoms used in training
all_symptoms = pd.read_csv("dataset.csv").columns[:-1]  # assuming last column is 'prognosis'

# Input symptoms from command line
input_symptoms = [s.strip().lower() for s in sys.argv[1:]]

# Create input vector of 0s and set 1 where symptom matches
input_vector = [1 if symptom in input_symptoms else 0 for symptom in all_symptoms]

# Predict
prediction = model.predict([input_vector])
print(prediction[0])
