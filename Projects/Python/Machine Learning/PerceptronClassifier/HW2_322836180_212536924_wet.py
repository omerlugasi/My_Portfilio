import os
import sys
import argparse
import time
import itertools
import numpy as np
import pandas as pd


class PerceptronClassifier:
    def __init__(self):
        """
        Constructor for the PerceptronClassifier.
        """
        # TODO - Place your student IDs here. Single submitters please use a tuple like so: self.ids = (123456789,)
        self.ids = (322836180, 212536924)
        self.K = 0
        self.weights = None

    def fit(self, X: np.ndarray, y: np.ndarray):
        """
        This method trains a multiclass perceptron classifier on a given training set X with label set y.
        :param X: A 2-dimensional numpy array of m rows and d columns. It is guaranteed that m >= 1 and d >= 1.
        Array datatype is guaranteed to be np.float32.
        :param y: A 1-dimensional numpy array of m rows. it is guaranteed to match X's rows in length (|m_x| == |m_y|).
        Array datatype is guaranteed to be np.uint8.
        """

        # TODO - your code here
        self.K = len(np.unique(y))

        num_of_labels = self.K
        num_of_features = X.shape[1]
        num_of_train_points = len(X)
        

        W = np.zeros((num_of_labels, num_of_features), dtype=np.float32)

        done = False
        while not done:
            # Given that the data is linearly separable, so we continue until 100% accuracy
            done = True
            for t in range(num_of_train_points):
                xt = X[t]
                max = float('-inf')
                y_hat = 0
                for i in range(num_of_labels):
                    if np.inner(W[i], xt) > max:
                        y_hat = i
                        max = np.inner(W[i], xt)
                y_actual = y[t]

                if y_hat != y_actual:
                    done = False
                    W[y_actual] = W[y_actual] + xt
                    W[y_hat] = W[y_hat] - xt

        self.weights = W
                
            







    def predict(self, X: np.ndarray) -> np.ndarray:
        """
        This method predicts the y labels of a given dataset X, based on a previous training of the model.
        It is mandatory to call PerceptronClassifier.fit before calling this method.
        :param X: A 2-dimensional numpy array of m rows and d columns. It is guaranteed that m >= 1 and d >= 1.
        Array datatype is guaranteed to be np.float32.
        :return: A 1-dimensional numpy array of m rows. Should be of datatype np.uint8.
        """

        # TODO - your code here
        num_of_tests = len(X)
        labels = np.zeros(num_of_tests)

        for i in range(num_of_tests):
            test_point = X[i]
            max = float('-inf')
            label = 0

            for j in range(self.K):
                current_value = np.inner(self.weights[j], test_point)
                if current_value > max:
                    max = current_value
                    label = j
            
            labels[i] = label

        return labels

        ### Example code - don't use this:
        # return np.random.randint(low=0, high=2, size=len(X), dtype=np.uint8)


if __name__ == "__main__":

    print("*" * 20)
    print("Started HW2_ID1_ID2.py")
    # Parsing script arguments
    parser = argparse.ArgumentParser()
    parser.add_argument('csv', type=str, help='Input csv file path')
    args = parser.parse_args()

    print("Processed input arguments:")
    print(f"csv = {args.csv}")

    print("Initiating PerceptronClassifier")
    model = PerceptronClassifier()
    print(f"Student IDs: {model.ids}")
    print(f"Loading data from {args.csv}...")
    data = pd.read_csv(args.csv, header=None)
    print(f"Loaded {data.shape[0]} rows and {data.shape[1]} columns")
    X = data[data.columns[:-1]].values.astype(np.float32)
    y = pd.factorize(data[data.columns[-1]])[0].astype(np.uint8)

    print("Fitting...")
    is_separable = model.fit(X, y)
    print("Done")
    y_pred = model.predict(X)
    print("Done")
    accuracy = np.sum(y_pred == y.ravel()) / y.shape[0]
    print(f"Train accuracy: {accuracy * 100 :.2f}%")

    print("*" * 20)
