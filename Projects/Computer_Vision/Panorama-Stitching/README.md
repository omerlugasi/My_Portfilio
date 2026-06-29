# Feature-Based Panorama Stitching using SIFT, Homography and RANSAC

This project implements a classical computer vision pipeline for panorama stitching. The implementation aligns overlapping images using local feature matching, homography estimation, and image warping to generate seamless panoramas.

## Project Overview

The project is organized into three main stages:

### Feature Matching
Detect and match SIFT features between overlapping images to establish reliable point correspondences.

### Homography & Image Warping
Estimate the homography matrix, validate the geometric transformation, and warp images into a common coordinate system.

### Panorama Stitching
Generate panoramic images from multiple datasets and improve robustness using RANSAC-based outlier rejection.

## Main Techniques

- SIFT feature detection and description
- Feature matching
- Homography estimation
- Inverse image warping
- OpenCV `warpPerspective()`
- Panorama stitching
- RANSAC

## Repository Structure

```text
code/
    Jupyter notebook containing the complete implementation.

data/
    Input images and datasets.

output-images/
    Generated feature matches, warping visualizations, and panorama results.

report/
    Final project report.
```

## Installation

```bash
pip install -r requirements.txt
```

Run:

```text
code/notebook.ipynb
```