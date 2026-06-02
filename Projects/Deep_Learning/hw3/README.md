# Deep Learning HW3

This repository contains implementations and experiments in adversarial robustness and contrastive self-supervised learning using PyTorch.

## Project Overview

### Part 1 - CNN Training on SVHN
Trained a ResNet18 classifier on the SVHN (Street View House Numbers) dataset.

Implemented:
- Image normalization and preprocessing
- CNN training using ResNet18
- Confusion matrix evaluation
- Misclassified image analysis
- Accuracy visualization

### Part 2 - Adversarial Attacks
Implemented the FGSM (Fast Gradient Sign Method) adversarial attack on the trained classifier.

Implemented:
- FGSM adversarial perturbations
- Adversarial evaluation pipeline
- Accuracy vs epsilon experiments
- Perturbed image visualization
- Adversarial confusion matrices

### Part 3 - Adversarial Training
Improved model robustness by training on both clean and adversarial examples.

Implemented:
- Adversarial training loop
- Robustness evaluation
- Comparison between standard and adversarial training

### Part 4 - Contrastive Learning with SimCLR
Implemented a SimCLR-style self-supervised contrastive learning framework on Tiny ImageNet.

Implemented:
- ResNet18 encoder
- Projection head
- NT-Xent contrastive loss
- Data augmentations for SimCLR
- Embedding visualization using t-SNE
- Nearest-neighbor retrieval in embedding space

## Repository Structure

hw3/
│
├── README.md
├── requirements.txt
├── .gitignore
│
├── notebooks/
│   └── adversarial_and_contrastive_learning.ipynb
│
├── src/
│   ├── adversarial_training.py
│   └── contrastive_learning.py
│
└── reports/
    └── hw3_report.pdf

## Installation

pip install -r requirements.txt

## Technologies
- Python
- PyTorch
- Torchvision
- NumPy
- Matplotlib
- Scikit-learn
- PIL
- Jupyter Notebook