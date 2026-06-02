# Deep Learning HW4

This repository contains a Deep Convolutional Generative Adversarial Network (DCGAN) implementation for image generation using the Oxford 102 Flowers dataset.

## Project Overview

The project focuses on generative modeling using GANs.

The implemented model learns to generate realistic flower images from randomly sampled latent vectors.

Main components:
- DCGAN Generator
- DCGAN Discriminator
- Adversarial training procedure
- Latent space sampling
- Generated image visualization
- Latent vector analysis

## Dataset

The project uses the Oxford 102 Category Flower Dataset.

Images were resized to 64×64 RGB images for training efficiency.

## Implemented Features

### GAN Architecture
- Deep Convolutional GAN (DCGAN)
- ConvTranspose2d-based generator
- Convolutional discriminator
- Batch normalization
- LeakyReLU activations
- Tanh generator output

### Training
- BCEWithLogitsLoss
- Adam optimizer
- Label smoothing
- Generator and discriminator loss tracking
- Fixed latent noise visualization during training

### Evaluation and Visualization
- Generated image sampling
- Similar and dissimilar latent vector analysis
- L2 distance comparisons in latent space
- Loss curve visualization

## Repository Structure

hw4/
│
├── README.md
├── requirements.txt
├── .gitignore
│
├── src/
│   ├── dcgan_training_flowers.py
│   └── generate_flower_samples.py
│
├── models/
│   └── dcgan_flowers_weights.pkl
│
└── reports/
    └── hw4_report.pdf

## Installation

pip install -r requirements.txt

## Technologies
- Python
- PyTorch
- Torchvision
- NumPy
- Matplotlib
- PIL
- Jupyter Notebook