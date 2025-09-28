import os
import sys
import argparse
import time
import itertools
import numpy as np
import pandas as pd


class KnnClassifier:
    def __init__(self, k: int, p: float):
        """
        Constructor for the KnnClassifier.

        :param k: Number of nearest neighbors to use.
        :param p: p parameter for Minkowski distance calculation.
        """
        self.k = k
        self.p = p

        # TODO - Place your student IDs here. Single submitters please use a tuple like so: self.ids = (123456789,)
        self.ids = (212536924, 322836180)

    def fit(self, X: np.ndarray, y: np.ndarray) -> None:
        """
        This method trains a k-NN classifier on a given training set X with label set y.

        :param X: A 2-dimensional numpy array of m rows and d columns. It is guaranteed that m >= 1 and d >= 1.
            Array datatype is guaranteed to be np.float32.
        :param y: A 1-dimensional numpy array of m rows. it is guaranteed to match X's rows in length (|m_x| == |m_y|).
            Array datatype is guaranteed to be np.uint8.
        """

        # TODO - your code here
        self.X_train = X
        self.y_train = y
        self.m = X.shape[0]
        self.d = X.shape[1]

    def update_entry(entry, flag):
        """
        This method trains a k-NN classifier on a given training set X with label set y.

        :param X: A 2-dimensional numpy array of m rows and d columns. It is guaranteed that m >= 1 and d >= 1.
                    Array datatype is guaranteed to be np.float32.
        :return:
        """
        entry[2] = flag
        return entry

    def find_k_closest(self, X):
        """
        This method finds for each test point, the k nearest neighbors from train set

        :param X: A 2-dimensional numpy array of m rows and d columns. It is guaranteed that m >= 1 and d >= 1.
             Array datatype is guaranteed to be np.float32.
        :return: A 2-dimensional numpy array of n row and k columns containing
             the k nearest neighbors from train set. each value of the matrix is a list[distance from test point, label of train point].
        """

        # Compute the Minkowski distance matrix between each test point in X and all training points in self.X_train
        distance_matrix = np.sum(np.abs(self.X_train[:, np.newaxis] - X) ** self.p, axis=2) ** (1 / self.p)

        # This creates a (m, n) matrix where each row is the training labels
        labels = np.tile(self.y_train, (self.m, 1))

        # Creates a full data matrix that each row represent a test point,
        #      and each entry represent train point and holds (distance, label)
        stacked = np.stack([distance_matrix, labels], axis=-1)

        # Sort by distance
        sorted_indices = np.argsort(stacked[:, :, 0], axis=1)
        stacked = np.take_along_axis(stacked, sorted_indices[:, :, np.newaxis], axis=1)

        # Get the distance to the k-th neighbor for each test point
        kth_dists = stacked[:, self.k - 1, 0].reshape(-1, 1)

        # Create a boolean mask where True indicates a tie with the k-th neighbor's distance
        is_tie_mask = stacked[:, :, 0] == kth_dists

        # Create a structured array to sort primarily by distance, then by label only the k-th's tied points (as tie-breaker)
        sort_keys = np._core.records.fromarrays([
            stacked[:, :, 0],  # distance
            stacked[:, :, 1],  # label
            is_tie_mask.astype(int)
        ], names='dist,label')

        # Get the indices that would sort each row by distance (then by label due to structured array ordering)
        sorted_indices = np.argsort(sort_keys, axis=1, order=('dist', 'label'))

        # Construct row indices to enable advanced indexing
        row_indices = np.arange(stacked.shape[0])[:, None]

        # Apply the sorted indices to reorder the neighbors for each test point
        final_sorted = stacked[row_indices, sorted_indices]

        # Return only the first k nearest neighbors (distance and label) for each test point
        # Shape: (m, k, 2)
        return final_sorted[:, :self.k]


    def find_majority_label(self, neighbors_matrix):
        """
        This method finds the predicted label for each test point based on its k nearest neighbors.

        :param neighbors_matrix: A 3D NumPy array of shape (n_test_samples, k, 2).
                                 Each row corresponds to a test point.
                                 Each entry holds (distance, label) of one training point,
                                 sorted first by distance and then by label.

        :return: A 1D NumPy array of length n_samples with the predicted labels.
        """

        # Extract distances and labels from the neighbor matrix
        distances = neighbors_matrix[:, :, 0]  # shape (n_samples, k)
        labels = neighbors_matrix[:, :, 1]     # shape (n_samples, k)

        # Identify all unique labels across the dataset
        unique_labels = np.unique(labels)
        n_samples, k = labels.shape
        n_classes = len(unique_labels)

        # Map each label in the labels matrix to its index in unique_labels
        # This allows to control it better when the labels are not integers
        label_to_index = np.searchsorted(unique_labels, labels)

        # Initialize count matrix: shape (n_test_samples, d_classes)
        # Each row represent a test point and each entry counts occurrences of the colum's match label
        count_matrix = np.zeros((n_samples, n_classes), dtype=int)

        # Efficiently count occurrences of each label
        # np.add.at is used for index-based accumulation
        np.add.at(
            count_matrix,
            (np.repeat(np.arange(n_samples), k), label_to_index.ravel()),
            1
        )

        # Find the maximum vote count per test sample
        max_counts = np.max(count_matrix, axis=1, keepdims=True)

        # Create a mask indicating which labels are tied for the max count - "winning labels"
        tied_mask = count_matrix == max_counts

        # For each neighbor, mark whether its label is among the tied ones
        is_tied_label = tied_mask[np.arange(n_samples)[:, None], label_to_index]

        # Keep distances only for neighbors with tied labels; others become ∞
        masked_distances = np.where(is_tied_label, distances, np.inf)

        # Among tied labels, pick the one with the smallest distance
        min_indices = np.argmin(masked_distances, axis=1)

        # Extract final predicted labels using the selected indices
        majority_labels = labels[np.arange(n_samples), min_indices]

        return majority_labels

    def predict(self, X: np.ndarray) -> np.ndarray:
        """
        This method predicts the y labels of a given dataset X, based on a previous training of the model.
        It is mandatory to call KnnClassifier.fit before calling this method.

        :param X: A 2-dimensional numpy array of m rows and d columns. It is guaranteed that m >= 1 and d >= 1.
            Array datatype is guaranteed to be np.float32.
        :return: A 1-dimensional numpy array of m rows. Should be of datatype np.uint8.
        """

        # TODO - your code here
        k_closest_neighbors = self.find_k_closest(X)
        return self.find_majority_label(k_closest_neighbors)



