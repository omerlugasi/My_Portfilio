# Stock Management System

Data structures and algorithms project focused on efficient stock-price management and indexing.

The project implements two different approaches for managing and querying stock data:
- Treap-based implementation
- 2-3 Tree based implementation

Both implementations support efficient stock insertion, deletion, updates, and range queries while maintaining ordered price indexing.

---

## Project Structure

### treap-version
Implementation based on randomized balanced binary search trees (Treaps).

Main features:
- Treap-based indexing
- Dynamic stock updates
- Price range queries
- Ordered stock retrieval
- Generic map implementation

---

### two-three-tree-version
Implementation based on a custom 2-3 Tree data structure with linked leaves and rank support.

Main features:
- Custom 2-3 Tree implementation
- Generic key abstraction
- Rank queries
- Successor and predecessor operations
- Linked leaf traversal
- Efficient range queries

---

## Supported Operations

Both implementations support:
- Add stock
- Remove stock
- Update stock price
- Remove timestamped updates
- Get current stock price
- Query stocks within price ranges
- Count stocks within price ranges

---

## Technologies Used

- Java
- Custom Data Structures
- Balanced Search Trees
- Object-Oriented Design
- Generic Programming