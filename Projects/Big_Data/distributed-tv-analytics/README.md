# Distributed TV Analytics

Big Data analytics project using MapReduce and PySpark
for large-scale TV program analysis.

## Overview

This project analyzes large television broadcast datasets
using distributed computing techniques and parallel data processing.

The project includes:
- MapReduce jobs using MRJob
- PySpark DataFrame analysis
- Distributed filtering and aggregation
- Ranking and scoring pipelines

The dataset contains TV program information such as:
- titles
- genres
- airing dates and times
- durations
- program identifiers

---

## Technologies

- Python
- MRJob
- PySpark
- Spark DataFrames
- Distributed Computing

---

## Project Structure

```text
distributed-tv-analytics/
│
├── mapreduce/
│   ├── approved-programs.py
│   └── best-approved-program.py
│
├── pyspark/
│   └── tv-program-scoring.ipynb
│
├── docs/
│   └── assignment.pdf
│
├── data/
│   └── sample_data.csv
│
└── README.md