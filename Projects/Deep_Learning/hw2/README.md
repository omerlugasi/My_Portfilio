# Deep Learning HW2

This repository contains implementations and experiments for recurrent neural networks (RNNs) and LSTMs for sentiment analysis on the IMDB movie reviews dataset.

## Project Overview

### 1. Generalization and Overfitting

Demonstration of overfitting on randomly labeled MNIST samples using a Fully Connected Neural Network.

Topics explored:

* Random labels memorization
* Generalization vs. overfitting
* Training/test accuracy behavior
* Model complexity and dataset size
* Regularization techniques

### 2. Sentiment Analysis with RNN and LSTM

Implementation and comparison of recurrent neural network architectures for binary sentiment classification on the IMDB dataset.

Implemented components:

* Text preprocessing and cleaning
* Word-based tokenization
* Vocabulary handling with special tokens
* Trainable word embeddings
* Bidirectional RNN
* Bidirectional LSTM
* Gradient clipping
* Dropout regularization
* Accuracy/loss visualization
* Confusion matrices
* Model checkpoint saving

## Results

* RNN model achieved approximately 75% test accuracy.
* LSTM model achieved approximately 85% test accuracy.
* The experiments demonstrate the advantage of gated recurrent architectures for long-text sequence modeling.

## Repository Structure

```text id="v6qfdm"
hw2/
│
├── README.md
├── requirements.txt
├── .gitignore
│
├── notebooks/
│   └── rnn_lstm_sentiment_analysis.ipynb
│
├── models/
│   ├── rnn_sentiment_model.pt
│   └── lstm_sentiment_model.pt
│
└── reports/
    └── HW2_report.pdf
```

## Installation

```bash id="10mt8t"
pip install -r requirements.txt
```

## Technologies

* Python
* PyTorch
* Torchvision
* NumPy
* Matplotlib
* Scikit-learn
* Jupyter Notebook
