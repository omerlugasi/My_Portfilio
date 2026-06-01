# Townsville Broadcast Analytics

Large-scale PySpark project for detecting suspicious TV broadcast behavior
and optimizing educational broadcast strategies using demographic
and viewing analytics.

## Overview

This project analyzes television broadcast and demographic datasets
using distributed data processing techniques with PySpark.

The project is divided into two main parts:

### Part 1 — Brainwash Detection

Detects potentially malicious TV programs by analyzing:
- suspicious airing patterns
- genre distributions
- broadcast timing
- viewing behavior indicators

The pipeline applies multiple conditions and flags programs
with abnormal broadcasting characteristics.

### Part 2 — Broadcast Optimization

Builds a distributed analytics pipeline for selecting optimal
broadcast content using:
- demographic information
- household statistics
- DMA regions
- genre popularity
- viewing trends

The project evaluates which broadcast strategy maximizes
audience reach and engagement.

---

## Technologies

- Python
- PySpark
- Spark SQL
- Spark DataFrames
- Distributed Computing
- Databricks

---

## Project Structure

```text
townsville-broadcast-analytics/
│
├── part1-brainwash-detection/
│   └── brainwash-detection.ipynb
│
├── part2-broadcast-optimization/
│   └── broadcast-optimization.ipynb
│
├── docs/
│   └── assignment.pdf
│
└── README.md