def main():
    print("*" * 20)
    print("Started HW1_ID1_ID2.py")
    # Parsing script arguments
    parser = argparse.ArgumentParser()
    parser.add_argument('csv', type=str, help='Input csv file path')
    parser.add_argument('k', type=int, help='k parameter')
    parser.add_argument('p', type=float, help='p parameter')
    args = parser.parse_args()

    print("Processed input arguments:")
    print(f"csv = {args.csv}, k = {args.k}, p = {args.p}")

    print("Initiating KnnClassifier")
    model = KnnClassifier(k=args.k, p=args.p)
    print(f"Student IDs: {model.ids}")
    print(f"Loading data from {args.csv}...")
    data = pd.read_csv(args.csv, header=None)
    print(f"Loaded {data.shape[0]} rows and {data.shape[1]} columns")
    X = data[data.columns[:-1]].values.astype(np.float32)
    y = pd.factorize(data[data.columns[-1]])[0].astype(np.uint8)

    print("Fitting...")
    model.fit(X, y)
    print("Done")
    print("Predicting...")
    y_pred = model.predict(X)
    print("Done")
    accuracy = np.sum(y_pred == y) / len(y)
    print(f"Train accuracy: {accuracy * 100 :.2f}%")
    print("*" * 20)


if __name__ == "__main__":
    main()
