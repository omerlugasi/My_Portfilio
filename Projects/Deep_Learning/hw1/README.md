# Deep Learning HW1

This repository contains two deep learning projects implemented using PyTorch.

## 1. Fully Connected Neural Network on MNIST

Implemented a fully connected neural network from scratch for handwritten digit classification on the MNIST dataset.

### Features

* Manual forward and backward propagation
* Custom cross-entropy loss
* No usage of:

  * `torch.nn`
  * autograd / `backward()`
  * built-in optimizers
* Learning rate comparison experiments
* Training and evaluation plots

## 2. CNN for Big Cats Classification

Implemented and trained a Convolutional Neural Network (CNN) for classifying different species of big cats.

### Features

* CNN-based image classifier
* Data preprocessing and augmentation
* Training / validation evaluation
* Accuracy and loss visualization
* Model weights saving

## Repository Structure

```text
hw1/
│
├── README.md
├── requirements.txt
├── .gitignore
│
├── notebooks/
│   └── mnist_and_bigcats_classification.ipynb
│
├── models/
│   ├── mnist_fc_weights.pkl
│   └── bigcats_cnn_weights.pkl
│
├── reports/
│   └── hw1_report.pdf
```

## Installation

```bash
pip install -r requirements.txt
```

## Technologies

* Python
* PyTorch
* Torchvision
* Matplotlib
* NumPy
