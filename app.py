from flask import Flask, request, jsonify
import joblib
import numpy as np

app = Flask(__name__)

model = joblib.load('model.pkl')

SYMPTOMS = list(model.feature_names_in_)  # feature_names_in_ is available in sklearn ≥1.0

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()

    # Expecting a list of symptoms like ["itching", "headache"]
    input_symptoms = data.get('symptoms', [])

    # Create binary input vector for symptoms
    input_vector = [1 if symptom in input_symptoms else 0 for symptom in SYMPTOMS]

    prediction = model.predict([input_vector])[0]

    return jsonify({'predicted_disease': prediction})


if __name__ == '__main__':
    app.run(debug=True)

