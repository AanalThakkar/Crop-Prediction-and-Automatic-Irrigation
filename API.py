from flask import Flask,request, jsonify
import traceback
import pandas as pd
import numpy as np
import pickle
import sys
import json

app = Flask(__name__)

@app.route('/predict', methods=['POST','GET'])
def predict():
    #return str('The predicted crop is mango')
    if lr:
        try:
            #print(request.get_json(force=True))
            json_ = request.get_json(force=True)
            print(json_)
            #json_list = json.loads(json_)
            query_df = pd.DataFrame(json_,index=[0])
            query = pd.get_dummies(query_df)
            print(query)
            prediction = lr.predict(query)
            print(prediction)
            crops=['wheat','mungbean','Tea','millet','maize','lentil','jute','cofee','cotton','ground nut','peas','rubber','sugarcane','tobacco','kidney beans','moth beans','coconut','blackgram','adzuki beans','pigeon peas','chick peas','banana','watermelon','grapes','mango','apple','muskmelon','orange','papaya','pomegranate']
            cr='rice'
            count=0
            for i in range(0,30):
                if(prediction[0][i]==1):
                    c=crops[i]
                    count=count+1
                    break;
                i=i+1
            
            if(count==0):
                print('The predicted crop is '+cr)
                return jsonify({'The predicted crop is   ':cr.upper()})
            else:
                print('The predicted crop is '+c)
                return jsonify({'The predicted crop is   ':c.upper()})
            
        except:
            return jsonify({'trace': traceback.format_exc()})
    else:
        print ('Train the model first')
        return ('No model here to use') 

if __name__ == '__main__':
    try:
        port = int(sys.argv[1]) # This is for a command-line input
    except:
        port = 12345 # If you don't provide any port the port will be set to 12345

    lr = pickle.load(open("finalized_model.pkl", 'rb')) # Load "finalized_model.pkl"
    print ('Model loaded')
    
    app.run(host="192.168.43.9",port=port, debug=True)   
    