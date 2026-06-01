# Household Viewing & Streaming Analytics

Large-scale Big Data analytics project using PySpark, Spark MLlib,
Window Functions, and Spark Streaming to analyze demographic
household behavior and television viewing patterns.

The project combines machine learning, distributed processing,
real-time analytics, and behavioral clustering techniques
on large-scale television broadcast datasets.

The project extends the television broadcast datasets
introduced in previous distributed analytics assignments.

---

# Overview

This project explores how demographic characteristics
influence television viewing behavior.

The analysis is divided into two major parts:

## 1. Household Clustering Analytics

Uses demographic household information to:
- preprocess and normalize features
- encode categorical attributes
- reduce dimensionality using PCA
- cluster households using KMeans
- analyze viewing preferences across clusters

The goal is to determine whether households with similar
demographic characteristics also exhibit similar viewing habits.

---

## 2. Real-Time Streaming Analytics

Uses Spark Streaming and Kafka to:
- process live TV viewing events
- compute incremental station popularity
- track cluster viewing behavior in real time
- compare clusters dynamically across streaming batches

The streaming pipeline performs distributed aggregations
continuously over incoming data streams.

---

# Technologies

- Python
- PySpark
- Spark MLlib
- Spark SQL
- Spark Streaming
- Kafka
- PCA
- KMeans
- Window Functions
- Distributed Computing
- Databricks

---

# Key Features

## Feature Engineering
- Numerical normalization
- One-hot encoding
- Vector assembly pipelines

## Machine Learning
- PCA dimensionality reduction
- KMeans clustering
- Cluster distance analysis

## Distributed Analytics
- Large-scale joins
- Parallel aggregations
- Diff-rank computations
- Viewing preference analysis

## Streaming Analytics
- Kafka integration
- Incremental aggregation
- Batch-based streaming analysis
- Real-time popularity tracking

---

# Concepts Demonstrated

- Distributed machine learning
- Spark optimization
- Streaming data processing
- Real-time analytics
- Parallel data pipelines
- Window functions
- Feature preprocessing
- Behavioral clustering
- Big Data engineering

---

# Repository Structure

```text
household-viewing-streaming-analytics/
│
├── static-analysis/
│   └── household-clustering.ipynb
│
├── streaming-analysis/
│   └── kafka-streaming-analysis.ipynb
│
├── docs/
│   └── assignment.pdf
│
└── README.md